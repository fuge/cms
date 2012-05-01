package foo.cms.entity.assist;

import java.io.Serializable;

import foo.cms.entity.main.CmsSite;

public class CmsVoteTopic implements Serializable {
	private static final long serialVersionUID = 4329334066287399900L;

	public void init() {
		if (getTotalCount() == null) {
			setTotalCount(0);
		}
		if (getMultiSelect() == null) {
			setMultiSelect(1);
		}
		if (getDef() == null) {
			setDef(false);
		}
		if (getDisabled() == null) {
			setDisabled(false);
		}
		if (getRestrictMember() == null) {
			setRestrictMember(false);
		}
		if (getRestrictIp() == null) {
			setRestrictIp(false);
		}
		if (getRestrictCookie() == null) {
			setRestrictCookie(true);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsVoteTopic () {
	}

	/**
	 * Constructor for primary key
	 */
	public CmsVoteTopic (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsVoteTopic (
		Integer id,
		CmsSite site,
		String title,
		Integer totalCount,
		Integer multiSelect,
		Boolean restrictMember,
		Boolean restrictIp,
		Boolean restrictCookie,
		Boolean disabled,
		Boolean def) {
		this.setId(id);
		this.setSite(site);
		this.setTitle(title);
		this.setTotalCount(totalCount);
		this.setMultiSelect(multiSelect);
		this.setRestrictMember(restrictMember);
		this.setRestrictIp(restrictIp);
		this.setRestrictCookie(restrictCookie);
		this.setDisabled(disabled);
		this.setDef(def);
	}

	/* [CONSTRUCTOR MARKER END] */
	public static String PROP_END_TIME = "endTime";
	public static String PROP_START_TIME = "startTime";

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String title;
	private String description;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private Integer repeateHour;
	private Integer totalCount;
	private Integer multiSelect;
	private Boolean restrictMember;
	private Boolean restrictIp;
	private Boolean restrictCookie;
	private Boolean disabled;
	private Boolean def;

	// many to one
	private CmsSite site;

	// collections
	private java.util.Set<CmsVoteItem> items;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="votetopic_id"
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
	 * Return the value associated with the column: start_time
	 */
	public java.util.Date getStartTime () {
		return startTime;
	}

	/**
	 * Set the value related to the column: start_time
	 * @param startTime the start_time value
	 */
	public void setStartTime (java.util.Date startTime) {
		this.startTime = startTime;
	}


	/**
	 * Return the value associated with the column: end_time
	 */
	public java.util.Date getEndTime () {
		return endTime;
	}

	/**
	 * Set the value related to the column: end_time
	 * @param endTime the end_time value
	 */
	public void setEndTime (java.util.Date endTime) {
		this.endTime = endTime;
	}


	/**
	 * Return the value associated with the column: repeate_hour
	 */
	public Integer getRepeateHour () {
		return repeateHour;
	}

	/**
	 * Set the value related to the column: repeate_hour
	 * @param repeateHour the repeate_hour value
	 */
	public void setRepeateHour (Integer repeateHour) {
		this.repeateHour = repeateHour;
	}


	/**
	 * Return the value associated with the column: total_count
	 */
	public Integer getTotalCount () {
		return totalCount;
	}

	/**
	 * Set the value related to the column: total_count
	 * @param totalCount the total_count value
	 */
	public void setTotalCount (Integer totalCount) {
		this.totalCount = totalCount;
	}


	/**
	 * Return the value associated with the column: multi_select
	 */
	public Integer getMultiSelect () {
		return multiSelect;
	}

	/**
	 * Set the value related to the column: multi_select
	 * @param multiSelect the multi_select value
	 */
	public void setMultiSelect (Integer multiSelect) {
		this.multiSelect = multiSelect;
	}


	/**
	 * Return the value associated with the column: is_restrict_member
	 */
	public Boolean getRestrictMember () {
		return restrictMember;
	}

	/**
	 * Set the value related to the column: is_restrict_member
	 * @param restrictMember the is_restrict_member value
	 */
	public void setRestrictMember (Boolean restrictMember) {
		this.restrictMember = restrictMember;
	}


	/**
	 * Return the value associated with the column: is_restrict_ip
	 */
	public Boolean getRestrictIp () {
		return restrictIp;
	}

	/**
	 * Set the value related to the column: is_restrict_ip
	 * @param restrictIp the is_restrict_ip value
	 */
	public void setRestrictIp (Boolean restrictIp) {
		this.restrictIp = restrictIp;
	}


	/**
	 * Return the value associated with the column: is_restrict_cookie
	 */
	public Boolean getRestrictCookie () {
		return restrictCookie;
	}

	/**
	 * Set the value related to the column: is_restrict_cookie
	 * @param restrictCookie the is_restrict_cookie value
	 */
	public void setRestrictCookie (Boolean restrictCookie) {
		this.restrictCookie = restrictCookie;
	}


	/**
	 * Return the value associated with the column: is_disabled
	 */
	public Boolean getDisabled () {
		return disabled;
	}

	/**
	 * Set the value related to the column: is_disabled
	 * @param disabled the is_disabled value
	 */
	public void setDisabled (Boolean disabled) {
		this.disabled = disabled;
	}


	/**
	 * Return the value associated with the column: is_def
	 */
	public Boolean getDef () {
		return def;
	}

	/**
	 * Set the value related to the column: is_def
	 * @param def the is_def value
	 */
	public void setDef (Boolean def) {
		this.def = def;
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


	/**
	 * Return the value associated with the column: items
	 */
	public java.util.Set<CmsVoteItem> getItems () {
		return items;
	}

	/**
	 * Set the value related to the column: items
	 * @param items the items value
	 */
	public void setItems (java.util.Set<CmsVoteItem> items) {
		this.items = items;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsVoteTopic)) return false;
		else {
			CmsVoteTopic cmsVoteTopic = (CmsVoteTopic) obj;
			if (null == this.getId() || null == cmsVoteTopic.getId()) return false;
			else return (this.getId().equals(cmsVoteTopic.getId()));
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