package foo.cms.action.directive;

import static foo.cms.Constants.TPL_STYLE_LIST;
import static foo.cms.Constants.TPL_SUFFIX;
import static foo.cms.web.FrontUtils.PARAM_STYLE_LIST;
import static foo.common.web.Constants.UTF8;
import static foo.common.web.freemarker.DirectiveUtils.OUT_LIST;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import foo.cms.action.directive.abs.AbstractContentDirective;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.Content;
import foo.cms.web.FrontUtils;
import foo.common.web.freemarker.DirectiveUtils;
import foo.common.web.freemarker.ParamsRequiredException;
import foo.common.web.freemarker.DirectiveUtils.InvokeType;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 内容列表标签
 * 
 * @author liufang
 * 
 */
public class ContentListDirective extends AbstractContentDirective {
	/**
	 * 模板名称
	 */
	public static final String TPL_NAME = "content_list";

	/**
	 * 输入参数，文章ID。允许多个文章ID，用","分开。排斥其他所有筛选参数。
	 */
	public static final String PARAM_IDS = "ids";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		CmsSite site = FrontUtils.getSite(env);
		List<Content> list = getList(params, env);

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		InvokeType type = DirectiveUtils.getInvokeType(params);
		String listStyle = DirectiveUtils.getString(PARAM_STYLE_LIST, params);
		if (InvokeType.sysDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			env.include(TPL_STYLE_LIST + listStyle + TPL_SUFFIX, UTF8, true);
		} else if (InvokeType.userDefined == type) {
			if (StringUtils.isBlank(listStyle)) {
				throw new ParamsRequiredException(PARAM_STYLE_LIST);
			}
			FrontUtils.includeTpl(TPL_STYLE_LIST, site, env);
		} else if (InvokeType.custom == type) {
			FrontUtils.includeTpl(TPL_NAME, site, params, env);
		} else if (InvokeType.body == type) {
			body.render(env.getOut());
		} else {
			throw new RuntimeException("invoke type not handled: " + type);
		}
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@SuppressWarnings("unchecked")
	protected List<Content> getList(Map<String, TemplateModel> params,
			Environment env) throws TemplateException {
		Integer[] ids = DirectiveUtils.getIntArray(PARAM_IDS, params);
		if (ids != null) {
			return contentMng.getListByIdsForTag(ids, getOrderBy(params));
		} else {
			return (List<Content>) super.getData(params, env);
		}
	}

	@Override
	protected boolean isPage() {
		return false;
	}
}
