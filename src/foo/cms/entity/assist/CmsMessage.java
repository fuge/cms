package foo.cms.entity.assist;

import java.io.Serializable;

import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.common.util.StrUtils;



public class CmsMessage implements Serializable {
	private static final long serialVersionUID = -2639099699859045511L;


	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsMessage () {
	}

	/**
	 * Constructor for primary key
	 */
	public CmsMessage (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsMessage(Integer id, CmsUser msgReceiverUser, CmsUser msgSendUser,
			CmsSite site, String msgTitle, Boolean msgStatus, Integer msgBox) {
		this.setId(id);
		this.setMsgReceiverUser(msgReceiverUser);
		this.setMsgSendUser(msgSendUser);
		this.setSite(site);
		this.setMsgTitle(msgTitle);
		this.setMsgStatus(msgStatus);
		this.setMsgBox(msgBox);
	}


	/*[CONSTRUCTOR MARKER END]*/
	public String getTitleHtml() {
		return StrUtils.txt2htm(getMsgTitle());
	}
	public String getContentHtml() {
		return StrUtils.txt2htm(getMsgContent());
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String msgTitle;
	private String msgContent;
	private java.util.Date sendTime;
	private Boolean msgStatus;
	private Integer msgBox;

	// many to one
	private CmsUser msgReceiverUser;
	private CmsUser msgSendUser;
	private CmsSite site;
	
	// collections
	private java.util.Set<CmsReceiverMessage> receiverMsgs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="msg_id"
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
	 * Return the value associated with the column: msg_title
	 */
	public String getMsgTitle () {
		return msgTitle;
	}

	/**
	 * Set the value related to the column: msg_title
	 * @param msgTitle the msg_title value
	 */
	public void setMsgTitle (String msgTitle) {
		this.msgTitle = msgTitle;
	}


	/**
	 * Return the value associated with the column: msg_content
	 */
	public String getMsgContent () {
		return msgContent;
	}

	/**
	 * Set the value related to the column: msg_content
	 * @param msgContent the msg_content value
	 */
	public void setMsgContent (String msgContent) {
		this.msgContent = msgContent;
	}


	/**
	 * Return the value associated with the column: send_time
	 */
	public java.util.Date getSendTime () {
		return sendTime;
	}

	/**
	 * Set the value related to the column: send_time
	 * @param sendTime the send_time value
	 */
	public void setSendTime (java.util.Date sendTime) {
		this.sendTime = sendTime;
	}


	/**
	 * Return the value associated with the column: msg_status
	 */
	public Boolean getMsgStatus () {
		return msgStatus;
	}

	/**
	 * Set the value related to the column: msg_status
	 * @param msgStatus the msg_status value
	 */
	public void setMsgStatus (Boolean msgStatus) {
		this.msgStatus = msgStatus;
	}


	/**
	 * Return the value associated with the column: msg_box
	 */
	public Integer getMsgBox () {
		return msgBox;
	}

	/**
	 * Set the value related to the column: msg_box
	 * @param msgBox the msg_box value
	 */
	public void setMsgBox (Integer msgBox) {
		this.msgBox = msgBox;
	}


	/**
	 * Return the value associated with the column: msg_receiver_user
	 */
	public CmsUser getMsgReceiverUser () {
		return msgReceiverUser;
	}

	/**
	 * Set the value related to the column: msg_receiver_user
	 * @param msgReceiverUser the msg_receiver_user value
	 */
	public void setMsgReceiverUser (CmsUser msgReceiverUser) {
		this.msgReceiverUser = msgReceiverUser;
	}


	/**
	 * Return the value associated with the column: msg_send_user
	 */
	public CmsUser getMsgSendUser () {
		return msgSendUser;
	}

	/**
	 * Set the value related to the column: msg_send_user
	 * @param msgSendUser the msg_send_user value
	 */
	public void setMsgSendUser (CmsUser msgSendUser) {
		this.msgSendUser = msgSendUser;
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
	
	public java.util.Set<CmsReceiverMessage> getReceiverMsgs() {
		return receiverMsgs;
	}

	public void setReceiverMsgs(
			java.util.Set<CmsReceiverMessage> receiverMsgs) {
		this.receiverMsgs = receiverMsgs;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsMessage)) return false;
		else {
			CmsMessage cmsMessage = (CmsMessage) obj;
			if (null == this.getId() || null == cmsMessage.getId()) return false;
			else return (this.getId().equals(cmsMessage.getId()));
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