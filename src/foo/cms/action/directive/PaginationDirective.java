package foo.cms.action.directive;

import static foo.cms.Constants.TPLDIR_STYLE_PAGE;
import static foo.cms.Constants.TPL_STYLE_PAGE_CONTENT;
import static foo.cms.Constants.TPL_SUFFIX;
import static foo.cms.web.FrontUtils.PARAM_SYS_PAGE;
import static foo.cms.web.FrontUtils.PARAM_USER_PAGE;
import static foo.cms.web.FrontUtils.getTplPath;
import static foo.common.web.Constants.UTF8;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import foo.cms.entity.main.CmsSite;
import foo.cms.web.FrontUtils;
import foo.common.web.freemarker.DirectiveUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 翻页包含标签
 * 
 * @author liufang
 * 
 */
public class PaginationDirective implements TemplateDirectiveModel {
	/**
	 * 是否为内容分页。1：内容分页；0：栏目分页。默认栏目分页。
	 */
	public static final String PARAM_CONTENT = "content";

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		CmsSite site = FrontUtils.getSite(env);
		String content = DirectiveUtils.getString(PARAM_CONTENT, params);
		if ("1".equals(content)) {
			String sysPage = DirectiveUtils.getString(PARAM_SYS_PAGE, params);
			String userPage = DirectiveUtils.getString(PARAM_USER_PAGE, params);
			if (!StringUtils.isBlank(sysPage)) {
				String tpl = TPL_STYLE_PAGE_CONTENT + sysPage + TPL_SUFFIX;
				env.include(tpl, UTF8, true);
			} else if (!StringUtils.isBlank(userPage)) {
				String tpl = getTplPath(site.getSolutionPath(),
						TPLDIR_STYLE_PAGE, userPage);
				env.include(tpl, UTF8, true);
			} else {
				// 没有包含分页
			}
		} else {
			FrontUtils.includePagination(site, params, env);
		}
	}
}
