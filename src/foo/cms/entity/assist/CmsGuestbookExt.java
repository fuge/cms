package foo.cms.entity.assist;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class CmsGuestbookExt implements Serializable {
	private static final long serialVersionUID = -8540485720926090576L;

	public void init() {
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getContent())) {
			setContent(null);
		}
		if (StringUtils.isBlank(getReply())) {
			setReply(null);
		}
		if (StringUtils.isBlank(getTitle())) {
			setTitle(null);
		}
		if (StringUtils.isBlank(getEmail())) {
			setEmail(null);
		}
		if (StringUtils.isBlank(getPhone())) {
			setPhone(null);
		}
		if (StringUtils.isBlank(getQq())) {
			setQq(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsGuestbookExt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsGuestbookExt (Integer id) {
		this.setId(id);
	}
	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String title;
	private String content;
	private String reply;
	private String email;
	private String phone;
	private String qq;

	// one to one
	private CmsGuestbook guestbook;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="guestbook_id"
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
	 * Return the value associated with the column: content
	 */
	public String getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (String content) {
		this.content = content;
	}


	/**
	 * Return the value associated with the column: reply
	 */
	public String getReply () {
		return reply;
	}

	/**
	 * Set the value related to the column: reply
	 * @param reply the reply value
	 */
	public void setReply (String reply) {
		this.reply = reply;
	}


	/**
	 * Return the value associated with the column: email
	 */
	public String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (String email) {
		this.email = email;
	}


	/**
	 * Return the value associated with the column: phone
	 */
	public String getPhone () {
		return phone;
	}

	/**
	 * Set the value related to the column: phone
	 * @param phone the phone value
	 */
	public void setPhone (String phone) {
		this.phone = phone;
	}


	/**
	 * Return the value associated with the column: qq
	 */
	public String getQq () {
		return qq;
	}

	/**
	 * Set the value related to the column: qq
	 * @param qq the qq value
	 */
	public void setQq (String qq) {
		this.qq = qq;
	}


	/**
	 * Return the value associated with the column: guestbook
	 */
	public CmsGuestbook getGuestbook () {
		return guestbook;
	}

	/**
	 * Set the value related to the column: guestbook
	 * @param guestbook the guestbook value
	 */
	public void setGuestbook (CmsGuestbook guestbook) {
		this.guestbook = guestbook;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsGuestbookExt)) return false;
		else {
			CmsGuestbookExt cmsGuestbookExt = (CmsGuestbookExt) obj;
			if (null == this.getId() || null == cmsGuestbookExt.getId()) return false;
			else return (this.getId().equals(cmsGuestbookExt.getId()));
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