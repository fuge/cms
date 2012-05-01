package foo.cms.statistic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import foo.cms.statistic.CmsStatistic.TimeRange;
import foo.common.page.Pagination;

public interface CmsStatisticDao {
	public long memberStatistic(TimeRange timeRange);

	public long contentStatistic(TimeRange timeRange,
			Map<String, Object> restrictions);

	public long commentStatistic(TimeRange timeRange,
			Map<String, Object> restrictions);

	public long guestbookStatistic(TimeRange timeRange,
			Map<String, Object> restrictions);

	public long getPvCountByTimeRange(Integer siteId, TimeRange timeRange);
	
	public long getPvCount(Integer siteId);

	public List<Object[]> getPvCountByGroup(Integer siteId);

	public long getUniqueIpCountByTimeRange(Integer siteId, TimeRange timeRange);
	
	public long getUniqueIpCount(Integer siteId);

	public List<Object[]> getUniqueIpCountByGroup(Integer siteId);

	public long getUniqueVisitorCountByTimeRange(Integer siteId, TimeRange timeRange);
	
	public long getUniqueVisitorCount(Integer siteId);

	public List<Object[]> getUniqueVisitorCountByGroup(Integer siteId);

	public Pagination flowAnalysisPage(String groupCondition, Integer siteId, Integer pageNo, Integer pageSize);

	public long flowAnalysisTotal(Integer siteId);

	public void flowInit(Integer siteId, Date startDate, Date endDate);
}
