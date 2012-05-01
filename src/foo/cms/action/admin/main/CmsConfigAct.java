package foo.cms.action.admin.main;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.CmsConfig;
import foo.cms.entity.main.MarkConfig;
import foo.cms.entity.main.MemberConfig;
import foo.cms.manager.main.CmsConfigMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.web.WebErrors;
import foo.core.entity.Config.ConfigEmailSender;
import foo.core.entity.Config.ConfigLogin;
import foo.core.entity.Config.ConfigMessageTemplate;
import foo.core.manager.ConfigMng;

@Controller
public class CmsConfigAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsConfigAct.class);

	@RequestMapping("/config/v_system_edit.do")
	public String systemEdit(HttpServletRequest request, ModelMap model) {
		model.addAttribute("cmsConfig", manager.get());
		return "config/system_edit";
	}

	@RequestMapping("/config/o_system_update.do")
	public String systemUpdate(CmsConfig bean, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSystemUpdate(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		model.addAttribute("message", "global.success");
		log.info("update systemConfig of CmsConfig.");
		cmsLogMng.operating(request, "cmsConfig.log.systemUpdate", null);
		return systemEdit(request, model);
	}

	@RequestMapping("/config/v_mark_edit.do")
	public String markEdit(HttpServletRequest request, ModelMap model) {
		model.addAttribute("markConfig", manager.get().getMarkConfig());
		return "config/mark_edit";
	}

	@RequestMapping("/config/o_mark_update.do")
	public String markUpdate(MarkConfig bean, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateMarkUpdate(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.updateMarkConfig(bean);
		model.addAttribute("message", "global.success");
		log.info("update markConfig of CmsConfig.");
		cmsLogMng.operating(request, "cmsConfig.log.markUpdate", null);
		return markEdit(request, model);
	}

	@RequestMapping("/config/v_member_edit.do")
	public String memberEdit(HttpServletRequest request, ModelMap model) {
		model.addAttribute("memberConfig", manager.get().getMemberConfig());
		return "config/member_edit";
	}

	@RequestMapping("/config/o_member_update.do")
	public String memberUpdate(MemberConfig bean, ConfigLogin configLogin,
			ConfigEmailSender emailSender, ConfigMessageTemplate msgTpl,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateMemberUpdate(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		manager.updateMemberConfig(bean);
		model.addAttribute("message", "global.success");
		log.info("update memberConfig of CmsConfig.");
		cmsLogMng.operating(request, "cmsConfig.log.memberUpdate", null);
		return memberEdit(request, model);
	}

	@RequestMapping("/config/v_login_edit.do")
	public String loginEdit(HttpServletRequest request, ModelMap model) {
		model.addAttribute("configLogin", configMng.getConfigLogin());
		model.addAttribute("emailSender", configMng.getEmailSender());
		model.addAttribute("forgotPasswordTemplate", configMng.getForgotPasswordMessageTemplate());
		model.addAttribute("registerTemplate", configMng.getRegisterMessageTemplate());
		return "config/login_edit";
	}

	@RequestMapping("/config/o_login_update.do")
	public String loginUpdate(ConfigLogin configLogin,
			ConfigEmailSender emailSender, ConfigMessageTemplate msgTpl,
			HttpServletRequest request, ModelMap model) {
		configMng.updateOrSave(configLogin.getAttr());
		configMng.updateOrSave(emailSender.getAttr());
		configMng.updateOrSave(msgTpl.getAttr());
		model.addAttribute("message", "global.success");
		log.info("update loginCoinfig of Config.");
		cmsLogMng.operating(request, "cmsConfig.log.loginUpdate", null);
		return loginEdit(request, model);
	}

	private WebErrors validateSystemUpdate(CmsConfig bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateMarkUpdate(MarkConfig bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateMemberUpdate(MemberConfig bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	@Autowired
	private ConfigMng configMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsConfigMng manager;
}