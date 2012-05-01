package foo.cms.action.admin.assist;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.assist.CmsKeyword;
import foo.cms.entity.main.CmsSite;
import foo.cms.manager.assist.CmsKeywordMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;

@Controller
public class CmsKeywordAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsKeywordAct.class);

	@RequestMapping("/keyword/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<CmsKeyword> list = manager.getListBySiteId(site.getId(), false,
				false);
		model.addAttribute("list", list);
		return "keyword/list";
	}

	@RequestMapping("/keyword/o_save.do")
	public String save(CmsKeyword bean, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean);
		model.addAttribute("message", "global.success");
		log.info("save CmsKeyword id={}", bean.getId());
		cmsLogMng.operating(request, "cmsKeyword.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(request, model);
	}

	@RequestMapping("/keyword/o_update.do")
	public String update(Integer[] id, String[] name, String[] url,
			Boolean[] disabled, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(id, name, url, disabled, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		if (id != null && id.length > 0) {
			manager.updateKeywords(id, name, url, disabled);
		}
		log.info("update CmsKeyword");
		model.addAttribute("message", "global.success");
		cmsLogMng.operating(request, "cmsKeyword.log.update", null);
		return list(request, model);
	}

	@RequestMapping("/keyword/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsKeyword[] beans = manager.deleteByIds(ids);
		for (CmsKeyword bean : beans) {
			log.info("delete CmsKeyword id={}", bean.getId());
			cmsLogMng.operating(request, "cmsKeyword.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model);
	}

	private WebErrors validateSave(CmsKeyword bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateUpdate(Integer[] ids, String[] names,
			String[] urls, Boolean[] disalbeds, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "id")) {
			return errors;
		}
		if (errors.ifEmpty(names, "name")) {
			return errors;
		}
		if (errors.ifEmpty(urls, "url")) {
			return errors;
		}
		if (ids.length != names.length || ids.length != urls.length) {
			errors.addErrorString("id, name, url length not equals");
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsKeyword entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsKeyword.class, id)) {
			return true;
		}
		// TODO 暂时不判断站点相关权限
		// if (!entity.getSite().getId().equals(siteId)) {
		// errors.notInSite(CmsKeyword.class, id);
		// return true;
		// }
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsKeywordMng manager;
}