package foo.cms.action.admin.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.CmsSite;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;
import foo.core.entity.Ftp;
import foo.core.manager.FtpMng;

@Controller
public class CmsSiteConfigAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsSiteConfigAct.class);

	@RequestMapping("/site_config/v_base_edit.do")
	public String baseEdit(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<Ftp> ftpList = ftpMng.getList();
		model.addAttribute("ftpList", ftpList);
		model.addAttribute("cmsSite", site);
		return "site_config/base_edit";
	}

	@RequestMapping("/site_config/o_base_update.do")
	public String baseUpdate(CmsSite bean, Integer uploadFtpId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateBaseUpdate(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		bean.setId(site.getId());
		bean = manager.update(bean, uploadFtpId);
		model.addAttribute("message", "global.success");
		log.info("update CmsSite success. id={}", site.getId());
		cmsLogMng.operating(request, "cmsSiteConfig.log.updateBase", null);
		return baseEdit(request, model);
	}

	private WebErrors validateBaseUpdate(CmsSite bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	@Autowired
	private FtpMng ftpMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsSiteMng manager;
}