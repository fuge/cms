package foo.cms.entity.main;

import static foo.common.web.Constants.INDEX;
import static foo.common.web.Constants.SPT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import foo.cms.staticpage.StaticPageUtils;
import foo.common.hibernate3.HibernateTree;
import foo.common.hibernate3.PriorityComparator;
import foo.common.hibernate3.PriorityInterface;

/**
 * 栏目实体类
 * @author liufang
 */
public class Channel implements HibernateTree<Integer>, PriorityInterface, Serializable{
	private static final long serialVersionUID = 4244849829511700387L;

	/**
	 * 审核后内容修改方式
	 * @author liufang
	 */
	public static enum AfterCheckEnum {
		/** 不能修改，不能删除。 */
		CANNOT_UPDATE,
		/** 可以修改，可以删除。 修改后文章的审核级别将退回到修改人级别的状态。如果修改人的级别高于当前文章的审核级别，那么文章审核级别将保持不变。 */
		BACK_UPDATE,
		/** 可以修改，可以删除。 修改后文章保持原状态。 */
		KEEP_UPDATE
	}

	/**
	 * 获得URL地址
	 * @return
	 */
	public String getUrl() {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		if (getStaticChannel()) {
			return getUrlStatic(false, 1);
		} else {
			return getUrlDynamic(null);
		}
	}

	public String getUrlWhole() {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		if (getStaticChannel()) {
			return getUrlStatic(true, 1);
		} else {
			return getUrlDynamic(true);
		}
	}

	/**
	 * 获得静态URL地址
	 * @return
	 */
	public String getUrlStatic() {
		return getUrlStatic(null, 1);
	}

	public String getUrlStatic(int pageNo) {
		return getUrlStatic(null, pageNo);
	}

	public String getUrlStatic(Boolean whole, int pageNo) {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		CmsSite site = getSite();
		StringBuilder url = site.getUrlBuffer(false, whole, false);
		String filename = getStaticFilenameByRule();
		if (!StringUtils.isBlank(filename)) {
			if (pageNo > 1) {
				int index = filename.indexOf(".", filename.lastIndexOf("/"));
				if (index != -1) {
					url.append(filename.substring(0, index));
					url.append("_").append(pageNo);
					url.append(filename.substring(index));
				} else {
					url.append("_").append(pageNo);
				}
			} else {
				if (getAccessByDir()) {
					url.append(filename.substring(0,
							filename.lastIndexOf("/") + 1));
				} else {
					url.append(filename);
				}
			}
		} else {
			// 默认静态页面访问路径
			url.append(SPT).append(getPath());
			if (pageNo > 1) {
				url.append("_").append(pageNo);
				url.append(site.getStaticSuffix());
			} else {
				if (getHasContent()) {
					url.append(SPT);
				} else {
					url.append(site.getStaticSuffix());
				}
			}
		}
		return url.toString();
	}

	public String getStaticFilename(int pageNo) {
		CmsSite site = getSite();
		StringBuilder url = new StringBuilder();
		String staticDir = site.getStaticDir();
		if (!StringUtils.isBlank(staticDir)) {
			url.append(staticDir);
		}
		String filename = getStaticFilenameByRule();
		if (!StringUtils.isBlank(filename)) {
			int index = filename.indexOf(".", filename.lastIndexOf("/"));
			if (pageNo > 1) {
				if (index != -1) {
					url.append(filename.substring(0, index)).append("_")
							.append(pageNo).append(filename.substring(index));
				} else {
					url.append(filename).append("_").append(pageNo);
				}
			} else {
				url.append(filename);
			}
		} else {
			// 默认静态页面访问路径
			url.append(SPT).append(getPath());
			String suffix = site.getStaticSuffix();
			if (getHasContent()) {
				url.append(SPT).append(INDEX);
				if (pageNo > 1) {
					url.append("_").append(pageNo);
				}
				url.append(suffix);
			} else {
				if (pageNo > 1) {
					url.append("_").append(pageNo);
				}
				url.append(suffix);
			}
		}
		return url.toString();
	}

	public String getStaticFilenameByRule() {
		String rule = getChannelRule();
		if (StringUtils.isBlank(rule)) {
			return null;
		}
		CmsModel model = getModel();
		String url = StaticPageUtils.staticUrlRule(rule, model.getId(), model
				.getPath(), getId(), getPath(), null, null);
		return url;
	}

	/**
	 * 获得动态URL地址
	 * @return
	 */
	public String getUrlDynamic() {
		return getUrlDynamic(null);
	}

	public String getUrlDynamic(Boolean whole) {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		CmsSite site = getSite();
		StringBuilder url = site.getUrlBuffer(true, whole, false);
		url.append(SPT).append(getPath());
		if (getHasContent()) {
			url.append(SPT).append(INDEX);
		}
		url.append(site.getDynamicSuffix());
		return url.toString();
	}

	/**
	 * 获得节点列表。从父节点到自身。
	 * @return
	 */
	public List<Channel> getNodeList() {
		LinkedList<Channel> list = new LinkedList<Channel>();
		Channel node = this;
		while (node != null) {
			list.addFirst(node);
			node = node.getParent();
		}
		return list;
	}

	/**
	 * 获得节点列表ID。从父节点到自身。
	 * @return
	 */
	public Integer[] getNodeIds() {
		List<Channel> channels = getNodeList();
		Integer[] ids = new Integer[channels.size()];
		int i = 0;
		for (Channel c : channels) {
			ids[i++] = c.getId();
		}
		return ids;
	}

	/**
	 * 获得深度
	 * @return 第一层为0，第二层为1，以此类推。
	 */
	public int getDeep() {
		int deep = 0;
		Channel parent = getParent();
		while (parent != null) {
			deep++;
			parent = parent.getParent();
		}
		return deep;
	}

	/**
	 * 获得栏目终审级别
	 * @return
	 */
	public Byte getFinalStepExtends() {
		Byte step = getFinalStep();
		if (step == null) {
			Channel parent = getParent();
			if (parent == null) {
				return getSite().getFinalStep();
			} else {
				return parent.getFinalStepExtends();
			}
		} else {
			return step;
		}
	}

	public Byte getAfterCheck() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getAfterCheck();
		} else {
			return null;
		}
	}

	/**
	 * 获得审核后修改方式的枚举值。如果该值为null则取父级栏目，父栏目为null则取站点相关设置。
	 * @return
	 */
	public AfterCheckEnum getAfterCheckEnum() {
		Byte after = getChannelExt().getAfterCheck();
		Channel channel = getParent();
		// 如果为null，则查找父栏目。
		while (after == null && channel != null) {
			after = channel.getAfterCheck();
			channel = channel.getParent();
		}
		// 如果依然为null，则查找站点设置
		if (after == null) {
			after = getSite().getAfterCheck();
		}
		if (after == 1) {
			return AfterCheckEnum.CANNOT_UPDATE;
		} else if (after == 2) {
			return AfterCheckEnum.BACK_UPDATE;
		} else if (after == 3) {
			return AfterCheckEnum.KEEP_UPDATE;
		} else {
			// 默认为不可改、不可删
			return AfterCheckEnum.CANNOT_UPDATE;
		}
	}

	/**
	 * 获得列表用于下拉选择。条件：有内容的栏目。
	 * @return
	 */
	public List<Channel> getListForSelect(Set<Channel> rights,
			boolean hasContentOnly) {
		return getListForSelect(rights, null, hasContentOnly);
	}

	public List<Channel> getListForSelect(Set<Channel> rights, Channel exclude,
			boolean hasContentOnly) {
		List<Channel> list = new ArrayList<Channel>((getRgt() - getLft()) / 2);
		addChildToList(list, this, rights, exclude, hasContentOnly);
		return list;
	}

	/**
	 * 获得列表用于下拉选择。条件：有内容的栏目。
	 * @param topList 顶级栏目
	 * @return
	 */
	public static List<Channel> getListForSelect(List<Channel> topList,
			Set<Channel> rights, boolean hasContentOnly) {
		return getListForSelect(topList, rights, null, hasContentOnly);
	}

	public static List<Channel> getListForSelect(List<Channel> topList,
			Set<Channel> rights, Channel exclude, boolean hasContentOnly) {
		List<Channel> list = new ArrayList<Channel>();
		for (Channel c : topList) {
			addChildToList(list, c, rights, exclude, hasContentOnly);
		}
		return list;
	}

	/**
	 * 递归将子栏目加入列表。条件：有内容的栏目。
	 * @param list    栏目容器
	 * @param channel 待添加的栏目，且递归添加子栏目
	 * @param rights  有权限的栏目，为null不控制权限。
	 */
	private static void addChildToList(List<Channel> list, Channel channel,
			Set<Channel> rights, Channel exclude, boolean hasContentOnly) {
		if ((rights != null && !rights.contains(channel))
				|| (exclude != null && exclude.equals(channel))) {
			return;
		}
		list.add(channel);
		Set<Channel> child = channel.getChild();
		for (Channel c : child) {
			if (hasContentOnly) {
				if (c.getHasContent()) {
					addChildToList(list, c, rights, exclude, hasContentOnly);
				}
			} else {
				addChildToList(list, c, rights, exclude, hasContentOnly);
			}
		}
	}

	public String getTplChannelOrDef() {
		String tpl = getTplChannel();
		if (!StringUtils.isBlank(tpl)) {
			return tpl;
		} else {
			String sol = getSite().getSolutionPath();
			return getModel().getTplChannel(sol, true);
		}
	}

	public String getTplContentOrDef() {
		String tpl = getTplContent();
		if (!StringUtils.isBlank(tpl)) {
			return tpl;
		} else {
			String sol = getSite().getSolutionPath();
			return getModel().getTplContent(sol, true);
		}
	}

	public Integer[] getUserIds() {
		Set<CmsUser> users = getUsers();
		return CmsUser.fetchIds(users);
	}

	public void addToViewGroups(CmsGroup group) {
		Set<CmsGroup> groups = getViewGroups();
		if (groups == null) {
			groups = new TreeSet<CmsGroup>(new PriorityComparator());
			setViewGroups(groups);
		}
		groups.add(group);
		group.getViewChannels().add(this);
	}

	public void addToContriGroups(CmsGroup group) {
		Set<CmsGroup> groups = getContriGroups();
		if (groups == null) {
			groups = new TreeSet<CmsGroup>(new PriorityComparator());
			setContriGroups(groups);
		}
		groups.add(group);
		group.getContriChannels().add(this);
	}

	public void addToUsers(CmsUser user) {
		Set<CmsUser> set = getUsers();
		if (set == null) {
			set = new TreeSet<CmsUser>(new PriorityComparator());
			setUsers(set);
		}
		set.add(user);
		user.addToChannels(this);
	}

	public void init() {
		if (getPriority() == null) {
			setPriority(10);
		}
		if (getDisplay() == null) {
			setDisplay(true);
		}
	}

	public String getName() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getName();
		} else {
			return null;
		}
	}

	public Boolean getStaticChannel() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getStaticChannel();
		} else {
			return null;
		}
	}

	public Boolean getStaticContent() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getStaticContent();
		} else {
			return null;
		}
	}

	public Boolean getAccessByDir() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getAccessByDir();
		} else {
			return null;
		}
	}

	public Boolean getListChild() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getListChild();
		} else {
			return null;
		}
	}

	public Integer getPageSize() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getPageSize();
		} else {
			return null;
		}
	}

	public String getChannelRule() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getChannelRule();
		} else {
			return null;
		}
	}

	public String getContentRule() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getContentRule();
		} else {
			return null;
		}
	}

	public Byte getFinalStep() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getFinalStep();
		} else {
			return null;
		}
	}

	public String getLink() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getLink();
		} else {
			return null;
		}
	}

	public String getTplChannel() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTplChannel();
		} else {
			return null;
		}
	}

	public String getTplContent() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTplContent();
		} else {
			return null;
		}
	}

	public Boolean getHasTitleImg() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getHasTitleImg();
		} else {
			return null;
		}
	}

	public Boolean getHasContentImg() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getHasContentImg();
		} else {
			return null;
		}
	}

	public Integer getTitleImgWidth() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTitleImgWidth();
		} else {
			return null;
		}
	}

	public Integer getTitleImgHeight() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTitleImgHeight();
		} else {
			return null;
		}
	}

	public Integer getContentImgWidth() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getContentImgWidth();
		} else {
			return null;
		}
	}

	public Integer getContentImgHeight() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getContentImgHeight();
		} else {
			return null;
		}
	}

	public String getTitleImg() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTitleImg();
		} else {
			return null;
		}
	}

	public String getContentImg() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getContentImg();
		} else {
			return null;
		}
	}

	public String getTitle() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getTitle();
		} else {
			return null;
		}
	}

	public String getKeywords() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getKeywords();
		} else {
			return null;
		}
	}

	public String getDescription() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getDescription();
		} else {
			return null;
		}
	}

	public Integer getCommentControl() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getCommentControl();
		} else {
			return null;
		}
	}

	public Boolean getAllowUpdown() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getAllowUpdown();
		} else {
			return null;
		}
	}

	public Boolean getBlank() {
		ChannelExt ext = getChannelExt();
		if (ext != null) {
			return ext.getBlank();
		} else {
			return null;
		}
	}

	/**
	 * 获得栏目内容
	 * @return
	 */
	public String getTxt() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt();
		} else {
			return null;
		}
	}

	/**
	 * 获得栏目内容1
	 * @return
	 */
	public String getTxt1() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt1();
		} else {
			return null;
		}
	}

	/**
	 * 获得栏目内容2
	 * @return
	 */
	public String getTxt2() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt2();
		} else {
			return null;
		}
	}

	/**
	 * 获得栏目内容3
	 * @return
	 */
	public String getTxt3() {
		ChannelTxt txt = getChannelTxt();
		if (txt != null) {
			return txt.getTxt3();
		} else {
			return null;
		}
	}

	public ChannelTxt getChannelTxt() {
		Set<ChannelTxt> set = getChannelTxtSet();
		if (set != null && set.size() > 0) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	/**
	 * 每个站点各自维护独立的树结构
	 * @see HibernateTree#getTreeCondition()
	 */
	public String getTreeCondition() {
		return "bean.site.id=" + getSite().getId();
	}

	/**
	 * @see HibernateTree#getParentId()
	 */
	public Integer getParentId() {
		Channel parent = getParent();
		if (parent != null) {
			return parent.getId();
		} else {
			return null;
		}
	}

	/**
	 * @see HibernateTree#getLftName()
	 */
	public String getLftName() {
		return DEF_LEFT_NAME;
	}

	/**
	 * @see HibernateTree#getParentName()
	 */
	public String getParentName() {
		return DEF_PARENT_NAME;
	}

	/**
	 * @see HibernateTree#getRgtName()
	 */
	public String getRgtName() {
		return DEF_RIGHT_NAME;
	}

	public static Integer[] fetchIds(Collection<Channel> channels) {
		if (channels == null) {
			return null;
		}
		Integer[] ids = new Integer[channels.size()];
		int i = 0;
		for (Channel c : channels) {
			ids[i++] = c.getId();
		}
		return ids;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */

	/**
	 * default Constructor
	 */
	public Channel() {
	}

	/** Constructor for primary key */
	public Channel(java.lang.Integer id) {
	}

	/** Constructor for required fields */
	public Channel(Integer id, CmsSite site, CmsModel model,
		Integer lft, Integer rgt, Integer priority,
		Boolean hasContent, Boolean display) {

		this.setId(id);
		this.setSite(site);
		this.setModel(model);
		this.setLft(lft);
		this.setRgt(rgt);
		this.setPriority(priority);
		this.setHasContent(hasContent);
		this.setDisplay(display);
	}
	/* [CONSTRUCTOR MARKER END] */

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String path;
	private java.lang.Integer lft;
	private java.lang.Integer rgt;
	private java.lang.Integer priority;
	private java.lang.Boolean hasContent;
	private java.lang.Boolean display;

	// one to one
	private foo.cms.entity.main.ChannelExt channelExt;

	// many to one
	private foo.cms.entity.main.CmsSite site;
	private foo.cms.entity.main.CmsModel model;
	private foo.cms.entity.main.Channel parent;

	// collections
	private java.util.Set<foo.cms.entity.main.Channel> child;
	private java.util.Set<foo.cms.entity.main.CmsGroup> viewGroups;
	private java.util.Set<foo.cms.entity.main.CmsGroup> contriGroups;
	private java.util.Set<foo.cms.entity.main.CmsUser> users;
	private java.util.Set<foo.cms.entity.main.ChannelTxt> channelTxtSet;
	private java.util.Map<java.lang.String, java.lang.String> attr;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
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
	 * Return the value associated with the column: channel_path
	 */
	public java.lang.String getPath () {
		return path;
	}

	/**
	 * Set the value related to the column: channel_path
	 * @param path the channel_path value
	 */
	public void setPath (java.lang.String path) {
		this.path = path;
	}

	/** Return the value associated with the column: lft */
	public java.lang.Integer getLft () {
		return lft;
	}

	/**
	 * Set the value related to the column: lft
	 * @param lft the lft value
	 */
	public void setLft (java.lang.Integer lft) {
		this.lft = lft;
	}

	/** Return the value associated with the column: rgt */
	public java.lang.Integer getRgt () {
		return rgt;
	}

	/**
	 * Set the value related to the column: rgt
	 * @param rgt the rgt value
	 */
	public void setRgt (java.lang.Integer rgt) {
		this.rgt = rgt;
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

	/** Return the value associated with the column: has_content */
	public java.lang.Boolean getHasContent () {
		return hasContent;
	}

	/**
	 * Set the value related to the column: has_content
	 * @param hasContent the has_content value
	 */
	public void setHasContent (java.lang.Boolean hasContent) {
		this.hasContent = hasContent;
	}


	/** Return the value associated with the column: is_display */
	public java.lang.Boolean getDisplay () {
		return display;
	}

	/**
	 * Set the value related to the column: is_display
	 * @param display the is_display value
	 */
	public void setDisplay (java.lang.Boolean display) {
		this.display = display;
	}


	/** Return the value associated with the column: channelExt */
	public foo.cms.entity.main.ChannelExt getChannelExt () {
		return channelExt;
	}

	/**
	 * Set the value related to the column: channelExt
	 * @param channelExt the channelExt value
	 */
	public void setChannelExt (foo.cms.entity.main.ChannelExt channelExt) {
		this.channelExt = channelExt;
	}


	/**  Return the value associated with the column: site_id */
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


	/** Return the value associated with the column: model_id */
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


	/** Return the value associated with the column: parent_id */
	public foo.cms.entity.main.Channel getParent () {
		return parent;
	}

	/**
	 * Set the value related to the column: parent_id
	 * @param parent the parent_id value
	 */
	public void setParent (foo.cms.entity.main.Channel parent) {
		this.parent = parent;
	}


	/** Return the value associated with the column: child */
	public java.util.Set<foo.cms.entity.main.Channel> getChild () {
		return child;
	}

	/**
	 * Set the value related to the column: child
	 * @param child the child value
	 */
	public void setChild (java.util.Set<foo.cms.entity.main.Channel> child) {
		this.child = child;
	}


	/** Return the value associated with the column: viewGroups */
	public java.util.Set<foo.cms.entity.main.CmsGroup> getViewGroups () {
		return viewGroups;
	}

	/**
	 * Set the value related to the column: viewGroups
	 * @param viewGroups the viewGroups value
	 */
	public void setViewGroups (java.util.Set<foo.cms.entity.main.CmsGroup> viewGroups) {
		this.viewGroups = viewGroups;
	}


	/** Return the value associated with the column: contriGroups */
	public java.util.Set<foo.cms.entity.main.CmsGroup> getContriGroups () {
		return contriGroups;
	}

	/**
	 * Set the value related to the column: contriGroups
	 * @param contriGroups the contriGroups value
	 */
	public void setContriGroups (java.util.Set<foo.cms.entity.main.CmsGroup> contriGroups) {
		this.contriGroups = contriGroups;
	}


	/** Return the value associated with the column: users */
	public java.util.Set<foo.cms.entity.main.CmsUser> getUsers () {
		return users;
	}

	/**
	 * Set the value related to the column: users
	 * @param users the users value
	 */
	public void setUsers (java.util.Set<foo.cms.entity.main.CmsUser> users) {
		this.users = users;
	}


	/** Return the value associated with the column: channelTxtSet */
	public java.util.Set<foo.cms.entity.main.ChannelTxt> getChannelTxtSet () {
		return channelTxtSet;
	}

	/**
	 * Set the value related to the column: channelTxtSet
	 * @param channelTxtSet the channelTxtSet value
	 */
	public void setChannelTxtSet (java.util.Set<foo.cms.entity.main.ChannelTxt> channelTxtSet) {
		this.channelTxtSet = channelTxtSet;
	}


	/** Return the value associated with the column: attr */
	public java.util.Map<java.lang.String, java.lang.String> getAttr () {
		return attr;
	}

	/**
	 * Set the value related to the column: attr
	 * @param attr the attr value
	 */
	public void setAttr (java.util.Map<java.lang.String, java.lang.String> attr) {
		this.attr = attr;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.Channel)) return false;
		else {
			foo.cms.entity.main.Channel channel = (foo.cms.entity.main.Channel) obj;
			if (null == this.getId() || null == channel.getId()) return false;
			else return (this.getId().equals(channel.getId()));
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