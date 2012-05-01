package foo.cms.entity.main;

import java.io.Serializable;

public class CmsUserSite implements Serializable {
	private static final long serialVersionUID = -5043063651466920182L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsUserSite () {
		super();
	}

	/** Constructor for primary key */
	public CmsUserSite (Integer id) {
		this.setId(id);
	}

	/** Constructor for required fields */
	public CmsUserSite(Integer id, CmsUser user, CmsSite site, 
			Byte checkStep, Boolean allChannel) {
		this.setId(id);
		this.setUser(user);
		this.setSite(site);
		this.setCheckStep(checkStep);
		this.setAllChannel(allChannel);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private Byte checkStep;
	private Boolean allChannel;

	// many to one
	private foo.cms.entity.main.CmsUser user;
	private foo.cms.entity.main.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="usersite_id"
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
	 * Return the value associated with the column: check_step
	 */
	public Byte getCheckStep () {
		return checkStep;
	}

	/**
	 * Set the value related to the column: check_step
	 * @param checkStep the check_step value
	 */
	public void setCheckStep (Byte checkStep) {
		this.checkStep = checkStep;
	}


	/**
	 * Return the value associated with the column: is_all_channel
	 */
	public Boolean getAllChannel () {
		return allChannel;
	}

	/**
	 * Set the value related to the column: is_all_channel
	 * @param allChannel the is_all_channel value
	 */
	public void setAllChannel (Boolean allChannel) {
		this.allChannel = allChannel;
	}


	/**
	 * Return the value associated with the column: user_id
	 */
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
		if (!(obj instanceof foo.cms.entity.main.CmsUserSite)) return false;
		else {
			foo.cms.entity.main.CmsUserSite cmsUserSite = (foo.cms.entity.main.CmsUserSite) obj;
			if (null == this.getId() || null == cmsUserSite.getId()) return false;
			else return (this.getId().equals(cmsUserSite.getId()));
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