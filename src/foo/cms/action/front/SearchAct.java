package foo.cms.action.front;

import static foo.cms.Constants.TPLDIR_SPECIAL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import foo.cms.entity.main.CmsSite;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.common.web.RequestUtils;

@Controller
public class SearchAct {
	public static final String SEARCH_INPUT = "tpl.searchInput";
	public static final String SEARCH_RESULT = "tpl.searchResult";

	@RequestMapping(value = "/search*.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		String q = RequestUtils.getQueryParam(request, "q");
		String channelId = RequestUtils.getQueryParam(request, "channelId");
		if (StringUtils.isBlank(q) && StringUtils.isBlank(channelId)) {
			model.remove("q");
			model.remove("channelId");
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, SEARCH_INPUT);
		} else {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, SEARCH_RESULT);
		}
	}
}
