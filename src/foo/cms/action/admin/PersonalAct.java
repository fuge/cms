package foo.cms.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.CmsUserExt;
import foo.cms.manager.main.CmsUserExtMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;
import foo.common.web.ResponseUtils;

@Controller
public class PersonalAct {
	@RequestMapping("/personal/v_profile.do")
	public String profileEdit(HttpServletRequest request, ModelMap model) {
		CmsUser user = CmsUtils.getUser(request);
		model.addAttribute("user", user);
		return "personal/profile";
	}

	@RequestMapping("/personal/o_profile.do")
	public String profileUpdate(String origPwd, String newPwd, String email,
			String realname, HttpServletRequest request, ModelMap model) {
		CmsUser user = CmsUtils.getUser(request);
		WebErrors errors = validatePasswordSubmit(user.getId(), origPwd,
				newPwd, email, realname, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUserExt ext = user.getUserExt();
		if (ext == null) {
			ext = new CmsUserExt();
		}
		ext.setRealname(realname);
		cmsUserExtMng.update(ext, user);
		cmsUserMng.updatePwdEmail(user.getId(), newPwd, email);
		model.addAttribute("message", "global.success");
		return profileEdit(request, model);
	}

	/**
	 * 验证密码是否正确
	 * 
	 * @param origPwd
	 *            原密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/personal/v_checkPwd.do")
	public void checkPwd(String origPwd, HttpServletRequest request,
			HttpServletResponse response) {
		CmsUser user = CmsUtils.getUser(request);
		boolean pass = cmsUserMng.isPasswordValid(user.getId(), origPwd);
		ResponseUtils.renderJson(response, pass ? "true" : "false");
	}

	private WebErrors validatePasswordSubmit(Integer id, String origPwd,
			String newPwd, String email, String realname,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifBlank(origPwd, "origPwd", 32)) {
			return errors;
		}
		if (errors.ifMaxLength(newPwd, "newPwd", 32)) {
			return errors;
		}
		if (errors.ifMaxLength(email, "email", 100)) {
			return errors;
		}
		if (errors.ifMaxLength(realname, "realname", 100)) {
			return errors;
		}
		if (!cmsUserMng.isPasswordValid(id, origPwd)) {
			errors.addErrorCode("member.origPwdInvalid");
			return errors;
		}
		return errors;
	}

	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsUserExtMng cmsUserExtMng;
}
