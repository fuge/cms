package foo.cms.entity.assist;

import java.io.Serializable;

public class CmsCommentExt implements Serializable {
	private static final long serialVersionUID = 2798062768157271401L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsCommentExt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsCommentExt (java.lang.Integer id) {
		this.setId(id);
	}

	/*[CONSTRUCTOR MARKER END]*/
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String ip;
	private java.lang.String text;
	private java.lang.String reply;

	// one to one
	private foo.cms.entity.assist.CmsComment comment;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="comment_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: ip
	 */
	public java.lang.String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: ip
	 * @param ip the ip value
	 */
	public void setIp (java.lang.String ip) {
		this.ip = ip;
	}


	/**
	 * Return the value associated with the column: text
	 */
	public java.lang.String getText () {
		return text;
	}

	/**
	 * Set the value related to the column: text
	 * @param text the text value
	 */
	public void setText (java.lang.String text) {
		this.text = text;
	}


	/**
	 * Return the value associated with the column: reply
	 */
	public java.lang.String getReply () {
		return reply;
	}

	/**
	 * Set the value related to the column: reply
	 * @param reply the reply value
	 */
	public void setReply (java.lang.String reply) {
		this.reply = reply;
	}


	/**
	 * Return the value associated with the column: comment
	 */
	public foo.cms.entity.assist.CmsComment getComment () {
		return comment;
	}

	/**
	 * Set the value related to the column: comment
	 * @param comment the comment value
	 */
	public void setComment (foo.cms.entity.assist.CmsComment comment) {
		this.comment = comment;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.assist.CmsCommentExt)) return false;
		else {
			foo.cms.entity.assist.CmsCommentExt cmsCommentExt = (foo.cms.entity.assist.CmsCommentExt) obj;
			if (null == this.getId() || null == cmsCommentExt.getId()) return false;
			else return (this.getId().equals(cmsCommentExt.getId()));
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