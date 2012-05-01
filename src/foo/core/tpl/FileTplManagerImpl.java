package foo.core.tpl;

import static foo.common.web.Constants.UTF8;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import foo.common.web.springmvc.RealPathResolver;

public class FileTplManagerImpl implements TplManager {
	private static Logger log = LoggerFactory
			.getLogger(FileTplManagerImpl.class);

	public int delete(String[] names) {
		File f;
		int count = 0;
		for (String name : names) {
			f = new File(realPathResolver.get(name));
			if (f.isDirectory()) {
				if (FileUtils.deleteQuietly(f)) {
					count++;
				}
			} else {
				if (f.delete()) {
					count++;
				}
			}
		}
		return count;
	}

	public Tpl get(String name) {
		File f = new File(realPathResolver.get(name));
		if (f.exists()) {
			return new FileTpl(f, root);
		} else {
			return null;
		}
	}

	public List<Tpl> getListByPrefix(String prefix) {
		File f = new File(realPathResolver.get(prefix));
		String name = prefix.substring(prefix.lastIndexOf("/") + 1);
		File parent;
		if (prefix.endsWith("/")) {
			name = "";
			parent = f;
		} else {
			parent = f.getParentFile();
		}
		if (parent.exists()) {
			File[] files = parent.listFiles(new PrefixFilter(name));
			if (files != null) {
				List<Tpl> list = new ArrayList<Tpl>();
				for (File file : files) {
					list.add(new FileTpl(file, root));
				}
				return list;
			} else {
				return new ArrayList<Tpl>(0);
			}
		} else {
			return new ArrayList<Tpl>(0);
		}
	}

	public List<String> getNameListByPrefix(String prefix) {
		List<Tpl> list = getListByPrefix(prefix);
		List<String> result = new ArrayList<String>(list.size());
		for (Tpl tpl : list) {
			result.add(tpl.getName());
		}
		return result;
	}

	public List<Tpl> getChild(String path) {
		File file = new File(realPathResolver.get(path));
		File[] child = file.listFiles();
		if (child != null) {
			List<Tpl> list = new ArrayList<Tpl>(child.length);
			for (File f : child) {
				list.add(new FileTpl(f, root));
			}
			return list;
		} else {
			return new ArrayList<Tpl>(0);
		}
	}

	public void rename(String orig, String dist) {
		String os = realPathResolver.get(orig);
		File of = new File(os);
		String ds = realPathResolver.get(dist);
		File df = new File(ds);
		try {
			if (of.isDirectory()) {
				FileUtils.moveDirectory(of, df);
			} else {
				FileUtils.moveFile(of, df);
			}
		} catch (IOException e) {
			log.error("Move template error: " + orig + " to " + dist, e);
		}
	}

	public void save(String name, String source, boolean isDirectory) {
		String real = realPathResolver.get(name);
		File f = new File(real);
		if (isDirectory) {
			f.mkdirs();
		} else {
			try {
				FileUtils.writeStringToFile(f, source, UTF8);
			} catch (IOException e) {
				log.error("Save template error: " + name, e);
				throw new RuntimeException(e);
			}
		}
	}

	public void save(String path, MultipartFile file) {
		File f = new File(realPathResolver.get(path), file
				.getOriginalFilename());
		try {
			file.transferTo(f);
		} catch (IllegalStateException e) {
			log.error("upload template error!", e);
		} catch (IOException e) {
			log.error("upload template error!", e);
		}
	}

	public void update(String name, String source) {
		String real = realPathResolver.get(name);
		File f = new File(real);
		try {
			FileUtils.writeStringToFile(f, source, UTF8);
		} catch (IOException e) {
			log.error("Save template error: " + name, e);
			throw new RuntimeException(e);
		}
	}

	private String root;

	private RealPathResolver realPathResolver;

	@Autowired
	public void setRealPathResolver(RealPathResolver realPathResolver) {
		this.realPathResolver = realPathResolver;
		root = realPathResolver.get("");
	}

	private static class PrefixFilter implements FileFilter {
		private String prefix;

		public PrefixFilter(String prefix) {
			this.prefix = prefix;
		}

		public boolean accept(File file) {
			String name = file.getName();
			return file.isFile() && name.startsWith(prefix);
		}
	}

}
