package foo.cms.action.directive.abs;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.ContentTag;
import foo.cms.manager.main.ChannelMng;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.manager.main.ContentMng;
import foo.cms.manager.main.ContentTagMng;
import foo.cms.web.FrontUtils;
import foo.common.web.freemarker.DirectiveUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 内容标签基类
 * 
 * @author liufang
 * 
 */
public abstract class AbstractContentDirective implements
		TemplateDirectiveModel {
	/**
	 * 输入参数，TAG ID。允许多个TAG ID，用","分开。和tagNames之间二选一，ID优先级更高。
	 */
	public static final String PARAM_TAG_ID = "tagId";
	/**
	 * 输入参数，TAG NAME。允许多个TAG NAME，用","分开。
	 */
	public static final String PARAM_TAG_NAME = "tagName";
	/**
	 * 输入参数，专题ID。
	 */
	public static final String PARAM_TOPIC_ID = "topicId";
	/**
	 * 输入参数，栏目ID。允许多个栏目ID，用","分开。和channelPath之间二选一，ID优先级更高。
	 */
	public static final String PARAM_CHANNEL_ID = "channelId";
	/**
	 * 输入参数，栏目路径。允许多个栏目路径，用","分开。
	 */
	public static final String PARAM_CHANNEL_PATH = "channelPath";
	/**
	 * 输入参数，栏目选项。用于单栏目情况下。0：自身栏目；1：包含子栏目；2：包含副栏目。
	 */
	public static final String PARAM_CHANNEL_OPTION = "channelOption";
	/**
	 * 输入参数，站点ID。可选。允许多个站点ID，用","分开。
	 */
	public static final String PARAM_SITE_ID = "siteId";
	/**
	 * 输入参数，类型ID。可选。允许多个类型ID,用","分开。
	 */
	public static final String PARAM_TYPE_ID = "typeId";
	/**
	 * 输入参数，推荐。0：所有；1：推荐；2：不推荐。默认所有。
	 */
	public static final String PARAM_RECOMMEND = "recommend";
	/**
	 * 输入参数，标题。可以为null。
	 */
	public static final String PARAM_TITLE = "title";
	/**
	 * 输入参数，标题图片。0：所有；1：有；2：没有。默认所有。
	 */
	public static final String PARAM_IMAGE = "image";
	/**
	 * 输入参数，排序方式。
	 * <ul>
	 * <li>0：ID降序
	 * <li>1：ID升序
	 * <li>2：发布时间降序
	 * <li>3：发布时间升序
	 * <li>4：固定级别降序,发布时间降序
	 * <li>5：固定级别降序,发布时间升序
	 * 
	 * <li>6：日访问降序（推荐）
	 * <li>7：周访问降序
	 * <li>8：月访问降序
	 * <li>9：总访问降序
	 * 
	 * <li>10：日评论降序（推荐）
	 * <li>11：周评论降序
	 * <li>12：月评论降序
	 * <li>13：总评论降序
	 * 
	 * <li>14：日下载降序（推荐）
	 * <li>15：周下载降序
	 * <li>16：月下载降序
	 * <li>17：总下载降序
	 * 
	 * <li>18：周顶降序（推荐）
	 * <li>19：周顶降序
	 * <li>20：周顶降序
	 * <li>21：周顶降序
	 * </ul>
	 */
	public static final String PARAM_ORDER_BY = "orderBy";
	/**
	 * 输入参数，不包含的文章ID。用于按tag查询相关文章。
	 */
	public static final String PARAM_EXCLUDE_ID = "excludeId";

	protected Integer[] getTagIds(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer[] ids = DirectiveUtils.getIntArray(PARAM_TAG_ID, params);
		if (ids != null && ids.length > 0) {
			return ids;
		} else {
			String nameStr = DirectiveUtils.getString(PARAM_TAG_NAME, params);
			if (StringUtils.isBlank(nameStr)) {
				return null;
			}
			String[] names = StringUtils.split(nameStr, ',');
			Set<Integer> set = new HashSet<Integer>();
			ContentTag tag;
			for (String name : names) {
				tag = contentTagMng.findByNameForTag(name);
				if (tag != null) {
					set.add(tag.getId());
				}
			}
			if (set.size() > 0) {
				return set.toArray(new Integer[set.size()]);
			} else {
				return null;
			}
		}
	}

	protected Integer getTopicId(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(PARAM_TOPIC_ID, params);
	}

	protected Integer[] getChannelIds(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer[] ids = DirectiveUtils.getIntArray(PARAM_CHANNEL_ID, params);
		if (ids != null && ids.length > 0) {
			return ids;
		} else {
			return null;
		}
	}

	protected String[] getChannelPaths(Map<String, TemplateModel> params)
			throws TemplateException {
		String nameStr = DirectiveUtils.getString(PARAM_CHANNEL_PATH, params);
		if (StringUtils.isBlank(nameStr)) {
			return null;
		}
		return StringUtils.split(nameStr, ',');
	}

	protected Integer[] getChannelIdsByPaths(String[] paths, Integer siteId) {
		Set<Integer> set = new HashSet<Integer>();
		Channel channel;
		for (String path : paths) {
			channel = channelMng.findByPathForTag(path, siteId);
			if (channel != null) {
				set.add(channel.getId());
			}
		}
		if (set.size() > 0) {
			return set.toArray(new Integer[set.size()]);
		} else {
			return null;
		}
	}

	/**
	 * 获取ID数组，如果ID不存在则获取PATH数组。
	 * 
	 * @param params
	 *            标签参数
	 * @param siteIds
	 *            如果为null，则自动获取所有站点path
	 * @return 如果不存在，则返回null。
	 * @throws TemplateException
	 */
	protected Integer[] getChannelIdsOrPaths(Map<String, TemplateModel> params,
			Integer[] siteIds) throws TemplateException {
		Integer[] ids = getChannelIds(params);
		if (ids != null) {
			return ids;
		}
		String[] paths = getChannelPaths(params);
		if (paths == null) {
			return null;
		}
		Set<Integer> set = new HashSet<Integer>();
		Channel channel;
		if (siteIds == null) {
			List<CmsSite> list = cmsSiteMng.getListFromCache();
			siteIds = new Integer[list.size()];
			int i = 0;
			for (CmsSite site : list) {
				siteIds[i++] = site.getId();
			}
		}
		for (Integer siteId : siteIds) {
			for (String path : paths) {
				channel = channelMng.findByPathForTag(path, siteId);
				if (channel != null) {
					set.add(channel.getId());
				}
			}
		}
		if (set.size() > 0) {
			return set.toArray(new Integer[set.size()]);
		} else {
			return null;
		}
	}

	protected int getChannelOption(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer option = DirectiveUtils.getInt(PARAM_CHANNEL_OPTION, params);
		if (option == null || option < 0 || option > 2) {
			return 0;
		} else {
			return option;
		}
	}

	protected Integer[] getSiteIds(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer[] siteIds = DirectiveUtils.getIntArray(PARAM_SITE_ID, params);
		return siteIds;
	}

	protected Integer[] getTypeIds(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer[] typeIds = DirectiveUtils.getIntArray(PARAM_TYPE_ID, params);
		return typeIds;
	}

	protected Boolean getHasTitleImg(Map<String, TemplateModel> params)
			throws TemplateException {
		String titleImg = DirectiveUtils.getString(PARAM_IMAGE, params);
		if ("1".equals(titleImg)) {
			return true;
		} else if ("2".equals(titleImg)) {
			return false;
		} else {
			return null;
		}
	}

	protected Boolean getRecommend(Map<String, TemplateModel> params)
			throws TemplateException {
		String recommend = DirectiveUtils.getString(PARAM_RECOMMEND, params);
		if ("1".equals(recommend)) {
			return true;
		} else if ("2".equals(recommend)) {
			return false;
		} else {
			return null;
		}
	}

	protected String getTitle(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getString(PARAM_TITLE, params);
	}

	protected int getOrderBy(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer orderBy = DirectiveUtils.getInt(PARAM_ORDER_BY, params);
		if (orderBy == null) {
			return 0;
		} else {
			return orderBy;
		}
	}

	protected Object getData(Map<String, TemplateModel> params, Environment env)
			throws TemplateException {
		int orderBy = getOrderBy(params);
		Boolean titleImg = getHasTitleImg(params);
		Boolean recommend = getRecommend(params);
		Integer[] typeIds = getTypeIds(params);
		Integer[] siteIds = getSiteIds(params);
		String title = getTitle(params);
		int count = FrontUtils.getCount(params);

		Integer[] tagIds = getTagIds(params);
		if (tagIds != null) {
			Integer[] channelIds = getChannelIdsOrPaths(params, siteIds);
			Integer excludeId = DirectiveUtils.getInt(PARAM_EXCLUDE_ID, params);
			if (isPage()) {
				int pageNo = FrontUtils.getPageNo(env);
				return contentMng.getPageByTagIdsForTag(tagIds, siteIds,
						channelIds, typeIds, excludeId, titleImg, recommend,
						title, orderBy, pageNo, count);
			} else {
				int first = FrontUtils.getFirst(params);
				return contentMng.getListByTagIdsForTag(tagIds, siteIds,
						channelIds, typeIds, excludeId, titleImg, recommend,
						title, orderBy, first, count);
			}
		}
		Integer topicId = getTopicId(params);
		if (topicId != null) {
			Integer[] channelIds = getChannelIdsOrPaths(params, siteIds);
			if (isPage()) {
				int pageNo = FrontUtils.getPageNo(env);
				return contentMng.getPageByTopicIdForTag(topicId, siteIds,
						channelIds, typeIds, titleImg, recommend, title,
						orderBy, pageNo, count);
			} else {
				int first = FrontUtils.getFirst(params);
				return contentMng.getListByTopicIdForTag(topicId, siteIds,
						channelIds, typeIds, titleImg, recommend, title,
						orderBy, first, count);
			}
		}
		Integer[] channelIds = getChannelIds(params);
		if (channelIds != null) {
			int option = getChannelOption(params);
			if (isPage()) {
				int pageNo = FrontUtils.getPageNo(env);
				return contentMng.getPageByChannelIdsForTag(channelIds,
						typeIds, titleImg, recommend, title, orderBy, option,
						pageNo, count);
			} else {
				int first = FrontUtils.getFirst(params);
				return contentMng.getListByChannelIdsForTag(channelIds,
						typeIds, titleImg, recommend, title, orderBy, option,
						first, count);
			}
		}
		String[] channelPaths = getChannelPaths(params);
		if (channelPaths != null) {
			int option = getChannelOption(params);
			// 如果只有一个站点或只传入一个站点ID，则将栏目path转化为ID。
			boolean pathsToIds = false;
			Integer siteId = null;
			if (siteIds == null || siteIds.length == 0) {
				List<CmsSite> siteList = cmsSiteMng.getListFromCache();
				if (siteList.size() == 1) {
					pathsToIds = true;
					siteId = siteList.get(0).getId();
				}
			} else if (siteIds != null && siteIds.length == 1) {
				pathsToIds = true;
				siteId = siteIds[0];
			}
			if (pathsToIds) {
				channelIds = getChannelIdsByPaths(channelPaths, siteId);
				if (channelIds != null) {
					if (isPage()) {
						int pageNo = FrontUtils.getPageNo(env);
						return contentMng.getPageByChannelIdsForTag(channelIds,
								typeIds, titleImg, recommend, title, orderBy,
								option, pageNo, count);
					} else {
						int first = FrontUtils.getFirst(params);
						return contentMng.getListByChannelIdsForTag(channelIds,
								typeIds, titleImg, recommend, title, orderBy,
								option, first, count);
					}
				} else {
					// 如果将栏目path转换为ID后为空，则按path不存在处理，转到最后执行。
				}
			} else {
				if (isPage()) {
					int pageNo = FrontUtils.getPageNo(env);
					return contentMng.getPageByChannelPathsForTag(channelPaths,
							siteIds, typeIds, titleImg, recommend, title,
							orderBy, pageNo, count);
				} else {
					int first = FrontUtils.getFirst(params);
					return contentMng.getListByChannelPathsForTag(channelPaths,
							siteIds, typeIds, titleImg, recommend, title,
							orderBy, first, count);
				}
			}
		}
		// 主要条件为空，则执行此处代码。
		if (isPage()) {
			int pageNo = FrontUtils.getPageNo(env);
			return contentMng.getPageBySiteIdsForTag(siteIds, typeIds,
					titleImg, recommend, title, orderBy, pageNo, count);
		} else {
			int first = FrontUtils.getFirst(params);
			return contentMng.getListBySiteIdsForTag(siteIds, typeIds,
					titleImg, recommend, title, orderBy, first, count);
		}
	}

	abstract protected boolean isPage();

	@Autowired
	protected ContentTagMng contentTagMng;

	@Autowired
	protected ChannelMng channelMng;

	@Autowired
	protected CmsSiteMng cmsSiteMng;

	@Autowired
	protected ContentMng contentMng;
}
