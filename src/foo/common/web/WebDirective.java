package foo.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateDirectiveModel;

public abstract class WebDirective implements TemplateDirectiveModel {
	/**
	 * 日志。可以被子类使用。
	 */
	protected final Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * 输出参数：列表数据
	 */
	public static final String OUT_LIST = "tag_list";
	/**
	 * 输出参数：分页数据
	 */
	public static final String OUT_PAGINATION = "tag_pagination";
}
