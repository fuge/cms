package foo.cms.entity.assist;

import java.io.Serializable;

import foo.cms.entity.main.CmsSite;

public class CmsKeyword implements Serializable{
	private static final long serialVersionUID = -2908411151923416577L;



	public void init() {
		if (getDisabled() == null) {
			setDisabled(false);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsKeyword () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsKeyword (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsKeyword (
		Integer id,
		String name,
		String url,
		Boolean disabled) {
		this.setId(id);
		this.setName(name);
		this.setUrl(url);
		this.setDisabled(disabled);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name;
	private String url;
	private Boolean disabled;

	// many to one
	private CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="keyword_id"
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
	 * Return the value associated with the column: keyword_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: keyword_name
	 * @param name the keyword_name value
	 */
	public void setName (String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: url
	 */
	public String getUrl () {
		return url;
	}

	/**
	 * Set the value related to the column: url
	 * @param url the url value
	 */
	public void setUrl (String url) {
		this.url = url;
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
		if (!(obj instanceof CmsKeyword)) return false;
		else {
			CmsKeyword cmsKeyword = (CmsKeyword) obj;
			if (null == this.getId() || null == cmsKeyword.getId()) return false;
			else return (this.getId().equals(cmsKeyword.getId()));
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