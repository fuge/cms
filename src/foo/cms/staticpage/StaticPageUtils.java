package foo.cms.staticpage;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class StaticPageUtils {
	// ${modelId}
	// ${modelPath}
	// ${channelId}
	// ${channelPath}
	// ${year}
	// ${month}
	// ${MM}
	// ${day}
	// ${DD}
	// ${time}
	// ${contentId}
	// ${page}
	public static String staticUrlRule(String rule, Integer modelId,
			String modelPath, Integer channelId, String channelPath,
			Integer contentId, Date date) {
		if (StringUtils.isBlank(rule)) {
			return rule;
		}
		if (modelId != null) {
			rule = StringUtils.replace(rule, "${modelId}", modelId.toString());
		}
		if (!StringUtils.isBlank(modelPath)) {
			rule = StringUtils.replace(rule, "${modelPath}", modelPath);
		}
		if (channelId != null) {
			rule = StringUtils.replace(rule, "${channelId}", channelId
					.toString());
		}
		if (StringUtils.isBlank(channelPath)) {
			rule = StringUtils.replace(rule, "${channelPath}", channelPath);
		}
		if (contentId != null) {
			rule = StringUtils.replace(rule, "${contentId}", contentId
					.toString());
		}
		if (date != null) {
			// 获得年月日
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String mm = month >= 10 ? String.valueOf(month) : "0" + month;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			String dd = day >= 10 ? String.valueOf(day) : "0" + day;
			long time = date.getTime();
			rule = StringUtils.replace(rule, "${year}", String.valueOf(year));
			rule = StringUtils.replace(rule, "${month}", String.valueOf(month));
			rule = StringUtils.replace(rule, "${MM}", mm);
			rule = StringUtils.replace(rule, "${day}", String.valueOf(day));
			rule = StringUtils.replace(rule, "${DD}", dd);
			rule = StringUtils.replace(rule, "${time}", String.valueOf(time));
		}
		return rule;
	}
}
