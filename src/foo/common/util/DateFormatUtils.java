package foo.common.util;

import java.text.DateFormat;
import java.util.Date;

public class DateFormatUtils extends org.apache.commons.lang.time.DateFormatUtils{
	private DateFormatUtils(){}
	
	public static String formatDate(Date date){
		return DateFormat.getDateInstance().format(date);
	}
}
