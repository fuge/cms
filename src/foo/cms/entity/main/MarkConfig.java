package foo.cms.entity.main;

import java.io.Serializable;



public class MarkConfig implements Serializable{
	private static final long serialVersionUID = 3684171217624666330L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public MarkConfig () {
	}

	/**
	 * Constructor for required fields
	 */
	public MarkConfig(Boolean on, Integer minWidth, Integer minHeight, String content,
			Integer size, String color, Integer alpha, Integer pos, Integer offsetX,
			Integer offsetY) {
		this.setOn(on);
		this.setMinWidth(minWidth);
		this.setMinHeight(minHeight);
		this.setContent(content);
		this.setSize(size);
		this.setColor(color);
		this.setAlpha(alpha);
		this.setPos(pos);
		this.setOffsetX(offsetX);
		this.setOffsetY(offsetY);
	}
	/*[CONSTRUCTOR MARKER END]*/
	// fields
	private Boolean on;
	private Integer minWidth;
	private Integer minHeight;
	private String imagePath;
	private String content;
	private Integer size;
	private String color;
	private Integer alpha;
	private Integer pos;
	private Integer offsetX;
	private Integer offsetY;






	/**
	 * Return the value associated with the column: mark_on
	 */
	public Boolean getOn () {
		return on;
	}

	/**
	 * Set the value related to the column: mark_on
	 * @param on the mark_on value
	 */
	public void setOn (Boolean on) {
		this.on = on;
	}


	/**
	 * Return the value associated with the column: mark_width
	 */
	public Integer getMinWidth () {
		return minWidth;
	}

	/**
	 * Set the value related to the column: mark_width
	 * @param minWidth the mark_width value
	 */
	public void setMinWidth (Integer minWidth) {
		this.minWidth = minWidth;
	}


	/**
	 * Return the value associated with the column: mark_height
	 */
	public Integer getMinHeight () {
		return minHeight;
	}

	/**
	 * Set the value related to the column: mark_height
	 * @param minHeight the mark_height value
	 */
	public void setMinHeight (Integer minHeight) {
		this.minHeight = minHeight;
	}


	/**
	 * Return the value associated with the column: mark_image
	 */
	public String getImagePath () {
		return imagePath;
	}

	/**
	 * Set the value related to the column: mark_image
	 * @param imagePath the mark_image value
	 */
	public void setImagePath (String imagePath) {
		this.imagePath = imagePath;
	}


	/**
	 * Return the value associated with the column: mark_content
	 */
	public String getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: mark_content
	 * @param content the mark_content value
	 */
	public void setContent (String content) {
		this.content = content;
	}


	/**
	 * Return the value associated with the column: mark_size
	 */
	public Integer getSize () {
		return size;
	}

	/**
	 * Set the value related to the column: mark_size
	 * @param size the mark_size value
	 */
	public void setSize (Integer size) {
		this.size = size;
	}


	/**
	 * Return the value associated with the column: mark_color
	 */
	public String getColor () {
		return color;
	}

	/**
	 * Set the value related to the column: mark_color
	 * @param color the mark_color value
	 */
	public void setColor (String color) {
		this.color = color;
	}


	/**
	 * Return the value associated with the column: mark_alpha
	 */
	public Integer getAlpha () {
		return alpha;
	}

	/**
	 * Set the value related to the column: mark_alpha
	 * @param alpha the mark_alpha value
	 */
	public void setAlpha (Integer alpha) {
		this.alpha = alpha;
	}


	/**
	 * Return the value associated with the column: mark_position
	 */
	public Integer getPos () {
		return pos;
	}

	/**
	 * Set the value related to the column: mark_position
	 * @param pos the mark_position value
	 */
	public void setPos (Integer pos) {
		this.pos = pos;
	}


	/**
	 * Return the value associated with the column: mark_offset_x
	 */
	public Integer getOffsetX () {
		return offsetX;
	}

	/**
	 * Set the value related to the column: mark_offset_x
	 * @param offsetX the mark_offset_x value
	 */
	public void setOffsetX (Integer offsetX) {
		this.offsetX = offsetX;
	}


	/**
	 * Return the value associated with the column: mark_offset_y
	 */
	public Integer getOffsetY () {
		return offsetY;
	}

	/**
	 * Set the value related to the column: mark_offset_y
	 * @param offsetY the mark_offset_y value
	 */
	public void setOffsetY (Integer offsetY) {
		this.offsetY = offsetY;
	}
}