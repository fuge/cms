package foo.cms.action.admin.main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.CmsRole;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.manager.main.CmsRoleMng;
import foo.cms.web.WebErrors;

@Controller
public class CmsRoleAct {
	private static final Logger log = LoggerFactory.getLogger(CmsRoleAct.class);

	@RequestMapping("/role/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		List<CmsRole> list = manager.getList();
		model.addAttribute("list", list);
		return "role/list";
	}

	@RequestMapping("/role/v_add.do")
	public String add(ModelMap model) {
		return "role/add";
	}

	@RequestMapping("/role/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("cmsRole", manager.findById(id));
		return "role/edit";
	}

	@RequestMapping("/role/o_save.do")
	public String save(CmsRole bean, String[] perms,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean, splitPerms(perms));
		log.info("save CmsRole id={}", bean.getId());
		cmsLogMng.operating(request, "cmsRole.log.save", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequestMapping("/role/o_update.do")
	public String update(CmsRole bean, String[] perms,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean, splitPerms(perms));
		log.info("update CmsRole id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsRole.log.update", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return list(request, model);
	}

	@RequestMapping("/role/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsRole[] beans = manager.deleteByIds(ids);
		for (CmsRole bean : beans) {
			log.info("delete CmsRole id={}", bean.getId());
			cmsLogMng.operating(request, "cmsRole.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model);
	}

	private WebErrors validateSave(CmsRole bean, HttpServletRequest request) {
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

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsRole entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsRole.class, id)) {
			return true;
		}
		return false;
	}

	private Set<String> splitPerms(String[] perms) {
		Set<String> set = new HashSet<String>();
		if (perms != null) {
			for (String perm : perms) {
				for (String p : StringUtils.split(perm, ',')) {
					if (!StringUtils.isBlank(p)) {
						set.add(p);
					}
				}
			}
		}
		return set;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsRoleMng manager;
}