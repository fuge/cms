package foo.cms.action.admin.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.CmsGroup;
import foo.cms.manager.main.CmsGroupMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.web.WebErrors;

@Controller
public class CmsGroupAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsGroupAct.class);

	@RequestMapping("/group/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		List<CmsGroup> list = manager.getList();
		model.addAttribute("list", list);
		return "group/list";
	}

	@RequestMapping("/group/v_add.do")
	public String add(ModelMap model) {
		return "group/add";
	}

	@RequestMapping("/group/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("cmsGroup", manager.findById(id));
		return "group/edit";
	}

	@RequestMapping("/group/o_save.do")
	public String save(CmsGroup bean, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean);
		log.info("save CmsGroup id={}", bean.getId());
		cmsLogMng.operating(request, "cmsGroup.log.save", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequestMapping("/group/o_update.do")
	public String update(CmsGroup bean, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update CmsGroup id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsGroup.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(request, model);
	}

	@RequestMapping("/group/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsGroup[] beans = manager.deleteByIds(ids);
		for (CmsGroup bean : beans) {
			log.info("delete CmsGroup id={}", bean.getId());
			cmsLogMng.operating(request, "cmsGroup.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model);
	}

	@RequestMapping("/group/o_priority.do")
	public String priority(Integer[] wids, Integer[] priority,
			Integer regDefId, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validatePriority(wids, priority, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		manager.updatePriority(wids, priority);
		manager.updateRegDef(regDefId);
		model.addAttribute("message", "global.success");
		return list(request, model);
	}

	private WebErrors validateSave(CmsGroup bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}

	private WebErrors validatePriority(Integer[] wids, Integer[] priority,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifEmpty(wids, "wids")) {
			return errors;
		}
		if (errors.ifEmpty(priority, "priority")) {
			return errors;
		}
		if (wids.length != priority.length) {
			errors.addErrorString("wids length not equals priority length");
			return errors;
		}
		for (int i = 0, len = wids.length; i < len; i++) {
			if (vldExist(wids[i], errors)) {
				return errors;
			}
			if (priority[i] == null) {
				priority[i] = 0;
			}
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsGroup entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsGroup.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsGroupMng manager;
}