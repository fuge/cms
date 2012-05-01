package foo.cms.entity.main;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ContentCheck implements Serializable{
	private static final long serialVersionUID = -6899919592387138880L;
	/**
	 * 草稿
	 */
	public static final byte DRAFT = 0;
	/**
	 * 审核中
	 */
	public static final byte CHECKING = 1;
	/**
	 * 已审核
	 */
	public static final byte CHECKED = 2;
	/**
	 * 回收站
	 */
	public static final byte RECYCLE = 3;

	public void init() {
		byte zero = 0;
		if (getCheckStep() == null) {
			setCheckStep(zero);
		}
		if (getRejected() == null) {
			setRejected(false);
		}
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getCheckOpinion())) {
			setCheckOpinion(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentCheck () {
		super();
	}

	/** Constructor for primary key */
	public ContentCheck (Integer id) {
		this.setId(id);
	}

	/** Constructor for required fields */
	public ContentCheck(Integer id, Byte checkStep, Boolean rejected) {
		this.setId(id);
		this.setCheckStep(checkStep);
		this.setRejected(rejected);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private Byte checkStep;
	private String checkOpinion;
	private Boolean rejected;

	// one to one
	private foo.cms.entity.main.Content content;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="content_id"
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
	 * Return the value associated with the column: check_step
	 */
	public Byte getCheckStep () {
		return checkStep;
	}

	/**
	 * Set the value related to the column: check_step
	 * @param checkStep the check_step value
	 */
	public void setCheckStep (Byte checkStep) {
		this.checkStep = checkStep;
	}


	/**
	 * Return the value associated with the column: check_opinion
	 */
	public String getCheckOpinion () {
		return checkOpinion;
	}

	/**
	 * Set the value related to the column: check_opinion
	 * @param checkOpinion the check_opinion value
	 */
	public void setCheckOpinion (String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}


	/**
	 * Return the value associated with the column: is_rejected
	 */
	public Boolean getRejected () {
		return rejected;
	}

	/**
	 * Set the value related to the column: is_rejected
	 * @param rejected the is_rejected value
	 */
	public void setRejected (Boolean rejected) {
		this.rejected = rejected;
	}


	/**
	 * Return the value associated with the column: content
	 */
	public foo.cms.entity.main.Content getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (foo.cms.entity.main.Content content) {
		this.content = content;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.ContentCheck)) return false;
		else {
			foo.cms.entity.main.ContentCheck contentCheck = (foo.cms.entity.main.ContentCheck) obj;
			if (null == this.getId() || null == contentCheck.getId()) return false;
			else return (this.getId().equals(contentCheck.getId()));
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