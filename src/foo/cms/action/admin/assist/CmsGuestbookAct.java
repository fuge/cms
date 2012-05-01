package foo.cms.action.admin.assist;

import static foo.common.page.SimplePage.cpn;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.assist.CmsGuestbook;
import foo.cms.entity.assist.CmsGuestbookCtg;
import foo.cms.entity.assist.CmsGuestbookExt;
import foo.cms.entity.main.CmsSite;
import foo.cms.manager.assist.CmsGuestbookCtgMng;
import foo.cms.manager.assist.CmsGuestbookMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.web.CookieUtils;
import foo.common.web.RequestUtils;

@Controller
public class CmsGuestbookAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsGuestbookAct.class);

	@RequestMapping("/guestbook/v_list.do")
	public String list(Integer queryCtgId, Boolean queryRecommend,
			Boolean queryChecked, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Pagination pagination = manager.getPage(site.getId(), queryCtgId,null,
				queryRecommend, queryChecked, true, false, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pagination.getPageNo());
		return "guestbook/list";
	}

	@RequestMapping("/guestbook/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<CmsGuestbookCtg> ctgList = cmsGuestbookCtgMng
				.getList(site.getId());
		model.addAttribute("ctgList", ctgList);
		return "guestbook/add";
	}

	@RequestMapping("/guestbook/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsGuestbook cmsGuestbook = manager.findById(id);
		List<CmsGuestbookCtg> ctgList = cmsGuestbookCtgMng
				.getList(site.getId());

		model.addAttribute("cmsGuestbook", cmsGuestbook);
		model.addAttribute("ctgList", ctgList);
		model.addAttribute("pageNo", pageNo);
		return "guestbook/edit";
	}

	@RequestMapping("/guestbook/o_save.do")
	public String save(CmsGuestbook bean, CmsGuestbookExt ext, Integer ctgId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		String ip = RequestUtils.getIpAddr(request);
		bean = manager.save(bean, ext, ctgId, ip);
		log.info("save CmsGuestbook id={}", bean.getId());
		cmsLogMng.operating(request, "cmsGuestbook.log.save", "id="
				+ bean.getId() + ";title=" + bean.getTitle());
		return "redirect:v_list.do";
	}

	@RequestMapping("/guestbook/o_update.do")
	public String update(Integer queryCtgId, Boolean queryRecommend,
			Boolean queryChecked, String oldreply,CmsGuestbook bean, CmsGuestbookExt ext,
			Integer ctgId, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Date now=new Date();
		if(StringUtils.isNotBlank(ext.getReply())&&!oldreply.equals(ext.getReply())){
			bean.setReplayTime(now);
			if(bean.getAdmin()!=null){
				if(!bean.getAdmin().equals(CmsUtils.getUser(request))){
					bean.setAdmin(CmsUtils.getUser(request));
				}
			}else{
				bean.setAdmin(CmsUtils.getUser(request));
			}
		}
		bean = manager.update(bean, ext, ctgId);
		log.info("update CmsGuestbook id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsGuestbook.log.update", "id="
				+ bean.getId() + ";title=" + bean.getTitle());
		return list(queryCtgId, queryRecommend, queryChecked, pageNo, request,
				model);
	}

	@RequestMapping("/guestbook/o_delete.do")
	public String delete(Integer queryCtgId, Boolean queryRecommend,
			Boolean queryChecked, Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsGuestbook[] beans = manager.deleteByIds(ids);
		for (CmsGuestbook bean : beans) {
			log.info("delete CmsGuestbook id={}", bean.getId());
			cmsLogMng.operating(request, "cmsGuestbook.log.delete", "id="
					+ bean.getId() + ";title=" + bean.getTitle());
		}
		return list(queryCtgId, queryRecommend, queryChecked, pageNo, request,
				model);
	}

	private WebErrors validateSave(CmsGuestbook bean, HttpServletRequest request) {
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
		CmsGuestbook entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsGuestbook.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsGuestbook.class, id);
			return true;
		}
		return false;
	}

	@Autowired
	private CmsGuestbookCtgMng cmsGuestbookCtgMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsGuestbookMng manager;
}