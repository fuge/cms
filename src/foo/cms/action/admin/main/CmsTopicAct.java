package foo.cms.action.admin.main;

import static foo.cms.Constants.TPLDIR_TOPIC;
import static foo.cms.action.front.TopicAct.TOPIC_DEFAULT;
import static foo.cms.action.front.TopicAct.TOPIC_INDEX;
import static foo.common.page.SimplePage.cpn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsTopic;
import foo.cms.manager.main.ChannelMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.manager.main.CmsTopicMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.web.CookieUtils;
import foo.common.web.ResponseUtils;
import foo.common.web.springmvc.MessageResolver;
import foo.core.tpl.TplManager;
import foo.core.web.CoreUtils;

@Controller
public class CmsTopicAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsTopicAct.class);

	@RequestMapping("/topic/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return "topic/list";
	}

	@RequestMapping("/topic/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 模板
		List<String> tplList = getTplList(request, site, null);
		// 栏目
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				true);
		model.addAttribute("tplList", tplList);
		model.addAttribute("channelList", channelList);
		return "topic/add";
	}

	@RequestMapping("/topic/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		CmsTopic topic = manager.findById(id);
		// 模板
		List<String> tplList = getTplList(request, site, topic.getTplContent());
		// 栏目
		Integer siteId;
		Channel channel = topic.getChannel();
		if (channel != null) {
			siteId = channel.getSite().getId();
		} else {
			siteId = site.getId();
		}
		List<Channel> topList = channelMng.getTopList(siteId, true);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				true);
		model.addAttribute("tplList", tplList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("cmsTopic", topic);
		return "topic/edit";
	}

	@RequestMapping("/topic/o_save.do")
	public String save(CmsTopic bean, Integer channelId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		if (!StringUtils.isBlank(bean.getTplContent())) {
			bean.setTplContent(site.getTplPath() + bean.getTplContent());
		}
		bean = manager.save(bean, channelId);
		log.info("save CmsTopic id={}", bean.getId());
		cmsLogMng.operating(request, "cmsTopic.log.save", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequestMapping("/topic/o_update.do")
	public String update(CmsTopic bean, Integer channelId, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		if (!StringUtils.isBlank(bean.getTplContent())) {
			bean.setTplContent(site.getTplPath() + bean.getTplContent());
		}
		bean = manager.update(bean, channelId);
		log.info("update CmsTopic id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsTopic.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(pageNo, request, model);
	}

	@RequestMapping("/topic/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsTopic[] beans = manager.deleteByIds(ids);
		for (CmsTopic bean : beans) {
			log.info("delete CmsTopic id={}", bean.getId());
			cmsLogMng.operating(request, "cmsTopic.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(pageNo, request, model);
	}

	@RequestMapping("/topic/o_priority.do")
	public String priority(Integer[] wids, Integer[] priority, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validatePriority(wids, priority, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		manager.updatePriority(wids, priority);
		model.addAttribute("message", "global.success");
		return list(pageNo, request, model);
	}

	@RequestMapping("/topic/by_channel.do")
	public void topicsByChannel(Integer channelId, HttpServletResponse response)
			throws JSONException {
		JSONArray arr = new JSONArray();
		if (channelId != null) {
			List<CmsTopic> list = manager.getListByChannel(channelId);
			JSONObject o;
			for (CmsTopic t : list) {
				o = new JSONObject();
				o.put("id", t.getId());
				o.put("name", t.getName());
				arr.put(o);
			}
		}
		ResponseUtils.renderJson(response, arr.toString());
	}

	private List<String> getTplList(HttpServletRequest request, CmsSite site,
			String tpl) {
		List<String> tplList = tplManager.getNameListByPrefix(site
				.getSolutionPath()
				+ "/" + TPLDIR_TOPIC + "/");
		String tplIndex = MessageResolver.getMessage(request, TOPIC_INDEX);
		String tplDefault = MessageResolver.getMessage(request, TOPIC_DEFAULT);
		tplList = CoreUtils.tplTrim(tplList, site.getTplPath(), tpl, tplIndex,
				tplDefault);
		return tplList;
	}

	private WebErrors validateSave(CmsTopic bean, HttpServletRequest request) {
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
		errors.ifEmpty(ids, "ids");
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
		CmsTopic entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsTopic.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private TplManager tplManager;
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsTopicMng manager;
}