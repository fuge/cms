package foo.cms.entity.main;

import java.io.Serializable;
import java.util.Date;

public class CmsLog implements Serializable {
	private static final long serialVersionUID = -8971662872490662548L;
	public static final int LOGIN_SUCCESS = 1;
	public static final int LOGIN_FAILURE = 2;
	public static final int OPERATING = 3;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsLog () {
	}

	/** Constructor for primary key */
	public CmsLog (java.lang.Integer id) {
		this.setId(id);
	}

	/** Constructor for required fields */
	public CmsLog(Integer id, Integer category, Date time) {
		this.setId(id);
		this.setCategory(category);
		this.setTime(time);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer category;
	private java.util.Date time;
	private java.lang.String ip;
	private java.lang.String url;
	private java.lang.String title;
	private java.lang.String content;

	// many to one
	private foo.cms.entity.main.CmsUser user;
	private foo.cms.entity.main.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="log_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/** Return the value associated with the column: category */
	public java.lang.Integer getCategory () {
		return category;
	}

	/**
	 * Set the value related to the column: category
	 * @param category the category value
	 */
	public void setCategory (java.lang.Integer category) {
		this.category = category;
	}


	/** Return the value associated with the column: log_time */
	public java.util.Date getTime () {
		return time;
	}

	/**
	 * Set the value related to the column: log_time
	 * @param time the log_time value
	 */
	public void setTime (java.util.Date time) {
		this.time = time;
	}


	/** Return the value associated with the column: ip */
	public java.lang.String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: ip
	 * @param ip the ip value
	 */
	public void setIp (java.lang.String ip) {
		this.ip = ip;
	}


	/** Return the value associated with the column: url */
	public java.lang.String getUrl () {
		return url;
	}

	/**
	 * Set the value related to the column: url
	 * @param url the url value
	 */
	public void setUrl (java.lang.String url) {
		this.url = url;
	}


	/** Return the value associated with the column: title */
	public java.lang.String getTitle () {
		return title;
	}

	/**
	 * Set the value related to the column: title
	 * @param title the title value
	 */
	public void setTitle (java.lang.String title) {
		this.title = title;
	}


	/** Return the value associated with the column: content */
	public java.lang.String getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (java.lang.String content) {
		this.content = content;
	}


	/** Return the value associated with the column: user_id */
	public foo.cms.entity.main.CmsUser getUser () {
		return user;
	}

	/**
	 * Set the value related to the column: user_id
	 * @param user the user_id value
	 */
	public void setUser (foo.cms.entity.main.CmsUser user) {
		this.user = user;
	}


	/** Return the value associated with the column: site_id */
	public foo.cms.entity.main.CmsSite getSite () {
		return site;
	}

	/**
	 * Set the value related to the column: site_id
	 * @param site the site_id value
	 */
	public void setSite (foo.cms.entity.main.CmsSite site) {
		this.site = site;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.CmsLog)) return false;
		else {
			foo.cms.entity.main.CmsLog cmsLog = (foo.cms.entity.main.CmsLog) obj;
			if (null == this.getId() || null == cmsLog.getId()) return false;
			else return (this.getId().equals(cmsLog.getId()));
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