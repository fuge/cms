package foo.cms.entity.assist;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import foo.cms.entity.main.CmsSite;

public class CmsAdvertising implements Serializable {
	private static final long serialVersionUID = 6944589797214657725L;



	public int getPercent() {
		if (getDisplayCount() <= 0) {
			return 0;
		} else {
			return (int) (getClickCount() * 100 / getDisplayCount());
		}
	}

	public Long getStartTimeMillis() {
		if (getStartTime() != null) {
			return getStartTime().getTime();
		} else {
			return null;
		}
	}

	public Long getEndTimeMillis() {
		if (getEndTime() != null) {
			return getEndTime().getTime();
		} else {
			return null;
		}
	}

	public void init() {
		if (getClickCount() == null) {
			setClickCount(0L);
		}
		if (getDisplayCount() == null) {
			setDisplayCount(0L);
		}
		if (getEnabled() == null) {
			setEnabled(true);
		}
		if (getWeight() == null) {
			setWeight(1);
		}
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getCode())) {
			setCode(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsAdvertising() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsAdvertising(Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsAdvertising(Integer id, CmsAdvertisingSpace adspace, CmsSite site,
			String name, String category, Integer weight, Long displayCount,
			Long clickCount, Boolean enabled) {
		this.setId(id);
		this.setAdspace(adspace);
		this.setSite(site);
		this.setName(name);
		this.setCategory(category);
		this.setWeight(weight);
		this.setDisplayCount(displayCount);
		this.setClickCount(clickCount);
		this.setEnabled(enabled);
	}

	/* [CONSTRUCTOR MARKER END] */
	public static String PROP_CODE = "code";
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name;
	private String category;
	private String code;
	private Integer weight;
	private Long displayCount;
	private Long clickCount;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private Boolean enabled;

	// many to one
	private CmsAdvertisingSpace adspace;
	private foo.cms.entity.main.CmsSite site;

	// collections
	private java.util.Map<String, String> attr;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="advertising_id"
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
	 * Return the value associated with the column: category
	 */
	public String getCategory () {
		return category;
	}

	/**
	 * Set the value related to the column: category
	 * @param category the category value
	 */
	public void setCategory (String category) {
		this.category = category;
	}


	/**
	 * Return the value associated with the column: ad_code
	 */
	public String getCode () {
		return code;
	}

	/**
	 * Set the value related to the column: ad_code
	 * @param code the ad_code value
	 */
	public void setCode (String code) {
		this.code = code;
	}


	/**
	 * Return the value associated with the column: ad_weight
	 */
	public Integer getWeight () {
		return weight;
	}

	/**
	 * Set the value related to the column: ad_weight
	 * @param weight the ad_weight value
	 */
	public void setWeight (Integer weight) {
		this.weight = weight;
	}


	/**
	 * Return the value associated with the column: display_count
	 */
	public Long getDisplayCount () {
		return displayCount;
	}

	/**
	 * Set the value related to the column: display_count
	 * @param displayCount the display_count value
	 */
	public void setDisplayCount (Long displayCount) {
		this.displayCount = displayCount;
	}


	/**
	 * Return the value associated with the column: click_count
	 */
	public Long getClickCount () {
		return clickCount;
	}

	/**
	 * Set the value related to the column: click_count
	 * @param clickCount the click_count value
	 */
	public void setClickCount (Long clickCount) {
		this.clickCount = clickCount;
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
	 * Return the value associated with the column: adspace_id
	 */
	public CmsAdvertisingSpace getAdspace () {
		return adspace;
	}

	/**
	 * Set the value related to the column: adspace_id
	 * @param adspace the adspace_id value
	 */
	public void setAdspace (CmsAdvertisingSpace adspace) {
		this.adspace = adspace;
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


	/**
	 * Return the value associated with the column: attr
	 */
	public java.util.Map<String, String> getAttr () {
		return attr;
	}

	/**
	 * Set the value related to the column: attr
	 * @param attr the attr value
	 */
	public void setAttr (java.util.Map<String, String> attr) {
		this.attr = attr;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsAdvertising)) return false;
		else {
			CmsAdvertising cmsAdvertising = (CmsAdvertising) obj;
			if (null == this.getId() || null == cmsAdvertising.getId()) return false;
			else return (this.getId().equals(cmsAdvertising.getId()));
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