package foo.cms.entity.assist;

import java.io.Serializable;

public class CmsSensitivity implements Serializable {
	private static final long serialVersionUID = 1788682533518886130L;

	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsSensitivity () {
	}

	/**
	 * Constructor for primary key
	 */
	public CmsSensitivity (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsSensitivity (
		Integer id,
		String search,
		String replacement) {
		this.setId(id);
		this.setSearch(search);
		this.setReplacement(replacement);
	}

	/*[CONSTRUCTOR MARKER END]*/
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String search;
	private String replacement;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="sensitivity_id"
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
	 * Return the value associated with the column: search
	 */
	public String getSearch () {
		return search;
	}

	/**
	 * Set the value related to the column: search
	 * @param search the search value
	 */
	public void setSearch (String search) {
		this.search = search;
	}


	/**
	 * Return the value associated with the column: replacement
	 */
	public String getReplacement () {
		return replacement;
	}

	/**
	 * Set the value related to the column: replacement
	 * @param replacement the replacement value
	 */
	public void setReplacement (String replacement) {
		this.replacement = replacement;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsSensitivity)) return false;
		else {
			CmsSensitivity cmsSensitivity = (CmsSensitivity) obj;
			if (null == this.getId() || null == cmsSensitivity.getId()) return false;
			else return (this.getId().equals(cmsSensitivity.getId()));
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