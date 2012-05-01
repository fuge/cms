package foo.common.file;

import static foo.common.web.Constants.SPT;

import java.io.File;
import java.io.FileFilter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;

import foo.common.image.ImageUtils;

/**
 * 文件包装类
 * 
 * 用于前台显示文件信息
 * 
 * @author liufang
 * 
 */
public class FileWrap {
	/**
	 * 可编辑的后缀名
	 */
	public static final String[] EDITABLE_EXT = new String[] { "html", "htm",
			"css", "js", "txt" };
	private File file;
	private String rootPath;
	private FileFilter filter;
	private List<FileWrap> child;
	private String filename;

	/**
	 * 构造器
	 * 
	 * @param file
	 *            待包装的文件
	 */
	public FileWrap(File file) {
		this(file, null);
	}

	/**
	 * 构造器
	 * 
	 * @param file
	 *            待包装的文件
	 * @param rootPath
	 *            根路径，用于计算相对路径
	 */
	public FileWrap(File file, String rootPath) {
		this(file, rootPath, null);
	}

	/**
	 * 构造器
	 * 
	 * @param file
	 *            待包装的文件
	 * @param rootPath
	 *            根路径，用于计算相对路径
	 * @param filter
	 *            文件过滤器，应用于所有子文件
	 */
	public FileWrap(File file, String rootPath, FileFilter filter) {
		this.file = file;
		this.rootPath = rootPath;
		this.filter = filter;
	}

	/**
	 * 是否允许编辑
	 * 
	 * @param ext
	 *            文件扩展名
	 * @return
	 */
	public static boolean editableExt(String ext) {
		ext = ext.toLowerCase(Locale.ENGLISH);
		for (String s : EDITABLE_EXT) {
			if (s.equals(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得完整文件名，根据根路径(rootPath)。
	 * 
	 * @return
	 */
	public String getName() {
		String path = file.getAbsolutePath();
		String relPath = path.substring(rootPath.length());
		return relPath.replace(File.separator, SPT);
	}

	/**
	 * 获得文件路径，不包含文件名的路径。
	 * 
	 * @return
	 */
	public String getPath() {
		String name = getName();
		return name.substring(0, name.lastIndexOf('/'));
	}

	/**
	 * 获得文件名，不包含路径的文件名。
	 * 
	 * 如没有指定名称，则返回文件自身的名称。
	 * 
	 * @return
	 */
	public String getFilename() {
		return filename != null ? filename : file.getName();
	}

	/**
	 * 扩展名
	 * 
	 * @return
	 */
	public String getExtension() {
		return FilenameUtils.getExtension(file.getName());
	}

	/**
	 * 最后修改时间。长整型(long)。
	 * 
	 * @return
	 */
	public long getLastModified() {
		return file.lastModified();
	}

	/**
	 * 最后修改时间。日期型(Timestamp)。
	 * 
	 * @return
	 */
	public Date getLastModifiedDate() {
		return new Timestamp(file.lastModified());
	}

	/**
	 * 文件大小，单位KB。
	 * 
	 * @return
	 */
	public long getSize() {
		return file.length() / 1024 + 1;
	}

	/**
	 * 获得文件的图标名称
	 * <ul>
	 * <li>directory = folder</li>
	 * <li>jpg,jpeg = jpg</li>
	 * <li>gif = gif</li>
	 * <li>html,htm = html</li>
	 * <li>swf = swf</li>
	 * <li>txt = txt</li>
	 * <li>其他 = unknow</li>
	 * </ul>
	 * 
	 * @return
	 */
	public String getIco() {
		if (file.isDirectory()) {
			return "folder";
		}
		String ext = getExtension().toLowerCase();
		if (ext.equals("jpg") || ext.equals("jpeg")) {
			return "jpg";
		} else if (ext.equals("png")) {
			return "png";
		} else if (ext.equals("gif")) {
			return "gif";
		} else if (ext.equals("html") || ext.equals("htm")) {
			return "html";
		} else if (ext.equals("swf")) {
			return "swf";
		} else if (ext.equals("txt")) {
			return "txt";
		} else {
			return "unknow";
		}
	}

	/**
	 * 获得下级目录
	 * 
	 * 如果没有指定下级目录，则返回文件夹自身的下级文件，并运用FileFilter。
	 * 
	 * @return
	 */
	public List<FileWrap> getChild() {
		if (this.child != null) {
			return this.child;
		}
		File[] files;
		if (filter == null) {
			files = getFile().listFiles();
		} else {
			files = getFile().listFiles(filter);
		}
		List<FileWrap> list = new ArrayList<FileWrap>();
		if (files != null) {
			Arrays.sort(files, new FileComparator());
			for (File f : files) {
				FileWrap fw = new FileWrap(f, rootPath, filter);
				list.add(fw);
			}
		}
		return list;
	}

	/**
	 * 获得被包装的文件
	 * 
	 * @return
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * 是否图片
	 * 
	 * @return
	 */
	public boolean isImage() {
		if (isDirectory()) {
			return false;
		}
		String ext = getExtension();
		return ImageUtils.isValidImageExt(ext);
	}

	/**
	 * 是否可编辑
	 * 
	 * @return
	 */
	public boolean isEditable() {
		if (isDirectory()) {
			return false;
		}
		String ext = getExtension().toLowerCase();
		for (String s : EDITABLE_EXT) {
			if (s.equals(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否目录
	 * 
	 * @return
	 */
	public boolean isDirectory() {
		return file.isDirectory();
	}

	/**
	 * 是否文件
	 * 
	 * @return
	 */
	public boolean isFile() {
		return file.isFile();
	}

	/**
	 * 设置待包装的文件
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 指定名称
	 * 
	 * @param name
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * 指定下级目录
	 * 
	 * @param child
	 */
	public void setChild(List<FileWrap> child) {
		this.child = child;
	}

	/**
	 * 文件比较器，文件夹靠前排。
	 * 
	 * @author liufang
	 * 
	 */
	public static class FileComparator implements Comparator<File> {
		public int compare(File o1, File o2) {
			if (o1.isDirectory() && !o2.isDirectory()) {
				return -1;
			} else if (!o1.isDirectory() && o2.isDirectory()) {
				return 1;
			} else {
				return o1.compareTo(o2);
			}
		}
	}
}
