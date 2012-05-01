package foo.cms.entity.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import foo.common.hibernate3.PriorityInterface;

public class CmsGroup implements PriorityInterface, Serializable {
	private static final long serialVersionUID = -5597664752159583239L;

	/**
	 * 从集合中提取ID数组
	 * @param groups
	 * @return
	 */
	public static Integer[] fetchIds(Collection<CmsGroup> groups) {
		Integer[] ids = new Integer[groups.size()];
		int i = 0;
		for (CmsGroup g : groups) {
			ids[i++] = g.getId();
		}
		return ids;
	}

	/**
	 * 根据列表排序
	 * @param source 元素集合
	 * @param target 有顺序列表
	 * @return 一个新的、按目标排序的列表
	 */
	public static List<CmsGroup> sortByList(Set<CmsGroup> source,
			List<CmsGroup> target) {
		List<CmsGroup> list = new ArrayList<CmsGroup>(source.size());
		for (CmsGroup g : target) {
			if (source.contains(g)) {
				list.add(g);
			}
		}
		return list;
	}

	/**
	 * 是否允许上传后缀
	 * @param ext
	 * @return
	 */
	public boolean isAllowSuffix(String ext) {
		String suffix = getAllowSuffix();
		if (StringUtils.isBlank(suffix)) {
			return true;
		}
		String[] attr = StringUtils.split(suffix, ",");
		for (int i = 0, len = attr.length; i < len; i++) {
			if (attr[i].equals(ext)) {
				return true;
			}
		}
		return false;
	}

	public void init() {
		if (getRegDef() == null) {
			setRegDef(false);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsGroup () {
	}

	/** Constructor for primary key */
	public CmsGroup (java.lang.Integer id) {
		this.setId(id);
	}

	/** Constructor for required fields */
	public CmsGroup(Integer id, String name, Integer priority, Integer allowPerDay, 
			Integer allowMaxFile, Boolean needCaptcha, Boolean needCheck, Boolean regDef) {
		this.setId(id);
		this.setName(name);
		this.setPriority(priority);
		this.setAllowPerDay(allowPerDay);
		this.setAllowMaxFile(allowMaxFile);
		this.setNeedCaptcha(needCaptcha);
		this.setNeedCheck(needCheck);
		this.setRegDef(regDef);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.Integer priority;
	private java.lang.Integer allowPerDay;
	private java.lang.Integer allowMaxFile;
	private java.lang.String allowSuffix;
	private java.lang.Boolean needCaptcha;
	private java.lang.Boolean needCheck;
	private java.lang.Boolean regDef;

	// collections
	private java.util.Set<foo.cms.entity.main.Channel> viewChannels;
	private java.util.Set<foo.cms.entity.main.Channel> contriChannels;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="group_id"
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

	/** Return the value associated with the column: group_name */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: group_name
	 * @param name the group_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/** Return the value associated with the column: priority */
	public java.lang.Integer getPriority () {
		return priority;
	}

	/**
	 * Set the value related to the column: priority
	 * @param priority the priority value
	 */
	public void setPriority (java.lang.Integer priority) {
		this.priority = priority;
	}


	/** Return the value associated with the column: allow_per_day */
	public java.lang.Integer getAllowPerDay () {
		return allowPerDay;
	}

	/**
	 * Set the value related to the column: allow_per_day
	 * @param allowPerDay the allow_per_day value
	 */
	public void setAllowPerDay (java.lang.Integer allowPerDay) {
		this.allowPerDay = allowPerDay;
	}


	/** Return the value associated with the column: allow_max_file */
	public java.lang.Integer getAllowMaxFile () {
		return allowMaxFile;
	}

	/**
	 * Set the value related to the column: allow_max_file
	 * @param allowMaxFile the allow_max_file value
	 */
	public void setAllowMaxFile (java.lang.Integer allowMaxFile) {
		this.allowMaxFile = allowMaxFile;
	}


	/** Return the value associated with the column: allow_suffix */
	public java.lang.String getAllowSuffix () {
		return allowSuffix;
	}

	/**
	 * Set the value related to the column: allow_suffix
	 * @param allowSuffix the allow_suffix value
	 */
	public void setAllowSuffix (java.lang.String allowSuffix) {
		this.allowSuffix = allowSuffix;
	}


	/** Return the value associated with the column: need_captcha */
	public java.lang.Boolean getNeedCaptcha () {
		return needCaptcha;
	}

	/**
	 * Set the value related to the column: need_captcha
	 * @param needCaptcha the need_captcha value
	 */
	public void setNeedCaptcha (java.lang.Boolean needCaptcha) {
		this.needCaptcha = needCaptcha;
	}

	/** Return the value associated with the column: need_check */
	public java.lang.Boolean getNeedCheck () {
		return needCheck;
	}

	/**
	 * Set the value related to the column: need_check
	 * @param needCheck the need_check value
	 */
	public void setNeedCheck (java.lang.Boolean needCheck) {
		this.needCheck = needCheck;
	}


	/** Return the value associated with the column: is_reg_def */
	public java.lang.Boolean getRegDef () {
		return regDef;
	}

	/**
	 * Set the value related to the column: is_reg_def
	 * @param regDef the is_reg_def value
	 */
	public void setRegDef (java.lang.Boolean regDef) {
		this.regDef = regDef;
	}

	/** Return the value associated with the column: viewChannels */
	public java.util.Set<foo.cms.entity.main.Channel> getViewChannels () {
		return viewChannels;
	}

	/**
	 * Set the value related to the column: viewChannels
	 * @param viewChannels the viewChannels value
	 */
	public void setViewChannels (java.util.Set<foo.cms.entity.main.Channel> viewChannels) {
		this.viewChannels = viewChannels;
	}


	/** Return the value associated with the column: contriChannels */
	public java.util.Set<foo.cms.entity.main.Channel> getContriChannels () {
		return contriChannels;
	}

	/**
	 * Set the value related to the column: contriChannels
	 * @param contriChannels the contriChannels value
	 */
	public void setContriChannels (java.util.Set<foo.cms.entity.main.Channel> contriChannels) {
		this.contriChannels = contriChannels;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.CmsGroup)) return false;
		else {
			foo.cms.entity.main.CmsGroup cmsGroup = (foo.cms.entity.main.CmsGroup) obj;
			if (null == this.getId() || null == cmsGroup.getId()) return false;
			else return (this.getId().equals(cmsGroup.getId()));
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