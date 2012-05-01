package foo.cms.entity.main;

import java.io.Serializable;

public class ContentType implements Serializable {
	private static final long serialVersionUID = 3316172154773686270L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentType () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ContentType (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ContentType (
		Integer id,
		String name,
		Boolean hasImage,
		Boolean disabled) {
		this.setId(id);
		this.setName(name);
		this.setHasImage(hasImage);
		this.setDisabled(disabled);
	}

	/* [CONSTRUCTOR MARKER END] */
	public static String REF = "ContentType";
	public static String PROP_IMG_HEIGHT = "imgHeight";
	public static String PROP_IMG_WIDTH = "imgWidth";
	public static String PROP_DISABLED = "disabled";
	public static String PROP_NAME = "name";
	public static String PROP_ID = "id";
	public static String PROP_HAS_IMAGE = "hasImage";

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name;
	private Integer imgWidth;
	private Integer imgHeight;
	private Boolean hasImage;
	private Boolean disabled;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="type_id"
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
	 * Return the value associated with the column: type_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: type_name
	 * @param name the type_name value
	 */
	public void setName (String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: img_width
	 */
	public Integer getImgWidth () {
		return imgWidth;
	}

	/**
	 * Set the value related to the column: img_width
	 * @param imgWidth the img_width value
	 */
	public void setImgWidth (Integer imgWidth) {
		this.imgWidth = imgWidth;
	}


	/**
	 * Return the value associated with the column: img_height
	 */
	public Integer getImgHeight () {
		return imgHeight;
	}

	/**
	 * Set the value related to the column: img_height
	 * @param imgHeight the img_height value
	 */
	public void setImgHeight (Integer imgHeight) {
		this.imgHeight = imgHeight;
	}


	/**
	 * Return the value associated with the column: has_image
	 */
	public Boolean getHasImage () {
		return hasImage;
	}

	/**
	 * Set the value related to the column: has_image
	 * @param hasImage the has_image value
	 */
	public void setHasImage (Boolean hasImage) {
		this.hasImage = hasImage;
	}


	/**
	 * Return the value associated with the column: is_disabled
	 */
	public Boolean getDisabled () {
		return disabled;
	}

	/**
	 * Set the value related to the column: is_disabled
	 * @param disabled the is_disabled value
	 */
	public void setDisabled (Boolean disabled) {
		this.disabled = disabled;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.ContentType)) return false;
		else {
			foo.cms.entity.main.ContentType contentType = (foo.cms.entity.main.ContentType) obj;
			if (null == this.getId() || null == contentType.getId()) return false;
			else return (this.getId().equals(contentType.getId()));
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