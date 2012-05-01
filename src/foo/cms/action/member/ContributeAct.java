package foo.cms.action.member;

import static foo.cms.Constants.TPLDIR_MEMBER;
import static foo.common.page.SimplePage.cpn;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentExt;
import foo.cms.entity.main.ContentTxt;
import foo.cms.entity.main.ContentType;
import foo.cms.entity.main.MemberConfig;
import foo.cms.manager.main.ChannelMng;
import foo.cms.manager.main.ContentMng;
import foo.cms.manager.main.ContentTypeMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.util.StrUtils;
import foo.common.web.session.SessionProvider;

/**
 * 会员投稿Action
 * 
 * @author liufang
 * 
 */
@Controller
public class ContributeAct {
	private static final Logger log = LoggerFactory
			.getLogger(ContributeAct.class);

	public static final String CONTRIBUTE_LIST = "tpl.contributeList";
	public static final String CONTRIBUTE_ADD = "tpl.contributeAdd";
	public static final String CONTRIBUTE_EDIT = "tpl.contributeEdit";

	/**
	 * 会员投稿列表
	 * 
	 * @param title
	 *            文章标题
	 * @param channelId
	 *            栏目ID
	 * @param pageNo
	 *            页码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/contribute_list.jspx")
	public String list(String queryTitle, Integer queryChannelId,
			Integer pageNo, HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		Pagination p = contentMng.getPageForMember(queryTitle, queryChannelId,
				site.getId(), user.getId(), cpn(pageNo), 20);
		model.addAttribute("pagination", p);
		if (!StringUtils.isBlank(queryTitle)) {
			model.addAttribute("queryTitle", queryTitle);
		}
		if (queryChannelId != null) {
			model.addAttribute("queryChannelId", queryChannelId);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, CONTRIBUTE_LIST);
	}

	/**
	 * 会员投稿添加
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/contribute_add.jspx")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		// 获得本站栏目列表
		Set<Channel> rights = user.getGroup().getContriChannels();
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, rights,
				true);
		model.addAttribute("channelList", channelList);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, CONTRIBUTE_ADD);
	}

	/**
	 * 会员投稿保存
	 * 
	 * @param id
	 *            文章ID
	 * @param title
	 *            标题
	 * @param author
	 *            作者
	 * @param description
	 *            描述
	 * @param txt
	 *            内容
	 * @param tagStr
	 *            TAG字符串
	 * @param channelId
	 *            栏目ID
	 * @param nextUrl
	 *            下一个页面地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/contribute_save.jspx")
	public String save(String title, String author, String description,
			String txt, String tagStr, Integer channelId, String captcha,
			String nextUrl, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		WebErrors errors = validateSave(title, author, description, txt,
				tagStr, channelId, site, user, captcha, request, response);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}

		Content c = new Content();
		c.setSite(site);
		ContentExt ext = new ContentExt();
		ext.setTitle(title);
		ext.setAuthor(author);
		ext.setDescription(description);
		ContentTxt t = new ContentTxt();
		t.setTxt(txt);
		ContentType type = contentTypeMng.getDef();
		if (type == null) {
			throw new RuntimeException("Default ContentType not found.");
		}
		Integer typeId = type.getId();
		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", null);
		c = contentMng.save(c, ext, t, null, null, null, tagArr, null, null,
				null, null, null, channelId, typeId, null, user, true);
		log.info("member contribute save Content success. id={}", c.getId());
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	/**
	 * 会员投稿修改
	 * 
	 * @param id
	 *            文章ID
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/contribute_edit.jspx")
	public String edit(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		WebErrors errors = validateEdit(id, site, user, request);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		Content content = contentMng.findById(id);
		// 获得本站栏目列表
		Set<Channel> rights = user.getGroup().getContriChannels();
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, rights,
				true);
		model.addAttribute("content", content);
		model.addAttribute("channelList", channelList);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MEMBER, CONTRIBUTE_EDIT);
	}

	/**
	 * 会有投稿更新
	 * 
	 * @param id
	 *            文章ID
	 * @param title
	 *            标题
	 * @param author
	 *            作者
	 * @param description
	 *            描述
	 * @param txt
	 *            内容
	 * @param tagStr
	 *            TAG字符串
	 * @param channelId
	 *            栏目ID
	 * @param nextUrl
	 *            下一个页面地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/contribute_update.jspx")
	public String update(Integer id, String title, String author,
			String description, String txt, String tagStr, Integer channelId,
			String nextUrl, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		WebErrors errors = validateUpdate(id, channelId, site, user, request);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		Content c = new Content();
		c.setId(id);
		c.setSite(site);
		ContentExt ext = new ContentExt();
		ext.setId(id);
		ext.setTitle(title);
		ext.setAuthor(author);
		ext.setDescription(description);
		ContentTxt t = new ContentTxt();
		t.setId(id);
		t.setTxt(txt);
		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", null);
		contentMng.update(c, ext, t, tagArr, null, null, null, null, null,
				null, null, null, null, channelId, null, null, user, true);
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	/**
	 * 会员投稿删除
	 * 
	 * @param ids
	 *            待删除的文章ID数组
	 * @param nextUrl
	 *            下一个页面地址
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/contribute_delete.jspx")
	public String delete(Integer[] ids, HttpServletRequest request,
			String nextUrl, HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		FrontUtils.frontData(request, model, site);
		MemberConfig mcfg = site.getConfig().getMemberConfig();
		// 没有开启会员功能
		if (!mcfg.isMemberOn()) {
			return FrontUtils.showMessage(request, model, "member.memberClose");
		}
		if (user == null) {
			return FrontUtils.showLogin(request, model, site);
		}
		WebErrors errors = validateDelete(ids, site, user, request);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		Content[] arr = contentMng.deleteByIds(ids);
		log.info("member contribute delete Content success. ids={}",
				StringUtils.join(arr, ","));
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	private WebErrors validateSave(String title, String author,
			String description, String txt, String tagStr, Integer channelId,
			CmsSite site, CmsUser user, String captcha,
			HttpServletRequest request, HttpServletResponse response) {
		WebErrors errors = WebErrors.create(request);
		try {
			if (!imageCaptchaService.validateResponseForID(session
					.getSessionId(request, response), captcha)) {
				errors.addErrorCode("error.invalidCaptcha");
				return errors;
			}
		} catch (CaptchaServiceException e) {
			errors.addErrorCode("error.exceptionCaptcha");
			log.warn("", e);
			return errors;
		}
		if (errors.ifBlank(title, "title", 150)) {
			return errors;
		}
		if (errors.ifMaxLength(author, "author", 100)) {
			return errors;
		}
		if (errors.ifMaxLength(description, "description", 255)) {
			return errors;
		}
		// 内容不能大于1M
		if (errors.ifBlank(txt, "txt", 1048575)) {
			return errors;
		}
		if (errors.ifMaxLength(tagStr, "tagStr", 255)) {
			return errors;
		}
		if (errors.ifNull(channelId, "channelId")) {
			return errors;
		}
		if (vldChannel(errors, site, user, channelId)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateEdit(Integer id, CmsSite site, CmsUser user,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldOpt(errors, site, user, new Integer[] { id })) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, Integer channelId,
			CmsSite site, CmsUser user, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldOpt(errors, site, user, new Integer[] { id })) {
			return errors;
		}
		if (vldChannel(errors, site, user, channelId)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, CmsSite site, CmsUser user,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldOpt(errors, site, user, ids)) {
			return errors;
		}
		return errors;
	}

	private boolean vldOpt(WebErrors errors, CmsSite site, CmsUser user,
			Integer[] ids) {
		for (Integer id : ids) {
			if (errors.ifNull(id, "id")) {
				return true;
			}
			Content c = contentMng.findById(id);
			// 数据不存在
			if (errors.ifNotExist(c, Content.class, id)) {
				return true;
			}
			// 非本用户数据
			if (!c.getUser().getId().equals(user.getId())) {
				errors.noPermission(Content.class, id);
				return true;
			}
			// 非本站点数据
			if (!c.getSite().getId().equals(site.getId())) {
				errors.notInSite(Content.class, id);
				return true;
			}
			// 文章级别大于0，不允许修改
			if (c.getCheckStep() > 0) {
				errors.addErrorCode("member.contentChecked");
				return true;
			}
		}
		return false;
	}

	private boolean vldChannel(WebErrors errors, CmsSite site, CmsUser user,
			Integer channelId) {
		Channel channel = channelMng.findById(channelId);
		if (errors.ifNotExist(channel, Channel.class, channelId)) {
			return true;
		}
		if (!channel.getSite().getId().equals(site.getId())) {
			errors.notInSite(Channel.class, channelId);
			return true;
		}
		if (!channel.getContriGroups().contains(user.getGroup())) {
			errors.noPermission(Channel.class, channelId);
			return true;
		}
		return false;
	}

	@Autowired
	private ContentMng contentMng;
	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private SessionProvider session;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
}
