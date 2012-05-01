package foo.cms.entity.assist;

import java.io.Serializable;
import java.sql.Timestamp;

import foo.cms.entity.main.CmsSite;
import foo.common.util.StrUtils;

public class CmsGuestbook implements Serializable {
	private static final long serialVersionUID = -9174603337163334862L;

	public String getTitleHtml() {
		return StrUtils.txt2htm(getTitle());
	}

	public String getContentHtml() {
		return StrUtils.txt2htm(getContent());
	}

	public String getReplyHtml() {
		return StrUtils.txt2htm(getReply());
	}

	public String getTitle() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getTitle();
		} else {
			return null;
		}
	}

	public String getContent() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getContent();
		} else {
			return null;
		}
	}

	public String getReply() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getReply();
		} else {
			return null;
		}
	}

	public String getEmail() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getEmail();
		} else {
			return null;
		}
	}

	public String getPhone() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getPhone();
		} else {
			return null;
		}
	}

	public String getQq() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getQq();
		} else {
			return null;
		}
	}

	public void init() {
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
	public CmsGuestbook () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsGuestbook (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsGuestbook(Integer id, CmsSite site, CmsGuestbookCtg ctg, String ip,
			java.util.Date createTime, Boolean checked, Boolean recommend) {
		this.setId(id);
		this.setSite(site);
		this.setCtg(ctg);
		this.setIp(ip);
		this.setCreateTime(createTime);
		this.setChecked(checked);
		this.setRecommend(recommend);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String ip;
	private java.util.Date createTime;
	private java.util.Date replayTime;
	private Boolean checked;
	private Boolean recommend;

	// one to one
	private CmsGuestbookExt ext;

	// many to one
	private foo.cms.entity.main.CmsUser member;
	private foo.cms.entity.main.CmsUser admin;
	private foo.cms.entity.main.CmsSite site;
	private CmsGuestbookCtg ctg;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
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
	 * Return the value associated with the column: ip
	 */
	public String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: ip
	 * @param ip the ip value
	 */
	public void setIp (String ip) {
		this.ip = ip;
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
	 * Return the value associated with the column: replay_time
	 */
	public java.util.Date getReplayTime () {
		return replayTime;
	}

	/**
	 * Set the value related to the column: replay_time
	 * @param replayTime the replay_time value
	 */
	public void setReplayTime (java.util.Date replayTime) {
		this.replayTime = replayTime;
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
	 * Return the value associated with the column: ext
	 */
	public CmsGuestbookExt getExt () {
		return ext;
	}

	/**
	 * Set the value related to the column: ext
	 * @param ext the ext value
	 */
	public void setExt (CmsGuestbookExt ext) {
		this.ext = ext;
	}


	/**
	 * Return the value associated with the column: member_id
	 */
	public foo.cms.entity.main.CmsUser getMember () {
		return member;
	}

	/**
	 * Set the value related to the column: member_id
	 * @param member the member_id value
	 */
	public void setMember (foo.cms.entity.main.CmsUser member) {
		this.member = member;
	}


	/**
	 * Return the value associated with the column: admin_id
	 */
	public foo.cms.entity.main.CmsUser getAdmin () {
		return admin;
	}

	/**
	 * Set the value related to the column: admin_id
	 * @param admin the admin_id value
	 */
	public void setAdmin (foo.cms.entity.main.CmsUser admin) {
		this.admin = admin;
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
	 * Return the value associated with the column: guestbookctg_id
	 */
	public CmsGuestbookCtg getCtg () {
		return ctg;
	}

	/**
	 * Set the value related to the column: guestbookctg_id
	 * @param ctg the guestbookctg_id value
	 */
	public void setCtg (CmsGuestbookCtg ctg) {
		this.ctg = ctg;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsGuestbook)) return false;
		else {
			CmsGuestbook cmsGuestbook = (CmsGuestbook) obj;
			if (null == this.getId() || null == cmsGuestbook.getId()) return false;
			else return (this.getId().equals(cmsGuestbook.getId()));
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