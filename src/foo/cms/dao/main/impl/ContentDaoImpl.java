package foo.cms.dao.main.impl;

import static foo.cms.entity.main.Content.ContentStatus.all;
import static foo.cms.entity.main.Content.ContentStatus.checked;
import static foo.cms.entity.main.Content.ContentStatus.draft;
import static foo.cms.entity.main.Content.ContentStatus.passed;
import static foo.cms.entity.main.Content.ContentStatus.prepared;
import static foo.cms.entity.main.Content.ContentStatus.recycle;
import static foo.cms.entity.main.Content.ContentStatus.rejected;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import foo.cms.dao.main.ContentDao;
import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentCheck;
import foo.cms.entity.main.Content.ContentStatus;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;
import foo.common.page.Pagination;

@Repository
public class ContentDaoImpl extends HibernateBaseDao<Content, Integer>
		implements ContentDao {
	public Pagination getPage(String title, Integer typeId,
			Integer inputUserId, boolean topLevel, boolean recommend,
			ContentStatus status, Byte checkStep, Integer siteId,
			Integer channelId, int orderBy, int pageNo, int pageSize) {
		Finder f = Finder.create("select bean from Content bean");
		if (prepared == status || passed == status || rejected == status) {
			f.append(" join bean.contentCheckSet check");
		}
		if (channelId != null) {
			f.append(" join bean.channel channel,Channel parent");
			f.append(" where channel.lft between parent.lft and parent.rgt");
			f.append(" and channel.site.id=parent.site.id");
			f.append(" and parent.id=:parentId");
			f.setParam("parentId", channelId);
		} else if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if (prepared == status) {
			f.append(" and check.checkStep<:checkStep");
			f.append(" and check.rejected=false");
			f.setParam("checkStep", checkStep);
		} else if (passed == status) {
			f.append(" and check.checkStep=:checkStep");
			f.append(" and check.rejected=false");
			f.setParam("checkStep", checkStep);
		} else if (rejected == status) {
			f.append(" and check.checkStep=:checkStep");
			f.append(" and check.rejected=true");
			f.setParam("checkStep", checkStep);
		}
		appendQuery(f, title, typeId, inputUserId, status, topLevel, recommend);
		if (prepared == status) {
			f.append(" order by check.checkStep desc,bean.id desc");
		} else {
			appendOrder(f, orderBy);
		}
		return find(f, pageNo, pageSize);
	}
	
	public Pagination getPageForCollection(Integer siteId, Integer memberId, int pageNo, int pageSize){
		Finder f = Finder.create("select bean from Content bean join bean.collectUsers user where user.id=:userId").setParam("userId", memberId);
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" and bean.status<>:status");
		f.setParam("status", ContentCheck.RECYCLE);
		return find(f, pageNo, pageSize);
	}

	public Pagination getPageBySelf(String title, Integer typeId,
			Integer inputUserId, boolean topLevel, boolean recommend,
			ContentStatus status, Byte checkStep, Integer siteId,
			Integer channelId, Integer userId, int orderBy, int pageNo,
			int pageSize) {
		Finder f = Finder.create("select bean from Content bean");
		if (prepared == status || passed == status || rejected == status) {
			f.append(" join bean.contentCheckSet check");
		}
		if (channelId != null) {
			f.append(" join bean.channel channel,Channel parent");
			f.append(" where channel.lft between parent.lft and parent.rgt");
			f.append(" and channel.site.id=parent.site.id");
			f.append(" and parent.id=:parentId");
			f.setParam("parentId", channelId);
		} else if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		f.append(" and bean.user.id=:userId");
		f.setParam("userId", userId);
		if (prepared == status) {
			f.append(" and check.checkStep<:checkStep");
			f.append(" and check.rejected=false");
			f.setParam("checkStep", checkStep);
		} else if (passed == status) {
			f.append(" and check.checkStep=:checkStep");
			f.append(" and check.rejected=false");
			f.setParam("checkStep", checkStep);
		} else if (rejected == status) {
			f.append(" and check.checkStep=:checkStep");
			f.append(" and check.rejected=true");
			f.setParam("checkStep", checkStep);
		}
		appendQuery(f, title, typeId, inputUserId, status, topLevel, recommend);
		if (prepared == status) {
			f.append(" order by check.checkStep desc,bean.id desc");
		} else {
			appendOrder(f, orderBy);
		}
		return find(f, pageNo, pageSize);
	}

	public Pagination getPageByRight(String title, Integer typeId,
			Integer inputUserId, boolean topLevel, boolean recommend,
			ContentStatus status, Byte checkStep, Integer siteId,
			Integer channelId, Integer userId, boolean selfData, int orderBy,
			int pageNo, int pageSize) {
		Finder f = Finder.create("select bean from Content bean");
		if (prepared == status || passed == status || rejected == status) {
			f.append(" join bean.contentCheckSet check");
		}
		f.append(" join bean.channel channel join channel.users user");
		if (channelId != null) {
			f.append(",Channel parent");
			f.append(" where channel.lft between parent.lft and parent.rgt");
			f.append(" and channel.site.id=parent.site.id");
			f.append(" and parent.id=:parentId");
			f.setParam("parentId", channelId);
			f.append(" and user.id=:userId");
			f.setParam("userId", userId);
		} else if (siteId != null) {
			f.append(" where user.id=:userId");
			f.setParam("userId", userId);
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where user.id=:userId");
			f.setParam("userId", userId);
		}
		if (selfData) {
			// userId前面已赋值
			f.append(" and bean.user.id=:userId");
		}
		if (prepared == status) {
			f.append(" and check.checkStep<:checkStep");
			f.append(" and check.rejected=false");
			f.setParam("checkStep", checkStep);
		} else if (passed == status) {
			f.append(" and check.checkStep=:checkStep");
			f.append(" and check.rejected=false");
			f.setParam("checkStep", checkStep);
		} else if (rejected == status) {
			f.append(" and check.checkStep=:checkStep");
			f.append(" and check.rejected=true");
			f.setParam("checkStep", checkStep);
		}
		appendQuery(f, title, typeId, inputUserId, status, topLevel, recommend);
		if (prepared == status) {
			f.append(" order by check.checkStep desc,bean.id desc");
		} else {
			appendOrder(f, orderBy);
		}
		return find(f, pageNo, pageSize);
	}

	private void appendQuery(Finder f, String title, Integer typeId,
			Integer inputUserId, ContentStatus status, boolean topLevel,
			boolean recommend) {
		if (!StringUtils.isBlank(title)) {
			f.append(" and bean.contentExt.title like :title");
			f.setParam("title", "%" + title + "%");
		}
		if (typeId != null) {
			f.append(" and bean.type.id=:typeId");
			f.setParam("typeId", typeId);
		}
		if (inputUserId != null) {
			f.append(" and bean.user.id=:inputUserId");
			f.setParam("inputUserId", inputUserId);
		}
		if (topLevel) {
			f.append(" and bean.topLevel>0");
		}
		if (recommend) {
			f.append(" and bean.recommend=true");
		}
		if (draft == status) {
			f.append(" and bean.status=:status");
			f.setParam("status", ContentCheck.DRAFT);
		} else if (checked == status) {
			f.append(" and bean.status=:status");
			f.setParam("status", ContentCheck.CHECKED);
		} else if (prepared == status || rejected == status) {
			f.append(" and bean.status=:status");
			f.setParam("status", ContentCheck.CHECKING);
		} else if (passed == status) {
			f.append(" and (bean.status=:checking or bean.status=:checked)");
			f.setParam("checking", ContentCheck.CHECKING);
			f.setParam("checked", ContentCheck.CHECKED);
		} else if (all == status) {
			f.append(" and bean.status<>:status");
			f.setParam("status", ContentCheck.RECYCLE);
		} else if (recycle == status) {
			f.append(" and bean.status=:status");
			f.setParam("status", ContentCheck.RECYCLE);
		} else {
			// never
		}
	}

	public Content getSide(Integer id, Integer siteId, Integer channelId,
			boolean next, boolean cacheable) {
		Finder f = Finder.create("from Content bean where 1=1");
		if (channelId != null) {
			f.append(" and bean.channel.id=:channelId");
			f.setParam("channelId", channelId);
		} else if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (next) {
			f.append(" and bean.id>:id");
			f.setParam("id", id);
			f.append(" and bean.status=" + ContentCheck.CHECKED);
			f.append(" order by bean.id asc");
		} else {
			f.append(" and bean.id<:id");
			f.setParam("id", id);
			f.append(" and bean.status=" + ContentCheck.CHECKED);
			f.append(" order by bean.id desc");
		}
		Query query = f.createQuery(getSession());
		query.setCacheable(cacheable).setMaxResults(1);
		return (Content) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Content> getListByIdsForTag(Integer[] ids, int orderBy) {
		Finder f = Finder.create("from Content bean where bean.id in (:ids)");
		f.setParamList("ids", ids);
		appendOrder(f, orderBy);
		f.setCacheable(true);
		return find(f);
	}

	public Pagination getPageBySiteIdsForTag(Integer[] siteIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, int pageNo, int pageSize) {
		Finder f = bySiteIds(siteIds, typeIds, titleImg, recommend, title,
				orderBy);
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<Content> getListBySiteIdsForTag(Integer[] siteIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, Integer first, Integer count) {
		Finder f = bySiteIds(siteIds, typeIds, titleImg, recommend, title,
				orderBy);
		if (first != null) {
			f.setFirstResult(first);
		}
		if (count != null) {
			f.setMaxResults(count);
		}
		f.setCacheable(true);
		return find(f);
	}

	public Pagination getPageByChannelIdsForTag(Integer[] channelIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, int option, int pageNo, int pageSize) {
		Finder f = byChannelIds(channelIds, typeIds, titleImg, recommend,
				title, orderBy, option);
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<Content> getListByChannelIdsForTag(Integer[] channelIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, int option, Integer first, Integer count) {
		Finder f = byChannelIds(channelIds, typeIds, titleImg, recommend,
				title, orderBy, option);
		if (first != null) {
			f.setFirstResult(first);
		}
		if (count != null) {
			f.setMaxResults(count);
		}
		f.setCacheable(true);
		return find(f);
	}

	public Pagination getPageByChannelPathsForTag(String[] paths,
			Integer[] siteIds, Integer[] typeIds, Boolean titleImg,
			Boolean recommend, String title, int orderBy, int pageNo,
			int pageSize) {
		Finder f = byChannelPaths(paths, siteIds, typeIds, titleImg, recommend,
				title, orderBy);
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<Content> getListByChannelPathsForTag(String[] paths,
			Integer[] siteIds, Integer[] typeIds, Boolean titleImg,
			Boolean recommend, String title, int orderBy, Integer first,
			Integer count) {
		Finder f = byChannelPaths(paths, siteIds, typeIds, titleImg, recommend,
				title, orderBy);
		if (first != null) {
			f.setFirstResult(first);
		}
		if (count != null) {
			f.setMaxResults(count);
		}
		f.setCacheable(true);
		return find(f);
	}

	public Pagination getPageByTopicIdForTag(Integer topicId,
			Integer[] siteIds, Integer[] channelIds, Integer[] typeIds,
			Boolean titleImg, Boolean recommend, String title, int orderBy,
			int pageNo, int pageSize) {
		Finder f = byTopicId(topicId, siteIds, channelIds, typeIds, titleImg,
				recommend, title, orderBy);
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<Content> getListByTopicIdForTag(Integer topicId,
			Integer[] siteIds, Integer[] channelIds, Integer[] typeIds,
			Boolean titleImg, Boolean recommend, String title, int orderBy,
			Integer first, Integer count) {
		Finder f = byTopicId(topicId, siteIds, channelIds, typeIds, titleImg,
				recommend, title, orderBy);
		if (first != null) {
			f.setFirstResult(first);
		}
		if (count != null) {
			f.setMaxResults(count);
		}
		f.setCacheable(true);
		return find(f);
	}

	public Pagination getPageByTagIdsForTag(Integer[] tagIds,
			Integer[] siteIds, Integer[] channelIds, Integer[] typeIds,
			Integer excludeId, Boolean titleImg, Boolean recommend,
			String title, int orderBy, int pageNo, int pageSize) {
		Finder f = byTagIds(tagIds, siteIds, channelIds, typeIds, excludeId,
				titleImg, recommend, title, orderBy);
		f.setCacheable(true);
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<Content> getListByTagIdsForTag(Integer[] tagIds,
			Integer[] siteIds, Integer[] channelIds, Integer[] typeIds,
			Integer excludeId, Boolean titleImg, Boolean recommend,
			String title, int orderBy, Integer first, Integer count) {
		Finder f = byTagIds(tagIds, siteIds, channelIds, typeIds, excludeId,
				titleImg, recommend, title, orderBy);
		if (first != null) {
			f.setFirstResult(first);
		}
		if (count != null) {
			f.setMaxResults(count);
		}
		f.setCacheable(true);
		return find(f);
	}

	private Finder bySiteIds(Integer[] siteIds, Integer[] typeIds,
			Boolean titleImg, Boolean recommend, String title, int orderBy) {
		Finder f = Finder.create("select bean from Content bean");
		f.append(" join bean.contentExt as ext where 1=1");
		if (titleImg != null) {
			f.append(" and bean.hasTitleImg=:titleImg");
			f.setParam("titleImg", titleImg);
		}
		if (recommend != null) {
			f.append(" and bean.recommend=:recommend");
			f.setParam("recommend", recommend);
		}
		appendReleaseDate(f);
		appendTypeIds(f, typeIds);
		appendSiteIds(f, siteIds);
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		if (!StringUtils.isBlank(title)) {
			f.append(" and bean.contentExt.title like :title");
			f.setParam("title", "%" + title + "%");
		}
		appendOrder(f, orderBy);
		return f;
	}

	private Finder byChannelIds(Integer[] channelIds, Integer[] typeIds,
			Boolean titleImg, Boolean recommend, String title, int orderBy,
			int option) {
		Finder f = Finder.create();
		int len = channelIds.length;
		// 如果多个栏目
		if (option == 0 || len > 1) {
			f.append("select bean from Content bean");
			f.append(" join bean.contentExt as ext");
			if (len == 1) {
				f.append(" where bean.channel.id=:channelId");
				f.setParam("channelId", channelIds[0]);
			} else {
				f.append(" where bean.channel.id in (:channelIds)");
				f.setParamList("channelIds", channelIds);
			}
		} else if (option == 1) {
			// 包含子栏目
			f.append("select bean from Content bean");
			f.append(" join bean.contentExt as ext");
			f.append(" join bean.channel node,Channel parent");
			f.append(" where node.lft between parent.lft and parent.rgt");
			f.append(" and bean.site.id=parent.site.id");
			f.append(" and parent.id=:channelId");
			f.setParam("channelId", channelIds[0]);
		} else if (option == 2) {
			// 包含副栏目
			f.append("select bean from Content bean");
			f.append(" join bean.contentExt as ext");
			f.append(" join bean.channels as channel");
			f.append(" where channel.id=:channelId");
			f.setParam("channelId", channelIds[0]);
		} else {
			throw new RuntimeException("option value must be 0 or 1 or 2.");
		}
		if (titleImg != null) {
			f.append(" and bean.hasTitleImg=:titleImg");
			f.setParam("titleImg", titleImg);
		}
		if (recommend != null) {
			f.append(" and bean.recommend=:recommend");
			f.setParam("recommend", recommend);
		}
		appendReleaseDate(f);
		appendTypeIds(f, typeIds);
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		if (!StringUtils.isBlank(title)) {
			f.append(" and bean.contentExt.title like :title");
			f.setParam("title", "%" + title + "%");
		}
		appendOrder(f, orderBy);
		return f;
	}

	private Finder byChannelPaths(String[] paths, Integer[] siteIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy) {
		Finder f = Finder.create();
		f.append("select bean from Content bean join bean.channel channel");
		f.append(" join bean.contentExt as ext");
		int len = paths.length;
		if (len == 1) {
			f.append(" where channel.path=:path").setParam("path", paths[0]);
		} else {
			f.append(" where channel.path in (:paths)");
			f.setParamList("paths", paths);
		}
		if (siteIds != null) {
			len = siteIds.length;
			if (len == 1) {
				f.append(" and channel.site.id=:siteId");
				f.setParam("siteId", siteIds[0]);
			} else if (len > 1) {
				f.append(" and channel.site.id in (:siteIds)");
				f.setParamList("siteIds", siteIds);
			}
		}
		if (titleImg != null) {
			f.append(" and bean.hasTitleImg=:titleImg");
			f.setParam("titleImg", titleImg);
		}
		if (recommend != null) {
			f.append(" and bean.recommend=:recommend");
			f.setParam("recommend", recommend);
		}
		appendReleaseDate(f);
		appendTypeIds(f, typeIds);
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		if (!StringUtils.isBlank(title)) {
			f.append(" and bean.contentExt.title like :title");
			f.setParam("title", "%" + title + "%");
		}
		appendOrder(f, orderBy);
		return f;
	}

	private Finder byTopicId(Integer topicId, Integer[] siteIds,
			Integer[] channelIds, Integer[] typeIds, Boolean titleImg,
			Boolean recommend, String title, int orderBy) {
		Finder f = Finder.create();
		f.append("select bean from Content bean join bean.topics topic");
		f.append(" join bean.contentExt as ext");
		f.append(" where topic.id=:topicId").setParam("topicId", topicId);
		if (titleImg != null) {
			f.append(" and bean.hasTitleImg=:titleImg");
			f.setParam("titleImg", titleImg);
		}
		if (recommend != null) {
			f.append(" and bean.recommend=:recommend");
			f.setParam("recommend", recommend);
		}
		appendReleaseDate(f);
		appendTypeIds(f, typeIds);
		appendChannelIds(f, channelIds);
		appendSiteIds(f, siteIds);
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		if (!StringUtils.isBlank(title)) {
			f.append(" and bean.contentExt.title like :title");
			f.setParam("title", "%" + title + "%");
		}
		appendOrder(f, orderBy);
		return f;
	}

	private Finder byTagIds(Integer[] tagIds, Integer[] siteIds,
			Integer[] channelIds, Integer[] typeIds, Integer excludeId,
			Boolean titleImg, Boolean recommend, String title, int orderBy) {
		Finder f = Finder.create();
		int len = tagIds.length;
		if (len == 1) {
			f.append("select bean from Content bean join bean.tags tag");
			f.append(" join bean.contentExt as ext");
			f.append(" where tag.id=:tagId").setParam("tagId", tagIds[0]);
		} else {
			f.append("select distinct bean from Content bean");
			f.append(" join bean.contentExt as ext");
			f.append(" join bean.tags tag");
			f.append(" where tag.id in(:tagIds)");
			f.setParamList("tagIds", tagIds);
		}
		if (titleImg != null) {
			f.append(" and bean.hasTitleImg=:titleImg");
			f.setParam("titleImg", titleImg);
		}
		if (recommend != null) {
			f.append(" and bean.recommend=:recommend");
			f.setParam("recommend", recommend);
		}
		appendReleaseDate(f);
		appendTypeIds(f, typeIds);
		appendChannelIds(f, channelIds);
		appendSiteIds(f, siteIds);
		if (excludeId != null) {
			f.append(" and bean.id<>:excludeId");
			f.setParam("excludeId", excludeId);
		}
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		if (!StringUtils.isBlank(title)) {
			f.append(" and bean.contentExt.title like :title");
			f.setParam("title", "%" + title + "%");
		}
		appendOrder(f, orderBy);
		return f;
	}

	private void appendReleaseDate(Finder f) {
		f.append(" and ext.releaseDate<:currentDate");
		f.setParam("currentDate", new Date());
	}

	private void appendTypeIds(Finder f, Integer[] typeIds) {
		int len;
		if (typeIds != null) {
			len = typeIds.length;
			if (len == 1) {
				f.append(" and bean.type.id=:typeId");
				f.setParam("typeId", typeIds[0]);
			} else if (len > 1) {
				f.append(" and bean.type.id in (:typeIds)");
				f.setParamList("typeIds", typeIds);
			}
		}
	}

	private void appendChannelIds(Finder f, Integer[] channelIds) {
		int len;
		if (channelIds != null) {
			len = channelIds.length;
			if (len == 1) {
				f.append(" and bean.channel.id=:channelId");
				f.setParam("channelId", channelIds[0]);
			} else if (len > 1) {
				f.append(" and bean.channel.id in (:channelIds)");
				f.setParamList("channelIds", channelIds);
			}
		}
	}

	private void appendSiteIds(Finder f, Integer[] siteIds) {
		int len;
		if (siteIds != null) {
			len = siteIds.length;
			if (len == 1) {
				f.append(" and bean.site.id=:siteId");
				f.setParam("siteId", siteIds[0]);
			} else if (len > 1) {
				f.append(" and bean.site.id in (:siteIds)");
				f.setParamList("siteIds", siteIds);
			}
		}
	}

	private void appendOrder(Finder f, int orderBy) {
		switch (orderBy) {
		case 1:
			// ID升序
			f.append(" order by bean.id asc");
			break;
		case 2:
			// 发布时间降序
			f.append(" order by bean.sortDate desc");
			break;
		case 3:
			// 发布时间升序
			f.append(" order by bean.sortDate asc");
			break;
		case 4:
			// 固顶级别降序、发布时间降序
			f.append(" order by bean.topLevel desc, bean.sortDate desc");
			break;
		case 5:
			// 固顶级别降序、发布时间升序
			f.append(" order by bean.topLevel desc, bean.sortDate asc");
			break;
		case 6:
			// 日访问降序
			f.append(" order by bean.viewsDay desc, bean.id desc");
			break;
		case 7:
			// 周访问降序
			f.append(" order by bean.contentCount.viewsWeek desc");
			f.append(", bean.id desc");
			break;
		case 8:
			// 月访问降序
			f.append(" order by bean.contentCount.viewsMonth desc");
			f.append(", bean.id desc");
			break;
		case 9:
			// 总访问降序
			f.append(" order by bean.contentCount.views desc");
			f.append(", bean.id desc");
			break;
		case 10:
			// 日评论降序
			f.append(" order by bean.commentsDay desc, bean.id desc");
			break;
		case 11:
			// 周评论降序
			f.append(" order by bean.contentCount.commentsWeek desc");
			f.append(", bean.id desc");
			break;
		case 12:
			// 月评论降序
			f.append(" order by bean.contentCount.commentsMonth desc");
			f.append(", bean.id desc");
			break;
		case 13:
			// 总评论降序
			f.append(" order by bean.contentCount.comments desc");
			f.append(", bean.id desc");
			break;
		case 14:
			// 日下载降序
			f.append(" order by bean.downloadsDay desc, bean.id desc");
			break;
		case 15:
			// 周下载降序
			f.append(" order by bean.contentCount.downloadsWeek desc");
			f.append(", bean.id desc");
			break;
		case 16:
			// 月下载降序
			f.append(" order by bean.contentCount.downloadsMonth desc");
			f.append(", bean.id desc");
			break;
		case 17:
			// 总下载降序
			f.append(" order by bean.contentCount.downloads desc");
			f.append(", bean.id desc");
			break;
		case 18:
			// 日顶降序
			f.append(" order by bean.upsDay desc, bean.id desc");
			break;
		case 19:
			// 周顶降序
			f.append(" order by bean.contentCount.upsWeek desc");
			f.append(", bean.id desc");
			break;
		case 20:
			// 月顶降序
			f.append(" order by bean.contentCount.upsMonth desc");
			f.append(", bean.id desc");
			break;
		case 21:
			// 总顶降序
			f.append(" order by bean.contentCount.ups desc, bean.id desc");
			break;
		default:
			// 默认： ID降序
			f.append(" order by bean.id desc");
		}
	}

	public int countByChannelId(int channelId) {
		String hql = "select count(*) from Content bean"
				+ " join bean.channel channel,Channel parent"
				+ " where channel.lft between parent.lft and parent.rgt"
				+ " and channel.site.id=parent.site.id"
				+ " and parent.id=:parentId";
		Query query = getSession().createQuery(hql);
		query.setParameter("parentId", channelId);
		return ((Number) (query.iterate().next())).intValue();
	}

	public Content findById(Integer id) {
		Content entity = get(id);
		return entity;
	}

	public Content save(Content bean) {
		getSession().save(bean);
		return bean;
	}

	public Content deleteById(Integer id) {
		Content entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<Content> getEntityClass() {
		return Content.class;
	}
}