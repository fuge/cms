package foo.cms.entity.assist;

import java.io.Serializable;
import java.sql.Timestamp;

import foo.common.util.StrUtils;

public class CmsComment implements Serializable {
	private static final long serialVersionUID = -1832134277219483381L;


	public String getText() {
		return getCommentExt().getText();
	}

	public String getTextHtml() {
		return StrUtils.txt2htm(getText());
	}

	public String getReply() {
		return getCommentExt().getReply();
	}

	public String getReplayHtml() {
		return StrUtils.txt2htm(getReply());
	}

	public String getIp() {
		return getCommentExt().getIp();
	}

	public void init() {
		short zero = 0;
		if (getDowns() == null) {
			setDowns(zero);
		}
		if (getUps() == null) {
			setUps(zero);
		}
		if (getChecked() == null) {
			setChecked(false);
		}
		if (getRecommend() == null) {
			setRecommend(false);
		}
		if (getCreateTime() == null) {
			setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsComment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsComment (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsComment(Integer id, foo.cms.entity.main.Content content,
			foo.cms.entity.main.CmsSite site, java.util.Date createTime, Short ups,
			Short downs, Boolean recommend, Boolean checked) {
		this.setId(id);
		this.setContent(content);
		this.setSite(site);
		this.setCreateTime(createTime);
		this.setUps(ups);
		this.setDowns(downs);
		this.setRecommend(recommend);
		this.setChecked(checked);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private java.util.Date createTime;
	private java.util.Date replayTime;
	private Short ups;
	private Short downs;
	private Boolean recommend;
	private Boolean checked;

	// one to one
	private CmsCommentExt commentExt;

	// many to one
	private foo.cms.entity.main.CmsUser replayUser;
	private foo.cms.entity.main.Content content;
	private foo.cms.entity.main.CmsUser commentUser;
	private foo.cms.entity.main.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="comment_id"
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
	 * Return the value associated with the column: create_time
	 */
	public java.util.Date getCreateTime () {
		return createTime;
	}

	/**
	 * Set the value related to the column: create_time
	 * @param createTime the create_time value
	 */
	public void setCreateTime (java.util.Date createTime) {
		this.createTime = createTime;
	}


	/**
	 * Return the value associated with the column: reply_time
	 */
	public java.util.Date getReplayTime () {
		return replayTime;
	}

	/**
	 * Set the value related to the column: reply_time
	 * @param replayTime the reply_time value
	 */
	public void setReplayTime (java.util.Date replayTime) {
		this.replayTime = replayTime;
	}


	/**
	 * Return the value associated with the column: ups
	 */
	public Short getUps () {
		return ups;
	}

	/**
	 * Set the value related to the column: ups
	 * @param ups the ups value
	 */
	public void setUps (Short ups) {
		this.ups = ups;
	}


	/**
	 * Return the value associated with the column: downs
	 */
	public Short getDowns () {
		return downs;
	}

	/**
	 * Set the value related to the column: downs
	 * @param downs the downs value
	 */
	public void setDowns (Short downs) {
		this.downs = downs;
	}


	/**
	 * Return the value associated with the column: is_recommend
	 */
	public Boolean getRecommend () {
		return recommend;
	}

	/**
	 * Set the value related to the column: is_recommend
	 * @param recommend the is_recommend value
	 */
	public void setRecommend (Boolean recommend) {
		this.recommend = recommend;
	}


	/**
	 * Return the value associated with the column: is_checked
	 */
	public Boolean getChecked () {
		return checked;
	}

	/**
	 * Set the value related to the column: is_checked
	 * @param checked the is_checked value
	 */
	public void setChecked (Boolean checked) {
		this.checked = checked;
	}


	/**
	 * Return the value associated with the column: commentExt
	 */
	public CmsCommentExt getCommentExt () {
		return commentExt;
	}

	/**
	 * Set the value related to the column: commentExt
	 * @param commentExt the commentExt value
	 */
	public void setCommentExt (CmsCommentExt commentExt) {
		this.commentExt = commentExt;
	}


	/**
	 * Return the value associated with the column: reply_user_id
	 */
	public foo.cms.entity.main.CmsUser getReplayUser () {
		return replayUser;
	}

	/**
	 * Set the value related to the column: reply_user_id
	 * @param replayUser the reply_user_id value
	 */
	public void setReplayUser (foo.cms.entity.main.CmsUser replayUser) {
		this.replayUser = replayUser;
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


	/**
	 * Return the value associated with the column: comment_user_id
	 */
	public foo.cms.entity.main.CmsUser getCommentUser () {
		return commentUser;
	}

	/**
	 * Set the value related to the column: comment_user_id
	 * @param commentUser the comment_user_id value
	 */
	public void setCommentUser (foo.cms.entity.main.CmsUser commentUser) {
		this.commentUser = commentUser;
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
		if (!(obj instanceof CmsComment)) return false;
		else {
			CmsComment cmsComment = (CmsComment) obj;
			if (null == this.getId() || null == cmsComment.getId()) return false;
			else return (this.getId().equals(cmsComment.getId()));
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