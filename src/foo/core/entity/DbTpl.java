package foo.core.entity;

import static foo.common.web.Constants.SPT;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import foo.common.web.Constants;
import foo.core.tpl.Tpl;

public class DbTpl implements Tpl, Serializable {
	private static final long serialVersionUID = -7228561486183185979L;

	/**
	 * 获得文件夹或文件的所有父文件夹
	 * 
	 * @param path
	 * @return
	 */
	public static String[] getParentDir(String path) {
		Assert.notNull(path);
		if (!path.startsWith(SPT)) {
			throw new IllegalArgumentException("path must start with /");
		}
		List<String> list = new ArrayList<String>();
		int index = path.indexOf(SPT, 1);
		while (index >= 0) {
			list.add(path.substring(0, index));
			index = path.indexOf(SPT, index + 1);
		}
		String[] arr = new String[list.size()];
		return list.toArray(arr);
	}

	public String getName() {
		return getId();
	}

	public String getPath() {
		String name = getId();
		return getId().substring(0, name.lastIndexOf("/"));
	}

	public String getFilename() {
		String name = getId();
		if (!StringUtils.isBlank(name)) {
			int index = name.lastIndexOf(Constants.SPT);
			if (index != -1) {
				return name.substring(index + 1, name.length());
			}
		}
		return name;
	}

	public long getLength() {
		if (isDirectory() || getSource() == null) {
			return 128;
		} else {
			// 一个英文字符占1个字节，而一个中文则占3-4字节，这里取折中一个字符1.5个字节
			return (long) (getSource().length() * 1.5);
		}
	}

	public int getSize() {
		return (int) (getLength() / 1024) + 1;
	}

	public Date getLastModifiedDate() {
		return new Timestamp(getLastModified());
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public DbTpl () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DbTpl (String id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DbTpl(String id, long lastModified, boolean directory) {
		this.setId(id);
		this.setLastModified(lastModified);
		this.setDirectory(directory);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private String id;

	// fields
	private String source;
	private long lastModified;
	private boolean directory;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="tpl_name"
     */
	public String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: tpl_source
	 */
	public String getSource () {
		return source;
	}

	/**
	 * Set the value related to the column: tpl_source
	 * @param source the tpl_source value
	 */
	public void setSource (String source) {
		this.source = source;
	}


	/**
	 * Return the value associated with the column: last_modified
	 */
	public long getLastModified () {
		return lastModified;
	}

	/**
	 * Set the value related to the column: last_modified
	 * @param lastModified the last_modified value
	 */
	public void setLastModified (long lastModified) {
		this.lastModified = lastModified;
	}


	/**
	 * Return the value associated with the column: is_directory
	 */
	public boolean isDirectory () {
		return directory;
	}

	/**
	 * Set the value related to the column: is_directory
	 * @param directory the is_directory value
	 */
	public void setDirectory (boolean directory) {
		this.directory = directory;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof DbTpl)) return false;
		else {
			DbTpl dbTpl = (DbTpl) obj;
			if (null == this.getId() || null == dbTpl.getId()) return false;
			else return (this.getId().equals(dbTpl.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
}