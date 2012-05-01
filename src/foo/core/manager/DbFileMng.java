package foo.core.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import foo.core.entity.DbFile;

public interface DbFileMng {
	public DbFile findById(String id);

	public String storeByExt(String path, String ext, InputStream in)
			throws IOException;

	public String storeByFilename(String filename, InputStream in)
			throws IOException;

	public File retrieve(String name) throws IOException;

	public boolean restore(String name, File file)
			throws FileNotFoundException, IOException;

	public DbFile deleteById(String id);

	public DbFile[] deleteByIds(String[] ids);
}