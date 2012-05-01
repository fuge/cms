package foo.cms.entity.assist;

import java.io.Serializable;
import foo.cms.entity.main.CmsUser;

public class CmsVoteRecord implements Serializable{
	private static final long serialVersionUID = 4168514091032469076L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsVoteRecord () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsVoteRecord (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsVoteRecord (
		Integer id,
		CmsVoteTopic topic,
		java.util.Date time,
		String ip,
		String cookie) {
		this.setId(id);
		this.setTopic(topic);
		this.setTime(time);
		this.setIp(ip);
		this.setCookie(cookie);
	}

	/*[CONSTRUCTOR MARKER END]*/
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private java.util.Date time;
	private String ip;
	private String cookie;

	// many to one
	private CmsUser user;
	private CmsVoteTopic topic;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="voterecored_id"
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
	 * Return the value associated with the column: vote_time
	 */
	public java.util.Date getTime () {
		return time;
	}

	/**
	 * Set the value related to the column: vote_time
	 * @param time the vote_time value
	 */
	public void setTime (java.util.Date time) {
		this.time = time;
	}


	/**
	 * Return the value associated with the column: vote_ip
	 */
	public String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: vote_ip
	 * @param ip the vote_ip value
	 */
	public void setIp (String ip) {
		this.ip = ip;
	}


	/**
	 * Return the value associated with the column: vote_cookie
	 */
	public String getCookie () {
		return cookie;
	}

	/**
	 * Set the value related to the column: vote_cookie
	 * @param cookie the vote_cookie value
	 */
	public void setCookie (String cookie) {
		this.cookie = cookie;
	}


	/**
	 * Return the value associated with the column: user_id
	 */
	public CmsUser getUser () {
		return user;
	}

	/**
	 * Set the value related to the column: user_id
	 * @param user the user_id value
	 */
	public void setUser (CmsUser user) {
		this.user = user;
	}


	/**
	 * Return the value associated with the column: votetopic_id
	 */
	public CmsVoteTopic getTopic () {
		return topic;
	}

	/**
	 * Set the value related to the column: votetopic_id
	 * @param topic the votetopic_id value
	 */
	public void setTopic (CmsVoteTopic topic) {
		this.topic = topic;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsVoteRecord)) return false;
		else {
			CmsVoteRecord cmsVoteRecord = (CmsVoteRecord) obj;
			if (null == this.getId() || null == cmsVoteRecord.getId()) return false;
			else return (this.getId().equals(cmsVoteRecord.getId()));
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