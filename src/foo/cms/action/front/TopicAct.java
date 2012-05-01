package foo.cms.action.front;

import static foo.cms.Constants.TPLDIR_TOPIC;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsTopic;
import foo.cms.manager.main.ChannelMng;
import foo.cms.manager.main.CmsTopicMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;

@Controller
public class TopicAct {

	public static final String TOPIC_INDEX = "tpl.topicIndex";
	public static final String TOPIC_CHANNEL = "tpl.topicChannel";
	public static final String TOPIC_DEFAULT = "tpl.topicDefault";

	@RequestMapping(value = "/topic*.jspx", method = RequestMethod.GET)
	public String index(Integer channelId, Integer topicId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		// 全部？按站点？按栏目？要不同模板？
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		if (topicId != null) {
			CmsTopic topic = cmsTopicMng.findById(topicId);
			if (topic == null) {
				return FrontUtils.pageNotFound(request, response, model);
			}
			model.addAttribute("topic", topic);
			String tpl = topic.getTplContent();
			if (StringUtils.isBlank(tpl)) {
				tpl = FrontUtils.getTplPath(request, site.getSolutionPath(),
						TPLDIR_TOPIC, TOPIC_DEFAULT);
			}
			return tpl;
		} else if (channelId != null) {
			Channel channel = channelMng.findById(channelId);
			if (channel != null) {
				model.addAttribute("channel", channel);
			} else {
				return FrontUtils.pageNotFound(request, response, model);
			}
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TOPIC_CHANNEL);
		} else {
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TOPIC_INDEX);
		}
	}

	@RequestMapping(value = "/topic/{id}.jspx", method = RequestMethod.GET)
	public String topic(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		if (id == null) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		CmsTopic topic = cmsTopicMng.findById(id);
		if (topic == null) {
			return FrontUtils.pageNotFound(request, response, model);
		}
		model.addAttribute("topic", topic);
		String tpl = topic.getTplContent();
		if (StringUtils.isBlank(tpl)) {
			tpl = FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_TOPIC, TOPIC_DEFAULT);
		}
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		return tpl;
	}

	@Autowired
	private CmsTopicMng cmsTopicMng;
	@Autowired
	private ChannelMng channelMng;
}
