package foo.cms.entity.main;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

public class ContentExt implements Serializable {
	private static final long serialVersionUID = -3558875759873257603L;

	/**
	 * 如果简短标题为空，则返回标题
	 * 
	 * @return
	 */
	public String getStitle() {
		if (!StringUtils.isBlank(getShortTitle())) {
			return getShortTitle();
		} else {
			return getTitle();
		}
	}

	public void init() {
		if (getReleaseDate() == null) {
			setReleaseDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getBold() == null) {
			setBold(false);
		}
		if(getNeedRegenerate()==null){
			setNeedRegenerate(true);
		}
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getShortTitle())) {
			setShortTitle(null);
		}
		if (StringUtils.isBlank(getAuthor())) {
			setAuthor(null);
		}
		if (StringUtils.isBlank(getOrigin())) {
			setOrigin(null);
		}
		if (StringUtils.isBlank(getOriginUrl())) {
			setOriginUrl(null);
		}
		if (StringUtils.isBlank(getDescription())) {
			setDescription(null);
		}
		if (StringUtils.isBlank(getTitleColor())) {
			setTitleColor(null);
		}
		if (StringUtils.isBlank(getTitleImg())) {
			setTitleImg(null);
		}
		if (StringUtils.isBlank(getContentImg())) {
			setContentImg(null);
		}
		if (StringUtils.isBlank(getTypeImg())) {
			setTypeImg(null);
		}
		if (StringUtils.isBlank(getLink())) {
			setLink(null);
		}
		if (StringUtils.isBlank(getTplContent())) {
			setTplContent(null);
		}
		if (StringUtils.isBlank(getMediaPath())) {
			setMediaPath(null);
		}
		if (StringUtils.isBlank(getMediaType())) {
			setMediaType(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentExt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ContentExt (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ContentExt(Integer id, String title, java.util.Date releaseDate, Boolean bold,
			Boolean needRegenerate) {
		this.setId(id);
		this.setTitle(title);
		this.setReleaseDate(releaseDate);
		this.setBold(bold);
		this.setNeedRegenerate(needRegenerate);
	}

	/* [CONSTRUCTOR MARKER END] */

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String title;
	private String shortTitle;
	private String description;
	private String author;
	private String origin;
	private String originUrl;
	private java.util.Date releaseDate;
	private String mediaPath;
	private String mediaType;
	private String titleColor;
	private Boolean bold;
	private String titleImg;
	private String contentImg;
	private String typeImg;
	private String link;
	private String tplContent;
	private Boolean needRegenerate;

	// one to one
	private foo.cms.entity.main.Content content;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="content_id"
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
	 * Return the value associated with the column: title
	 */
	public String getTitle () {
		return title;
	}

	/**
	 * Set the value related to the column: title
	 * @param title the title value
	 */
	public void setTitle (String title) {
		this.title = title;
	}


	/**
	 * Return the value associated with the column: short_title
	 */
	public String getShortTitle () {
		return shortTitle;
	}

	/**
	 * Set the value related to the column: short_title
	 * @param shortTitle the short_title value
	 */
	public void setShortTitle (String shortTitle) {
		this.shortTitle = shortTitle;
	}


	/**
	 * Return the value associated with the column: description
	 */
	public String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (String description) {
		this.description = description;
	}


	/**
	 * Return the value associated with the column: author
	 */
	public String getAuthor () {
		return author;
	}

	/**
	 * Set the value related to the column: author
	 * @param author the author value
	 */
	public void setAuthor (String author) {
		this.author = author;
	}


	/**
	 * Return the value associated with the column: origin
	 */
	public String getOrigin () {
		return origin;
	}

	/**
	 * Set the value related to the column: origin
	 * @param origin the origin value
	 */
	public void setOrigin (String origin) {
		this.origin = origin;
	}


	/**
	 * Return the value associated with the column: origin_url
	 */
	public String getOriginUrl () {
		return originUrl;
	}

	/**
	 * Set the value related to the column: origin_url
	 * @param originUrl the origin_url value
	 */
	public void setOriginUrl (String originUrl) {
		this.originUrl = originUrl;
	}


	/**
	 * Return the value associated with the column: release_date
	 */
	public java.util.Date getReleaseDate () {
		return releaseDate;
	}

	/**
	 * Set the value related to the column: release_date
	 * @param releaseDate the release_date value
	 */
	public void setReleaseDate (java.util.Date releaseDate) {
		this.releaseDate = releaseDate;
	}


	/**
	 * Return the value associated with the column: media_path
	 */
	public String getMediaPath () {
		return mediaPath;
	}

	/**
	 * Set the value related to the column: media_path
	 * @param mediaPath the media_path value
	 */
	public void setMediaPath (String mediaPath) {
		this.mediaPath = mediaPath;
	}


	/**
	 * Return the value associated with the column: media_type
	 */
	public String getMediaType () {
		return mediaType;
	}

	/**
	 * Set the value related to the column: media_type
	 * @param mediaType the media_type value
	 */
	public void setMediaType (String mediaType) {
		this.mediaType = mediaType;
	}


	/**
	 * Return the value associated with the column: title_color
	 */
	public String getTitleColor () {
		return titleColor;
	}

	/**
	 * Set the value related to the column: title_color
	 * @param titleColor the title_color value
	 */
	public void setTitleColor (String titleColor) {
		this.titleColor = titleColor;
	}


	/**
	 * Return the value associated with the column: is_bold
	 */
	public Boolean getBold () {
		return bold;
	}

	/**
	 * Set the value related to the column: is_bold
	 * @param bold the is_bold value
	 */
	public void setBold (Boolean bold) {
		this.bold = bold;
	}


	/**
	 * Return the value associated with the column: title_img
	 */
	public String getTitleImg () {
		return titleImg;
	}

	/**
	 * Set the value related to the column: title_img
	 * @param titleImg the title_img value
	 */
	public void setTitleImg (String titleImg) {
		this.titleImg = titleImg;
	}


	/**
	 * Return the value associated with the column: content_img
	 */
	public String getContentImg () {
		return contentImg;
	}

	/**
	 * Set the value related to the column: content_img
	 * @param contentImg the content_img value
	 */
	public void setContentImg (String contentImg) {
		this.contentImg = contentImg;
	}


	/**
	 * Return the value associated with the column: type_img
	 */
	public String getTypeImg () {
		return typeImg;
	}

	/**
	 * Set the value related to the column: type_img
	 * @param typeImg the type_img value
	 */
	public void setTypeImg (String typeImg) {
		this.typeImg = typeImg;
	}


	/**
	 * Return the value associated with the column: link
	 */
	public String getLink () {
		return link;
	}

	/**
	 * Set the value related to the column: link
	 * @param link the link value
	 */
	public void setLink (String link) {
		this.link = link;
	}


	/**
	 * Return the value associated with the column: tpl_content
	 */
	public String getTplContent () {
		return tplContent;
	}

	/**
	 * Set the value related to the column: tpl_content
	 * @param tplContent the tpl_content value
	 */
	public void setTplContent (String tplContent) {
		this.tplContent = tplContent;
	}


	/**
	 * Return the value associated with the column: need_regenerate
	 */
	public Boolean getNeedRegenerate () {
		return needRegenerate;
	}

	/**
	 * Set the value related to the column: need_regenerate
	 * @param needRegenerate the need_regenerate value
	 */
	public void setNeedRegenerate (Boolean needRegenerate) {
		this.needRegenerate = needRegenerate;
	}


	/**
	 * Return the value associated with the column: content
	 */
	public foo.cms.entity.main.Content getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (foo.cms.entity.main.Content content) {
		this.content = content;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.ContentExt)) return false;
		else {
			foo.cms.entity.main.ContentExt contentExt = (foo.cms.entity.main.ContentExt) obj;
			if (null == this.getId() || null == contentExt.getId()) return false;
			else return (this.getId().equals(contentExt.getId()));
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