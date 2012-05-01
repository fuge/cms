package foo.cms.entity.main;

import java.io.Serializable;



public class ContentPicture implements Serializable {
	private static final long serialVersionUID = 1364290002741501505L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public ContentPicture () {
		super();
	}

	/**
	 * Constructor for required fields
	 */
	public ContentPicture(java.lang.String imgPath) {
		this.setImgPath(imgPath);
	}

	/*[CONSTRUCTOR MARKER END]*/

	// fields
	private java.lang.String imgPath;
	private java.lang.String description;

	/**
	 * Return the value associated with the column: img_path
	 */
	public java.lang.String getImgPath () {
		return imgPath;
	}

	/**
	 * Set the value related to the column: img_path
	 * @param imgPath the img_path value
	 */
	public void setImgPath (java.lang.String imgPath) {
		this.imgPath = imgPath;
	}


	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}
}