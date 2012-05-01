package foo.cms.action.directive.abs;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import foo.cms.manager.assist.CmsCommentMng;
import foo.common.web.freemarker.DirectiveUtils;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 评论标签基类
 * 
 * @author liufang
 * 
 */
public abstract class AbstractCmsCommentDirective implements
		TemplateDirectiveModel {

	/**
	 * 输入参数，内容ID。
	 */
	public static final String PARAM_CONTENT_ID = "contentId";
	/**
	 * 输入参数，支持大于多少。
	 */
	public static final String PARAM_GREATER_THEN = "greaterThen";
	/**
	 * 输入参数，是否审核。
	 */
	public static final String PARAM_CHECKED = "checked";
	/**
	 * 输入参数，是否推荐。
	 */
	public static final String PARAM_RECOMMEND = "recommend";
	/**
	 * 输入参数，排列顺序。0：按评论时间降序；1：按评论时间升序。默认降序。
	 */
	public static final String PARAM_ORDER_BY = "orderBy";

	protected Integer getContentId(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(PARAM_CONTENT_ID, params);
	}

	protected Integer getGreaterThen(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(PARAM_GREATER_THEN, params);
	}

	protected Boolean getChecked(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getBool(PARAM_CHECKED, params);
	}

	protected boolean getRecommend(Map<String, TemplateModel> params)
			throws TemplateException {
		Boolean recommend = DirectiveUtils.getBool(PARAM_RECOMMEND, params);
		return recommend != null ? recommend : false;
	}

	protected boolean getDesc(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer orderBy = DirectiveUtils.getInt(PARAM_ORDER_BY, params);
		if (orderBy == null || orderBy == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Autowired
	protected CmsCommentMng cmsCommentMng;

}
