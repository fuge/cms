package foo.cms.entity.assist;

import java.io.Serializable;

import foo.cms.entity.main.CmsSite;



public class CmsAdvertisingSpace implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = -7018746417936381432L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsAdvertisingSpace () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsAdvertisingSpace (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsAdvertisingSpace(Integer id, CmsSite site, String name, Boolean enabled) {
		this.setId(id);
		this.setSite(site);
		this.setName(name);
		this.setEnabled(enabled);
	}

	/*[CONSTRUCTOR MARKER END]*/
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name;
	private String description;
	private Boolean enabled;

	// many to one
	private foo.cms.entity.main.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="adspace_id"
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
	 * Return the value associated with the column: ad_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: ad_name
	 * @param name the ad_name value
	 */
	public void setName (String name) {
		this.name = name;
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
		if (!(obj instanceof CmsAdvertisingSpace)) return false;
		else {
			CmsAdvertisingSpace cmsAdvertisingSpace = (CmsAdvertisingSpace) obj;
			if (null == this.getId() || null == cmsAdvertisingSpace.getId()) return false;
			else return (this.getId().equals(cmsAdvertisingSpace.getId()));
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