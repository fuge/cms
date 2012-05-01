package foo.cms.entity.assist;

import java.io.Serializable;

import foo.cms.entity.main.CmsSite;


public class CmsGuestbookCtg implements Serializable {
	private static final long serialVersionUID = 2389862866670080020L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsGuestbookCtg () {
	}

	/**
	 * Constructor for primary key
	 */
	public CmsGuestbookCtg (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsGuestbookCtg(Integer id, CmsSite site, String name, Integer priority) {
		this.setId(id);
		this.setSite(site);
		this.setName(name);
		this.setPriority(priority);
	}

	/*[CONSTRUCTOR MARKER END]*/

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name;
	private Integer priority;
	private java.lang.String description;
	
	// many to one
	private foo.cms.entity.main.CmsSite site;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="friendlinkctg_id"
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
	 * Return the value associated with the column: friendlinkctg_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: friendlinkctg_name
	 * @param name the friendlinkctg_name value
	 */
	public void setName (String name) {
		this.name = name;
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
		if (!(obj instanceof CmsFriendlinkCtg)) return false;
		else {
			CmsFriendlinkCtg cmsFriendlinkCtg = (CmsFriendlinkCtg) obj;
			if (null == this.getId() || null == cmsFriendlinkCtg.getId()) return false;
			else return (this.getId().equals(cmsFriendlinkCtg.getId()));
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