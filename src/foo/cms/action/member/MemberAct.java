package foo.cms.action.member;

import static foo.cms.Constants.TPLDIR_MEMBER;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.CmsUserExt;
import foo.cms.entity.main.MemberConfig;
import foo.cms.manager.main.CmsUserExtMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.cms.web.WebErrors;
import foo.common.web.ResponseUtils;

/**
 * 会员中心Action
 * 
 * @author liufang
 * 
 */
@Controller
public class MemberAct {
	private static final Logger log = LoggerFactory.getLogger(MemberAct.class);

	public static final String MEMBER_CENTER = "tpl.memberCenter";
	public static final String MEMBER_PROFILE = "tpl.memberProfile";
	public static final String MEMBER_PORTRAIT = "tpl.memberPortrait";
	public static final String MEMBER_PASSWORD = "tpl.memberPassword";

	/**
	 * 会员中心页
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/index.jspx", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_CENTER);
	}

	/**
	 * 个人资料输入页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/profile.jspx", method = RequestMethod.GET)
	public String profileInput(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_PROFILE);
	}
	/**
	 * 更换头像
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/portrait.jspx", method = RequestMethod.GET)
	public String portrait(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_PORTRAIT);
	}

	/**
	 * 个人资料提交页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/member/profile.jspx", method = RequestMethod.POST)
	public String profileSubmit(CmsUserExt ext, String nextUrl,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		ext.setId(user.getId());
		cmsUserExtMng.update(ext, user);
		log.info("update CmsUserExt success. id={}", user.getId());
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	/**
	 * 密码修改输入页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/pwd.jspx", method = RequestMethod.GET)
	public String passwordInput(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, MEMBER_PASSWORD);
	}

	/**
	 * 密码修改提交页
	 * 
	 * @param origPwd
	 *            原始密码
	 * @param newPwd
	 *            新密码
	 * @param email
	 *            邮箱
	 * @param nextUrl
	 *            下一个页面地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/member/pwd.jspx", method = RequestMethod.POST)
	public String passwordSubmit(String origPwd, String newPwd, String email,
			String nextUrl, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		WebErrors errors = validatePasswordSubmit(user.getId(), origPwd,
				newPwd, email, request);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		cmsUserMng.updatePwdEmail(user.getId(), newPwd, email);
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	/**
	 * 验证密码是否正确
	 * 
	 * @param origPwd
	 *            原密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/member/checkPwd.jspx")
	public void checkPwd(String origPwd, HttpServletRequest request,
			HttpServletResponse response) {
		CmsUser user = CmsUtils.getUser(request);
		boolean pass = cmsUserMng.isPasswordValid(user.getId(), origPwd);
		ResponseUtils.renderJson(response, pass ? "true" : "false");
	}

	private WebErrors validatePasswordSubmit(Integer id, String origPwd,
			String newPwd, String email, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifBlank(origPwd, "origPwd", 100)) {
			return errors;
		}
		if (errors.ifMaxLength(newPwd, "newPwd", 100)) {
			return errors;
		}
		if (errors.ifMaxLength(email, "email", 100)) {
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
