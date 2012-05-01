package foo.cms.entity.main;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ChannelTxt implements Serializable {
	private static final long serialVersionUID = 6103938315945336205L;

	public void init() {
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getTxt())) {
			setTxt(null);
		}
		if (StringUtils.isBlank(getTxt1())) {
			setTxt1(null);
		}
		if (StringUtils.isBlank(getTxt2())) {
			setTxt2(null);
		}
		if (StringUtils.isBlank(getTxt3())) {
			setTxt3(null);
		}
	}

	/**
	 * 是否所有属性都为空
	 * 
	 * @return
	 */
	public boolean isAllBlank() {
		return StringUtils.isBlank(getTxt()) && StringUtils.isBlank(getTxt1())
				&& StringUtils.isBlank(getTxt2())
				&& StringUtils.isBlank(getTxt3());
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ChannelTxt () {
	}

	/**
	 * Constructor for primary key
	 */
	public ChannelTxt (java.lang.Integer id) {
		this.setId(id);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String txt;
	private java.lang.String txt1;
	private java.lang.String txt2;
	private java.lang.String txt3;

	// one to one
	private foo.cms.entity.main.Channel channel;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="channel_id"
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
	 * Return the value associated with the column: txt
	 */
	public java.lang.String getTxt () {
		return txt;
	}

	/**
	 * Set the value related to the column: txt
	 * @param txt the txt value
	 */
	public void setTxt (java.lang.String txt) {
		this.txt = txt;
	}


	/**
	 * Return the value associated with the column: txt1
	 */
	public java.lang.String getTxt1 () {
		return txt1;
	}

	/**
	 * Set the value related to the column: txt1
	 * @param txt1 the txt1 value
	 */
	public void setTxt1 (java.lang.String txt1) {
		this.txt1 = txt1;
	}


	/**
	 * Return the value associated with the column: txt2
	 */
	public java.lang.String getTxt2 () {
		return txt2;
	}

	/**
	 * Set the value related to the column: txt2
	 * @param txt2 the txt2 value
	 */
	public void setTxt2 (java.lang.String txt2) {
		this.txt2 = txt2;
	}

	/**
	 * Return the value associated with the column: txt3
	 */
	public java.lang.String getTxt3 () {
		return txt3;
	}

	/**
	 * Set the value related to the column: txt3
	 * @param txt3 the txt3 value
	 */
	public void setTxt3 (java.lang.String txt3) {
		this.txt3 = txt3;
	}

	/**
	 * Return the value associated with the column: channel
	 */
	public foo.cms.entity.main.Channel getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: channel
	 * @param channel the channel value
	 */
	public void setChannel (foo.cms.entity.main.Channel channel) {
		this.channel = channel;
	}

	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.ChannelTxt)) return false;
		else {
			foo.cms.entity.main.ChannelTxt channelTxt = (foo.cms.entity.main.ChannelTxt) obj;
			if (null == this.getId() || null == channelTxt.getId()) return false;
			else return (this.getId().equals(channelTxt.getId()));
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