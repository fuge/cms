package foo.cms.entity.main;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class CmsConfig implements Serializable {
	private static final long serialVersionUID = -6018025361688815506L;
	public static final String VERSION = "version";

	public String getVersion() {
		return getAttr().get(VERSION);
	}

	public MemberConfig getMemberConfig() {
		MemberConfig memberConfig = new MemberConfig(getAttr());
		return memberConfig;
	}

	public void setMemberConfig(MemberConfig memberConfig) {
		getAttr().putAll(memberConfig.getAttr());
	}

	public void blankToNull() {
		// oracle varchar2把空串当作null处理，这里为了统一这个特征，特做此处理。
		if (StringUtils.isBlank(getProcessUrl())) {
			setProcessUrl(null);
		}
		if (StringUtils.isBlank(getContextPath())) {
			setContextPath(null);
		}
		if (StringUtils.isBlank(getServletPoint())) {
			setServletPoint(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsConfig() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsConfig(java.lang.Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsConfig(java.lang.Integer id, java.lang.String dbFileUri,
			java.lang.Boolean uploadToDb, java.lang.String defImg,
			java.lang.String loginUrl, java.util.Date countClearTime,
			java.util.Date countCopyTime, java.lang.String downloadCode,
			java.lang.Integer downloadTime) {
		this.setId(id);
		this.setDbFileUri(dbFileUri);
		this.setUploadToDb(uploadToDb);
		this.setDefImg(defImg);
		this.setLoginUrl(loginUrl);
		this.setCountClearTime(countClearTime);
		this.setCountCopyTime(countCopyTime);
		this.setDownloadCode(downloadCode);
		this.setDownloadTime(downloadTime);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String contextPath;
	private java.lang.String servletPoint;
	private java.lang.Integer port;
	private java.lang.String dbFileUri;
	private java.lang.Boolean uploadToDb;
	private java.lang.String defImg;
	private java.lang.String loginUrl;
	private java.lang.String processUrl;
	private java.util.Date countClearTime;
	private java.util.Date countCopyTime;
	private java.lang.String downloadCode;
	private java.lang.Integer downloadTime;

	// components
	 foo.cms.entity.main.MarkConfig m_markConfig;
	 foo.cms.entity.main.EmailConfig m_emailConfig;

	// collections
	private java.util.Map<java.lang.String, java.lang.String> attr;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="config_id"
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

	/**
	 * Return the value associated with the column: context_path
	 */
	public java.lang.String getContextPath () {
		return contextPath;
	}

	/**
	 * Set the value related to the column: context_path
	 * @param contextPath the context_path value
	 */
	public void setContextPath (java.lang.String contextPath) {
		this.contextPath = contextPath;
	}


	/**
	 * Return the value associated with the column: servlet_point
	 */
	public java.lang.String getServletPoint () {
		return servletPoint;
	}

	/**
	 * Set the value related to the column: servlet_point
	 * @param servletPoint the servlet_point value
	 */
	public void setServletPoint (java.lang.String servletPoint) {
		this.servletPoint = servletPoint;
	}


	/** Return the value associated with the column: port */
	public java.lang.Integer getPort () {
		return port;
	}

	/**
	 * Set the value related to the column: port
	 * @param port the port value
	 */
	public void setPort (java.lang.Integer port) {
		this.port = port;
	}

	/** Return the value associated with the column: db_file_uri */
	public java.lang.String getDbFileUri () {
		return dbFileUri;
	}

	/**
	 * Set the value related to the column: db_file_uri
	 * @param dbFileUri the db_file_uri value
	 */
	public void setDbFileUri (java.lang.String dbFileUri) {
		this.dbFileUri = dbFileUri;
	}


	/** Return the value associated with the column: is_upload_to_db */
	public java.lang.Boolean getUploadToDb () {
		return uploadToDb;
	}

	/**
	 * Set the value related to the column: is_upload_to_db
	 * @param uploadToDb the is_upload_to_db value
	 */
	public void setUploadToDb (java.lang.Boolean uploadToDb) {
		this.uploadToDb = uploadToDb;
	}


	/** Return the value associated with the column: def_img */
	public java.lang.String getDefImg () {
		return defImg;
	}

	/**
	 * Set the value related to the column: def_img
	 * @param defImg the def_img value
	 */
	public void setDefImg (java.lang.String defImg) {
		this.defImg = defImg;
	}


	/** Return the value associated with the column: login_url */
	public java.lang.String getLoginUrl () {
		return loginUrl;
	}

	/**
	 * Set the value related to the column: login_url
	 * @param loginUrl the login_url value
	 */
	public void setLoginUrl (java.lang.String loginUrl) {
		this.loginUrl = loginUrl;
	}


	/** Return the value associated with the column: process_url */
	public java.lang.String getProcessUrl () {
		return processUrl;
	}

	/**
	 * Set the value related to the column: process_url
	 * @param processUrl the process_url value
	 */
	public void setProcessUrl (java.lang.String processUrl) {
		this.processUrl = processUrl;
	}


	/** Return the value associated with the column: count_clear_time */
	public java.util.Date getCountClearTime () {
		return countClearTime;
	}

	/**
	 * Set the value related to the column: count_clear_time
	 * @param countClearTime the count_clear_time value
	 */
	public void setCountClearTime (java.util.Date countClearTime) {
		this.countClearTime = countClearTime;
	}


	/** Return the value associated with the column: count_copy_time */
	public java.util.Date getCountCopyTime () {
		return countCopyTime;
	}

	/**
	 * Set the value related to the column: count_copy_time
	 * @param countCopyTime the count_copy_time value
	 */
	public void setCountCopyTime (java.util.Date countCopyTime) {
		this.countCopyTime = countCopyTime;
	}


	/** Return the value associated with the column: download_code */
	public java.lang.String getDownloadCode () {
		return downloadCode;
	}

	/**
	 * Set the value related to the column: download_code
	 * @param downloadCode the download_code value
	 */
	public void setDownloadCode (java.lang.String downloadCode) {
		this.downloadCode = downloadCode;
	}


	/** Return the value associated with the column: download_time */
	public java.lang.Integer getDownloadTime () {
		return downloadTime;
	}

	/**
	 * Set the value related to the column: download_time
	 * @param downloadTime the download_time value
	 */
	public void setDownloadTime (java.lang.Integer downloadTime) {
		this.downloadTime = downloadTime;
	}


	public foo.cms.entity.main.MarkConfig getMarkConfig () {
		return m_markConfig;
	}

	/**
	 * Set the value related to the column: ${prop.Column}
	 * @param m_markConfig the ${prop.Column} value
	 */
	public void setMarkConfig (foo.cms.entity.main.MarkConfig m_markConfig) {
		this.m_markConfig = m_markConfig;
	}


	public foo.cms.entity.main.EmailConfig getEmailConfig () {
		return m_emailConfig;
	}

	/**
	 * Set the value related to the column: ${prop.Column}
	 * @param m_emailConfig the ${prop.Column} value
	 */
	public void setEmailConfig (foo.cms.entity.main.EmailConfig m_emailConfig) {
		this.m_emailConfig = m_emailConfig;
	}


	/** Return the value associated with the column: attr */
	public java.util.Map<java.lang.String, java.lang.String> getAttr () {
		return attr;
	}

	/**
	 * Set the value related to the column: attr
	 * @param attr the attr value
	 */
	public void setAttr (java.util.Map<java.lang.String, java.lang.String> attr) {
		this.attr = attr;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.CmsConfig)) return false;
		else {
			foo.cms.entity.main.CmsConfig cmsConfig = (foo.cms.entity.main.CmsConfig) obj;
			if (null == this.getId() || null == cmsConfig.getId()) return false;
			else return (this.getId().equals(cmsConfig.getId()));
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