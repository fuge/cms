package foo.core.manager.impl;

import static foo.common.web.Constants.SPT;
import static foo.common.web.Constants.UTF8;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import foo.common.web.Constants;
import foo.core.dao.DbTplDao;
import foo.core.entity.DbTpl;
import foo.core.tpl.ParentDirIsFileExceptioin;
import foo.core.tpl.Tpl;
import foo.core.tpl.TplManager;
import freemarker.cache.TemplateLoader;

@Service
@Transactional
public class DbTplMngImpl implements TemplateLoader, TplManager {
	private static final Logger log = LoggerFactory
			.getLogger(DbTplMngImpl.class);

	/**
	 * @see TemplateLoader#findTemplateSource(String)
	 */
	public Object findTemplateSource(String name) throws IOException {
		for (String ignore : ignoreLocales) {
			if (name.indexOf(ignore) != -1) {
				log.debug("templete ignore: {}", name);
				return null;
			}
		}
		name = "/" + name;
		Tpl tpl = get(name);
		if (tpl == null) {
			log.debug("templete not found: {}", name);
			return null;
		} else if (tpl.isDirectory()) {
			log.warn("template is a directory,not a file!");
			return null;
		} else {
			log.debug("templete loaded: {}", name);
			return tpl;
		}
	}

	/**
	 * @see TemplateLoader#getLastModified(Object)
	 */
	public long getLastModified(Object templateSource) {
		return ((DbTpl) templateSource).getLastModified();
	}

	/**
	 * @see TemplateLoader#getReader(Object, String)
	 */
	public Reader getReader(Object templateSource, String encoding)
			throws IOException {
		return new StringReader(((DbTpl) templateSource).getSource());
	}

	/**
	 * @see TemplateLoader#closeTemplateSource(Object)
	 */
	public void closeTemplateSource(Object templateSource) throws IOException {
		// do nothing.
	}

	@Transactional(readOnly = true)
	public Tpl get(String name) {
		DbTpl entity = dao.findById(name);
		return entity;
	}

	public void save(String name, String source, boolean isDirectory) {
		DbTpl bean = new DbTpl();
		bean.setId(name);
		if (!isDirectory && source == null) {
			source = "";
		}
		bean.setSource(source);
		bean.setLastModified(System.currentTimeMillis());
		bean.setDirectory(isDirectory);
		dao.save(bean);
		createParentDir(name);
	}

	public void save(String path, MultipartFile file) {
		String name = path + SPT + file.getOriginalFilename();
		try {
			String source = new String(file.getBytes(), UTF8);
			save(name, source, false);
		} catch (UnsupportedEncodingException e) {
			log.error("upload template error!", e);
		} catch (IOException e) {
			log.error("upload template error!", e);
		}
	}

	private void createParentDir(String name) {
		String[] dirs = DbTpl.getParentDir(name);
		DbTpl dirTpl;
		Tpl parentDir;
		for (String dir : dirs) {
			parentDir = get(dir);
			if (parentDir != null && !parentDir.isDirectory()) {
				throw new ParentDirIsFileExceptioin(
						"parent directory is a file: " + parentDir.getName());
			} else if (parentDir == null) {
				dirTpl = new DbTpl();
				dirTpl.setId(dir);
				dirTpl.setDirectory(true);
				dao.save(dirTpl);
			}
		}
	}

	public void update(String name, String source) {
		DbTpl entity = (DbTpl) get(name);
		entity.setSource(source);
		entity.setLastModified(System.currentTimeMillis());
	}

	public int delete(String[] names) {
		int count = 0;
		DbTpl tpl;
		for (String name : names) {
			tpl = dao.deleteById(name);
			count++;
			if (tpl.isDirectory()) {
				count += deleteByDir(tpl.getName());
			}
		}
		return names.length;
	}

	private int deleteByDir(String dir) {
		dir += Constants.SPT;
		List<? extends Tpl> list = getListByPrefix(dir);
		for (Tpl tpl : list) {
			dao.deleteById(tpl.getName());
		}
		return list.size();
	}

	public List<? extends Tpl> getListByPrefix(String prefix) {
		return dao.getStartWith(prefix);
	}

	public List<String> getNameListByPrefix(String prefix) {
		// 有可能要在第一位插入一个元素
		List<String> list = new LinkedList<String>();
		for (Tpl tpl : getListByPrefix(prefix)) {
			list.add(tpl.getName());
		}
		return list;
	}

	public List<? extends Tpl> getChild(String path) {
		List<DbTpl> dirs = dao.getChild(path, true);
		List<DbTpl> files = dao.getChild(path, false);
		dirs.addAll(files);
		return dirs;
	}

	public void rename(String orig, String dist) {
		DbTpl tpl = dao.deleteById(orig);
		if (tpl == null) {
			return;
		}
		dao.deleteById(orig);
		String name = StringUtils.replace(tpl.getId(), orig, dist, 1);
		save(name, tpl.getSource(), tpl.isDirectory());
		createParentDir(name);
		if (tpl.isDirectory()) {
			List<DbTpl> list = dao.getStartWith(orig + "/");
			for (DbTpl t : list) {
				dao.deleteById(t.getId());
				name = StringUtils.replace(t.getId(), orig, dist, 1);
				save(name, t.getSource(), t.isDirectory());
			}
		}
	}

	private String[] ignoreLocales = { "_zh_CN.", "_zh.", "_en_US.", "_en." };

	public void setIgnoreLocales(String[] ignoreLocales) {
		this.ignoreLocales = ignoreLocales;
	}

	private DbTplDao dao;

	@Autowired
	public void setDao(DbTplDao dao) {
		this.dao = dao;
	}

}