package foo.cms.web;

import static foo.cms.web.AdminContextInterceptor.PERMISSION_MODEL;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import foo.common.web.freemarker.DirectiveUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;

/**
 * 后台管理员权限许可
 * 
 * @author liufang
 * 
 */
public class PermistionDirective implements TemplateDirectiveModel {
	/**
	 * 此url必须和perm中url一致。
	 */
	public static final String PARAM_URL = "url";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// 此处的权限判断有可能和拦截器的不一致，有没有关系？大部分应该没有关系，因为不需要判断权限的可以不加这个标签。
		// 光一个perms可能还不够，至少还有一个是否只浏览的问题。这个是否可以不管？可以！
		// 是否控制权限这个总是要的吧？perms为null代表无需控制权限。
		String url = DirectiveUtils.getString(PARAM_URL, params);
		boolean pass = false;
		if (StringUtils.isBlank(url)) {
			// url为空，则认为有权限。
			pass = true;
		} else {
			TemplateSequenceModel perms = getPerms(env);
			if (perms == null) {
				// perms为null，则代表不需要判断权限。
				pass = true;
			} else {
				String perm;
				for (int i = 0, len = perms.size(); i < len; i++) {
					perm = ((TemplateScalarModel) perms.get(i)).getAsString();
					if (url.startsWith(perm)) {
						pass = true;
						break;
					}
				}
			}
		}
		if (pass) {
			body.render(env.getOut());
		}
	}

	private TemplateSequenceModel getPerms(Environment env)
			throws TemplateModelException {
		TemplateModel model = env.getDataModel().get(PERMISSION_MODEL);
		if (model == null) {
			return null;
		}
		if (model instanceof TemplateSequenceModel) {
			return (TemplateSequenceModel) model;
		} else {
			throw new TemplateModelException(
					"'perms' in data model not a TemplateSequenceModel");
		}

	}
}
