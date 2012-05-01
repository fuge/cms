package foo.cms.action.directive.abs;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import foo.cms.manager.assist.CmsGuestbookMng;
import foo.common.web.freemarker.DirectiveUtils;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 内容标签基类
 * 
 * @author liufang
 * 
 */
public abstract class AbstractCmsGuestbookDirective implements
		TemplateDirectiveModel {
	/**
	 * 输入参数，站点ID。
	 */
	public static final String PARAM_SITE_ID = "siteId";
	/**
	 * 输入参数，评论类别ID。
	 */
	public static final String PARAM_CTG_ID = "ctgId";
	/**
	 * 输入参数，是否推荐。
	 */
	public static final String PARAM_RECOMMEND = "recommend";
	/**
	 * 输入参数，是否审核。
	 */
	public static final String PARAM_CHECKED = "checked";
	/**
	 * 输入参数，排列顺序。0：按留言时间降序；1：按留言时间升序。默认降序。
	 */
	public static final String PARAM_ORDER_BY = "orderBy";

	protected Integer getSiteId(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(PARAM_SITE_ID, params);
	}

	protected Integer getCtgId(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(PARAM_CTG_ID, params);
	}

	protected Boolean getRecommend(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getBool(PARAM_RECOMMEND, params);
	}

	protected Boolean getChecked(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getBool(PARAM_CHECKED, params);
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
	protected CmsGuestbookMng cmsGuestbookMng;

}
