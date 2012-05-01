package foo.cms.entity.main;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import foo.common.hibernate3.PriorityInterface;
import foo.core.entity.UnifiedUser;

public class CmsUser implements PriorityInterface, Serializable  {
	private static final long serialVersionUID = 5628795315993932021L;

	public Byte getCheckStep(Integer siteId) {
		CmsUserSite us = getUserSite(siteId);
		if (us != null) {
			return getUserSite(siteId).getCheckStep();
		} else {
			return null;
		}
	}

	public String getRealname() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getRealname();
		} else {
			return null;
		}
	}

	public Boolean getGender() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getGender();
		} else {
			return null;
		}
	}

	public Date getBirthday() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getBirthday();
		} else {
			return null;
		}
	}

	public String getIntro() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getIntro();
		} else {
			return null;
		}
	}

	public String getComefrom() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getComefrom();
		} else {
			return null;
		}
	}

	public String getQq() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getQq();
		} else {
			return null;
		}
	}

	public String getMsn() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getMsn();
		} else {
			return null;
		}
	}

	public String getPhone() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getPhone();
		} else {
			return null;
		}
	}

	public String getMobile() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getMobile();
		} else {
			return null;
		}
	}
	public String getUserImg() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getUserImg();
		} else {
			return null;
		}
	}
	public String getUserSignature() {
		CmsUserExt ext = getUserExt();
		if (ext != null) {
			return ext.getUserSignature();
		} else {
			return null;
		}
	}

	public CmsUserExt getUserExt() {
		Set<CmsUserExt> set = getUserExtSet();
		if (set != null && set.size() > 0) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	public CmsUserSite getUserSite(Integer siteId) {
		Set<CmsUserSite> set = getUserSites();
		for (CmsUserSite us : set) {
			if (us.getSite().getId().equals(siteId)) {
				return us;
			}
		}
		return null;
	}

	public Set<Channel> getChannels(Integer siteId) {
		Set<Channel> set = getChannels();
		Set<Channel> results = new HashSet<Channel>();
		for (Channel c : set) {
			if (c.getSite().getId().equals(siteId)) {
				results.add(c);
			}
		}
		return results;
	}

	public Integer[] getChannelIds() {
		Set<Channel> channels = getChannels();
		return Channel.fetchIds(channels);
	}

	public Set<Integer> getChannelIds(Integer siteId) {
		Set<Channel> channels = getChannels();
		Set<Integer> ids = new HashSet<Integer>();
		for (Channel c : channels) {
			if (c.getSite().getId().equals(siteId)) {
				ids.add(c.getId());
			}
		}
		return ids;
	}

	public Integer[] getRoleIds() {
		Set<CmsRole> roles = getRoles();
		return CmsRole.fetchIds(roles);
	}

	public Integer[] getSiteIds() {
		Set<CmsSite> sites = getSites();
		return CmsSite.fetchIds(sites);
	}

	public void addToRoles(CmsRole role) {
		if (role == null) {
			return;
		}
		Set<CmsRole> set = getRoles();
		if (set == null) {
			set = new HashSet<CmsRole>();
			setRoles(set);
		}
		set.add(role);
	}

	public void addToChannels(Channel channel) {
		if (channel == null) {
			return;
		}
		Set<Channel> set = getChannels();
		if (set == null) {
			set = new HashSet<Channel>();
			setChannels(set);
		}
		set.add(channel);
	}
	
	public void addToCollection(Content content) {
		if (content == null) {
			return;
		}
		Set<Content> set =getCollectContents();
		if (set == null) {
			set = new HashSet<Content>();
			setCollectContents(set);
		}
		set.add(content);
	}
	public void delFromCollection(Content content) {
		if (content == null) {
			return;
		}
		Set<Content> set =getCollectContents();
		if (set == null) {
			return;
		}else{
			set.remove(content);
		}
	}
	public void clearCollection() {
		getCollectContents().clear();
	}

	public Set<CmsSite> getSites() {
		Set<CmsUserSite> userSites = getUserSites();
		Set<CmsSite> sites = new HashSet<CmsSite>(userSites.size());
		for (CmsUserSite us : userSites) {
			sites.add(us.getSite());
		}
		return sites;
	}

	public boolean isSuper() {
		Set<CmsRole> roles = getRoles();
		if (roles == null) {
			return false;
		}
		for (CmsRole role : getRoles()) {
			if (role.getSuper()) {
				return true;
			}
		}
		return false;
	}

	public Set<String> getPerms() {
		Set<CmsRole> roles = getRoles();
		if (roles == null) {
			return null;
		}
		Set<String> allPerms = new HashSet<String>();
		for (CmsRole role : getRoles()) {
			allPerms.addAll(role.getPerms());
		}
		return allPerms;
	}

	/**
	 * 是否允许上传，根据每日限额
	 * 
	 * @param size
	 * @return
	 */
	public boolean isAllowPerDay(int size) {
		int allowPerDay = getGroup().getAllowPerDay();
		if (allowPerDay == 0) {
			return true;
		}
		if (getUploadDate() != null) {
			if (isToday(getUploadDate())) {
				size += getUploadSize();
			}
		}
		return allowPerDay >= size;
	}

	/**
	 * 是否允许上传，根据文件大小
	 * 
	 * @param size
	 *            文件大小，单位KB
	 * @return
	 */
	public boolean isAllowMaxFile(int size) {
		int allowPerFile = getGroup().getAllowMaxFile();
		if (allowPerFile == 0) {
			return true;
		} else {
			return allowPerFile >= size;
		}
	}

	/**
	 * 是否允许上传后缀
	 * 
	 * @param ext
	 * @return
	 */
	public boolean isAllowSuffix(String ext) {
		return getGroup().isAllowSuffix(ext);
	}

	public void forMember(UnifiedUser u) {
		forUser(u);
		setAdmin(false);
		setRank(0);
		setViewonlyAdmin(false);
		setSelfAdmin(false);
	}

	public void forAdmin(UnifiedUser u, boolean viewonly, boolean selfAdmin,
			int rank) {
		forUser(u);
		setAdmin(true);
		setRank(rank);
		setViewonlyAdmin(viewonly);
		setSelfAdmin(selfAdmin);
	}

	public void forUser(UnifiedUser u) {
		setDisabled(false);
		setId(u.getId());
		setUsername(u.getUsername());
		setEmail(u.getEmail());
		setRegisterIp(u.getRegisterIp());
		setRegisterTime(u.getRegisterTime());
		setLastLoginIp(u.getLastLoginIp());
		setLastLoginTime(u.getLastLoginTime());
		setLoginCount(0);
	}

	public void init() {
		if (getUploadTotal() == null) {
			setUploadTotal(0L);
		}
		if (getUploadSize() == null) {
			setUploadSize(0);
		}
		if (getUploadDate() == null) {
			setUploadDate(new java.sql.Date(System.currentTimeMillis()));
		}
		if (getAdmin() == null) {
			setAdmin(false);
		}
		if (getRank() == null) {
			setRank(0);
		}
		if (getViewonlyAdmin() == null) {
			setViewonlyAdmin(false);
		}
		if (getSelfAdmin() == null) {
			setSelfAdmin(false);
		}
		if (getDisabled() == null) {
			setDisabled(false);
		}
	}

	public static Integer[] fetchIds(Collection<CmsUser> users) {
		if (users == null) {
			return null;
		}
		Integer[] ids = new Integer[users.size()];
		int i = 0;
		for (CmsUser u : users) {
			ids[i++] = u.getId();
		}
		return ids;
	}

	/**
	 * 用于排列顺序。此处优先级为0，则按ID升序排。
	 */
	public Number getPriority() {
		return 0;
	}

	/**
	 * 是否是今天。根据System.currentTimeMillis() / 1000 / 60 / 60 / 24计算。
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		long day = date.getTime() / 1000 / 60 / 60 / 24;
		long currentDay = System.currentTimeMillis() / 1000 / 60 / 60 / 24;
		return day == currentDay;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsUser() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsUser(Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsUser(Integer id, CmsGroup group, String username,
			java.util.Date registerTime, String registerIp, Integer loginCount,
			Integer rank, Long uploadTotal, Integer uploadSize, Boolean admin,
			Boolean viewonlyAdmin, Boolean selfAdmin, Boolean disabled) {
		this.setId(id);
		this.setGroup(group);
		this.setUsername(username);
		this.setRegisterTime(registerTime);
		this.setRegisterIp(registerIp);
		this.setLoginCount(loginCount);
		this.setRank(rank);
		this.setUploadTotal(uploadTotal);
		this.setUploadSize(uploadSize);
		this.setAdmin(admin);
		this.setViewonlyAdmin(viewonlyAdmin);
		this.setSelfAdmin(selfAdmin);
		this.setDisabled(disabled);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String username;
	private String email;
	private java.util.Date registerTime;
	private String registerIp;
	private java.util.Date lastLoginTime;
	private String lastLoginIp;
	private Integer loginCount;
	private Integer rank;
	private Long uploadTotal;
	private Integer uploadSize;
	private java.sql.Date uploadDate;
	private Boolean admin;
	private Boolean viewonlyAdmin;
	private Boolean selfAdmin;
	private Boolean disabled;

	// many to one
	private foo.cms.entity.main.CmsGroup group;

	// collections
	private java.util.Set<foo.cms.entity.main.CmsUserExt> userExtSet;
	private java.util.Set<foo.cms.entity.main.CmsUserSite> userSites;
	private java.util.Set<foo.cms.entity.main.CmsRole> roles;
	private java.util.Set<foo.cms.entity.main.Channel> channels;
	private java.util.Set<foo.cms.entity.main.Content> collectContents;
	
	private java.util.Set<foo.cms.entity.assist.CmsMessage> sendMessages;
	private java.util.Set<foo.cms.entity.assist.CmsMessage> receivMessages;
	private java.util.Set<foo.cms.entity.assist.CmsReceiverMessage> sendReceiverMessages;
	private java.util.Set<foo.cms.entity.assist.CmsReceiverMessage> receivReceiverMessages;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="user_id"
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
	 * Return the value associated with the column: register_time
	 */
	public java.util.Date getRegisterTime () {
		return registerTime;
	}

	/**
	 * Set the value related to the column: register_time
	 * @param registerTime the register_time value
	 */
	public void setRegisterTime (java.util.Date registerTime) {
		this.registerTime = registerTime;
	}


	/**
	 * Return the value associated with the column: register_ip
	 */
	public String getRegisterIp () {
		return registerIp;
	}

	/**
	 * Set the value related to the column: register_ip
	 * @param registerIp the register_ip value
	 */
	public void setRegisterIp (String registerIp) {
		this.registerIp = registerIp;
	}


	/**
	 * Return the value associated with the column: last_login_time
	 */
	public java.util.Date getLastLoginTime () {
		return lastLoginTime;
	}

	/**
	 * Set the value related to the column: last_login_time
	 * @param lastLoginTime the last_login_time value
	 */
	public void setLastLoginTime (java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}


	/**
	 * Return the value associated with the column: last_login_ip
	 */
	public String getLastLoginIp () {
		return lastLoginIp;
	}

	/**
	 * Set the value related to the column: last_login_ip
	 * @param lastLoginIp the last_login_ip value
	 */
	public void setLastLoginIp (String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}


	/**
	 * Return the value associated with the column: login_count
	 */
	public Integer getLoginCount () {
		return loginCount;
	}

	/**
	 * Set the value related to the column: login_count
	 * @param loginCount the login_count value
	 */
	public void setLoginCount (Integer loginCount) {
		this.loginCount = loginCount;
	}


	/**
	 * Return the value associated with the column: rank
	 */
	public Integer getRank () {
		return rank;
	}

	/**
	 * Set the value related to the column: rank
	 * @param rank the rank value
	 */
	public void setRank (Integer rank) {
		this.rank = rank;
	}


	/**
	 * Return the value associated with the column: upload_total
	 */
	public Long getUploadTotal () {
		return uploadTotal;
	}

	/**
	 * Set the value related to the column: upload_total
	 * @param uploadTotal the upload_total value
	 */
	public void setUploadTotal (Long uploadTotal) {
		this.uploadTotal = uploadTotal;
	}


	/**
	 * Return the value associated with the column: upload_size
	 */
	public Integer getUploadSize () {
		return uploadSize;
	}

	/**
	 * Set the value related to the column: upload_size
	 * @param uploadSize the upload_size value
	 */
	public void setUploadSize (Integer uploadSize) {
		this.uploadSize = uploadSize;
	}


	/**
	 * Return the value associated with the column: upload_date
	 */
	public java.sql.Date getUploadDate () {
		return uploadDate;
	}

	/**
	 * Set the value related to the column: upload_date
	 * @param uploadDate the upload_date value
	 */
	public void setUploadDate (java.sql.Date uploadDate) {
		this.uploadDate = uploadDate;
	}


	/**
	 * Return the value associated with the column: is_admin
	 */
	public Boolean getAdmin () {
		return admin;
	}

	/**
	 * Set the value related to the column: is_admin
	 * @param admin the is_admin value
	 */
	public void setAdmin (Boolean admin) {
		this.admin = admin;
	}


	/**
	 * Return the value associated with the column: is_viewonly_admin
	 */
	public Boolean getViewonlyAdmin () {
		return viewonlyAdmin;
	}

	/**
	 * Set the value related to the column: is_viewonly_admin
	 * @param viewonlyAdmin the is_viewonly_admin value
	 */
	public void setViewonlyAdmin (Boolean viewonlyAdmin) {
		this.viewonlyAdmin = viewonlyAdmin;
	}


	/**
	 * Return the value associated with the column: is_self_admin
	 */
	public Boolean getSelfAdmin () {
		return selfAdmin;
	}

	/**
	 * Set the value related to the column: is_self_admin
	 * @param selfAdmin the is_self_admin value
	 */
	public void setSelfAdmin (Boolean selfAdmin) {
		this.selfAdmin = selfAdmin;
	}


	/**
	 * Return the value associated with the column: is_disabled
	 */
	public Boolean getDisabled () {
		return disabled;
	}

	/**
	 * Set the value related to the column: is_disabled
	 * @param disabled the is_disabled value
	 */
	public void setDisabled (Boolean disabled) {
		this.disabled = disabled;
	}
	


	/**
	 * Return the value associated with the column: group_id
	 */
	public foo.cms.entity.main.CmsGroup getGroup () {
		return group;
	}

	/**
	 * Set the value related to the column: group_id
	 * @param group the group_id value
	 */
	public void setGroup (foo.cms.entity.main.CmsGroup group) {
		this.group = group;
	}


	/**
	 * Return the value associated with the column: userExtSet
	 */
	public java.util.Set<foo.cms.entity.main.CmsUserExt> getUserExtSet () {
		return userExtSet;
	}

	/**
	 * Set the value related to the column: userExtSet
	 * @param userExtSet the userExtSet value
	 */
	public void setUserExtSet (java.util.Set<foo.cms.entity.main.CmsUserExt> userExtSet) {
		this.userExtSet = userExtSet;
	}


	/**
	 * Return the value associated with the column: userSites
	 */
	public java.util.Set<foo.cms.entity.main.CmsUserSite> getUserSites () {
		return userSites;
	}

	/**
	 * Set the value related to the column: userSites
	 * @param userSites the userSites value
	 */
	public void setUserSites (java.util.Set<foo.cms.entity.main.CmsUserSite> userSites) {
		this.userSites = userSites;
	}


	/**
	 * Return the value associated with the column: roles
	 */
	public java.util.Set<foo.cms.entity.main.CmsRole> getRoles () {
		return roles;
	}

	/**
	 * Set the value related to the column: roles
	 * @param roles the roles value
	 */
	public void setRoles (java.util.Set<foo.cms.entity.main.CmsRole> roles) {
		this.roles = roles;
	}


	/**
	 * Return the value associated with the column: channels
	 */
	public java.util.Set<foo.cms.entity.main.Channel> getChannels () {
		return channels;
	}

	/**
	 * Set the value related to the column: channels
	 * @param channels the channels value
	 */
	public void setChannels (java.util.Set<foo.cms.entity.main.Channel> channels) {
		this.channels = channels;
	}
	

	public java.util.Set<foo.cms.entity.main.Content> getCollectContents() {
		return collectContents;
	}

	public void setCollectContents(
			java.util.Set<foo.cms.entity.main.Content> collectContents) {
		this.collectContents = collectContents;
	}
	
	public java.util.Set<foo.cms.entity.assist.CmsMessage> getSendMessages() {
		return sendMessages;
	}

	public void setSendMessages(
			java.util.Set<foo.cms.entity.assist.CmsMessage> sendMessages) {
		this.sendMessages = sendMessages;
	}

	public java.util.Set<foo.cms.entity.assist.CmsMessage> getReceivMessages() {
		return receivMessages;
	}

	public void setReceivMessages(
			java.util.Set<foo.cms.entity.assist.CmsMessage> receivMessages) {
		this.receivMessages = receivMessages;
	}

	public java.util.Set<foo.cms.entity.assist.CmsReceiverMessage> getSendReceiverMessages() {
		return sendReceiverMessages;
	}

	public void setSendReceiverMessages(
			java.util.Set<foo.cms.entity.assist.CmsReceiverMessage> sendReceiverMessages) {
		this.sendReceiverMessages = sendReceiverMessages;
	}

	public java.util.Set<foo.cms.entity.assist.CmsReceiverMessage> getReceivReceiverMessages() {
		return receivReceiverMessages;
	}

	public void setReceivReceiverMessages(
			java.util.Set<foo.cms.entity.assist.CmsReceiverMessage> receivReceiverMessages) {
		this.receivReceiverMessages = receivReceiverMessages;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.CmsUser)) return false;
		else {
			foo.cms.entity.main.CmsUser cmsUser = (foo.cms.entity.main.CmsUser) obj;
			if (null == this.getId() || null == cmsUser.getId()) return false;
			else return (this.getId().equals(cmsUser.getId()));
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