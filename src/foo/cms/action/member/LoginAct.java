package foo.cms.action.member;

import static foo.cms.Constants.TPLDIR_CSI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.CmsSite;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.common.web.RequestUtils;

@Controller
public class LoginAct {
	public static final String LOGIN_CSI = "tpl.loginCsi";

	/**
	 * 客户端包含
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login_csi.jspx")
	public String csi(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, LOGIN_CSI);
	}

}
