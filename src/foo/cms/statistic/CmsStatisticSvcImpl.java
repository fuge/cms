package foo.cms.statistic;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static foo.cms.statistic.CmsStatistic.AVGVIEWS;
import static foo.cms.statistic.CmsStatistic.COMMENT;
import static foo.cms.statistic.CmsStatistic.CONTENT;
import static foo.cms.statistic.CmsStatistic.GUESTBOOK;
import static foo.cms.statistic.CmsStatistic.JOIN;
import static foo.cms.statistic.CmsStatistic.MEMBER;
import static foo.cms.statistic.CmsStatistic.PV;
import static foo.cms.statistic.CmsStatistic.THISMONTH;
import static foo.cms.statistic.CmsStatistic.THISWEEK;
import static foo.cms.statistic.CmsStatistic.THISYEAR;
import static foo.cms.statistic.CmsStatistic.TIMEPATTERN;
import static foo.cms.statistic.CmsStatistic.TODAY;
import static foo.cms.statistic.CmsStatistic.UNIQUEIP;
import static foo.cms.statistic.CmsStatistic.UNIQUEVISITOR;
import static foo.cms.statistic.CmsStatistic.YESTERDAY;
import static foo.common.util.ArithmeticUtils.dividend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.statistic.CmsStatistic.CmsStatisticModel;
import foo.cms.statistic.CmsStatistic.TimeRange;
import foo.common.page.Pagination;
import foo.common.util.DateFormatUtils;

@Service
@Transactional(readOnly = true)
public class CmsStatisticSvcImpl implements CmsStatisticSvc {
	public List<CmsStatistic> statisticByModel(int type,
			CmsStatisticModel statisticModel, Integer year, Integer month,
			Integer day, Map<String, Object> restrictions) {
		Calendar calendar;
		if (month == null) {
			month = 0;
		} else {
			month = month - 1;
		}
		if (day == null) {
			day = 1;
		}
		if (year == null) {
			calendar = new GregorianCalendar();
		} else {
			calendar = new GregorianCalendar(year, month, day);
		}
		return statisticByModel(type, statisticModel, calendar, restrictions);
	}

	private List<CmsStatistic> statisticByModel(int type,
			CmsStatisticModel statisticModel, Calendar calendar,
			Map<String, Object> restrictions) {
		switch (statisticModel) {
		case day: {
			return statisticByDay(type, calendar, restrictions);
		}
		case week: {
			return statisticByWeek(type, calendar, restrictions);
		}
		case month: {
			return statisticByMonth(type, calendar, restrictions);
		}
		case year: {
			return statisticByYear(type, calendar, restrictions);
		}
		}
		return new ArrayList<CmsStatistic>();
	}

	private List<CmsStatistic> statisticByDay(int type, Calendar calendar,
			Map<String, Object> restrictions) {
		calendar = clearTime(calendar);
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		long total = 0, count = 0;
		Date begin, end;
		Calendar clone = (Calendar) calendar.clone();
		total = statistic(type, getTimeRange(TODAY, clone), restrictions);
		for (int i = 0; i < 24; i++) {
			calendar.set(HOUR_OF_DAY, i);
			begin = calendar.getTime();
			calendar.set(HOUR_OF_DAY, i + 1);
			end = calendar.getTime();
			count = statistic(type, TimeRange.getInstance(begin, end), restrictions);
			CmsStatistic bean = new CmsStatistic(format(i), count, total);
			list.add(bean);
		}
		return list;
	}

	private List<CmsStatistic> statisticByWeek(int type, Calendar calendar,
			Map<String, Object> restrictions) {
		calendar = clearTime(calendar);
		flush(calendar);
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		long total = 0, count = 0;
		Date begin, end;
		Calendar clone = (Calendar) calendar.clone();
		total = statistic(type, getTimeRange(THISWEEK, clone), restrictions);
		for (int i = 1; i <= 7; i++) {
			calendar.set(DAY_OF_WEEK, i);
			begin = calendar.getTime();
			if (i == 7) {
				calendar.add(DAY_OF_WEEK, 1);
			} else {
				calendar.set(DAY_OF_WEEK, i + 1);
			}
			end = calendar.getTime();
			count = statistic(type, TimeRange.getInstance(begin, end), restrictions);
			CmsStatistic bean = new CmsStatistic(String.valueOf(i), count,
					total);
			list.add(bean);
		}
		return list;
	}

	private List<CmsStatistic> statisticByMonth(int type, Calendar calendar,
			Map<String, Object> restrictions) {
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		int year = getYear(calendar);
		int month = getMonth(calendar);
		long total = 0, count = 0;
		int day = 1, days;
		Date begin, end;
		calendar = new GregorianCalendar(year, month, day);
		total = statistic(type, getTimeRange(THISMONTH, (Calendar) calendar.clone()), restrictions);
		Calendar clone = (Calendar) calendar.clone();
		clone.set(MONTH, month + 1);
		end = clone.getTime();
		clone.add(DATE, -1);
		days = getDay(clone);
		for (int i = 1; i <= days; i++) {
			calendar.set(DATE, i);
			begin = calendar.getTime();
			calendar.set(DATE, i + 1);
			end = calendar.getTime();
			count = statistic(type, TimeRange.getInstance(begin, end), restrictions);
			CmsStatistic bean = new CmsStatistic(String.valueOf(i), count,
					total);
			list.add(bean);
		}
		return list;
	}

	private List<CmsStatistic> statisticByYear(int type, Calendar calendar,
			Map<String, Object> restrictions) {
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		int year = getYear(calendar);
		long total = 0, count = 0;
		int day = 1, month = 0;
		Date begin, end;
		calendar = new GregorianCalendar(year, month, day);
		Calendar clone = (Calendar) calendar.clone();
		total = statistic(type, getTimeRange(THISYEAR, clone), restrictions);
		for (int i = 0; i < 12; i++) {
			calendar.set(MONTH, i);
			begin = calendar.getTime();
			calendar.set(MONTH, i + 1);
			end = calendar.getTime();
			count = statistic(type, TimeRange.getInstance(begin, end), restrictions);
			CmsStatistic bean = new CmsStatistic(String.valueOf(i + 1), count,
					total);
			list.add(bean);
		}
		return list;
	}

	private long statistic(int type, TimeRange timeRange,
			Map<String, Object> restrictions) {
		switch (type) {
		case MEMBER: {
			return dao.memberStatistic(timeRange);
		}
		case CONTENT: {
			return dao.contentStatistic(timeRange, restrictions);
		}
		case COMMENT: {
			return dao.commentStatistic(timeRange, restrictions);
		}
		case GUESTBOOK: {
			return dao.guestbookStatistic(timeRange, restrictions);
		}
		}
		return 0;
	}

	private List<CmsStatistic> pvStatistic(Integer siteId) {
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		Date begin;
		long count;
		Calendar calendar = new GregorianCalendar();
		calendar = clearTime(calendar);
		begin = calendar.getTime();
		count = dao.getPvCountByTimeRange(siteId, getTimeRange(TODAY));
		CmsStatistic today = new CmsStatistic("statistic.pv.today", count);
		today.setVice(DateFormatUtils.formatDate(begin));
		list.add(today);
		calendar.add(DATE, -1);
		begin = calendar.getTime();
		count = dao.getPvCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		CmsStatistic yesterday = new CmsStatistic("statistic.pv.yesterday",
				count);
		yesterday.setVice(DateFormatUtils.formatDate(begin));
		list.add(yesterday);
		count = avg(dao.getPvCountByGroup(siteId));
		CmsStatistic avg = new CmsStatistic("statistic.pv.avg", count);
		list.add(avg);
		Object[] objs = max(dao.getPvCountByGroup(siteId));
		count = (Integer) objs[0];
		CmsStatistic max = new CmsStatistic("statistic.pv.max", count);
		max.setVice((String) objs[1]);
		list.add(max);
		count = dao.getPvCount(siteId);
		CmsStatistic total = new CmsStatistic("statistic.pv.total", count);
		list.add(total);
		return list;
	}

	private List<CmsStatistic> uniqueIpStatistic(Integer siteId) {
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		Date begin;
		long count;
		Calendar calendar = new GregorianCalendar();
		calendar = clearTime(calendar);
		begin = calendar.getTime();
		count = dao.getUniqueIpCountByTimeRange(siteId, getTimeRange(TODAY));
		CmsStatistic today = new CmsStatistic("statistic.uniqueIp.today", count);
		today.setVice(DateFormatUtils.formatDate(begin));
		list.add(today);
		calendar.add(DATE, -1);
		begin = calendar.getTime();
		count = dao.getUniqueIpCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		CmsStatistic yesterday = new CmsStatistic(
				"statistic.uniqueIp.yesterday", count);
		yesterday.setVice(DateFormatUtils.formatDate(begin));
		list.add(yesterday);
		count = avg(dao.getUniqueIpCountByGroup(siteId));
		CmsStatistic avg = new CmsStatistic("statistic.uniqueIp.avg", count);
		list.add(avg);
		Object[] objs = max(dao.getUniqueIpCountByGroup(siteId));
		count = (Integer) objs[0];
		CmsStatistic max = new CmsStatistic("statistic.uniqueIp.max", count);
		max.setVice((String) objs[1]);
		list.add(max);
		count = dao.getUniqueIpCount(siteId);
		CmsStatistic total = new CmsStatistic("statistic.uniqueIp.total", count);
		list.add(total);
		return list;
	}

	private List<CmsStatistic> uniqueVisitorStatistic(Integer siteId) {
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		Date begin;
		long count;
		Calendar calendar = new GregorianCalendar();
		calendar = clearTime(calendar);
		begin = calendar.getTime();
		count = dao.getUniqueVisitorCountByTimeRange(siteId, getTimeRange(TODAY));
		CmsStatistic today = new CmsStatistic("statistic.uniqueVisitor.today",
				count);
		today.setVice(DateFormatUtils.formatDate(begin));
		list.add(today);
		calendar.add(DATE, -1);
		begin = calendar.getTime();
		count = dao.getUniqueVisitorCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		CmsStatistic yesterday = new CmsStatistic(
				"statistic.uniqueVisitor.yesterday", count);
		yesterday.setVice(DateFormatUtils.formatDate(begin));
		list.add(yesterday);
		count = avg(dao.getUniqueVisitorCountByGroup(siteId));
		CmsStatistic avg = new CmsStatistic("statistic.uniqueVisitor.avg",
				count);
		list.add(avg);
		Object[] objs = max(dao.getUniqueVisitorCountByGroup(siteId));
		count = (Integer) objs[0];
		CmsStatistic max = new CmsStatistic("statistic.uniqueVisitor.max",
				count);
		max.setVice((String) objs[1]);
		list.add(max);
		count = dao.getUniqueVisitorCount(siteId);
		CmsStatistic total = new CmsStatistic("statistic.uniqueVisitor.total",
				count);
		list.add(total);
		return list;
	}

	private List<CmsStatistic> avgViewsStatistic(Integer siteId) {
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		Date begin;
		long count, pvs, visitors;
		Calendar calendar = new GregorianCalendar();
		calendar = clearTime(calendar);
		begin = calendar.getTime();
		pvs = dao.getPvCountByTimeRange(siteId, getTimeRange(TODAY));
		visitors = dao.getUniqueVisitorCountByTimeRange(siteId,getTimeRange(TODAY));
		CmsStatistic today = new CmsStatistic("statistic.avgViews.today", pvs
				/ dividend(visitors));
		today.setVice(DateFormatUtils.formatDate(begin));
		list.add(today);
		calendar.add(DATE, -1);
		begin = calendar.getTime();
		pvs = dao.getPvCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		visitors = dao.getUniqueVisitorCountByTimeRange(siteId, getTimeRange(YESTERDAY));
		CmsStatistic yesterday = new CmsStatistic(
				"statistic.avgViews.yesterday", pvs / dividend(visitors));
		yesterday.setVice(DateFormatUtils.formatDate(begin));
		list.add(yesterday);
		count = avg(dao.getPvCountByGroup(siteId), dao.getUniqueVisitorCountByGroup(siteId));
		CmsStatistic avg = new CmsStatistic("statistic.avgViews.avg", count);
		list.add(avg);
		Object[] objs = max(dao.getPvCountByGroup(siteId), dao.getUniqueVisitorCountByGroup(siteId));
		count = (Integer) objs[0];
		CmsStatistic max = new CmsStatistic("statistic.avgViews.max", count);
		max.setVice((String) objs[1]);
		list.add(max);
		pvs = dao.getPvCount(siteId);
		visitors = dao.getUniqueVisitorCount(siteId);
		CmsStatistic total = new CmsStatistic("statistic.avgViews.total", pvs
				/ dividend(visitors));
		list.add(total);
		return list;
	}

	public List<CmsStatistic> flowStatistic(int type, Integer siteId) {
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		switch (type) {
		case PV: {
			return pvStatistic(siteId);
		}
		case UNIQUEIP: {
			return uniqueIpStatistic(siteId);
		}
		case UNIQUEVISITOR: {
			return uniqueVisitorStatistic(siteId);
		}
		case AVGVIEWS: {
			return avgViewsStatistic(siteId);
		}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Pagination flowAnalysisPage(String groupCondition, Integer siteId,
			Integer pageNo, Integer pageSize) {
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		Pagination pagination = dao.flowAnalysisPage(groupCondition, siteId,
				pageNo, pageSize);
		long total = dao.flowAnalysisTotal(siteId);
		for (Object[] objArr : (List<Object[]>) pagination.getList()) {
			CmsStatistic cmsStatistic = new CmsStatistic((String) objArr[1],
					(Long) objArr[0], total);
			list.add(cmsStatistic);
		}
		pagination.setList(list);
		return pagination;
	}

	public Map<String, List<CmsStatistic>> getWelcomeSiteFlowData(Integer siteId) {
		Map<String, List<CmsStatistic>> map = new HashMap<String, List<CmsStatistic>>();
		map.put("today", getListByTimeRange(siteId, getTimeRange(TODAY)));
		map.put("yesterday",getListByTimeRange(siteId, getTimeRange(YESTERDAY)));
		map.put("thisweek", getListByTimeRange(siteId, getTimeRange(THISWEEK)));
		map.put("thismonth",getListByTimeRange(siteId, getTimeRange(THISMONTH)));
		map.put("total",getListByTimeRange(siteId, getTimeRange(-1)));
		return map;
	}

	@Transactional
	public void flowInit(Integer siteId, Date startDate, Date endDate) {
		dao.flowInit(siteId, startDate, endDate);
	}

	@Autowired
	private CmsStatisticDao dao;

	private List<CmsStatistic> getListByTimeRange(Integer siteId,
			TimeRange timeRange) {
		List<CmsStatistic> list = new ArrayList<CmsStatistic>();
		list.add(new CmsStatistic(getPvCountByTimeRange(siteId, timeRange)));
		list.add(new CmsStatistic(getUniqueIpCountByTimeRange(siteId, timeRange)));
		list.add(new CmsStatistic(getUniqueVisitorCountByTimeRange(siteId, timeRange)));
		return list;
	}

	private long getPvCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return dao.getPvCountByTimeRange(siteId, timeRange);
	}

	private long getUniqueIpCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return dao.getUniqueIpCountByTimeRange(siteId, timeRange);
	}

	private long getUniqueVisitorCountByTimeRange(Integer siteId, TimeRange timeRange) {
		return dao.getUniqueVisitorCountByTimeRange(siteId, timeRange);
	}

	private String format(int time) {
		Calendar calendar = clearTime(new GregorianCalendar());
		calendar.set(HOUR_OF_DAY, time);
		String begin, end;
		begin = DateFormatUtils.format(calendar.getTime(), TIMEPATTERN);
		calendar.add(HOUR_OF_DAY, 1);
		end = DateFormatUtils.format(calendar.getTime(), TIMEPATTERN);
		return begin + JOIN + end;
	}

	private int getYear(Calendar calendar) {
		return calendar.get(YEAR);
	}

	private int getMonth(Calendar calendar) {
		return calendar.get(MONTH);
	}

	private int getDay(Calendar calendar) {
		return calendar.get(DATE);
	}

	private Calendar clearTime(Calendar calendar) {
		return new GregorianCalendar(getYear(calendar), getMonth(calendar),
				getDay(calendar));
	}

	private void flush(Calendar calendar) {
		calendar.getTime();
	}

	private int avg(List<Object[]> list) {
		int count = 0;
		for (Object[] obj : list) {
			count += (Long) obj[0];
		}
		return count / dividend(list.size());
	}

	private int avg(List<Object[]> pvList, List<Object[]> visitorsList) {
		int count = 0;
		if (pvList.size() != visitorsList.size()) {
			return count;
		}
		for (int i = 0; i < pvList.size(); i++) {
			long pvCount = (Long) pvList.get(i)[0];
			long visitorCount = (Long) visitorsList.get(i)[0];
			count += pvCount / visitorCount;
		}
		return count / dividend((pvList.size()));
	}

	private Object[] max(List<Object[]> list) {
		int max = 0;
		String date = null;
		for (Object[] objs : list) {
			long curr = (Long) objs[0];
			if (max < curr) {
				max = (int) curr;
				date = (String) objs[1];
			}
		}
		return new Object[] { max, date };
	}

	private Object[] max(List<Object[]> pvList, List<Object[]> visitorsList) {
		int max = 0;
		String date = null;
		if (pvList.size() != visitorsList.size()) {
			return new Object[] { max, date };
		}
		for (int i = 0; i < pvList.size(); i++) {
			long pvCount = (Long) pvList.get(i)[0];
			long visitorCount = (Long) visitorsList.get(i)[0];
			long curr = pvCount / visitorCount;
			if (max < curr) {
				max = (int) curr;
				date = (String) pvList.get(i)[1];
			}
		}
		return new Object[] { max, date };
	}

	private TimeRange getTimeRange(int type){
		return getTimeRange(type, new GregorianCalendar());
	}
	
	// 获取今日、昨日、本周、本月时间范围
	private TimeRange getTimeRange(int type, Calendar calendar) {
		calendar = clearTime(calendar);
		Date begin, end;
		switch (type) {
		case TODAY: {
			begin = calendar.getTime();
			calendar.add(DATE, 1);
			end = calendar.getTime();
			return TimeRange.getInstance(begin, end);
		}
		case YESTERDAY: {
			calendar.add(DATE, -1);
			begin = calendar.getTime();
			calendar.add(DATE, 1);
			end = calendar.getTime();
			return TimeRange.getInstance(begin, end);
		}
		case THISWEEK: {
			flush(calendar);
			calendar.set(DAY_OF_WEEK, 1);
			begin = calendar.getTime();
			calendar.add(DAY_OF_WEEK, 7);
			end = calendar.getTime();
			return TimeRange.getInstance(begin, end);
		}
		case THISMONTH: {
			int month = calendar.get(MONTH);
			calendar.set(DATE, 1);
			begin = calendar.getTime();
			calendar.set(MONTH, month + 1);
			end = calendar.getTime();
			return TimeRange.getInstance(begin, end);
		}
		case THISYEAR:{
			int year = calendar.get(YEAR);
			calendar.set(MONTH, 0);
			calendar.set(DATE, 1);
			begin = calendar.getTime();
			calendar.set(YEAR, year + 1);
			end = calendar.getTime();
			return TimeRange.getInstance(begin, end);
		}
		}
		return null;
	}
}
