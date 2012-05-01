package foo.cms.statistic;

import java.text.NumberFormat;
import java.util.Date;

public class CmsStatistic {
	public static final String JOIN = "-";
	public static final String TIMEPATTERN = "HH:mm:ss";
	public static final String PERCENTSIGN = "%";
	public static final double COEFFICIENT = 0.8;

	public static final int MEMBER = 1;
	public static final int CONTENT = 2;
	public static final int COMMENT = 3;
	public static final int GUESTBOOK = 4;

	public static final int PV = 11;
	public static final int UNIQUEIP = 12;
	public static final int UNIQUEVISITOR = 13;
	public static final int AVGVIEWS = 14;
	
	public static final int TODAY = 21;
	public static final int YESTERDAY = 22;
	public static final int THISWEEK = 23;
	public static final int THISMONTH = 24;
	public static final int THISYEAR = 25;

	public static final String REFERER_WEBSITE = "refererWebSite";
	public static final String REFERER_PAGE = "refererPage";
	public static final String REFERER_KEYWORD = "refererKeyword";
	public static final String ACCESS_PAGE = "accessPage";
	public static final String AREA = "area";

	public static final String SITEID = "siteId";
	public static final String ISREPLYED = "isReplyed";
	public static final String USERID = "userId";
	public static final String CHANNELID = "channelId";

	public CmsStatistic() {
	}

	public CmsStatistic(Long count) {
		this.count = count;
	}

	public CmsStatistic(String description, Long count) {
		this(count);
		this.description = description;
	}

	public CmsStatistic(String description, Long count, Long total) {
		this(description, count);
		this.total = total;
	}

	/**
	 * 
	 * 统计模式
	 * 
	 */
	public static enum CmsStatisticModel {
		/**
		 * 日统计
		 */
		day,
		/**
		 * 周统计
		 */
		week,
		/**
		 * 月统计
		 */
		month,
		/**
		 * 年统计
		 */
		year
	}

	/**
	 * 时间范围
	 */
	public static class TimeRange {
		private final Date begin;
		private final Date end;
		
		public Date getBegin() {
			return begin;
		}
		
		public Date getEnd() {
			return end;
		}
		
		private TimeRange(Date begin, Date end){
			this.begin = begin ; 
			this.end = end;
		}
		
		public static TimeRange getInstance(Date begin, Date end){
			if(begin == null || end == null){
				throw new IllegalArgumentException("Params begin and end cannot be null!");
			}
			return new TimeRange(begin, end);
		}
	}

	private String description;
	private String vice;
	private Long count;
	private Long total;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getVice() {
		return vice;
	}

	public void setVice(String vice) {
		this.vice = vice;
	}

	public String getPercent() {
		return NumberFormat.getPercentInstance().format(
				count / (total == 0 ? 1.0 : total + 0.0));
	}

	public String getBarWidth() {
		return (int) ((Integer.parseInt(getPercent().replace(PERCENTSIGN, ""))) * COEFFICIENT)
				+ PERCENTSIGN;
	}
}
