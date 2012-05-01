package foo.cms.entity.main;

import java.io.Serializable;

public class ContentTag implements Serializable {
	private static final long serialVersionUID = -5685964144161770717L;



	public void init() {
		if (getCount() == null) {
			setCount(1);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentTag () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ContentTag (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ContentTag(Integer id, String name, Integer count) {
		this.setId(id);
		this.setName(name);
		this.setCount(count);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name;
	private Integer count;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="tag_id"
     */
	public Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: tag_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: tag_name
	 * @param name the tag_name value
	 */
	public void setName (String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: ref_counter
	 */
	public Integer getCount () {
		return count;
	}

	/**
	 * Set the value related to the column: ref_counter
	 * @param count the ref_counter value
	 */
	public void setCount (Integer count) {
		this.count = count;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.ContentTag)) return false;
		else {
			foo.cms.entity.main.ContentTag contentTag = (foo.cms.entity.main.ContentTag) obj;
			if (null == this.getId() || null == contentTag.getId()) return false;
			else return (this.getId().equals(contentTag.getId()));
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