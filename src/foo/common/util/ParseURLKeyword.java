package foo.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ParseURLKeyword {
	public static String getKeyword(String url) {
		String keywordReg = "(?:yahoo.+?[\\?|&]q=|openfind.+?q=|google.+?q=|lycos.+?query=|onseek.+?keyword=|search\\.tom.+?word=|search\\.qq\\.com.+?word=|zhongsou\\.com.+?word=|search\\.msn\\.com.+?q=|yisou\\.com.+?p=|sina.+?word=|sina.+?query=|sina.+?_searchkey=|sohu.+?word=|sohu.+?key_word=|sohu.+?query=|163.+?q=|baidu.+?wd=|soso.+?w=|3721\\.com.+?p=|Alltheweb.+?q=)([^&]*)";
		String encodeReg = "^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$";
		Pattern keywordPattern = Pattern.compile(keywordReg);
		StringBuffer keywordBuff = new StringBuffer(20);
		Matcher keywordMat = keywordPattern.matcher(url);
		while (keywordMat.find()) {
			keywordMat.appendReplacement(keywordBuff, "$1");
		}
		String keyword = keywordBuff.toString();
		if (StringUtils.isNotBlank(keyword.toString())) {
			keyword = StringUtils.remove(keyword, keyword.substring(0, keyword
					.indexOf(".") + 1));
			Pattern encodePatt = Pattern.compile(encodeReg);
			String unescapeString = ParseURLKeyword.unescape(keyword);
			Matcher encodeMat = encodePatt.matcher(unescapeString);
			String encode = "gbk";
			if (encodeMat.matches())
				encode = "utf-8";
			try {
				return URLDecoder.decode(keyword, encode);
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
		return "";
	}

	public static String unescape(String src) {
		StringBuffer result = new StringBuffer();
		result.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					result.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					result.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					result.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					result.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return result.toString();
	}
}
