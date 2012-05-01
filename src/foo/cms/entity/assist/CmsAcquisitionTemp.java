package foo.cms.entity.assist;

import java.io.Serializable;



public class CmsAcquisitionTemp implements Serializable{
	private static final long serialVersionUID = 28591381806671197L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsAcquisitionTemp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsAcquisitionTemp (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsAcquisitionTemp(Integer id, String channelUrl, String contentUrl,
			Integer percent, String description, Integer seq) {
		this.setId(id);
		this.setChannelUrl(channelUrl);
		this.setContentUrl(contentUrl);
		this.setPercent(percent);
		this.setDescription(description);
		this.setSeq(seq);
	}
	/*[CONSTRUCTOR MARKER END]*/

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String channelUrl;
	private String contentUrl;
	private String title;
	private Integer percent;
	private String description;
	private Integer seq;

	// many to one
	private foo.cms.entity.main.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="temp_id"
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
	 * Return the value associated with the column: channel_url
	 */
	public String getChannelUrl () {
		return channelUrl;
	}

	/**
	 * Set the value related to the column: channel_url
	 * @param channelUrl the channel_url value
	 */
	public void setChannelUrl (String channelUrl) {
		this.channelUrl = channelUrl;
	}


	/**
	 * Return the value associated with the column: content_url
	 */
	public String getContentUrl () {
		return contentUrl;
	}

	/**
	 * Set the value related to the column: content_url
	 * @param contentUrl the content_url value
	 */
	public void setContentUrl (String contentUrl) {
		this.contentUrl = contentUrl;
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
	 * Return the value associated with the column: percent
	 */
	public Integer getPercent () {
		return percent;
	}

	/**
	 * Set the value related to the column: percent
	 * @param percent the percent value
	 */
	public void setPercent (Integer percent) {
		this.percent = percent;
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
	 * Return the value associated with the column: seq
	 */
	public Integer getSeq () {
		return seq;
	}

	/**
	 * Set the value related to the column: seq
	 * @param seq the seq value
	 */
	public void setSeq (Integer seq) {
		this.seq = seq;
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
		if (!(obj instanceof CmsAcquisitionTemp)) return false;
		else {
			CmsAcquisitionTemp cmsAcquisitionTemp = (CmsAcquisitionTemp) obj;
			if (null == this.getId() || null == cmsAcquisitionTemp.getId()) return false;
			else return (this.getId().equals(cmsAcquisitionTemp.getId()));
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