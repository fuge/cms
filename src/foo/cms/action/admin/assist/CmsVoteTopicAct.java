package foo.cms.action.admin.assist;

import static foo.common.page.SimplePage.cpn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.assist.CmsVoteItem;
import foo.cms.entity.assist.CmsVoteTopic;
import foo.cms.entity.main.CmsSite;
import foo.cms.manager.assist.CmsVoteTopicMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.web.CookieUtils;

@Controller
public class CmsVoteTopicAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsVoteTopicAct.class);

	@RequestMapping("/vote_topic/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Pagination pagination = manager.getPage(site.getId(), cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pagination.getPageNo());
		return "vote_topic/list";
	}

	@RequestMapping("/vote_topic/v_add.do")
	public String add(ModelMap model) {
		return "vote_topic/add";
	}

	@RequestMapping("/vote_topic/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("cmsVoteTopic", manager.findById(id));
		model.addAttribute("pageNo", pageNo);
		return "vote_topic/edit";
	}

	@RequestMapping("/vote_topic/o_save.do")
	public String save(CmsVoteTopic bean, String[] itemTitle,
			Integer[] itemVoteCount, Integer[] itemPriority,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		List<CmsVoteItem> items = getItems(null, itemTitle, itemVoteCount,
				itemPriority);
		bean = manager.save(bean, items);
		log.info("save CmsVoteTopic id={}", bean.getId());
		cmsLogMng.operating(request, "cmsVoteTopic.log.save", "id="
				+ bean.getId() + ";title=" + bean.getTitle());
		return "redirect:v_list.do";
	}

	@RequestMapping("/vote_topic/o_update.do")
	public String update(CmsVoteTopic bean, Integer[] itemId,
			String[] itemTitle, Integer[] itemVoteCount,
			Integer[] itemPriority, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		List<CmsVoteItem> items = getItems(itemId, itemTitle, itemVoteCount,
				itemPriority);
		bean = manager.update(bean, items);
		log.info("update CmsVoteTopic id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsVoteTopic.log.update", "id="
				+ bean.getId() + ";title=" + bean.getTitle());
		return list(pageNo, request, model);
	}

	@RequestMapping("/vote_topic/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsVoteTopic[] beans = manager.deleteByIds(ids);
		for (CmsVoteTopic bean : beans) {
			log.info("delete CmsVoteTopic id={}", bean.getId());
			cmsLogMng.operating(request, "cmsVoteTopic.log.delete", "id="
					+ bean.getId() + ";title=" + bean.getTitle());
		}
		return list(pageNo, request, model);
	}

	private List<CmsVoteItem> getItems(Integer[] itemId, String[] itemTitle,
			Integer[] itemVoteCount, Integer[] itemPriority) {
		List<CmsVoteItem> items = new ArrayList<CmsVoteItem>();
		CmsVoteItem item;
		for (int i = 0, len = itemTitle.length; i < len; i++) {
			if (!StringUtils.isBlank(itemTitle[i])) {
				item = new CmsVoteItem();
				if (itemId != null && itemId[i] != null) {
					item.setId(itemId[i]);
				}
				item.setTitle(itemTitle[i]);
				item.setVoteCount(itemVoteCount[i]);
				item.setPriority(itemPriority[i]);
				items.add(item);
			}
		}
		return items;
	}

	private WebErrors validateSave(CmsVoteTopic bean, HttpServletRequest request) {
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
		CmsVoteTopic entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsVoteTopic.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsVoteTopic.class, id);
			return true;
		}
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsVoteTopicMng manager;
}