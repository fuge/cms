package foo.core.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.common.upload.UploadUtils;
import foo.core.dao.DbFileDao;
import foo.core.entity.DbFile;
import foo.core.manager.DbFileMng;

@Service
@Transactional
public class DbFileMngImpl implements DbFileMng {
	@Transactional(readOnly = true)
	public DbFile findById(String id) {
		DbFile entity = dao.findById(id);
		return entity;
	}

	public String storeByExt(String path, String ext, InputStream in)
			throws IOException {
		String filename;
		DbFile file;
		// 判断文件是否存在
		do {
			filename = UploadUtils.generateFilename(path, ext);
			file = findById(filename);
		} while (file != null);
		save(filename, in);
		return filename;
	}

	public String storeByFilename(String filename, InputStream in)
			throws IOException {
		DbFile file = findById(filename);
		if (file != null) {
			update(file, in);
		} else {
			save(filename, in);
		}
		return filename;
	}

	public File retrieve(String name) throws IOException {
		// 此方法依赖于文件名是唯一的，否则有可能出现问题。
		String path = System.getProperty("java.io.tmpdir");
		File file = new File(path, name);
		file = UploadUtils.getUniqueFile(file);
		DbFile df = findById(name);
		FileUtils.writeByteArrayToFile(file, df.getContent());
		return file;
	}

	public boolean restore(String name, File file)
			throws FileNotFoundException, IOException {
		storeByFilename(name, new FileInputStream(file));
		file.deleteOnExit();
		return true;
	}

	private DbFile update(DbFile file, InputStream in) throws IOException {
		byte[] content = IOUtils.toByteArray(in);
		file.setContent(content);
		file.setLastModified(System.currentTimeMillis());
		file.setLength(content.length);
		in.close();
		return file;
	}

	private DbFile save(String filename, InputStream in) throws IOException {
		byte[] content = IOUtils.toByteArray(in);
		DbFile file = new DbFile(filename, content.length, System
				.currentTimeMillis(), content);
		dao.save(file);
		in.close();
		return file;
	}

	public DbFile deleteById(String id) {
		DbFile bean = dao.deleteById(id);
		return bean;
	}

	public DbFile[] deleteByIds(String[] ids) {
		DbFile[] beans = new DbFile[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private DbFileDao dao;

	@Autowired
	public void setDao(DbFileDao dao) {
		this.dao = dao;
	}
}