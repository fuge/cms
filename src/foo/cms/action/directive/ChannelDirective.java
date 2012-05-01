package foo.cms.action.directive;

import static foo.common.web.freemarker.DirectiveUtils.OUT_BEAN;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsSite;
import foo.cms.manager.main.ChannelMng;
import foo.cms.web.FrontUtils;
import foo.common.web.freemarker.DirectiveUtils;
import foo.common.web.freemarker.ParamsRequiredException;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 栏目对象标签
 * 
 * @author liufang
 * 
 */
public class ChannelDirective implements TemplateDirectiveModel {
	/**
	 * 输入参数，栏目ID。
	 */
	public static final String PARAM_ID = "id";
	/**
	 * 输入参数，栏目路径。
	 */
	public static final String PARAM_PATH = "path";
	/**
	 * 输入参数，站点ID。存在时，获取该站点栏目，不存在时获取当前站点栏目。
	 */
	public static final String PARAM_SITE_ID = "siteId";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		CmsSite site = FrontUtils.getSite(env);
		Integer id = DirectiveUtils.getInt(PARAM_ID, params);
		Channel channel;
		if (id != null) {
			channel = channelMng.findById(id);
		} else {
			String path = DirectiveUtils.getString(PARAM_PATH, params);
			if (StringUtils.isBlank(path)) {
				// 如果path不存在，那么id必须存在。
				throw new ParamsRequiredException(PARAM_ID);
			}
			Integer siteId = DirectiveUtils.getInt(PARAM_SITE_ID, params);
			if (siteId == null) {
				siteId = site.getId();
			}
			channel = channelMng.findByPathForTag(path, siteId);
		}

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_BEAN, DEFAULT_WRAPPER.wrap(channel));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@Autowired
	private ChannelMng channelMng;
}
