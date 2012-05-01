package foo.cms.entity.main;

import static foo.common.web.Constants.SPT;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import foo.cms.entity.main.Channel.AfterCheckEnum;
import foo.cms.staticpage.StaticPageUtils;
import foo.cms.web.CmsThreadVariable;
import foo.core.web.ContentInterface;

public class Content implements ContentInterface, Serializable {
	private static final long serialVersionUID = 4550542629867132588L;

	/**
	 * 状态
	 * 
	 * @author liufang
	 * 
	 */
	public enum ContentStatus {
		/**
		 * 所有
		 */
		all,
		/**
		 * 草稿
		 */
		draft,
		/**
		 * 待审核
		 */
		prepared,
		/**
		 * 已审
		 */
		passed,
		/**
		 * 终审
		 */
		checked,
		/**
		 * 退回
		 */
		rejected,
		/**
		 * 回收
		 */
		recycle
	};

	private DateFormat df = new SimpleDateFormat("/yyyyMMdd");

	public Boolean getStaticContent() {
		Channel channel = getChannel();
		if (channel != null) {
			return channel.getStaticContent();
		} else {
			return null;
		}
	}

	/**
	 * 获得URL地址
	 * 
	 * @return
	 */
	public String getUrl() {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		if (getStaticContent()) {
			return getUrlStatic(false, 1);
		} else {
			return getUrlDynamic(null);
		}
	}

	public String getUrlWhole() {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		if (getStaticContent()) {
			return getUrlStatic(true, 1);
		} else {
			return getUrlDynamic(true);
		}

	}

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
					url.append(filename.subSequence(0, index)).append("_");
					url.append(pageNo).append(
							filename.subSequence(index, filename.length()));
				} else {
					url.append(filename).append("_").append(pageNo);
				}
			} else {
				url.append(filename);
			}
		} else {
			// 默认静态路径
			url.append(SPT).append(getChannel().getPath());
			url.append(df.format(getReleaseDate()));
			url.append(SPT).append(getId());
			if (pageNo > 1) {
				url.append("_").append(pageNo);
			}
			url.append(site.getStaticSuffix());

		}
		return url.toString();
	}

	public String getUrlDynamic() {
		return getUrlDynamic(null);
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
					url.append(filename.substring(0, index));
					url.append("_").append(pageNo);
					url.append(filename.substring(index));
				} else {
					url.append(filename).append("_").append(pageNo);
				}
			} else {
				url.append(filename);
			}
		} else {
			// 默认静态路径
			url.append(SPT).append(getChannel().getPath());
			url.append(df.format(getReleaseDate()));
			url.append(SPT).append(getId());
			if (pageNo > 1) {
				url.append("_").append(pageNo);
			}
			url.append(site.getStaticSuffix());
		}
		return url.toString();
	}

	public String getStaticFilenameByRule() {
		Channel channel = getChannel();
		CmsModel model = channel.getModel();
		String rule = channel.getContentRule();
		if (StringUtils.isBlank(rule)) {
			return null;
		}
		String url = StaticPageUtils.staticUrlRule(rule, model.getId(), model
				.getPath(), channel.getId(), channel.getPath(), getId(),
				getReleaseDate());
		return url;
	}

	public String getUrlDynamic(Boolean whole) {
		if (!StringUtils.isBlank(getLink())) {
			return getLink();
		}
		CmsSite site = getSite();
		StringBuilder url = site.getUrlBuffer(true, whole, false);
		url.append(SPT).append(getChannel().getPath());
		url.append(SPT).append(getId()).append(site.getDynamicSuffix());
		return url.toString();
	}

	public Set<Channel> getChannelsWithoutMain() {
		Set<Channel> set = new HashSet<Channel>(getChannels());
		set.remove(getChannel());
		return set;
	}

	public void setContentTxt(ContentTxt txt) {
		Set<ContentTxt> set = getContentTxtSet();
		if (set == null) {
			set = new HashSet<ContentTxt>();
			setContentTxtSet(set);
		}
		if (!set.isEmpty()) {
			set.clear();
		}
		set.add(txt);
	}

	public void setContentCheck(ContentCheck check) {
		Set<ContentCheck> set = getContentCheckSet();
		if (set == null) {
			set = new HashSet<ContentCheck>();
			setContentCheckSet(set);
		}
		if (!set.isEmpty()) {
			set.clear();
		}
		set.add(check);
	}

	public void addToChannels(Channel channel) {
		Set<Channel> channels = getChannels();
		if (channels == null) {
			channels = new HashSet<Channel>();
			setChannels(channels);
		}
		channels.add(channel);
	}

	public void addToTopics(CmsTopic topic) {
		Set<CmsTopic> topics = getTopics();
		if (topics == null) {
			topics = new HashSet<CmsTopic>();
			setTopics(topics);
		}
		topics.add(topic);
	}

	public void addToGroups(CmsGroup group) {
		Set<CmsGroup> groups = getViewGroups();
		if (groups == null) {
			groups = new HashSet<CmsGroup>();
			setViewGroups(groups);
		}
		groups.add(group);
	}

	public void addToAttachmemts(String path, String name, String filename) {
		List<ContentAttachment> list = getAttachments();
		if (list == null) {
			list = new ArrayList<ContentAttachment>();
			setAttachments(list);
		}
		ContentAttachment ca = new ContentAttachment(path, name, 0);
		if (!StringUtils.isBlank(filename)) {
			ca.setFilename(filename);
		}
		list.add(ca);
	}

	public void addToPictures(String path, String desc) {
		List<ContentPicture> list = getPictures();
		if (list == null) {
			list = new ArrayList<ContentPicture>();
			setPictures(list);
		}
		ContentPicture cp = new ContentPicture();
		cp.setImgPath(path);
		cp.setDescription(desc);
		list.add(cp);
	}

	public String getTagStr() {
		List<ContentTag> tags = getTags();
		if (tags != null && tags.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (ContentTag tag : tags) {
				sb.append(tag.getName()).append(',');
			}
			return sb.substring(0, sb.length() - 1);
		} else {
			return null;
		}
	}

	/**
	 * 是否草稿
	 * 
	 * @return
	 */
	public boolean isDraft() {
		return ContentCheck.DRAFT == getStatus();
	}

	/**
	 * 是否终审通过
	 * 
	 * @return
	 */
	public boolean isChecked() {
		return ContentCheck.CHECKED == getStatus();
	}

	public Set<CmsGroup> getViewGroupsExt() {
		Set<CmsGroup> set = getViewGroups();
		if (set != null && set.size() > 0) {
			return set;
		} else {
			return getChannel().getViewGroups();
		}
	}

	public String getTplContentOrDef() {
		String tpl = getTplContent();
		if (!StringUtils.isBlank(tpl)) {
			return tpl;
		} else {
			return getChannel().getTplContentOrDef();
		}
	}

	/**
	 * 是否有审核后的编辑权限。从CmsThread中获得当前用户。
	 * 
	 * @return
	 */
	public boolean isHasUpdateRight() {
		CmsUser user = CmsThreadVariable.getUser();
		if (user == null) {
			throw new IllegalStateException("CmsUser not found in CmsThread");
		}
		return isHasUpdateRight(user);
	}

	/**
	 * 是否有审核后的编辑权限
	 * 
	 * @param user
	 * @return
	 */
	public boolean isHasUpdateRight(CmsUser user) {
		AfterCheckEnum after = getChannel().getAfterCheckEnum();
		if (AfterCheckEnum.CANNOT_UPDATE == after) {
			CmsSite site = getSite();
			Byte userStep = user.getCheckStep(site.getId());
			Byte channelStep = getChannel().getFinalStepExtends();
			boolean checked = getStatus() == ContentCheck.CHECKED;
			// 如果内容审核级别大于用户审核级别，或者内容已经审核且用户审核级别小于栏目审核级别。
			if (getCheckStep() > userStep
					|| (checked && userStep < channelStep)) {
				return false;
			} else {
				return true;
			}
		} else if (AfterCheckEnum.BACK_UPDATE == after
				|| AfterCheckEnum.KEEP_UPDATE == after) {
			return true;
		} else {
			throw new RuntimeException("AfterCheckEnum '" + after
					+ "' did not handled");
		}
	}

	/**
	 * 是否有审核后的删除权限。从CmsThread中获得当前用户。
	 * 
	 * @return
	 */
	public boolean isHasDeleteRight() {
		CmsUser user = CmsThreadVariable.getUser();
		if (user == null) {
			throw new IllegalStateException("CmsUser not found in CmsThread");
		}
		return isHasDeleteRight(user);
	}

	/**
	 * 是否有审核后的删除权限
	 * 
	 * @param user
	 * @return
	 */
	public boolean isHasDeleteRight(CmsUser user) {
		AfterCheckEnum after = getChannel().getAfterCheckEnum();
		if (AfterCheckEnum.CANNOT_UPDATE == after) {
			CmsSite site = getSite();
			Byte userStep = user.getCheckStep(site.getId());
			Byte channelStep = getChannel().getFinalStepExtends();
			boolean checked = getStatus() == ContentCheck.CHECKED;
			// 如果内容审核级别大于用户审核级别，或者内容已经审核且用户审核级别小于栏目审核级别。
			if (getCheckStep() > userStep
					|| (checked && userStep < channelStep)) {
				return false;
			} else {
				return true;
			}
		} else if (AfterCheckEnum.BACK_UPDATE == after
				|| AfterCheckEnum.KEEP_UPDATE == after) {
			return true;
		} else {
			throw new RuntimeException("AfterCheckEnum '" + after
					+ "' did not handled");
		}

	}

	public void init() {
		short zero = 0;
		byte bzero = 0;
		if (getViewsDay() == null) {
			setViewsDay(0);
		}
		if (getCommentsDay() == null) {
			setCommentsDay(zero);
		}
		if (getDownloadsDay() == null) {
			setDownloadsDay(zero);
		}
		if (getUpsDay() == null) {
			setUpsDay(zero);
		}
		if (getHasTitleImg() == null) {
			setHasTitleImg(false);
		}
		if (getRecommend() == null) {
			setRecommend(false);
		}
		if (getSortDate() == null) {
			setSortDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getTopLevel() == null) {
			setTopLevel(bzero);
		}
		// 保存后立即生成静态化，如果这些值为null，则需要在模板中增加判断，使模板编写变得复杂。
		if (getChannels() == null) {
			setChannels(new HashSet<Channel>());
		}
		if (getTopics() == null) {
			setTopics(new HashSet<CmsTopic>());
		}
		if (getViewGroups() == null) {
			setViewGroups(new HashSet<CmsGroup>());
		}
		if (getTags() == null) {
			setTags(new ArrayList<ContentTag>());
		}
		if (getPictures() == null) {
			setPictures(new ArrayList<ContentPicture>());
		}
		if (getAttachments() == null) {
			setAttachments(new ArrayList<ContentAttachment>());
		}
	}

	public int getPageCount() {
		int txtCount = getTxtCount();
		if (txtCount <= 1) {
			List<ContentPicture> pics = getPictures();
			if (pics != null) {
				int picCount = pics.size();
				if (picCount > 1) {
					return picCount;
				}
			}
		}
		return txtCount;
	}

	public int getTxtCount() {
		ContentTxt txt = getContentTxt();
		if (txt != null) {
			return txt.getTxtCount();
		} else {
			return 1;
		}
	}

	public ContentPicture getPictureByNo(int pageNo) {
		List<ContentPicture> list = getPictures();
		if (pageNo >= 1 && list != null && list.size() >= pageNo) {
			return list.get(pageNo - 1);
		} else {
			return null;
		}
	}

	public String getTxtByNo(int pageNo) {
		ContentTxt txt = getContentTxt();
		if (txt != null) {
			return txt.getTxtByNo(pageNo);
		} else {
			return null;
		}
	}

	public String getTitleByNo(int pageNo) {
		ContentTxt txt = getContentTxt();
		if (txt != null) {
			return txt.getTitleByNo(pageNo);
		} else {
			return getTitle();
		}
	}

	public String getStitle() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getStitle();
		} else {
			return null;
		}
	}

	public String getTitle() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getTitle();
		} else {
			return null;
		}
	}

	public String getShortTitle() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getShortTitle();
		} else {
			return null;
		}
	}

	public String getDescription() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getDescription();
		} else {
			return null;
		}
	}

	public String getAuthor() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getAuthor();
		} else {
			return null;
		}
	}

	public String getOrigin() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getOrigin();
		} else {
			return null;
		}
	}

	public String getOriginUrl() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getOriginUrl();
		} else {
			return null;
		}
	}

	public Date getReleaseDate() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getReleaseDate();
		} else {
			return null;
		}
	}

	public String getMediaPath() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getMediaPath();
		} else {
			return null;
		}
	}

	public String getMediaType() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getMediaType();
		} else {
			return null;
		}
	}

	public String getTitleColor() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getTitleColor();
		} else {
			return null;
		}
	}

	public Boolean getBold() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getBold();
		} else {
			return null;
		}
	}

	public String getTitleImg() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getTitleImg();
		} else {
			return null;
		}
	}

	public String getContentImg() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getContentImg();
		} else {
			return null;
		}
	}

	public String getTypeImg() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getTypeImg();
		} else {
			return null;
		}
	}

	public String getLink() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getLink();
		} else {
			return null;
		}
	}

	public String getTplContent() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getTplContent();
		} else {
			return null;
		}
	}
	
	public Boolean getNeedRegenerate() {
		ContentExt ext = getContentExt();
		if (ext != null) {
			return ext.getNeedRegenerate();
		} else {
			return null;
		}
	}
	
	public void setNeedRegenerate(Boolean isNeed) {
		ContentExt ext = getContentExt();
		if (ext != null) {
			ext.setNeedRegenerate(isNeed);
		}
	}

	public String getTxt() {
		ContentTxt txt = getContentTxt();
		if (txt != null) {
			return txt.getTxt();
		} else {
			return null;
		}
	}

	public String getTxt1() {
		ContentTxt txt = getContentTxt();
		if (txt != null) {
			return txt.getTxt1();
		} else {
			return null;
		}
	}

	public String getTxt2() {
		ContentTxt txt = getContentTxt();
		if (txt != null) {
			return txt.getTxt2();
		} else {
			return null;
		}
	}

	public String getTxt3() {
		ContentTxt txt = getContentTxt();
		if (txt != null) {
			return txt.getTxt3();
		} else {
			return null;
		}
	}

	public Integer getViews() {
		ContentCount count = getContentCount();
		if (count != null) {
			return count.getViews();
		} else {
			return null;
		}
	}

	public Integer getComments() {
		ContentCount count = getContentCount();
		if (count != null) {
			return count.getComments();
		} else {
			return null;
		}
	}

	public Integer getUps() {
		ContentCount count = getContentCount();
		if (count != null) {
			return count.getUps();
		} else {
			return null;
		}
	}

	public Integer getDowns() {
		ContentCount count = getContentCount();
		if (count != null) {
			return count.getDowns();
		} else {
			return null;
		}
	}

	public Byte getCheckStep() {
		ContentCheck check = getContentCheck();
		if (check != null) {
			return check.getCheckStep();
		} else {
			return null;
		}
	}

	public String getCheckOpinion() {
		ContentCheck check = getContentCheck();
		if (check != null) {
			return check.getCheckOpinion();
		} else {
			return null;
		}
	}

	public Boolean getRejected() {
		ContentCheck check = getContentCheck();
		if (check != null) {
			return check.getRejected();
		} else {
			return null;
		}
	}

	public ContentTxt getContentTxt() {
		Set<ContentTxt> set = getContentTxtSet();
		if (set != null && set.size() > 0) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	public ContentCheck getContentCheck() {
		Set<ContentCheck> set = getContentCheckSet();
		if (set != null && set.size() > 0) {
			return set.iterator().next();
		} else {
			return null;
		}
	}

	public String getDesc() {
		return getDescription();
	}

	public String getCtgName() {
		return getChannel().getName();
	}

	public String getCtgUrl() {
		return getChannel().getUrl();
	}

	public String getImgUrl() {
		return getTitleImg();
	}

	public String getImgUrl2() {
		return getTypeImg();
	}

	public String getStit() {
		String stit = getShortTitle();
		if (!StringUtils.isBlank(stit)) {
			return stit;
		} else {
			return getTit();
		}
	}

	public String getTit() {
		return getTitle();
	}

	public String getTitCol() {
		return getTitleColor();
	}

	public String getSiteName() {
		return getSite().getName();
	}

	public String getSiteUrl() {
		return getSite().getUrl();
	}

	public boolean isTitBold() {
		return getBold();
	}

	public Date getDate() {
		return getReleaseDate();
	}

	public Boolean getTarget() {
		return null;
	}
	
	public void clear(){
		getCollectUsers().clear();
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Content() {
	}

	/** Constructor for primary key */
	public Content(Integer id) {
		this.setId(id);
	}

	/** Constructor for required fields */
	public Content(Integer id, CmsSite site, java.util.Date sortDate, Byte topLevel,
			Boolean hasTitleImg, Boolean recommend, Byte status, Integer viewsDay,
			Short commentsDay, Short downloadsDay, Short upsDay) {
		this.setId(id);
		this.setSite(site);
		this.setSortDate(sortDate);
		this.setTopLevel(topLevel);
		this.setHasTitleImg(hasTitleImg);
		this.setRecommend(recommend);
		this.setStatus(status);
		this.setViewsDay(viewsDay);
		this.setCommentsDay(commentsDay);
		this.setDownloadsDay(downloadsDay);
		this.setUpsDay(upsDay);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;
	// primary key
	private Integer id;

	// fields
	private java.util.Date sortDate;
	private Byte topLevel;
	private Boolean hasTitleImg;
	private Boolean recommend;
	private Byte status;
	private Integer viewsDay;
	private Short commentsDay;
	private Short downloadsDay;
	private Short upsDay;

	// one to one
	private foo.cms.entity.main.ContentExt contentExt;
	private foo.cms.entity.main.ContentCount contentCount;

	// many to one
	private foo.cms.entity.main.ContentType type;
	private foo.cms.entity.main.CmsSite site;
	private foo.cms.entity.main.CmsUser user;
	private foo.cms.entity.main.Channel channel;

	// collections
	private java.util.Set<foo.cms.entity.main.Channel> channels;
	private java.util.Set<foo.cms.entity.main.CmsTopic> topics;
	private java.util.Set<foo.cms.entity.main.CmsGroup> viewGroups;
	private java.util.List<foo.cms.entity.main.ContentTag> tags;
	private java.util.List<foo.cms.entity.main.ContentPicture> pictures;
	private java.util.List<foo.cms.entity.main.ContentAttachment> attachments;
	private java.util.Set<foo.cms.entity.main.ContentTxt> contentTxtSet;
	private java.util.Set<foo.cms.entity.main.ContentCheck> contentCheckSet;
	private java.util.Map<String, String> attr;
	private java.util.Set<foo.cms.entity.main.CmsUser> collectUsers;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
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
	 * Return the value associated with the column: sort_date
	 */
	public java.util.Date getSortDate () {
		return sortDate;
	}

	/**
	 * Set the value related to the column: sort_date
	 * @param sortDate the sort_date value
	 */
	public void setSortDate (java.util.Date sortDate) {
		this.sortDate = sortDate;
	}


	/**
	 * Return the value associated with the column: top_level
	 */
	public Byte getTopLevel () {
		return topLevel;
	}

	/**
	 * Set the value related to the column: top_level
	 * @param topLevel the top_level value
	 */
	public void setTopLevel (Byte topLevel) {
		this.topLevel = topLevel;
	}


	/**
	 * Return the value associated with the column: has_title_img
	 */
	public Boolean getHasTitleImg () {
		return hasTitleImg;
	}

	/**
	 * Set the value related to the column: has_title_img
	 * @param hasTitleImg the has_title_img value
	 */
	public void setHasTitleImg (Boolean hasTitleImg) {
		this.hasTitleImg = hasTitleImg;
	}


	/**
	 * Return the value associated with the column: is_recommend
	 */
	public Boolean getRecommend () {
		return recommend;
	}

	/**
	 * Set the value related to the column: is_recommend
	 * @param recommend the is_recommend value
	 */
	public void setRecommend (Boolean recommend) {
		this.recommend = recommend;
	}


	/**
	 * Return the value associated with the column: status
	 */
	public Byte getStatus () {
		return status;
	}

	/**
	 * Set the value related to the column: status
	 * @param status the status value
	 */
	public void setStatus (Byte status) {
		this.status = status;
	}


	/**
	 * Return the value associated with the column: views_day
	 */
	public Integer getViewsDay () {
		return viewsDay;
	}

	/**
	 * Set the value related to the column: views_day
	 * @param viewsDay the views_day value
	 */
	public void setViewsDay (Integer viewsDay) {
		this.viewsDay = viewsDay;
	}


	/**
	 * Return the value associated with the column: comments_day
	 */
	public Short getCommentsDay () {
		return commentsDay;
	}

	/**
	 * Set the value related to the column: comments_day
	 * @param commentsDay the comments_day value
	 */
	public void setCommentsDay (Short commentsDay) {
		this.commentsDay = commentsDay;
	}


	/**
	 * Return the value associated with the column: downloads_day
	 */
	public Short getDownloadsDay () {
		return downloadsDay;
	}

	/**
	 * Set the value related to the column: downloads_day
	 * @param downloadsDay the downloads_day value
	 */
	public void setDownloadsDay (Short downloadsDay) {
		this.downloadsDay = downloadsDay;
	}


	/**
	 * Return the value associated with the column: ups_day
	 */
	public Short getUpsDay () {
		return upsDay;
	}

	/**
	 * Set the value related to the column: ups_day
	 * @param upsDay the ups_day value
	 */
	public void setUpsDay (Short upsDay) {
		this.upsDay = upsDay;
	}


	/**
	 * Return the value associated with the column: contentExt
	 */
	public foo.cms.entity.main.ContentExt getContentExt () {
		return contentExt;
	}

	/**
	 * Set the value related to the column: contentExt
	 * @param contentExt the contentExt value
	 */
	public void setContentExt (foo.cms.entity.main.ContentExt contentExt) {
		this.contentExt = contentExt;
	}


	/**
	 * Return the value associated with the column: contentCount
	 */
	public foo.cms.entity.main.ContentCount getContentCount () {
		return contentCount;
	}

	/**
	 * Set the value related to the column: contentCount
	 * @param contentCount the contentCount value
	 */
	public void setContentCount (foo.cms.entity.main.ContentCount contentCount) {
		this.contentCount = contentCount;
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


	/**
	 * Return the value associated with the column: topics
	 */
	public java.util.Set<foo.cms.entity.main.CmsTopic> getTopics () {
		return topics;
	}

	/**
	 * Set the value related to the column: topics
	 * @param topics the topics value
	 */
	public void setTopics (java.util.Set<foo.cms.entity.main.CmsTopic> topics) {
		this.topics = topics;
	}


	/**
	 * Return the value associated with the column: viewGroups
	 */
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


	/**
	 * Return the value associated with the column: tags
	 */
	public java.util.List<foo.cms.entity.main.ContentTag> getTags () {
		return tags;
	}

	/**
	 * Set the value related to the column: tags
	 * @param tags the tags value
	 */
	public void setTags (java.util.List<foo.cms.entity.main.ContentTag> tags) {
		this.tags = tags;
	}


	/**
	 * Return the value associated with the column: pictures
	 */
	public java.util.List<foo.cms.entity.main.ContentPicture> getPictures () {
		return pictures;
	}

	/**
	 * Set the value related to the column: pictures
	 * @param pictures the pictures value
	 */
	public void setPictures (java.util.List<foo.cms.entity.main.ContentPicture> pictures) {
		this.pictures = pictures;
	}


	/**
	 * Return the value associated with the column: attachments
	 */
	public java.util.List<foo.cms.entity.main.ContentAttachment> getAttachments () {
		return attachments;
	}

	/**
	 * Set the value related to the column: attachments
	 * @param attachments the attachments value
	 */
	public void setAttachments (java.util.List<foo.cms.entity.main.ContentAttachment> attachments) {
		this.attachments = attachments;
	}


	/**
	 * Return the value associated with the column: contentTxtSet
	 */
	public java.util.Set<foo.cms.entity.main.ContentTxt> getContentTxtSet () {
		return contentTxtSet;
	}

	/**
	 * Set the value related to the column: contentTxtSet
	 * @param contentTxtSet the contentTxtSet value
	 */
	public void setContentTxtSet (java.util.Set<foo.cms.entity.main.ContentTxt> contentTxtSet) {
		this.contentTxtSet = contentTxtSet;
	}


	/**
	 * Return the value associated with the column: contentCheckSet
	 */
	public java.util.Set<foo.cms.entity.main.ContentCheck> getContentCheckSet () {
		return contentCheckSet;
	}

	/**
	 * Set the value related to the column: contentCheckSet
	 * @param contentCheckSet the contentCheckSet value
	 */
	public void setContentCheckSet (java.util.Set<foo.cms.entity.main.ContentCheck> contentCheckSet) {
		this.contentCheckSet = contentCheckSet;
	}


	/**
	 * Return the value associated with the column: attr
	 */
	public java.util.Map<String, String> getAttr () {
		return attr;
	}
	

	public java.util.Set<foo.cms.entity.main.CmsUser> getCollectUsers() {
		return collectUsers;
	}

	public void setCollectUsers(
			java.util.Set<foo.cms.entity.main.CmsUser> collectUsers) {
		this.collectUsers = collectUsers;
	}

	/**
	 * Set the value related to the column: attr
	 * @param attr the attr value
	 */
	public void setAttr (java.util.Map<String, String> attr) {
		this.attr = attr;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.Content)) return false;
		else {
			foo.cms.entity.main.Content content = (foo.cms.entity.main.Content) obj;
			if (null == this.getId() || null == content.getId()) return false;
			else return (this.getId().equals(content.getId()));
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