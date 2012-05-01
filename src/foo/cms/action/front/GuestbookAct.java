package foo.cms.action.front;

import static foo.cms.Constants.TPLDIR_SPECIAL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

import foo.cms.entity.assist.CmsGuestbook;
import foo.cms.entity.assist.CmsGuestbookCtg;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.assist.CmsGuestbookCtgMng;
import foo.cms.manager.assist.CmsGuestbookMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.common.web.RequestUtils;
import foo.common.web.ResponseUtils;
import foo.common.web.session.SessionProvider;

@Controller
public class GuestbookAct {
	private static final Logger log = LoggerFactory
			.getLogger(GuestbookAct.class);

	public static final String GUESTBOOK_INDEX = "tpl.guestbookIndex";
	public static final String GUESTBOOK_CTG = "tpl.guestbookCtg";
	public static final String GUESTBOOK_DETAIL = "tpl.guestbookDetail";

	/**
	 * 留言板首页或类别页
	 * 
	 * @param ctgId
	 *            留言类别
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/guestbook*.jspx", method = RequestMethod.GET)
	public String index(Integer ctgId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		CmsGuestbookCtg ctg = null;
		if (ctgId != null) {
			ctg = cmsGuestbookCtgMng.findById(ctgId);
		}
		if (ctg == null) {
			// 留言板首页
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, GUESTBOOK_INDEX);
		} else {
			// 留言板类别页
			model.addAttribute("ctg", ctg);
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_SPECIAL, GUESTBOOK_CTG);
		}
	}

	@RequestMapping(value = "/guestbook/{id}.jspx", method = RequestMethod.GET)
	public String detail(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsGuestbook guestbook = null;
		if (id != null) {
			guestbook = cmsGuestbookMng.findById(id);
		}
		model.addAttribute("guestbook", guestbook);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, GUESTBOOK_DETAIL);

	}

	/**
	 * 提交留言。ajax提交。
	 * 
	 * @param contentId
	 * @param pageNo
	 * @param request
	 * @param response
	 * @param model
	 * @throws JSONException
	 */
	@RequestMapping(value = "/guestbook.jspx", method = RequestMethod.POST)
	public void submit(Integer siteId, Integer ctgId, String title,
			String content, String email, String phone, String qq,
			String captcha, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws JSONException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser member = CmsUtils.getUser(request);
		if (siteId == null) {
			siteId = site.getId();
		}
		JSONObject json = new JSONObject();
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
			ResponseUtils.renderJson(response, json.toString());
			log.warn("", e);
			return;
		}
		String ip = RequestUtils.getIpAddr(request);
		cmsGuestbookMng.save(member, siteId, ctgId, ip, title, content, email,
				phone, qq);
		json.put("success", true);
		json.put("status", 0);
		ResponseUtils.renderJson(response, json.toString());
	}

	@Autowired
	private CmsGuestbookCtgMng cmsGuestbookCtgMng;
	@Autowired
	private CmsGuestbookMng cmsGuestbookMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;

}
