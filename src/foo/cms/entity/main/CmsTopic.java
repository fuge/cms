package foo.cms.entity.main;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

public class CmsTopic implements Serializable {
	private static final long serialVersionUID = 4863480326466308208L;


	/**
	 * 获得简短名称，如果不存在则返回名称
	 * 
	 * @return
	 */
	public String getSname() {
		if (!StringUtils.isBlank(getShortName())) {
			return getShortName();
		} else {
			return getName();
		}
	}

	public void init() {
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getTitleImg())) {
			setTitleImg(null);
		}
		if (StringUtils.isBlank(getContentImg())) {
			setContentImg(null);
		}
		if (StringUtils.isBlank(getShortName())) {
			setShortName(null);
		}
	}

	/**
	 * 从集合中获取ID数组
	 * 
	 * @param topics
	 * @return
	 */
	public static Integer[] fetchIds(Collection<CmsTopic> topics) {
		Integer[] ids = new Integer[topics.size()];
		int i = 0;
		for (CmsTopic g : topics) {
			ids[i++] = g.getId();
		}
		return ids;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsTopic () {
		super();
	}

	/** Constructor for primary key */
	public CmsTopic (Integer id) {
		this.setId(id);
	}

	/** Constructor for required fields */
	public CmsTopic(Integer id, String name, Integer priority, Boolean recommend) {
		this.setId(id);
		this.setName(name);
		this.setPriority(priority);
		this.setRecommend(recommend);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name;
	private String shortName;
	private String keywords;
	private String description;
	private String titleImg;
	private String contentImg;
	private String tplContent;
	private Integer priority;
	private Boolean recommend;

	// many to one
	private foo.cms.entity.main.Channel channel;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="topic_id"
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
	 * Return the value associated with the column: topic_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: topic_name
	 * @param name the topic_name value
	 */
	public void setName (String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: short_name
	 */
	public String getShortName () {
		return shortName;
	}

	/**
	 * Set the value related to the column: short_name
	 * @param shortName the short_name value
	 */
	public void setShortName (String shortName) {
		this.shortName = shortName;
	}


	/**
	 * Return the value associated with the column: keywords
	 */
	public String getKeywords () {
		return keywords;
	}

	/**
	 * Set the value related to the column: keywords
	 * @param keywords the keywords value
	 */
	public void setKeywords (String keywords) {
		this.keywords = keywords;
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
	 * Return the value associated with the column: priority
	 */
	public Integer getPriority () {
		return priority;
	}

	/**
	 * Set the value related to the column: priority
	 * @param priority the priority value
	 */
	public void setPriority (Integer priority) {
		this.priority = priority;
	}


	/**
	 * Return the value associated with the column: is_recommend
	 */
	public Boolean getRecommend () {
		return recommend;
	}

	/**
	 * Set the value related to the column: is_recommend
	 * @param recommend the is_recommend value
	 */
	public void setRecommend (Boolean recommend) {
		this.recommend = recommend;
	}


	/**
	 * Return the value associated with the column: channel_id
	 */
	public foo.cms.entity.main.Channel getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: channel_id
	 * @param channel the channel_id value
	 */
	public void setChannel (foo.cms.entity.main.Channel channel) {
		this.channel = channel;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.CmsTopic)) return false;
		else {
			foo.cms.entity.main.CmsTopic cmsTopic = (foo.cms.entity.main.CmsTopic) obj;
			if (null == this.getId() || null == cmsTopic.getId()) return false;
			else return (this.getId().equals(cmsTopic.getId()));
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