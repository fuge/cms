package foo.cms.entity.assist;

import java.io.Serializable;

import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.common.util.StrUtils;

public class CmsReceiverMessage implements Serializable {
	private static final long serialVersionUID = 6570849535238152323L;



	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsReceiverMessage() {
	}

	/**
	 * Constructor for primary key
	 */
	public CmsReceiverMessage(Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsReceiverMessage(Integer id,
			CmsUser msgReceiverUser,
			CmsUser msgSendUser,
			CmsSite site, String msgTitle,
			String msgContent, java.util.Date sendTime,
			boolean msgStatus, Integer msgBox) {
		this.setId(id);
		this.setMsgReceiverUser(msgReceiverUser);
		this.setMsgSendUser(msgSendUser);
		this.setSite(site);
		this.setMsgTitle(msgTitle);
		this.setMsgContent(msgContent);
		this.setSendTime(sendTime);
		this.setMsgStatus(msgStatus);
		this.setMsgBox(msgBox);
	}

	public CmsReceiverMessage(CmsMessage message) {
		this(message.getId(), message.getMsgReceiverUser(), message
				.getMsgSendUser(), message.getSite(), message.getMsgTitle(),
				message.getMsgContent(), message.getSendTime(), message
						.getMsgStatus(), message.getMsgBox());
	}
	public String getTitleHtml() {
		return StrUtils.txt2htm(getMsgTitle());
	}
	public String getContentHtml() {
		return StrUtils.txt2htm(getMsgContent());
	}

	/* [CONSTRUCTOR MARKER END] */
	
	public static String REF = "CmsReceiverMessage";
	public static String PROP_MSG_STATUS = "msgStatus";
	public static String PROP_SITE = "site";
	public static String PROP_MESSAGE = "message";
	public static String PROP_MSG_SEND_USER = "msgSendUser";
	public static String PROP_MSG_CONTENT = "msgContent";
	public static String PROP_MSG_BOX = "msgBox";
	public static String PROP_SEND_TIME = "sendTime";
	public static String PROP_ID = "id";
	public static String PROP_MSG_RECEIVER_USER = "msgReceiverUser";
	public static String PROP_MSG_TITLE = "msgTitle";

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String msgTitle;
	private String msgContent;
	private java.util.Date sendTime;
	private boolean msgStatus;
	private Integer msgBox;

	

	// many to one
	private CmsUser msgReceiverUser;
	private CmsUser msgSendUser;
	private CmsSite site;
	private CmsMessage message;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
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
	public boolean isMsgStatus () {
		return msgStatus;
	}

	/**
	 * Set the value related to the column: msg_status
	 * @param msgStatus the msg_status value
	 */
	public void setMsgStatus (boolean msgStatus) {
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
	 * Return the value associated with the column: message
	 */
	public CmsMessage getMessage () {
		return message;
	}

	/**
	 * Set the value related to the column: message
	 * @param message the message value
	 */
	public void setMessage (CmsMessage message) {
		this.message = message;
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



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsReceiverMessage)) return false;
		else {
			CmsReceiverMessage cmsReceiverMessage = (CmsReceiverMessage) obj;
			if (null == this.getId() || null == cmsReceiverMessage.getId()) return false;
			else return (this.getId().equals(cmsReceiverMessage.getId()));
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
