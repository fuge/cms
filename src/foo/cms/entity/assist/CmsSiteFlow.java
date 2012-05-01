package foo.cms.entity.assist;

import java.io.Serializable;

import foo.cms.entity.main.CmsSite;



public class CmsSiteFlow implements Serializable {
	private static final long serialVersionUID = 2053578517919041736L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsSiteFlow () {
	}

	/**
	 * Constructor for primary key
	 */
	public CmsSiteFlow (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsSiteFlow(Integer id, CmsSite site, String accessIp, String accessDate,
			String accessPage, String sessionId) {
		this.setId(id);
		this.setSite(site);
		this.setAccessIp(accessIp);
		this.setAccessDate(accessDate);
		this.setAccessPage(accessPage);
		this.setSessionId(sessionId);
	}

	/*[CONSTRUCTOR MARKER END]*/
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String accessIp;
	private String accessDate;
	private java.util.Date accessTime;
	private String accessPage;
	private String refererWebSite;
	private String refererPage;
	private String refererKeyword;
	private String area;
	private String sessionId;

	// many to one
	private CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="flow_id"
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
	 * Return the value associated with the column: access_ip
	 */
	public String getAccessIp () {
		return accessIp;
	}

	/**
	 * Set the value related to the column: access_ip
	 * @param accessIp the access_ip value
	 */
	public void setAccessIp (String accessIp) {
		this.accessIp = accessIp;
	}


	/**
	 * Return the value associated with the column: access_date
	 */
	public String getAccessDate () {
		return accessDate;
	}

	/**
	 * Set the value related to the column: access_date
	 * @param accessDate the access_date value
	 */
	public void setAccessDate (String accessDate) {
		this.accessDate = accessDate;
	}


	/**
	 * Return the value associated with the column: access_time
	 */
	public java.util.Date getAccessTime () {
		return accessTime;
	}

	/**
	 * Set the value related to the column: access_time
	 * @param accessTime the access_time value
	 */
	public void setAccessTime (java.util.Date accessTime) {
		this.accessTime = accessTime;
	}


	/**
	 * Return the value associated with the column: access_page
	 */
	public String getAccessPage () {
		return accessPage;
	}

	/**
	 * Set the value related to the column: access_page
	 * @param accessPage the access_page value
	 */
	public void setAccessPage (String accessPage) {
		this.accessPage = accessPage;
	}


	/**
	 * Return the value associated with the column: referer_website
	 */
	public String getRefererWebSite () {
		return refererWebSite;
	}

	/**
	 * Set the value related to the column: referer_website
	 * @param refererWebSite the referer_website value
	 */
	public void setRefererWebSite (String refererWebSite) {
		this.refererWebSite = refererWebSite;
	}


	/**
	 * Return the value associated with the column: referer_page
	 */
	public String getRefererPage () {
		return refererPage;
	}

	/**
	 * Set the value related to the column: referer_page
	 * @param refererPage the referer_page value
	 */
	public void setRefererPage (String refererPage) {
		this.refererPage = refererPage;
	}


	/**
	 * Return the value associated with the column: referer_keyword
	 */
	public String getRefererKeyword () {
		return refererKeyword;
	}

	/**
	 * Set the value related to the column: referer_keyword
	 * @param refererKeyword the referer_keyword value
	 */
	public void setRefererKeyword (String refererKeyword) {
		this.refererKeyword = refererKeyword;
	}


	/**
	 * Return the value associated with the column: area
	 */
	public String getArea () {
		return area;
	}

	/**
	 * Set the value related to the column: area
	 * @param area the area value
	 */
	public void setArea (String area) {
		this.area = area;
	}


	/**
	 * Return the value associated with the column: session_id
	 */
	public String getSessionId () {
		return sessionId;
	}

	/**
	 * Set the value related to the column: session_id
	 * @param sessionId the session_id value
	 */
	public void setSessionId (String sessionId) {
		this.sessionId = sessionId;
	}


	/**
	 * Return the value associated with the column: site_id
	 */
	public CmsSite getSite () {
		return site;
	}

	/**
	 * Set the value related to the column: site_id
	 * @param site the site_id value
	 */
	public void setSite (CmsSite site) {
		this.site = site;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsSiteFlow)) return false;
		else {
			CmsSiteFlow cmsSiteFlow = (CmsSiteFlow) obj;
			if (null == this.getId() || null == cmsSiteFlow.getId()) return false;
			else return (this.getId().equals(cmsSiteFlow.getId()));
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