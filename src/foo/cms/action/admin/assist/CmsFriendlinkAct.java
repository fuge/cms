package foo.cms.action.admin.assist;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.assist.CmsFriendlink;
import foo.cms.entity.assist.CmsFriendlinkCtg;
import foo.cms.entity.main.CmsSite;
import foo.cms.manager.assist.CmsFriendlinkCtgMng;
import foo.cms.manager.assist.CmsFriendlinkMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;

@Controller
public class CmsFriendlinkAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsFriendlinkAct.class);

	@RequestMapping("/friendlink/v_list.do")
	public String list(Integer queryCtgId, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<CmsFriendlink> list = manager.getList(site.getId(), queryCtgId,
				null);
		List<CmsFriendlinkCtg> ctgList = cmsFriendlinkCtgMng.getList(site
				.getId());
		model.addAttribute("list", list);
		model.addAttribute("ctgList", ctgList);
		if (queryCtgId != null) {
			model.addAttribute("queryCtgId", queryCtgId);
		}
		return "friendlink/list";
	}

	@RequestMapping("/friendlink/v_add.do")
	public String add(ModelMap model, HttpServletRequest request) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateAdd(request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		List<CmsFriendlinkCtg> ctgList = cmsFriendlinkCtgMng.getList(site
				.getId());
		model.addAttribute("ctgList", ctgList);
		return "friendlink/add";
	}

	@RequestMapping("/friendlink/v_edit.do")
	public String edit(Integer id, Integer queryCtgId,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("cmsFriendlink", manager.findById(id));
		List<CmsFriendlinkCtg> ctgList = cmsFriendlinkCtgMng.getList(site
				.getId());
		model.addAttribute("ctgList", ctgList);
		if (queryCtgId != null) {
			model.addAttribute("queryCtgId", queryCtgId);
		}
		return "friendlink/edit";
	}

	@RequestMapping("/friendlink/o_save.do")
	public String save(CmsFriendlink bean, Integer ctgId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean, ctgId);
		log.info("save CmsFriendlink id={}", bean.getId());
		cmsLogMng.operating(request, "cmsFriendlink.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequestMapping("/friendlink/o_update.do")
	public String update(CmsFriendlink bean, Integer ctgId, Integer queryCtgId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean, ctgId);
		log.info("update CmsFriendlink id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsFriendlink.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(queryCtgId, request, model);
	}

	@RequestMapping("/friendlink/o_priority.do")
	public String priority(Integer[] wids, Integer[] priority,
			Integer queryCtgId, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validatePriority(wids, priority, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		manager.updatePriority(wids, priority);
		log.info("update CmsFriendlink priority.");
		return list(queryCtgId, request, model);
	}

	@RequestMapping("/friendlink/o_delete.do")
	public String delete(Integer[] ids, Integer queryCtgId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsFriendlink[] beans = manager.deleteByIds(ids);
		for (CmsFriendlink bean : beans) {
			log.info("delete CmsFriendlink id={}", bean.getId());
			cmsLogMng.operating(request, "cmsFriendlink.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(queryCtgId, request, model);
	}

	private WebErrors validateAdd(HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (cmsFriendlinkCtgMng.countBySiteId(site.getId()) <= 0) {
			errors.addErrorCode("cmsFriendlink.error.addFriendlinkCtgFirst");
			return errors;
		}
		return errors;
	}

	private WebErrors validateSave(CmsFriendlink bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validatePriority(Integer[] ids, Integer[] priorities,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
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
		CmsFriendlink entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsFriendlink.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsFriendlink.class, id);
			return true;
		}
		return false;
	}

	@Autowired
	private CmsFriendlinkCtgMng cmsFriendlinkCtgMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsFriendlinkMng manager;
}