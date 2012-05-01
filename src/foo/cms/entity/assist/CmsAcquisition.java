package foo.cms.entity.assist;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class CmsAcquisition implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4641597795655291127L;
	/**
	 * 动态页翻页页号
	 */
	public static final String PAGE = "[page]";
	/**
	 * 停止状态
	 */
	public static final int STOP = 0;
	/**
	 * 采集状态
	 */
	public static final int START = 1;
	/**
	 * 暂停状态
	 */
	public static final int PAUSE = 2;

	public static enum AcquisitionResultType {
		SUCCESS, TITLESTARTNOTFOUND, TITLEENDNOTFOUND, CONTENTSTARTNOTFOUND, CONTENTENDNOTFOUND, TITLEEXIST, URLEXIST, UNKNOW
	}
	
	public static enum AcquisitionRepeatCheckType{
		NONE, TITLE, URL
	}

	/**
	 * 是否停止
	 * 
	 * @return
	 */
	public boolean isStop() {
		int status = getStatus();
		return status == 0;
	}

	/**
	 * 是否暂停
	 * 
	 * @return
	 */
	public boolean isPuase() {
		int status = getStatus();
		return status == 2;
	}

	/**
	 * 是否中断。停止和暂停都需要中断。
	 * 
	 * @return
	 */
	public boolean isBreak() {
		int status = getStatus();
		return status == 0 || status == 2;
	}

	public String[] getPlans() {
		String plan = getPlanList();
		if (!StringUtils.isBlank(plan)) {
			return StringUtils.split(plan);
		} else {
			return new String[0];
		}
	}

	public String[] getAllPlans() {
		String[] plans = getPlans();
		Integer start = getDynamicStart();
		Integer end = getDynamicEnd();
		if (!StringUtils.isBlank(getDynamicAddr()) && start != null
				&& end != null && start >= 0 && end >= start) {
			int plansLen = plans.length;
			String[] allPlans = new String[plansLen + end - start + 1];
			for (int i = 0; i < plansLen; i++) {
				allPlans[i] = plans[i];
			}
			for (int i = 0, len = end - start + 1; i < len; i++) {
				allPlans[plansLen + i] = getDynamicAddrByNum(start + i);
			}
			return allPlans;
		} else {
			return plans;
		}
	}

	public String getDynamicAddrByNum(int num) {
		return StringUtils.replace(getDynamicAddr(), PAGE, String.valueOf(num));
	}

	public int getTotalNum() {
		int count = 0;
		Integer start = getDynamicStart();
		Integer end = getDynamicEnd();
		if (!StringUtils.isBlank(getDynamicAddr()) && start != null
				&& end != null) {
			count = end - start + 1;
		}
		if (!StringUtils.isBlank(getPlanList())) {
			count += getPlans().length;
		}
		return count;
	}

	public void init() {
		if (getStatus() == null) {
			setStatus(STOP);
		}
		if (getCurrNum() == null) {
			setCurrNum(0);
		}
		if (getCurrItem() == null) {
			setCurrItem(0);
		}
		if (getTotalItem() == null) {
			setTotalItem(0);
		}
		if (getPauseTime() == null) {
			setPauseTime(0);
		}
		if(getQueue()==null){
			setQueue(0);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	/**
	 * default constructor
	 */
	public CmsAcquisition() {
	}

	/**
	 * constructor for primary key
	 */
	public CmsAcquisition(Integer id) {
		this.setId(id);
	}

	/**
	 * constructor for required fields
	 */
	public CmsAcquisition(
		Integer id,
		foo.cms.entity.main.CmsUser user,
		foo.cms.entity.main.ContentType type,
		foo.cms.entity.main.CmsSite site,
		foo.cms.entity.main.Channel channel,
		String name,
		Integer status,
		Integer currNum,
		Integer currItem,
		Integer totalItem,
		Integer pauseTime,
		String pageEncoding,
		Integer queue) {

		this.setId(id);
		this.setUser(user);
		this.setType(type);
		this.setSite(site);
		this.setChannel(channel);
		this.setName(name);
		this.setStatus(status);
		this.setCurrNum(currNum);
		this.setCurrItem(currItem);
		this.setTotalItem(totalItem);
		this.setPauseTime(pauseTime);
		this.setPageEncoding(pageEncoding);
		this.setQueue(queue);
	}
	
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private Integer status;
	private Integer currNum;
	private Integer currItem;
	private Integer totalItem;
	private Integer pauseTime;
	private String pageEncoding;
	private String planList;
	private String dynamicAddr;
	private Integer dynamicStart;
	private Integer dynamicEnd;
	private String linksetStart;
	private String linksetEnd;
	private String linkStart;
	private String linkEnd;
	private String titleStart;
	private String titleEnd;
	private String keywordsStart;
	private String keywordsEnd;
	private String descriptionStart;
	private String descriptionEnd;
	private String contentStart;
	private String contentEnd;
	private String paginationStart;
	private String paginationEnd;
	private Integer queue;

	// many to one
	private foo.cms.entity.main.CmsUser user;
	private foo.cms.entity.main.ContentType type;
	private foo.cms.entity.main.CmsSite site;
	private foo.cms.entity.main.Channel channel;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="acquisition_id"
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
	 * Return the value associated with the column: acq_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: acq_name
	 * @param name the acq_name value
	 */
	public void setName (String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: start_time
	 */
	public java.util.Date getStartTime () {
		return startTime;
	}

	/**
	 * Set the value related to the column: start_time
	 * @param startTime the start_time value
	 */
	public void setStartTime (java.util.Date startTime) {
		this.startTime = startTime;
	}


	/**
	 * Return the value associated with the column: end_time
	 */
	public java.util.Date getEndTime () {
		return endTime;
	}

	/**
	 * Set the value related to the column: end_time
	 * @param endTime the end_time value
	 */
	public void setEndTime (java.util.Date endTime) {
		this.endTime = endTime;
	}


	/**
	 * Return the value associated with the column: status
	 */
	public Integer getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (Integer status) {
		this.status = status;
	}


	/**
	 * Return the value associated with the column: curr_num
	 */
	public Integer getCurrNum () {
		return currNum;
	}

	/**
	 * Set the value related to the column: curr_num
	 * @param currNum the curr_num value
	 */
	public void setCurrNum (Integer currNum) {
		this.currNum = currNum;
	}


	/**
	 * Return the value associated with the column: curr_item
	 */
	public Integer getCurrItem () {
		return currItem;
	}

	/**
	 * Set the value related to the column: curr_item
	 * @param currItem the curr_item value
	 */
	public void setCurrItem (Integer currItem) {
		this.currItem = currItem;
	}


	/**
	 * Return the value associated with the column: total_item
	 */
	public Integer getTotalItem () {
		return totalItem;
	}

	/**
	 * Set the value related to the column: total_item
	 * @param totalItem the total_item value
	 */
	public void setTotalItem (Integer totalItem) {
		this.totalItem = totalItem;
	}


	/**
	 * Return the value associated with the column: pause_time
	 */
	public Integer getPauseTime () {
		return pauseTime;
	}

	/**
	 * Set the value related to the column: pause_time
	 * @param pauseTime the pause_time value
	 */
	public void setPauseTime (Integer pauseTime) {
		this.pauseTime = pauseTime;
	}


	/**
	 * Return the value associated with the column: page_encoding
	 */
	public String getPageEncoding () {
		return pageEncoding;
	}

	/**
	 * Set the value related to the column: page_encoding
	 * @param pageEncoding the page_encoding value
	 */
	public void setPageEncoding (String pageEncoding) {
		this.pageEncoding = pageEncoding;
	}


	/**
	 * Return the value associated with the column: plan_list
	 */
	public String getPlanList () {
		return planList;
	}

	/**
	 * Set the value related to the column: plan_list
	 * @param planList the plan_list value
	 */
	public void setPlanList (String planList) {
		this.planList = planList;
	}


	/**
	 * Return the value associated with the column: dynamic_addr
	 */
	public String getDynamicAddr () {
		return dynamicAddr;
	}

	/**
	 * Set the value related to the column: dynamic_addr
	 * @param dynamicAddr the dynamic_addr value
	 */
	public void setDynamicAddr (String dynamicAddr) {
		this.dynamicAddr = dynamicAddr;
	}


	/**
	 * Return the value associated with the column: dynamic_start
	 */
	public Integer getDynamicStart () {
		return dynamicStart;
	}

	/**
	 * Set the value related to the column: dynamic_start
	 * @param dynamicStart the dynamic_start value
	 */
	public void setDynamicStart (Integer dynamicStart) {
		this.dynamicStart = dynamicStart;
	}


	/**
	 * Return the value associated with the column: dynamic_end
	 */
	public Integer getDynamicEnd () {
		return dynamicEnd;
	}

	/**
	 * Set the value related to the column: dynamic_end
	 * @param dynamicEnd the dynamic_end value
	 */
	public void setDynamicEnd (Integer dynamicEnd) {
		this.dynamicEnd = dynamicEnd;
	}


	/**
	 * Return the value associated with the column: linkset_start
	 */
	public String getLinksetStart () {
		return linksetStart;
	}

	/**
	 * Set the value related to the column: linkset_start
	 * @param linksetStart the linkset_start value
	 */
	public void setLinksetStart (String linksetStart) {
		this.linksetStart = linksetStart;
	}


	/**
	 * Return the value associated with the column: linkset_end
	 */
	public String getLinksetEnd () {
		return linksetEnd;
	}

	/**
	 * Set the value related to the column: linkset_end
	 * @param linksetEnd the linkset_end value
	 */
	public void setLinksetEnd (String linksetEnd) {
		this.linksetEnd = linksetEnd;
	}


	/**
	 * Return the value associated with the column: link_start
	 */
	public String getLinkStart () {
		return linkStart;
	}

	/**
	 * Set the value related to the column: link_start
	 * @param linkStart the link_start value
	 */
	public void setLinkStart (String linkStart) {
		this.linkStart = linkStart;
	}


	/**
	 * Return the value associated with the column: link_end
	 */
	public String getLinkEnd () {
		return linkEnd;
	}

	/**
	 * Set the value related to the column: link_end
	 * @param linkEnd the link_end value
	 */
	public void setLinkEnd (String linkEnd) {
		this.linkEnd = linkEnd;
	}


	/**
	 * Return the value associated with the column: title_start
	 */
	public String getTitleStart () {
		return titleStart;
	}

	/**
	 * Set the value related to the column: title_start
	 * @param titleStart the title_start value
	 */
	public void setTitleStart (String titleStart) {
		this.titleStart = titleStart;
	}


	/**
	 * Return the value associated with the column: title_end
	 */
	public String getTitleEnd () {
		return titleEnd;
	}

	/**
	 * Set the value related to the column: title_end
	 * @param titleEnd the title_end value
	 */
	public void setTitleEnd (String titleEnd) {
		this.titleEnd = titleEnd;
	}


	/**
	 * Return the value associated with the column: keywords_start
	 */
	public String getKeywordsStart () {
		return keywordsStart;
	}

	/**
	 * Set the value related to the column: keywords_start
	 * @param keywordsStart the keywords_start value
	 */
	public void setKeywordsStart (String keywordsStart) {
		this.keywordsStart = keywordsStart;
	}


	/**
	 * Return the value associated with the column: keywords_end
	 */
	public String getKeywordsEnd () {
		return keywordsEnd;
	}

	/**
	 * Set the value related to the column: keywords_end
	 * @param keywordsEnd the keywords_end value
	 */
	public void setKeywordsEnd (String keywordsEnd) {
		this.keywordsEnd = keywordsEnd;
	}


	/**
	 * Return the value associated with the column: description_start
	 */
	public String getDescriptionStart () {
		return descriptionStart;
	}

	/**
	 * Set the value related to the column: description_start
	 * @param descriptionStart the description_start value
	 */
	public void setDescriptionStart (String descriptionStart) {
		this.descriptionStart = descriptionStart;
	}


	/**
	 * Return the value associated with the column: description_end
	 */
	public String getDescriptionEnd () {
		return descriptionEnd;
	}

	/**
	 * Set the value related to the column: description_end
	 * @param descriptionEnd the description_end value
	 */
	public void setDescriptionEnd (String descriptionEnd) {
		this.descriptionEnd = descriptionEnd;
	}


	/**
	 * Return the value associated with the column: content_start
	 */
	public String getContentStart () {
		return contentStart;
	}

	/**
	 * Set the value related to the column: content_start
	 * @param contentStart the content_start value
	 */
	public void setContentStart (String contentStart) {
		this.contentStart = contentStart;
	}


	/**
	 * Return the value associated with the column: content_end
	 */
	public String getContentEnd () {
		return contentEnd;
	}

	/**
	 * Set the value related to the column: content_end
	 * @param contentEnd the content_end value
	 */
	public void setContentEnd (String contentEnd) {
		this.contentEnd = contentEnd;
	}


	/**
	 * Return the value associated with the column: pagination_start
	 */
	public String getPaginationStart () {
		return paginationStart;
	}

	/**
	 * Set the value related to the column: pagination_start
	 * @param paginationStart the pagination_start value
	 */
	public void setPaginationStart (String paginationStart) {
		this.paginationStart = paginationStart;
	}


	/**
	 * Return the value associated with the column: pagination_end
	 */
	public String getPaginationEnd () {
		return paginationEnd;
	}

	/**
	 * Set the value related to the column: pagination_end
	 * @param paginationEnd the pagination_end value
	 */
	public void setPaginationEnd (String paginationEnd) {
		this.paginationEnd = paginationEnd;
	}


	/**
	 * Return the value associated with the column: queue
	 */
	public Integer getQueue () {
		return queue;
	}

	/**
	 * Set the value related to the column: queue
	 * @param queue the queue value
	 */
	public void setQueue (Integer queue) {
		this.queue = queue;
	}


	/**
	 * Return the value associated with the column: user_id
	 */
	public foo.cms.entity.main.CmsUser getUser () {
		return user;
	}

	/**
	 * Set the value related to the column: user_id
	 * @param user the user_id value
	 */
	public void setUser (foo.cms.entity.main.CmsUser user) {
		this.user = user;
	}


	/**
	 * Return the value associated with the column: type_id
	 */
	public foo.cms.entity.main.ContentType getType () {
		return type;
	}

	/**
	 * Set the value related to the column: type_id
	 * @param type the type_id value
	 */
	public void setType (foo.cms.entity.main.ContentType type) {
		this.type = type;
	}


	/**
	 * Return the value associated with the column: site_id
	 */
	public foo.cms.entity.main.CmsSite getSite () {
		return site;
	}

	/**
	 * Set the value related to the column: site_id
	 * @param site the site_id value
	 */
	public void setSite (foo.cms.entity.main.CmsSite site) {
		this.site = site;
	}

	/**
	 * Return the value associated with the column: channel_id
	 */
	public foo.cms.entity.main.Channel getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: channel_id
	 * @param channel the channel_id value
	 */
	public void setChannel (foo.cms.entity.main.Channel channel) {
		this.channel = channel;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof CmsAcquisition)) return false;
		else {
			CmsAcquisition cmsAcquisition = (CmsAcquisition) obj;
			if (null == this.getId() || null == cmsAcquisition.getId()) return false;
			else return (this.getId().equals(cmsAcquisition.getId()));
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