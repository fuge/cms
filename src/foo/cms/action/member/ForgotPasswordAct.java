package foo.cms.action.member;

import static foo.cms.Constants.TPLDIR_MEMBER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

import foo.cms.entity.main.CmsSite;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.cms.web.WebErrors;
import foo.common.email.EmailSender;
import foo.common.email.MessageTemplate;
import foo.common.web.session.SessionProvider;
import foo.core.entity.UnifiedUser;
import foo.core.manager.ConfigMng;
import foo.core.manager.UnifiedUserMng;

/**
 * 找回密码Action
 * 
 * 用户忘记密码后点击找回密码链接，输入用户名、邮箱和验证码<li>
 * 如果信息正确，返回一个提示页面，并发送一封找回密码的邮件，邮件包含一个链接及新密码，点击链接新密码即生效<li>
 * 如果输入错误或服务器邮箱等信息设置不完整，则给出提示信息<li>
 * 
 * @author liufang
 * 
 */
@Controller
public class ForgotPasswordAct {
	private static Logger log = LoggerFactory
			.getLogger(ForgotPasswordAct.class);

	public static final String FORGOT_PASSWORD_INPUT = "tpl.forgotPasswordInput";
	public static final String FORGOT_PASSWORD_RESULT = "tpl.forgotPasswordResult";
	public static final String PASSWORD_RESET = "tpl.passwordReset";

	/**
	 * 找回密码输入页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/forgot_password.jspx", method = RequestMethod.GET)
	public String forgotPasswordInput(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, FORGOT_PASSWORD_INPUT);
	}

	/**
	 * 找回密码提交页
	 * 
	 * @param username
	 * @param email
	 * @param captcha
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/member/forgot_password.jspx", method = RequestMethod.POST)
	public String forgotPasswordSubmit(String username, String email,
			String captcha, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateForgotPasswordSubmit(username, email,
				captcha, request, response);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		UnifiedUser user = unifiedUserMng.getByUsername(username);
		EmailSender sender = configMng.getEmailSender();
		MessageTemplate msgTpl = configMng.getForgotPasswordMessageTemplate();
		model.addAttribute("user", user);
		FrontUtils.frontData(request, model, site);
		if (user == null) {
			// 用户名不存在
			model.addAttribute("status", 1);
		} else if (StringUtils.isBlank(user.getEmail())) {
			// 用户没有设置邮箱
			model.addAttribute("status", 2);
		} else if (!user.getEmail().equals(email)) {
			// 邮箱输入错误
			model.addAttribute("status", 3);
		} else if (sender == null) {
			// 邮件服务器没有设置好
			model.addAttribute("status", 4);
		} else if (msgTpl == null) {
			// 邮件模板没有设置好
			model.addAttribute("status", 5);
		} else {
			try {
				unifiedUserMng.passwordForgotten(user.getId(), sender, msgTpl);
				model.addAttribute("status", 0);
			} catch (Exception e) {
				// 发送邮件异常
				model.addAttribute("status", 100);
				model.addAttribute("message", e.getMessage());
				log.error("send email exception.", e);
			}
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, FORGOT_PASSWORD_RESULT);
	}

	@RequestMapping(value = "/member/password_reset.jspx", method = RequestMethod.GET)
	public String passwordReset(Integer uid, String key,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validatePasswordReset(uid, key, request);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		UnifiedUser user = unifiedUserMng.findById(uid);
		if (user == null) {
			// 用户不存在
			model.addAttribute("status", 1);
		} else if (StringUtils.isBlank(user.getResetKey())) {
			// resetKey不存在
			model.addAttribute("status", 2);
		} else if (!user.getResetKey().equals(key)) {
			// 重置key错误
			model.addAttribute("status", 3);
		} else {
			unifiedUserMng.resetPassword(uid);
			model.addAttribute("status", 0);
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, PASSWORD_RESET);
	}

	private WebErrors validateForgotPasswordSubmit(String username,
			String email, String captcha, HttpServletRequest request,
			HttpServletResponse response) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifBlank(username, "username", 100)) {
			return errors;
		}
		if (errors.ifBlank(email, "email", 100)) {
			return errors;
		}
		if (errors.ifBlank(captcha, "captcha", 20)) {
			return errors;
		}
		try {
			if (!imageCaptchaService.validateResponseForID(session
					.getSessionId(request, response), captcha)) {
				errors.addErrorCode("error.invalidCaptcha");
				return errors;
			}
		} catch (CaptchaServiceException e) {
			errors.addErrorCode("error.exceptionCaptcha");
			log.warn("", e);
			return errors;
		}
		return errors;
	}

	private WebErrors validatePasswordReset(Integer uid, String key,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifNull(uid, "uid")) {
			return errors;
		}
		if (errors.ifBlank(key, "key", 50)) {
			return errors;
		}
		return errors;
	}

	@Autowired
	private UnifiedUserMng unifiedUserMng;
	@Autowired
	private ConfigMng configMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
}
