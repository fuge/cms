package foo.cms.action.admin.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.main.CmsConfigMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.web.CmsUtils;
import foo.common.web.ResponseUtils;
import foo.core.entity.Ftp;
import foo.core.manager.FtpMng;
import foo.core.web.WebErrors;

@Controller
public class CmsSiteAct {
	private static final Logger log = LoggerFactory.getLogger(CmsSiteAct.class);

	@RequestMapping("/site/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		List<CmsSite> list = manager.getList();
		model.addAttribute("list", list);
		return "site/list";
	}

	@RequestMapping("/site/v_add.do")
	public String add(ModelMap model) {
		List<Ftp> ftpList = ftpMng.getList();
		model.addAttribute("ftpList", ftpList);
		return "site/add";
	}

	@RequestMapping("/site/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		List<Ftp> ftpList = ftpMng.getList();
		model.addAttribute("ftpList", ftpList);
		model.addAttribute("cmsSite", manager.findById(id));
		return "site/edit";
	}

	@RequestMapping("/site/o_save.do")
	public String save(CmsSite bean, Integer uploadFtpId,
			HttpServletRequest request, ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		WebErrors errors = validateSave(bean, uploadFtpId, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(site, user, bean, uploadFtpId);
		log.info("save CmsSite id={}", bean.getId());
		cmsLogMng.operating(request, "cmsSite.log.save", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequestMapping("/site/o_update.do")
	public String update(CmsSite bean, Integer uploadFtpId, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), uploadFtpId, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean, uploadFtpId);
		log.info("update CmsSite id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsSite.log.update", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return list(pageNo, request, model);
	}

	@RequestMapping("/site/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite[] beans = manager.deleteByIds(ids);
		for (CmsSite bean : beans) {
			log.info("delete CmsSite id={}", bean.getId());
			cmsLogMng.operating(request, "cmsSite.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(pageNo, request, model);
	}

	@RequestMapping("/site/v_checkDomain.do")
	public void checkUserJson(String domain, HttpServletResponse response) {
		String pass;
		if (StringUtils.isBlank(domain)) {
			pass = "false";
		} else {
			pass = manager.findByDomain(domain, false) == null ? "true"
					: "false";
		}
		ResponseUtils.renderJson(response, pass);
	}

	private WebErrors validateSave(CmsSite bean, Integer uploadFtpId,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldFtpExist(uploadFtpId, errors)) {
			return errors;
		}
		// 加上config信息
		bean.setConfig(configMng.get());
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, Integer uploadFtpId,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		if (vldFtpExist(uploadFtpId, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}

	private boolean vldFtpExist(Integer id, WebErrors errors) {
		if (id == null) {
			return false;
		}
		Ftp entity = ftpMng.findById(id);
		return errors.ifNotExist(entity, Ftp.class, id);
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsSite entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsSite.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsConfigMng configMng;
	@Autowired
	private FtpMng ftpMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsSiteMng manager;
}