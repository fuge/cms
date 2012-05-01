package foo.cms.entity.main;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class CmsModelItem implements Serializable {
	private static final long serialVersionUID = 2511892385291701105L;



	public void init() {
		if (getPriority() == null) {
			setPriority(10);
		}
		if (getSingle() == null) {
			setSingle(true);
		}
		if (getCustom() == null) {
			setCustom(true);
		}
		if (getDisplay() == null) {
			setDisplay(true);
		}
	}

	// 将字符串字段全部设置为非null，方便判断。
	public void emptyToNull() {
		if (StringUtils.isBlank(getDefValue())) {
			setDefValue(null);
		}
		if (StringUtils.isBlank(getOptValue())) {
			setOptValue(null);
		}
		if (StringUtils.isBlank(getSize())) {
			setSize(null);
		}
		if (StringUtils.isBlank(getRows())) {
			setRows(null);
		}
		if (StringUtils.isBlank(getCols())) {
			setCols(null);
		}
		if (StringUtils.isBlank(getHelp())) {
			setHelp(null);
		}
		if (StringUtils.isBlank(getHelpPosition())) {
			setHelpPosition(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsModelItem () {
		super();
	}

	/** Constructor for primary key */
	public CmsModelItem (Integer id) {
		this.setId(id);
	}

	/** Constructor for required fields */
	public CmsModelItem(Integer id, foo.cms.entity.main.CmsModel model, String field, String label, 
			Integer dataType, Boolean single, Boolean channel, Boolean custom, Boolean display) {
		this.setId(id);
		this.setModel(model);
		this.setField(field);
		this.setLabel(label);
		this.setDataType(dataType);
		this.setSingle(single);
		this.setChannel(channel);
		this.setCustom(custom);
		this.setDisplay(display);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String field;
	private String label;
	private Integer priority;
	private String defValue;
	private String optValue;
	private String size;
	private String rows;
	private String cols;
	private String help;
	private String helpPosition;
	private Integer dataType;
	private Boolean single;
	private Boolean channel;
	private Boolean custom;
	private Boolean display;

	// many to one
	private foo.cms.entity.main.CmsModel model;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="modelitem_id"
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
	 * Return the value associated with the column: field
	 */
	public String getField () {
		return field;
	}

	/**
	 * Set the value related to the column: field
	 * @param field the field value
	 */
	public void setField (String field) {
		this.field = field;
	}


	/**
	 * Return the value associated with the column: item_label
	 */
	public String getLabel () {
		return label;
	}

	/**
	 * Set the value related to the column: item_label
	 * @param label the item_label value
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
	 * Return the value associated with the column: def_value
	 */
	public String getDefValue () {
		return defValue;
	}

	/**
	 * Set the value related to the column: def_value
	 * @param defValue the def_value value
	 */
	public void setDefValue (String defValue) {
		this.defValue = defValue;
	}


	/**
	 * Return the value associated with the column: opt_value
	 */
	public String getOptValue () {
		return optValue;
	}

	/**
	 * Set the value related to the column: opt_value
	 * @param optValue the opt_value value
	 */
	public void setOptValue (String optValue) {
		this.optValue = optValue;
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


	/**
	 * Return the value associated with the column: is_channel
	 */
	public Boolean getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: is_channel
	 * @param channel the is_channel value
	 */
	public void setChannel (Boolean channel) {
		this.channel = channel;
	}


	/**
	 * Return the value associated with the column: is_custom
	 */
	public Boolean getCustom () {
		return custom;
	}

	/**
	 * Set the value related to the column: is_custom
	 * @param custom the is_custom value
	 */
	public void setCustom (Boolean custom) {
		this.custom = custom;
	}


	/**
	 * Return the value associated with the column: is_display
	 */
	public Boolean getDisplay () {
		return display;
	}

	/**
	 * Set the value related to the column: is_display
	 * @param display the is_display value
	 */
	public void setDisplay (Boolean display) {
		this.display = display;
	}


	/**
	 * Return the value associated with the column: model_id
	 */
	public foo.cms.entity.main.CmsModel getModel () {
		return model;
	}

	/**
	 * Set the value related to the column: model_id
	 * @param model the model_id value
	 */
	public void setModel (foo.cms.entity.main.CmsModel model) {
		this.model = model;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.CmsModelItem)) return false;
		else {
			foo.cms.entity.main.CmsModelItem cmsModelItem = (foo.cms.entity.main.CmsModelItem) obj;
			if (null == this.getId() || null == cmsModelItem.getId()) return false;
			else return (this.getId().equals(cmsModelItem.getId()));
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