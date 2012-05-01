package foo.cms.action.directive;

import static foo.common.web.freemarker.DirectiveUtils.OUT_BEAN;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


import foo.cms.entity.assist.CmsAdvertising;
import foo.cms.manager.assist.CmsAdvertisingMng;
import foo.common.web.freemarker.DirectiveUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 广告对象标签
 * 
 * @author liufang
 * 
 */
public class CmsAdvertisingDirective implements TemplateDirectiveModel {
	/**
	 * 输入参数，广告ID。
	 */
	public static final String PARAM_ID = "id";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer id = DirectiveUtils.getInt(PARAM_ID, params);
		CmsAdvertising ad = null;
		if (id != null) {
			ad = cmsAdvertisingMng.findById(id);
		}
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_BEAN, DEFAULT_WRAPPER.wrap(ad));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}

	@Autowired
	private CmsAdvertisingMng cmsAdvertisingMng;
}
