package foo.cms.entity.assist;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import foo.cms.entity.main.CmsSite;

public class CmsFriendlink implements Serializable {
	private static final long serialVersionUID = -959690464386611384L;



	public void init() {
		if (getPriority() == null) {
			setPriority(10);
		}
		if (getViews() == null) {
			setViews(0);
		}
		if (getEnabled() == null) {
			setEnabled(true);
		}
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getLogo())) {
			setLogo(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsFriendlink() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsFriendlink(Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsFriendlink(Integer id, CmsFriendlinkCtg category,
			CmsSite site, String name, String domain, Integer views,
			Integer priority, Boolean enabled) {
		this.setId(id);
		this.setCategory(category);
		this.setSite(site);
		this.setName(name);
		this.setDomain(domain);
		this.setViews(views);
		this.setPriority(priority);
		this.setEnabled(enabled);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name;
	private String domain;
	private String logo;
	private String email;
	private String description;
	private Integer views;
	private Integer priority;
	private Boolean enabled;

	// many to one
	private CmsFriendlinkCtg category;
	private foo.cms.entity.main.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="friendlink_id"
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
	 * Return the value associated with the column: site_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: site_name
	 * @param name the site_name value
	 */
	public void setName (String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: domain
	 */
	public String getDomain () {
		return domain;
	}

	/**
	 * Set the value related to the column: domain
	 * @param domain the domain value
	 */
	public void setDomain (String domain) {
		this.domain = domain;
	}


	/**
	 * Return the value associated with the column: logo
	 */
	public String getLogo () {
		return logo;
	}

	/**
	 * Set the value related to the column: logo
	 * @param logo the logo value
	 */
	public void setLogo (String logo) {
		this.logo = logo;
	}


	/**
	 * Return the value associated with the column: email
	 */
	public String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (String email) {
		this.email = email;
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
	 * Return the value associated with the column: views
	 */
	public Integer getViews () {
		return views;
	}

	/**
	 * Set the value related to the column: views
	 * @param views the views value
	 */
	public void setViews (Integer views) {
		this.views = views;
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
	 * Return the value associated with the column: is_enabled
	 */
	public Boolean getEnabled () {
		return enabled;
	}

	/**
	 * Set the value related to the column: is_enabled
	 * @param enabled the is_enabled value
	 */
	public void setEnabled (Boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * Return the value associated with the column: friendlinkctg_id
	 */
	public CmsFriendlinkCtg getCategory () {
		return category;
	}

	/**
	 * Set the value related to the column: friendlinkctg_id
	 * @param category the friendlinkctg_id value
	 */
	public void setCategory (CmsFriendlinkCtg category) {
		this.category = category;
	}


	/**
	 * Return the value associated with the column: site_id
	 */
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
		if (!(obj instanceof CmsFriendlink)) return false;
		else {
			CmsFriendlink cmsFriendlink = (CmsFriendlink) obj;
			if (null == this.getId() || null == cmsFriendlink.getId()) return false;
			else return (this.getId().equals(cmsFriendlink.getId()));
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