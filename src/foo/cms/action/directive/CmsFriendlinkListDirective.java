package foo.cms.action.directive;

import static foo.common.web.freemarker.DirectiveUtils.OUT_LIST;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import foo.cms.entity.assist.CmsFriendlink;
import foo.cms.manager.assist.CmsFriendlinkMng;
import foo.cms.web.FrontUtils;
import foo.common.web.freemarker.DirectiveUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 友情链接类别列表标签
 * 
 * @author liufang
 * 
 */
public class CmsFriendlinkListDirective implements TemplateDirectiveModel {
	/**
	 * 输入参数，站点ID。
	 */
	public static final String PARAM_SITE_ID = "siteId";
	/**
	 * 输入参数，类别ID。
	 */
	public static final String PARAM_CTG_ID = "ctgId";
	/**
	 * 输入参数，是否显示。
	 */
	public static final String PARAM_ENABLED = "enabled";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer siteId = getSiteId(params);
		if (siteId == null) {
			siteId = FrontUtils.getSite(env).getId();
		}
		Integer ctgId = getCtgId(params);
		Boolean enabled = getEnabled(params);
		if (enabled == null) {
			enabled = true;
		}
		List<CmsFriendlink> list = cmsFriendlinkMng.getList(siteId, ctgId,
				enabled);

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(list));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	private Integer getSiteId(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(PARAM_SITE_ID, params);
	}

	private Integer getCtgId(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getInt(PARAM_CTG_ID, params);
	}

	private Boolean getEnabled(Map<String, TemplateModel> params)
			throws TemplateException {
		return DirectiveUtils.getBool(PARAM_ENABLED, params);
	}

	@Autowired
	private CmsFriendlinkMng cmsFriendlinkMng;
}
