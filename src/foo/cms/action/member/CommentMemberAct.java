package foo.cms.action.member;

import static foo.cms.Constants.TPLDIR_COMMENT;
import static foo.common.page.SimplePage.cpn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.assist.CmsComment;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.MemberConfig;
import foo.cms.manager.assist.CmsCommentMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.web.CookieUtils;

/**
 * 会员中心获取评论Action
 * 
 * @author 江西金磊科技发展有限公司
 * 
 */
@Controller
public class CommentMemberAct {
	private static final Logger log = LoggerFactory
			.getLogger(CommentMemberAct.class);

	public static final String COMMENT_LIST = "tpl.commentLists";
	public static final String COMMENT_MNG = "tpl.commentMng";
	public static final String COMMENT_REPLY = "tpl.commentReply";

	/**
	 * 我的评论
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/mycomments.jspx")
	public String mycomments(Integer pageNo, HttpServletRequest request,
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
		Pagination pagination = commentMng.getPageForMember(site.getId(), null,
				user.getId(), null, null, null, null, true, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_COMMENT, COMMENT_LIST);
	}
	/**
	 * 查看评论回复
	 */
	@RequestMapping(value = "/member/comment_replay.jspx")
	public String guestbook_replay(Integer id, String nextUrl,HttpServletRequest request,
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
		CmsComment comment=commentMng.findById(id);
		if(!comment.getCommentUser().equals(user)){
			WebErrors errors=WebErrors.create(request);
			errors.addErrorCode("error.noPermissionsView");
			return FrontUtils.showError(request, response, model, errors);
		}
		model.addAttribute("comment", comment);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_COMMENT, COMMENT_REPLY);
	}

	/**
	 * 我的信息所有的评论
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/news_comments.jspx")
	public String news_comments(Integer pageNo, HttpServletRequest request,
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
		Pagination pagination = commentMng.getPageForMember(site.getId(), null,
				null, user.getId(), null, null, null, true, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_COMMENT, COMMENT_MNG);
	}

	/**
	 * 删除评论（id，评论人id，来访ip）
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/comment_delete.jspx")
	public String delete(Integer commentId, Integer userId, String ip,
			Integer pageNo, String nextUrl, HttpServletRequest request,
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
		// 删除单条评论
		CmsComment bean;
		if (commentId != null) {
			bean = commentMng.deleteById(commentId);
			log.info("delete CmsComment id={}", bean.getId());
		} else {
			// 依据评论人或者评论ip删除评论
			List<CmsComment> comments = commentMng.getListForDel(site.getId(),
					user.getId(), userId, ip);
			for (int i = 0; i < comments.size(); i++) {
				bean = comments.get(i);
				commentMng.deleteById(comments.get(i).getId());
				log.info("delete CmsComment id={}", bean.getId());
			}
		}
		/*
		 * Pagination pagination = commentMng.getPageForMember(site.getId(),
		 * null, null, user.getId(), null, null, null, true, cpn(pageNo),
		 * CookieUtils.getPageSize(request)); model.addAttribute("pagination",
		 * pagination);
		 */
		// 返回评论列表
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	@Autowired
	private CmsCommentMng commentMng;
}
