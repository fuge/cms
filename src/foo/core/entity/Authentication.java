package foo.core.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Authentication implements Serializable{
	private static final long serialVersionUID = -7383629396971858792L;

	public void init() {
		Date now = new Timestamp(System.currentTimeMillis());
		setLoginTime(now);
		setUpdateTime(now);
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Authentication () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Authentication (String id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Authentication(String id, Integer uid, String username,
			Date loginTime, String loginIp, Date updateTime) {
		this.setId(id);
		this.setUid(uid);
		this.setUsername(username);
		this.setLoginTime(loginTime);
		this.setLoginIp(loginIp);
		this.setUpdateTime(updateTime);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private String id;

	// fields
	private Integer uid;
	private String username;
	private String email;
	private Date loginTime;
	private String loginIp;
	private Date updateTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="authentication_id"
     */
	public String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: userid
	 */
	public Integer getUid () {
		return uid;
	}

	/**
	 * Set the value related to the column: userid
	 * @param uid the userid value
	 */
	public void setUid (Integer uid) {
		this.uid = uid;
	}


	/**
	 * Return the value associated with the column: username
	 */
	public String getUsername () {
		return username;
	}

	/**
	 * Set the value related to the column: username
	 * @param username the username value
	 */
	public void setUsername (String username) {
		this.username = username;
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
	 * Return the value associated with the column: login_time
	 */
	public Date getLoginTime () {
		return loginTime;
	}

	/**
	 * Set the value related to the column: login_time
	 * @param loginTime the login_time value
	 */
	public void setLoginTime (Date loginTime) {
		this.loginTime = loginTime;
	}


	/**
	 * Return the value associated with the column: login_ip
	 */
	public String getLoginIp () {
		return loginIp;
	}

	/**
	 * Set the value related to the column: login_ip
	 * @param loginIp the login_ip value
	 */
	public void setLoginIp (String loginIp) {
		this.loginIp = loginIp;
	}


	/**
	 * Return the value associated with the column: update_time
	 */
	public Date getUpdateTime () {
		return updateTime;
	}

	/**
	 * Set the value related to the column: update_time
	 * @param updateTime the update_time value
	 */
	public void setUpdateTime (Date updateTime) {
		this.updateTime = updateTime;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof Authentication)) return false;
		else {
			Authentication authentication = (Authentication) obj;
			if (null == this.getId() || null == authentication.getId()) return false;
			else return (this.getId().equals(authentication.getId()));
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