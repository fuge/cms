package foo.cms.action.admin.assist;

import static foo.common.page.SimplePage.cpn;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.assist.CmsComment;
import foo.cms.entity.assist.CmsCommentExt;
import foo.cms.entity.main.CmsSite;
import foo.cms.manager.assist.CmsCommentMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.web.CookieUtils;

@Controller
public class CmsCommentAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsCommentAct.class);

	@RequestMapping("/comment/v_list.do")
	public String list(Integer queryContentId, Boolean queryChecked,
			Boolean queryRecommend, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		if (queryRecommend == null) {
			queryRecommend = false;
		}
		CmsSite site = CmsUtils.getSite(request);
		Pagination pagination = manager.getPage(site.getId(), queryContentId,
				null, queryChecked, queryRecommend, true, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return "comment/list";
	}

	@RequestMapping("/comment/v_add.do")
	public String add(ModelMap model) {
		return "comment/add";
	}

	@RequestMapping("/comment/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("cmsComment", manager.findById(id));
		return "comment/edit";
	}

	@RequestMapping("/comment/o_update.do")
	public String update(Integer queryContentId, Boolean queryChecked,
			Boolean queryRecommend,String reply, CmsComment bean, CmsCommentExt ext,
			Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		//若回复内容不为空而且回复更新，则设置回复时间，已最新回复时间为准
		if(StringUtils.isNotBlank(ext.getReply())&&!reply.equals(ext.getReply())){
			bean.setReplayTime(new Date());
		}
		bean = manager.update(bean, ext);
		log.info("update CmsComment id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsComment.log.update", "id="
				+ bean.getId());
		return list(queryContentId, queryChecked, queryRecommend, pageNo,
				request, model);
	}

	@RequestMapping("/comment/o_delete.do")
	public String delete(Integer queryContentId, Boolean queryChecked,
			Boolean queryRecommend, Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsComment[] beans = manager.deleteByIds(ids);
		for (CmsComment bean : beans) {
			log.info("delete CmsComment id={}", bean.getId());
			cmsLogMng.operating(request, "cmsComment.log.delete", "id="
					+ bean.getId());
		}
		return list(queryContentId, queryChecked, queryRecommend, pageNo,
				request, model);
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
		CmsComment entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsComment.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsComment.class, id);
			return true;
		}
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsCommentMng manager;
}