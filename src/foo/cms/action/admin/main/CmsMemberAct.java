package foo.cms.action.admin.main;

import static foo.common.page.SimplePage.cpn;

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

import foo.cms.entity.main.CmsGroup;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.CmsUserExt;
import foo.cms.manager.main.CmsGroupMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.web.CookieUtils;
import foo.common.web.RequestUtils;
import foo.common.web.ResponseUtils;

@Controller
public class CmsMemberAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsMemberAct.class);

	@RequestMapping("/member/v_list.do")
	public String list(String queryUsername, String queryEmail,
			Integer queryGroupId, Boolean queryDisabled, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(queryUsername, queryEmail,
				null, queryGroupId, queryDisabled, false, null, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);

		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryGroupId", queryGroupId);
		model.addAttribute("queryDisabled", queryDisabled);

		return "member/list";
	}

	@RequestMapping("/member/v_add.do")
	public String add(ModelMap model) {
		List<CmsGroup> groupList = cmsGroupMng.getList();
		model.addAttribute("groupList", groupList);
		return "member/add";
	}

	@RequestMapping("/member/v_edit.do")
	public String edit(Integer id, Integer queryGroupId, Boolean queryDisabled,
			HttpServletRequest request, ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		List<CmsGroup> groupList = cmsGroupMng.getList();
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryGroupId", queryGroupId);
		model.addAttribute("queryDisabled", queryDisabled);
		model.addAttribute("groupList", groupList);
		model.addAttribute("cmsMember", manager.findById(id));
		return "member/edit";
	}

	@RequestMapping("/member/o_save.do")
	public String save(CmsUser bean, CmsUserExt ext, String username,
			String email, String password, Integer groupId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		String ip = RequestUtils.getIpAddr(request);
		bean = manager.registerMember(username, email, password, ip, groupId,
				ext);
		log.info("save CmsMember id={}", bean.getId());
		cmsLogMng.operating(request, "cmsMember.log.save", "id=" + bean.getId()
				+ ";username=" + bean.getUsername());
		return "redirect:v_list.do";
	}

	@RequestMapping("/member/o_update.do")
	public String update(Integer id, String email, String password,
			Boolean disabled, CmsUserExt ext, Integer groupId,
			String queryUsername, String queryEmail, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser bean = manager.updateMember(id, email, password, disabled, ext,
				groupId);
		log.info("update CmsMember id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsMember.log.update", "id="
				+ bean.getId() + ";username=" + bean.getUsername());

		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo, request, model);
	}

	@RequestMapping("/member/o_delete.do")
	public String delete(Integer[] ids, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser[] beans = manager.deleteByIds(ids);
		for (CmsUser bean : beans) {
			log.info("delete CmsMember id={}", bean.getId());
			cmsLogMng.operating(request, "cmsMember.log.delete", "id="
					+ bean.getId() + ";username=" + bean.getUsername());
		}
		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo, request, model);
	}

	@RequestMapping(value = "/member/v_check_username.do")
	public void checkUsername(String username, HttpServletResponse response) {
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = manager.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}

	private WebErrors validateSave(CmsUser bean, HttpServletRequest request) {
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
		// TODO 验证是否为管理员，管理员不允许修改。
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

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsUser entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsUser.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsGroupMng cmsGroupMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsUserMng manager;
}