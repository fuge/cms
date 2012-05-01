package foo.cms.entity.assist;

import java.io.Serializable;

public class CmsAcquisitionHistory implements Serializable {
	private static final long serialVersionUID = 7631007650310382566L;



	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsAcquisitionHistory () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsAcquisitionHistory (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsAcquisitionHistory(Integer id, String channelUrl, String contentUrl) {
		this.setId(id);
		this.setChannelUrl(channelUrl);
		this.setContentUrl(contentUrl);
	}

	/*[CONSTRUCTOR MARKER END]*/

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String channelUrl;
	private String contentUrl;
	private String title;
	private String description;

	// many to one
	private foo.cms.entity.assist.CmsAcquisition acquisition;
	private foo.cms.entity.main.Content content;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="history_id"
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
	 * Return the value associated with the column: acquisition_id
	 */
	public foo.cms.entity.assist.CmsAcquisition getAcquisition () {
		return acquisition;
	}

	/**
	 * Set the value related to the column: acquisition_id
	 * @param acquisition the acquisition_id value
	 */
	public void setAcquisition (foo.cms.entity.assist.CmsAcquisition acquisition) {
		this.acquisition = acquisition;
	}


	/**
	 * Return the value associated with the column: content_id
	 */
	public foo.cms.entity.main.Content getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content_id
	 * @param content the content_id value
	 */
	public void setContent (foo.cms.entity.main.Content content) {
		this.content = content;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.assist.CmsAcquisitionHistory)) return false;
		else {
			foo.cms.entity.assist.CmsAcquisitionHistory cmsAcquisitionHistory = (foo.cms.entity.assist.CmsAcquisitionHistory) obj;
			if (null == this.getId() || null == cmsAcquisitionHistory.getId()) return false;
			else return (this.getId().equals(cmsAcquisitionHistory.getId()));
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