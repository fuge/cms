package foo.core.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

public class WebErrors extends foo.common.web.springmvc.WebErrors {
	/**
	 * 默认错误页面
	 */
	public static final String ERROR_PAGE = "/common/error_message";
	/**
	 * 默认错误信息属性名称
	 */
	public static final String ERROR_ATTR_NAME = "errors";

	/**
	 * 通过HttpServletRequest创建WebErrors
	 * 
	 * @param request
	 *            从request中获得MessageSource和Locale，如果存在的话。
	 * @return 如果LocaleResolver存在则返回国际化WebErrors
	 */
	public static WebErrors create(HttpServletRequest request) {
		return new WebErrors(request);
	}

	public WebErrors() {
	}

	public WebErrors(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 构造器
	 * 
	 * @param messageSource
	 * @param locale
	 */
	public WebErrors(MessageSource messageSource, Locale locale) {
		super(messageSource, locale);
	}

	@Override
	protected String getErrorAttrName() {
		return ERROR_ATTR_NAME;
	}

	@Override
	protected String getErrorPage() {
		return ERROR_PAGE;
	}
}
