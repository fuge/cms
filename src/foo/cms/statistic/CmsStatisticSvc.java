package foo.cms.statistic;

import java.util.Date;
import java.util.List;
import java.util.Map;

import foo.cms.statistic.CmsStatistic.CmsStatisticModel;
import foo.common.page.Pagination;

public interface CmsStatisticSvc {
	public List<CmsStatistic> statisticByModel(int type,
			CmsStatisticModel statisticModel, Integer year, Integer month,
			Integer day, Map<String, Object> restrictions);
	
	public List<CmsStatistic> flowStatistic(int type,Integer siteId);

	public Pagination flowAnalysisPage(String groupCondition, Integer siteId, Integer pageNo, Integer pageSize);

	public void flowInit(Integer siteId, Date startDate, Date endDate);
	
	public Map<String, List<CmsStatistic>> getWelcomeSiteFlowData(Integer siteId);
}
