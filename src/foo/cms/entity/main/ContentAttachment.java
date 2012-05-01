package foo.cms.entity.main;

import java.io.Serializable;

public class ContentAttachment implements Serializable {
	private static final long serialVersionUID = 3826509857356768413L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentAttachment () {
	}

	/** Constructor for required fields */
	public ContentAttachment(String path, String name, Integer count) {
		this.setPath(path);
		this.setName(name);
		this.setCount(count);
	}

	/* [CONSTRUCTOR MARKER END] */

	// fields
	private String path;
	private String name;
	private String filename;
	private Integer count;

	/**
	 * Return the value associated with the column: attachment_path
	 */
	public String getPath () {
		return path;
	}

	/**
	 * Set the value related to the column: attachment_path
	 * @param path the attachment_path value
	 */
	public void setPath (String path) {
		this.path = path;
	}


	/**
	 * Return the value associated with the column: attachment_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: attachment_name
	 * @param name the attachment_name value
	 */
	public void setName (String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: filename
	 */
	public String getFilename () {
		return filename;
	}

	/**
	 * Set the value related to the column: filename
	 * @param filename the filename value
	 */
	public void setFilename (String filename) {
		this.filename = filename;
	}


	/**
	 * Return the value associated with the column: download_count
	 */
	public Integer getCount () {
		return count;
	}

	/**
	 * Set the value related to the column: download_count
	 * @param count the download_count value
	 */
	public void setCount (Integer count) {
		this.count = count;
	}

}