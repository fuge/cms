package foo.cms.statistic;

import static foo.cms.statistic.CmsStatistic.CHANNELID;
import static foo.cms.statistic.CmsStatistic.ISREPLYED;
import static foo.cms.statistic.CmsStatistic.SITEID;
import static foo.cms.statistic.CmsStatistic.USERID;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import foo.cms.statistic.CmsStatistic.TimeRange;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateSimpleDao;
import foo.common.page.Pagination;

@Repository
public class CmsStatisticDaoImpl extends HibernateSimpleDao implements
		CmsStatisticDao {
	public long memberStatistic(TimeRange timeRange) {
		Finder f = createCacheableFinder("select count(*) from CmsUser bean where 1=1");
		if (timeRange != null) {
			f.append(" and bean.registerTime >= :begin");
			f.append(" and bean.registerTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		return (Long) find(f).iterator().next();
	}

	public long contentStatistic(TimeRange timeRange,
			Map<String, Object> restrictions) {
		Finder f = createCacheableFinder("select count(*) from Content bean");
		Integer userId = (Integer) restrictions.get(USERID);
		Integer channelId = (Integer) restrictions.get(CHANNELID);
		if (channelId != null) {
			f.append(" join bean.channel channel,Channel parent");
			f.append(" where channel.lft between parent.lft and parent.rgt");
			f.append(" and channel.site.id=parent.site.id");
			f.append(" and parent.id=:parentId");
			f.setParam("parentId", channelId);
		} else {
			f.append(" where bean.site.id=:siteId").setParam("siteId",
					restrictions.get(SITEID));
		}
		if (timeRange != null) {
			f.append(" and bean.contentExt.releaseDate >= :begin");
			f.append(" and bean.contentExt.releaseDate < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		if (userId != null) {
			f.append(" and bean.user.id=:userId").setParam("userId", userId);
		}
		return (Long) find(f).iterator().next();
	}

	public long commentStatistic(TimeRange timeRange,
			Map<String, Object> restrictions) {
		Finder f = createCacheableFinder("select count(*) from CmsComment bean where bean.site.id=:siteId");
		f.setParam("siteId", restrictions.get(SITEID));
		if (timeRange != null) {
			f.append(" and bean.createTime >= :begin");
			f.append(" and bean.createTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		Boolean isReplyed = (Boolean) restrictions.get(ISREPLYED);
		if (isReplyed != null) {
			if (isReplyed) {
				f.append(" and bean.replayTime is not null");
			} else {
				f.append(" and bean.replayTime is null");
			}
		}
		return (Long) find(f).iterator().next();
	}

	public long guestbookStatistic(TimeRange timeRange,
			Map<String, Object> restrictions) {
		Finder f = createCacheableFinder("select count(*) from CmsGuestbook bean where bean.site.id=:siteId");
		f.setParam("siteId", restrictions.get(SITEID));
		if (timeRange != null) {
			f.append(" and bean.createTime >= :begin");
			f.append(" and bean.createTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		Boolean isReplyed = (Boolean) restrictions.get(ISREPLYED);
		if (isReplyed != null) {
			if (isReplyed) {
				f.append(" and bean.replayTime is not null");
			} else {
				f.append(" and bean.replayTime is null");
			}
		}
		return (Long) find(f).iterator().next();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getPvCountByGroup(Integer siteId) {
		Finder f = createCacheableFinder("select count(*),bean.accessDate from CmsSiteFlow bean where bean.site.id=:siteId group by bean.accessDate");
		f.setParam("siteId", siteId);
		return find(f);
	}

	public long getPvCountByTimeRange(Integer siteId, TimeRange timeRange) {
		Finder f = createCacheableFinder("select count(*) from CmsSiteFlow bean");
		return byTimeRange(f, siteId, timeRange);
	}
	
	public long getPvCount(Integer siteId) {
		return getPvCountByTimeRange(siteId, null);
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getUniqueIpCountByGroup(Integer siteId) {
		Finder f = createCacheableFinder("select count(distinct bean.accessIp),bean.accessDate from CmsSiteFlow bean where bean.site.id=:siteId group by bean.accessDate");
		f.setParam("siteId", siteId);
		return find(f);
	}

	public long getUniqueIpCountByTimeRange(Integer siteId, TimeRange timeRange) {
		Finder f = createCacheableFinder("select count(distinct bean.accessIp) from CmsSiteFlow bean");
		return byTimeRange(f, siteId, timeRange);
	}

	public long getUniqueIpCount(Integer siteId) {
		return getUniqueIpCountByTimeRange(siteId, null);
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getUniqueVisitorCountByGroup(Integer siteId) {
		Finder f = createCacheableFinder("select count(distinct bean.sessionId),bean.accessDate from CmsSiteFlow bean where bean.site.id=:siteId group by bean.accessDate");
		f.setParam("siteId", siteId);
		return find(f);
	}

	public long getUniqueVisitorCountByTimeRange(Integer siteId,
			TimeRange timeRange) {
		Finder f = createCacheableFinder("select count(distinct bean.sessionId) from CmsSiteFlow bean");
		return byTimeRange(f, siteId, timeRange);
	}

	public long getUniqueVisitorCount(Integer siteId) {
		return getUniqueVisitorCountByTimeRange(siteId, null);
	}

	public Pagination flowAnalysisPage(String groupCondition, Integer siteId,
			Integer pageNo, Integer pageSize) {
		Finder f = createCacheableFinder("select count(*),bean."
				+ groupCondition
				+ " from CmsSiteFlow bean where bean.site.id=:siteId group by bean."
				+ groupCondition + " order by count(*) desc");
		f.setParam("siteId", siteId);
		f.setMaxResults(pageSize);
		f.setFirstResult((pageNo - 1) * pageSize);
		return new Pagination(pageNo, pageSize, getTotalCount(f.getOrigHql(),
				siteId), find(f));
	}

	public void flowInit(Integer siteId, Date startDate, Date endDate) {
		Finder f = Finder.create("delete from CmsSiteFlow bean where bean.site.id=:siteId");
		f.setParam("siteId", siteId);
		if (startDate != null) {
			f.append(" and bean.accessTime >= :startDate");
			f.setParam("startDate", startDate);
		}
		if (endDate != null) {
			f.append(" and bean.accessTime <= :endDate");
			f.setParam("endDate", endDate);
		}
		Query query = f.createQuery(getSession());
		query.executeUpdate();
	}

	public long flowAnalysisTotal(Integer siteId) {
		Finder f = createCacheableFinder("select count(*) from CmsSiteFlow bean where bean.site.id=:siteId");
		f.setParam("siteId", siteId);
		return (Long) find(f).iterator().next();
	}

	private int getTotalCount(String hql, Integer siteId) {
		Finder f = createCacheableFinder(hql);
		f.setParam("siteId", siteId);
		return find(f).size();
	}
	
	private long byTimeRange(Finder f, Integer siteId, TimeRange timeRange){
		f.append(" where bean.site.id=:siteId").setParam("siteId", siteId);
		if (timeRange != null) {
			f.append(" and bean.accessTime >= :begin");
			f.append(" and bean.accessTime < :end");
			f.setParam("begin", timeRange.getBegin());
			f.setParam("end", timeRange.getEnd());
		}
		return (Long) find(f).iterator().next();
	}

	private Finder createCacheableFinder(String hql) {
		Finder finder = Finder.create(hql);
		finder.setCacheable(true);
		return finder;
	}

}
