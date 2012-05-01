package foo.cms.entity.assist;

import java.io.Serializable;

import foo.common.hibernate3.PriorityInterface;

public class CmsVoteItem implements PriorityInterface, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9206913505961440763L;



	public int getPercent() {
		Integer totalCount = getTopic().getTotalCount();
		if (totalCount != null && totalCount != 0) {
			return (getVoteCount() * 100) / totalCount;
		} else {
			return 0;
		}
	}

	public void init() {
		if (getPriority() == null) {
			setPriority(10);
		}
		if (getVoteCount() == null) {
			setVoteCount(0);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsVoteItem() {
	}

	/**
	 * Constructor for primary key
	 */
	public CmsVoteItem(Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsVoteItem(Integer id, CmsVoteTopic topic, String title, Integer voteCount,
			Integer priority) {
		this.setId(id);
		this.setTopic(topic);
		this.setTitle(title);
		this.setVoteCount(voteCount);
		this.setPriority(priority);
	}
	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String title;
	private Integer voteCount;
	private Integer priority;

	// many to one
	private CmsVoteTopic topic;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="voteitem_id"
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
	 * Return the value associated with the column: vote_count
	 */
	public Integer getVoteCount () {
		return voteCount;
	}

	/**
	 * Set the value related to the column: vote_count
	 * @param voteCount the vote_count value
	 */
	public void setVoteCount (Integer voteCount) {
		this.voteCount = voteCount;
	}


	/**
	 * Return the value associated with the column: priority
	 */
	public Integer getPriority () {
		return priority;
	}

	/**
	 * Set the value related to the column: priority
	 * @param priority the priority value
	 */
	public void setPriority (Integer priority) {
		this.priority = priority;
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
		if (!(obj instanceof CmsVoteItem)) return false;
		else {
			CmsVoteItem cmsVoteItem = (CmsVoteItem) obj;
			if (null == this.getId() || null == cmsVoteItem.getId()) return false;
			else return (this.getId().equals(cmsVoteItem.getId()));
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