package foo.cms.action.admin.assist;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.assist.CmsFriendlinkCtg;
import foo.cms.entity.main.CmsSite;
import foo.cms.manager.assist.CmsFriendlinkCtgMng;
import foo.cms.manager.assist.CmsFriendlinkMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;

@Controller
public class CmsFriendlinkCtgAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsFriendlinkCtgAct.class);

	@RequestMapping("/friendlink_ctg/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<CmsFriendlinkCtg> list = manager.getList(site.getId());
		model.addAttribute("list", list);
		return "friendlink_ctg/list";
	}

	@RequestMapping("/friendlink_ctg/o_save.do")
	public String save(CmsFriendlinkCtg bean, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean);
		log.info("save CmsFriendlinkCtg id={}", bean.getId());
		cmsLogMng.operating(request, "cmsFriendlinkCtg.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequestMapping("/friendlink_ctg/o_update.do")
	public String update(Integer[] wids, String[] name, Integer[] priority,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(wids, name, priority, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		manager.updateFriendlinkCtgs(wids, name, priority);
		log.info("update CmsFriendlinkCtg.");
		cmsLogMng.operating(request, "cmsFriendlinkCtg.log.update", null);
		return "redirect:v_list.do";
	}

	@RequestMapping("/friendlink_ctg/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsFriendlinkCtg[] beans = manager.deleteByIds(ids);
		for (CmsFriendlinkCtg bean : beans) {
			log.info("delete CmsFriendlinkCtg id={}", bean.getId());
			cmsLogMng.operating(request, "cmsFriendlinkCtg.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return "redirect:v_list.do";
	}

	private WebErrors validateSave(CmsFriendlinkCtg bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		return errors;
	}

	private WebErrors validateUpdate(Integer[] ids, String[] names,
			Integer[] priorities, HttpServletRequest request) {
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
			if (cmsFriendlinkMng.countByCtgId(id) > 0) {
				String code = "cmsFriendlinkCtg.error.delFriendlinkFirst";
				errors.addErrorCode(code);
				return errors;
			}
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsFriendlinkCtg entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsFriendlinkCtg.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsFriendlinkCtg.class, id);
			return true;
		}
		return false;
	}

	@Autowired
	private CmsFriendlinkMng cmsFriendlinkMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsFriendlinkCtgMng manager;
}