package foo.cms.entity.main;

import java.io.Serializable;



public class CmsSiteModel implements Serializable{
	private static final long serialVersionUID = -8231141092547787190L;


	/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsSiteModel () {
		super();
	}

	/** Constructor for primary key */
	public CmsSiteModel (Integer id) {
		this.setId(id);
	}

	/** Constructor for required fields */
	public CmsSiteModel(Integer id, String field, String label, Integer priority) {
		this.setId(id);
		this.setField(field);
		this.setLabel(label);
		this.setPriority(priority);
	}
	/*[CONSTRUCTOR MARKER END]*/

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String field;
	private String label;
	private Integer priority;
	private String uploadPath;
	private String size;
	private String rows;
	private String cols;
	private String help;
	private String helpPosition;
	private Integer dataType;
	private Boolean single;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="model_id"
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
	 * Return the value associated with the column: model_label
	 */
	public String getField () {
		return field;
	}

	/**
	 * Set the value related to the column: model_label
	 * @param field the model_label value
	 */
	public void setField (String field) {
		this.field = field;
	}


	/**
	 * Return the value associated with the column: label
	 */
	public String getLabel () {
		return label;
	}

	/**
	 * Set the value related to the column: label
	 * @param label the label value
	 */
	public void setLabel (String label) {
		this.label = label;
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
	 * Return the value associated with the column: upload_path
	 */
	public String getUploadPath () {
		return uploadPath;
	}

	/**
	 * Set the value related to the column: upload_path
	 * @param uploadPath the upload_path value
	 */
	public void setUploadPath (String uploadPath) {
		this.uploadPath = uploadPath;
	}


	/**
	 * Return the value associated with the column: text_size
	 */
	public String getSize () {
		return size;
	}

	/**
	 * Set the value related to the column: text_size
	 * @param size the text_size value
	 */
	public void setSize (String size) {
		this.size = size;
	}


	/**
	 * Return the value associated with the column: area_rows
	 */
	public String getRows () {
		return rows;
	}

	/**
	 * Set the value related to the column: area_rows
	 * @param rows the area_rows value
	 */
	public void setRows (String rows) {
		this.rows = rows;
	}


	/**
	 * Return the value associated with the column: area_cols
	 */
	public String getCols () {
		return cols;
	}

	/**
	 * Set the value related to the column: area_cols
	 * @param cols the area_cols value
	 */
	public void setCols (String cols) {
		this.cols = cols;
	}


	/**
	 * Return the value associated with the column: help
	 */
	public String getHelp () {
		return help;
	}

	/**
	 * Set the value related to the column: help
	 * @param help the help value
	 */
	public void setHelp (String help) {
		this.help = help;
	}


	/**
	 * Return the value associated with the column: help_position
	 */
	public String getHelpPosition () {
		return helpPosition;
	}

	/**
	 * Set the value related to the column: help_position
	 * @param helpPosition the help_position value
	 */
	public void setHelpPosition (String helpPosition) {
		this.helpPosition = helpPosition;
	}


	/**
	 * Return the value associated with the column: data_type
	 */
	public Integer getDataType () {
		return dataType;
	}

	/**
	 * Set the value related to the column: data_type
	 * @param dataType the data_type value
	 */
	public void setDataType (Integer dataType) {
		this.dataType = dataType;
	}


	/**
	 * Return the value associated with the column: is_single
	 */
	public Boolean getSingle () {
		return single;
	}

	/**
	 * Set the value related to the column: is_single
	 * @param single the is_single value
	 */
	public void setSingle (Boolean single) {
		this.single = single;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.CmsSiteModel)) return false;
		else {
			foo.cms.entity.main.CmsSiteModel cmsSiteModel = (foo.cms.entity.main.CmsSiteModel) obj;
			if (null == this.getId() || null == cmsSiteModel.getId()) return false;
			else return (this.getId().equals(cmsSiteModel.getId()));
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