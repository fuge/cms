package foo.cms.action.front;

import static foo.cms.Constants.TPLDIR_CSI;
import static foo.cms.Constants.TPLDIR_SPECIAL;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

import foo.cms.entity.assist.CmsComment;
import foo.cms.entity.main.ChannelExt;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.Content;
import foo.cms.manager.assist.CmsCommentMng;
import foo.cms.manager.main.ContentMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.common.web.RequestUtils;
import foo.common.web.ResponseUtils;
import foo.common.web.session.SessionProvider;

@Controller
public class CommentAct {
	private static final Logger log = LoggerFactory.getLogger(CommentAct.class);

	public static final String COMMENT_PAGE = "tpl.commentPage";
	public static final String COMMENT_LIST = "tpl.commentList";

	@RequestMapping(value = "/comment*.jspx", method = RequestMethod.GET)
	public String page(Integer contentId, Integer pageNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Content content = contentMng.findById(contentId);
		if (content == null) {
			return FrontUtils.showMessage(request, model,
					"comment.contentNotFound");
		}
		if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
			return FrontUtils.showMessage(request, model, "comment.closed");
		}
		// 将request中所有参数保存至model中。
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		model.addAttribute("content", content);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, COMMENT_PAGE);
	}

	@RequestMapping(value = "/comment_list.jspx")
	public String list(Integer siteId, Integer contentId, Integer greatTo,
			Integer recommend, Integer checked, Integer orderBy, Integer count,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		if (count == null || count <= 0 || count > 200) {
			count = 200;
		}
		boolean desc, rec;
		if (orderBy == null || orderBy == 0) {
			desc = true;
		} else {
			desc = false;
		}
		if (recommend == null || recommend == 0) {
			rec = false;
		} else {
			rec = true;
		}
		Boolean chk;
		if (checked != null) {
			chk = checked != 0;
		} else {
			chk = null;
		}
		List<CmsComment> list = cmsCommentMng.getListForTag(siteId, contentId,
				greatTo, chk, rec, desc, count);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		model.addAttribute("list", list);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, COMMENT_LIST);
	}

	@RequestMapping(value = "/comment.jspx", method = RequestMethod.POST)
	public void submit(Integer contentId, String text, String captcha,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws JSONException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		JSONObject json = new JSONObject();
		if (contentId == null) {
			json.put("success", false);
			json.put("status", 100);
			ResponseUtils.renderJson(response, json.toString());
			return;
		}
		if (StringUtils.isBlank(text)) {
			json.put("success", false);
			json.put("status", 101);
			ResponseUtils.renderJson(response, json.toString());
			return;
		}
		if (user == null || user.getGroup().getNeedCaptcha()) {
			// 验证码错误
			try {
				if (!imageCaptchaService.validateResponseForID(session
						.getSessionId(request, response), captcha)) {
					json.put("success", false);
					json.put("status", 1);
					ResponseUtils.renderJson(response, json.toString());
					return;
				}
			} catch (CaptchaServiceException e) {
				json.put("success", false);
				json.put("status", 1);
				log.warn("", e);
				ResponseUtils.renderJson(response, json.toString());
				return;
			}
		}
		Content content = contentMng.findById(contentId);
		if (content == null) {
			// 内容不存在
			json.put("success", false);
			json.put("status", 2);
		} else if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_OFF) {
			// 评论关闭
			json.put("success", false);
			json.put("status", 3);
		} else if (content.getChannel().getCommentControl() == ChannelExt.COMMENT_LOGIN
				&& user == null) {
			// 需要登录才能评论
			json.put("success", false);
			json.put("status", 4);
		} else {
			boolean checked = false;
			Integer userId = null;
			if (user != null) {
				checked = !user.getGroup().getNeedCheck();
				userId = user.getId();
			}
			cmsCommentMng.comment(text, RequestUtils.getIpAddr(request),
					contentId, site.getId(), userId, checked, false);
			json.put("success", true);
			json.put("status", 0);
		}
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequestMapping(value = "/comment_up.jspx")
	public void up(Integer contentId, HttpServletRequest request,
			HttpServletResponse response) {
		if (exist(contentId)) {
			cmsCommentMng.ups(contentId);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}

	@RequestMapping(value = "/comment_down.jspx")
	public void down(Integer contentId, HttpServletRequest request,
			HttpServletResponse response) {
		if (exist(contentId)) {
			cmsCommentMng.downs(contentId);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}

	private boolean exist(Integer id) {
		if (id == null) {
			return false;
		}
		Content content = contentMng.findById(id);
		return content != null;
	}

	@Autowired
	private CmsCommentMng cmsCommentMng;
	@Autowired
	private ContentMng contentMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
}
