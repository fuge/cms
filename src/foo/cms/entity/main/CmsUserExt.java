package foo.cms.entity.main;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class CmsUserExt implements Serializable {
	private static final long serialVersionUID = -2513366189673467035L;

	public void blankToNull() {
		// 将空串设置为null，便于前台处理。
		if (StringUtils.isBlank(getRealname())) {
			setRealname(null);
		}
		if (StringUtils.isBlank(getIntro())) {
			setIntro(null);
		}
		if (StringUtils.isBlank(getComefrom())) {
			setComefrom(null);
		}
		if (StringUtils.isBlank(getMobile())) {
			setMobile(null);
		}
		if (StringUtils.isBlank(getPhone())) {
			setPhone(null);
		}
		if (StringUtils.isBlank(getMsn())) {
			setMsn(null);
		}
		if (StringUtils.isBlank(getQq())) {
			setQq(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsUserExt () {
	}

	/** Constructor for primary key */
	public CmsUserExt (java.lang.Integer id) {
		this.setId(id);
	}

	/* [CONSTRUCTOR MARKER END] */


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String realname;
	private java.lang.Boolean gender;
	private java.util.Date birthday;
	private java.lang.String intro;
	private java.lang.String comefrom;
	private java.lang.String qq;
	private java.lang.String msn;
	private java.lang.String phone;
	private java.lang.String mobile;
	private java.lang.String userImg;
	private java.lang.String userSignature;

	// one to one
	private foo.cms.entity.main.CmsUser user;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="user_id"
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
	 * Return the value associated with the column: realname
	 */
	public java.lang.String getRealname () {
		return realname;
	}

	/**
	 * Set the value related to the column: realname
	 * @param realname the realname value
	 */
	public void setRealname (java.lang.String realname) {
		this.realname = realname;
	}


	/**
	 * Return the value associated with the column: gender
	 */
	public java.lang.Boolean getGender () {
		return gender;
	}

	/**
	 * Set the value related to the column: gender
	 * @param gender the gender value
	 */
	public void setGender (java.lang.Boolean gender) {
		this.gender = gender;
	}


	/**
	 * Return the value associated with the column: birthday
	 */
	public java.util.Date getBirthday () {
		return birthday;
	}

	/**
	 * Set the value related to the column: birthday
	 * @param birthday the birthday value
	 */
	public void setBirthday (java.util.Date birthday) {
		this.birthday = birthday;
	}


	/**
	 * Return the value associated with the column: intro
	 */
	public java.lang.String getIntro () {
		return intro;
	}

	/**
	 * Set the value related to the column: intro
	 * @param intro the intro value
	 */
	public void setIntro (java.lang.String intro) {
		this.intro = intro;
	}


	/**
	 * Return the value associated with the column: comefrom
	 */
	public java.lang.String getComefrom () {
		return comefrom;
	}

	/**
	 * Set the value related to the column: comefrom
	 * @param comefrom the comefrom value
	 */
	public void setComefrom (java.lang.String comefrom) {
		this.comefrom = comefrom;
	}


	/**
	 * Return the value associated with the column: qq
	 */
	public java.lang.String getQq () {
		return qq;
	}

	/**
	 * Set the value related to the column: qq
	 * @param qq the qq value
	 */
	public void setQq (java.lang.String qq) {
		this.qq = qq;
	}


	/**
	 * Return the value associated with the column: msn
	 */
	public java.lang.String getMsn () {
		return msn;
	}

	/**
	 * Set the value related to the column: msn
	 * @param msn the msn value
	 */
	public void setMsn (java.lang.String msn) {
		this.msn = msn;
	}


	/**
	 * Return the value associated with the column: phone
	 */
	public java.lang.String getPhone () {
		return phone;
	}

	/**
	 * Set the value related to the column: phone
	 * @param phone the phone value
	 */
	public void setPhone (java.lang.String phone) {
		this.phone = phone;
	}


	/**
	 * Return the value associated with the column: mobile
	 */
	public java.lang.String getMobile () {
		return mobile;
	}

	/**
	 * Set the value related to the column: mobile
	 * @param mobile the mobile value
	 */
	public void setMobile (java.lang.String mobile) {
		this.mobile = mobile;
	}
	public java.lang.String getUserImg() {
		return userImg;
	}

	public void setUserImg(java.lang.String userImg) {
		this.userImg = userImg;
	}

	public java.lang.String getUserSignature() {
		return userSignature;
	}

	public void setUserSignature(java.lang.String userSignature) {
		this.userSignature = userSignature;
	}


	/**
	 * Return the value associated with the column: user
	 */
	public foo.cms.entity.main.CmsUser getUser () {
		return user;
	}

	/**
	 * Set the value related to the column: user
	 * @param user the user value
	 */
	public void setUser (foo.cms.entity.main.CmsUser user) {
		this.user = user;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.CmsUserExt)) return false;
		else {
			foo.cms.entity.main.CmsUserExt cmsUserExt = (foo.cms.entity.main.CmsUserExt) obj;
			if (null == this.getId() || null == cmsUserExt.getId()) return false;
			else return (this.getId().equals(cmsUserExt.getId()));
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