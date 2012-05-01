package foo.core.entity;

import java.io.Serializable;



public class DbFile implements Serializable {
	private static final long serialVersionUID = 1510153273530206272L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public DbFile () {
	}

	/**
	 * Constructor for primary key
	 */
	public DbFile (String id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DbFile (
		String id,
		Integer length,
		Long lastModified,
		byte[] content) {
		this.setId(id);
		this.setLength(length);
		this.setLastModified(lastModified);
		this.setContent(content);
	}

	/*[CONSTRUCTOR MARKER END]*/
	public static String REF = "DbFile";
	public static String PROP_LAST_MODIFIED = "lastModified";
	public static String PROP_LENGTH = "length";
	public static String PROP_CONTENT = "content";
	public static String PROP_ID = "id";

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private String id;

	// fields
	private Integer length;
	private Long lastModified;
	private byte[] content;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="filename"
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
	 * Return the value associated with the column: length
	 */
	public Integer getLength () {
		return length;
	}

	/**
	 * Set the value related to the column: length
	 * @param length the length value
	 */
	public void setLength (Integer length) {
		this.length = length;
	}


	/**
	 * Return the value associated with the column: last_modified
	 */
	public Long getLastModified () {
		return lastModified;
	}

	/**
	 * Set the value related to the column: last_modified
	 * @param lastModified the last_modified value
	 */
	public void setLastModified (Long lastModified) {
		this.lastModified = lastModified;
	}


	/**
	 * Return the value associated with the column: content
	 */
	public byte[] getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (byte[] content) {
		this.content = content;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof DbFile)) return false;
		else {
			DbFile dbFile = (DbFile) obj;
			if (null == this.getId() || null == dbFile.getId()) return false;
			else return (this.getId().equals(dbFile.getId()));
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