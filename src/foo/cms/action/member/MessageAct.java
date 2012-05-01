package foo.cms.action.member;

import static foo.cms.Constants.TPLDIR_MESSAGE;
import static foo.common.page.SimplePage.cpn;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

import foo.cms.entity.assist.CmsMessage;
import foo.cms.entity.assist.CmsReceiverMessage;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.MemberConfig;
import foo.cms.manager.assist.CmsMessageMng;
import foo.cms.manager.assist.CmsReceiverMessageMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.web.CookieUtils;
import foo.common.web.ResponseUtils;
import foo.common.web.session.SessionProvider;

/**
 * 站内信Action
 * 
 * @author 江西金磊科技发展有限公司
 * 
 */
@Controller
public class MessageAct {
	private static final Logger log = LoggerFactory.getLogger(MessageAct.class);

	public static final String MESSAGE_IN_BOX_LIST = "tpl.messageInBoxLists";
	public static final String MESSAGE_DRAFT_LIST = "tpl.messageDraftLists";
	public static final String MESSAGE_SEND_LIST = "tpl.messageSendLists";
	public static final String MESSAGE_TRASH_LIST = "tpl.messageTrashLists";
	public static final String MESSAGE_MNG = "tpl.messageMng";
	public static final String MESSAGE_ADD = "tpl.messageAdd";
	public static final String MESSAGE_EDIT = "tpl.messageEdit";
	public static final String MESSAGE_READ = "tpl.messageRead";
	public static final String MESSAGE_REPLY = "tpl.messageReply";

	/**
	 * 我的信息
	 * 
	 * 如果没有登录则跳转到登陆页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/message_mng.jspx")
	public String message_mng(Integer box, String msg,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
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
		if (box != null) {
			model.addAttribute("box", box);
		} else {
			model.addAttribute("box", 0);
		}
		model.addAttribute("msg", msg);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MESSAGE, MESSAGE_MNG);
	}

	/**
	 * 
	 * @param pageNo
	 * @param title
	 *            标题
	 * @param sendBeginTime
	 * @param sendEndTime
	 * @param status
	 *            信件状态 0未读，1已读
	 * @param box
	 *            信件邮箱 0收件箱 1发件箱 2草稿箱 3垃圾箱
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/member/message_list.jspx")
	public String message_inbox(Integer pageNo, String title,
			Date sendBeginTime, Date sendEndTime, Boolean status, Integer box,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
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
		Pagination pagination = null;
		String returnPage = MESSAGE_IN_BOX_LIST;
		if (box.equals(0)) {
			// 收件箱
			pagination = receiverMessageMng.getPage(site.getId(), null, user
					.getId(), title, sendBeginTime, sendEndTime, status, box,
					false, cpn(pageNo), CookieUtils.getPageSize(request));
			returnPage = MESSAGE_IN_BOX_LIST;
		} else if (box.equals(1)) {
			// 发件箱
			pagination = messageMng.getPage(site.getId(), user.getId(), null,
					title, sendBeginTime, sendEndTime, status, box, false,
					cpn(pageNo), CookieUtils.getPageSize(request));
			returnPage = MESSAGE_SEND_LIST;
		} else if (box.equals(2)) {
			// 草稿箱
			pagination = messageMng.getPage(site.getId(), user.getId(), null,
					title, sendBeginTime, sendEndTime, status, box, false,
					cpn(pageNo), CookieUtils.getPageSize(request));
			returnPage = MESSAGE_DRAFT_LIST;
		} else if (box.equals(3)) {
			// 垃圾箱(可能从收件箱或者从发件箱转过来)
			pagination = receiverMessageMng.getPage(site.getId(), user.getId(),
					user.getId(), title, sendBeginTime, sendEndTime, status,
					box, false, cpn(pageNo), CookieUtils.getPageSize(request));
			returnPage = MESSAGE_TRASH_LIST;
		}
		model.addAttribute("msg", request.getAttribute("msg"));
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("title", title);
		model.addAttribute("sendBeginTime", sendBeginTime);
		model.addAttribute("sendEndTime", sendEndTime);
		model.addAttribute("status", status);
		model.addAttribute("box", box);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MESSAGE, returnPage);
	}

	@RequestMapping(value = "/member/message_add.jspx")
	public String message_add(HttpServletRequest request,
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
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MESSAGE, MESSAGE_ADD);
	}

	@RequestMapping(value = "/member/message_reply.jspx")
	public String message_reply(Integer id, HttpServletRequest request,
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
		CmsReceiverMessage message = receiverMessageMng.findById(id);
		// 非收件人无法回复信件
		if (!message.getMsgReceiverUser().equals(user)) {
			WebErrors errors = WebErrors.create(request);
			errors.addErrorCode("error.noPermissionsView");
			return FrontUtils.showError(request, response, model, errors);
		}
		model.addAttribute("message", message);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MESSAGE, MESSAGE_REPLY);
	}

	// 直接发送
	@RequestMapping(value = "/member/message_send.jspx")
	public String message_send(CmsMessage message, String username,
			String captcha, String nextUrl, HttpServletRequest request,
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
		WebErrors errors = validateCaptcha(captcha, request, response);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		// 发送端
		message.setMsgBox(1);
		message.setMsgSendUser(user);
		CmsUser msgReceiverUser = userMng.findByUsername(username);
		message.setMsgReceiverUser(msgReceiverUser);
		message.setMsgStatus(false);
		message.setSendTime(new Date());
		message.setSite(site);
		messageMng.save(message);
		CmsReceiverMessage receiverMessage = new CmsReceiverMessage(message);
		receiverMessage.setMsgBox(0);
		receiverMessage.setMessage(message);
		// 接收端（有一定冗余）
		receiverMessageMng.save(receiverMessage);
		log.info("member CmsMessage save CmsMessage success. id={}", message
				.getId());
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	// 存草稿
	@RequestMapping(value = "/member/message_save.jspx")
	public String message_save(CmsMessage message, String username,
			String captcha, String nextUrl, HttpServletRequest request,
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
		WebErrors errors = validateCaptcha(captcha, request, response);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		message.setMsgBox(2);
		message.setMsgSendUser(user);
		CmsUser msgReceiverUser = userMng.findByUsername(username);
		message.setMsgReceiverUser(msgReceiverUser);
		message.setMsgStatus(false);
		// 作为草稿和发件箱的区别
		message.setSendTime(null);
		// message.setSendTime(new Date());
		message.setSite(site);
		messageMng.save(message);
		CmsReceiverMessage receiverMessage = new CmsReceiverMessage(message);
		receiverMessage.setMsgBox(2);
		receiverMessage.setMessage(message);
		// 接收端（有一定冗余）
		receiverMessageMng.save(receiverMessage);
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	// 发送
	@RequestMapping(value = "/member/message_tosend.jspx")
	public String message_tosend(Integer id, String nextUrl, String captcha,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
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
		WebErrors errors = validateCaptcha(captcha, request, response);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		CmsMessage message = messageMng.findById(id);
		message.setMsgBox(1);
		message.setSendTime(new Date());
		messageMng.update(message);
		Set<CmsReceiverMessage> receiverMessageSet = message.getReceiverMsgs();
		Iterator<CmsReceiverMessage> it = receiverMessageSet.iterator();
		CmsReceiverMessage receiverMessage;
		while (it.hasNext()) {
			receiverMessage = it.next();
			receiverMessage.setMsgBox(0);
			receiverMessage.setSendTime(new Date());
			receiverMessage.setMessage(message);
			// 接收端（有一定冗余）
			receiverMessageMng.update(receiverMessage);
		}
		log.info("member CmsMessage save CmsMessage success. id={}", message
				.getId());
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	@RequestMapping(value = "/member/message_edit.jspx")
	public String message_edit(Integer id, HttpServletRequest request,
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
		CmsMessage message = messageMng.findById(id);
		// 非发件人无权查看信件
		if (!message.getMsgSendUser().equals(user)) {
			WebErrors errors = WebErrors.create(request);
			errors.addErrorCode("error.noPermissionsView");
			return FrontUtils.showError(request, response, model, errors);
		}
		model.addAttribute("message", message);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MESSAGE, MESSAGE_EDIT);
	}

	@RequestMapping(value = "/member/message_update.jspx")
	public String message_update(CmsMessage message, String nextUrl,
			String captcha, HttpServletRequest request,
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
		WebErrors errors = validateCaptcha(captcha, request, response);
		if (errors.hasErrors()) {
			return FrontUtils.showError(request, response, model, errors);
		}
		message = messageMng.update(message);
		// 更新发送表的信息，收件表的信息同步更新
		Set<CmsReceiverMessage> receiverMessageSet = message.getReceiverMsgs();
		Iterator<CmsReceiverMessage> it = receiverMessageSet.iterator();
		CmsReceiverMessage receiverMessage;
		while (it.hasNext()) {
			receiverMessage = it.next();
			receiverMessage.setMsgContent(message.getContentHtml());
			receiverMessage.setMsgReceiverUser(message.getMsgReceiverUser());
			receiverMessage.setMsgTitle(message.getMsgTitle());
			receiverMessage.setMessage(message);
			// 接收端（有一定冗余）
			receiverMessageMng.update(receiverMessage);
		}
		log.info("member CmsMessage update CmsMessage success. id={}", message
				.getId());
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	@RequestMapping(value = "/member/message_read.jspx")
	public String message_read(Integer id, Integer box,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
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
		CmsReceiverMessage message = receiverMessageMng.findById(id);
		if (message != null) {
			// 阅读收信
			// 非收件人和发件人无权查看信件
			if (!message.getMsgReceiverUser().equals(user)
					&& !message.getMsgSendUser().equals(user)) {
				WebErrors errors = WebErrors.create(request);
				errors.addErrorCode("error.noPermissionsView");
				return FrontUtils.showError(request, response, model, errors);
			}
			// 收件人查看更新已读状态
			if (message.getMsgReceiverUser().equals(user)) {
				message.setMsgStatus(true);
				receiverMessageMng.update(message);
				log
						.info(
								"member CmsMessage read CmsMessage success. id={}",
								id);
			}
			model.addAttribute("message", message);
		} else {
			// 阅读已发信
			CmsMessage msg = messageMng.findById(id);
			model.addAttribute("message", msg);
		}
		model.addAttribute("box", box);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MESSAGE, MESSAGE_READ);
	}

	// 转发
	@RequestMapping(value = "/member/message_forward.jspx")
	public String message_forward(Integer id, HttpServletRequest request,
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
		CmsReceiverMessage receiverMessage = receiverMessageMng.findById(id);
		CmsMessage message;
		if (receiverMessage != null) {
			model.addAttribute("message", receiverMessage);
		} else {
			message = messageMng.findById(id);
			model.addAttribute("message", message);
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_MESSAGE, MESSAGE_ADD);
	}

	// 清空信息到垃圾箱
	@RequestMapping(value = "/member/message_trash.jspx")
	public void message_trash(Integer ids[], HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws JSONException {
		CmsUser user = CmsUtils.getUser(request);
		JSONObject object = new JSONObject();
		CmsMessage message;
		CmsReceiverMessage receiverMessage;
		if (user == null) {
			object.put("result", false);
		} else {
			Iterator<CmsReceiverMessage> it;
			for (Integer i = 0; i < ids.length; i++) {
				message = messageMng.findById(ids[i]);
				receiverMessage = receiverMessageMng.findById(ids[i]);
				if (message != null && message.getMsgSendUser().equals(user)) {
					message.setMsgBox(3);
					receiverMessage = new CmsReceiverMessage();
					receiverMessage.setMsgBox(3);
					receiverMessage.setMsgContent(message.getMsgContent());
					receiverMessage.setMsgSendUser(message.getMsgSendUser());
					receiverMessage.setMsgReceiverUser(user);
					receiverMessage.setMsgStatus(message.getMsgStatus());
					receiverMessage.setMsgTitle(message.getMsgTitle());
					receiverMessage.setSendTime(message.getSendTime());
					receiverMessage.setSite(message.getSite());
					receiverMessage.setMessage(null);
					// 接收端（有一定冗余）
					receiverMessageMng.save(receiverMessage);
					// 清空该发件对应的收件关联关系
					Set<CmsReceiverMessage> receiverMessages = message
							.getReceiverMsgs();
					if (receiverMessages != null && receiverMessages.size() > 0) {
						it = receiverMessages.iterator();
						CmsReceiverMessage tempReceiverMessage;
						while (it.hasNext()) {
							tempReceiverMessage = it.next();
							tempReceiverMessage.setMessage(null);
							receiverMessageMng.update(tempReceiverMessage);
						}
					}
					messageMng.deleteById(ids[i]);
				}
				if (receiverMessage != null
						&& receiverMessage.getMsgReceiverUser().equals(user)) {
					receiverMessage.setMsgBox(3);
					receiverMessageMng.update(receiverMessage);
				}
				log.info("member CmsMessage trash CmsMessage success. id={}",
						ids[i]);
			}
			object.put("result", true);
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	// 还原垃圾箱信息
	@RequestMapping(value = "/member/message_revert.jspx")
	public void message_revert(Integer ids[], HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws JSONException {
		CmsUser user = CmsUtils.getUser(request);
		JSONObject object = new JSONObject();
		CmsReceiverMessage receiverMessage;
		if (user == null) {
			object.put("result", false);
		} else {
			for (Integer i = 0; i < ids.length; i++) {
				receiverMessage = receiverMessageMng.findById(ids[i]);
				// 收件箱
				if (receiverMessage != null
						&& receiverMessage.getMsgReceiverUser().equals(user)) {
					receiverMessage.setMsgBox(0);
					receiverMessageMng.update(receiverMessage);
				}
				log.info("member CmsMessage revert CmsMessage success. id={}",
						ids[i]);
			}
			object.put("result", true);
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	// 清空垃圾箱信息
	@RequestMapping(value = "/member/message_empty.jspx")
	public void message_empty(Integer ids[], HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws JSONException {
		CmsUser user = CmsUtils.getUser(request);
		JSONObject object = new JSONObject();
		CmsMessage message;
		CmsReceiverMessage receiverMessage;
		if (user == null) {
			object.put("result", false);
		} else {
			for (Integer i = 0; i < ids.length; i++) {
				// 清空收到的站内信
				receiverMessage = receiverMessageMng.findById(ids[i]);
				if (receiverMessage != null
						&& receiverMessage.getMsgReceiverUser().equals(user)) {
					receiverMessageMng.deleteById(ids[i]);
				} else {
					// 清空发送的站内信
					message = receiverMessage.getMessage();
					if (receiverMessage.getMsgBox().equals(3)) {
						// 草稿直接删除
						receiverMessage.setMessage(null);
						if (message != null) {
							messageMng.deleteById(message.getId());
						}
					} else {
						// 非草稿删除和主表的关联
						receiverMessage.setMessage(null);
					}
					if (message != null
							&& message.getMsgSendUser().equals(user)) {
						messageMng.deleteById(message.getId());
					}
				}
				log.info("member CmsMessage empty CmsMessage success. id={}",
						ids[i]);
			}
			object.put("result", true);
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	// 查找是否存在该用户
	@RequestMapping(value = "/member/message_findUser")
	public void findUserByUserName(String username, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws JSONException {
		CmsUser user = CmsUtils.getUser(request);
		JSONObject object = new JSONObject();
		if (user == null) {
			object.put("result", false);
		} else {
			// 会员只能在会员之间发送站内信
			Boolean exist = userMng.usernameNotExistInMember(username);
			object.put("result", true);
			object.put("exist", exist);
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	// 查找未读信息条数
	@RequestMapping(value = "/member/message_countUnreadMsg.jspx")
	public void findUnreadMessagesByUser(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws JSONException {
		CmsUser user = CmsUtils.getUser(request);
		CmsSite site = CmsUtils.getSite(request);
		JSONObject object = new JSONObject();
		if (user == null) {
			object.put("result", false);
		} else {
			List<CmsReceiverMessage> receiverMessages = receiverMessageMng
					.getList(site.getId(), null, user.getId(), null, null,
							null, false, 0, false);
			object.put("result", true);
			if (receiverMessages != null && receiverMessages.size() > 0) {
				object.put("count", receiverMessages.size());
			} else {
				object.put("count", 0);
			}
			object.put("result", true);
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	// 物理删除信件（暂时无用）
	@RequestMapping(value = "/member/message_delete.jspx")
	public String message_delete(Integer[] ids, String nextUrl,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
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
		CmsMessage message;
		Boolean permission = true;
		if (ids != null && ids.length > 0) {
			for (Integer i = 0; i < ids.length; i++) {
				message = messageMng.findById(ids[i]);
				// 非收件人和发件人无权查看信件
				if (!message.getMsgReceiverUser().equals(user)
						&& !message.getMsgSendUser().equals(user)) {
					permission = false;
				}
			}
			if (permission) {
				messageMng.deleteByIds(ids);
				for (Integer i = 0; i < ids.length; i++) {
					log
							.info(
									"member CmsMessage delete CmsMessage success. id={}",
									ids[i]);
				}
			} else {
				WebErrors errors = WebErrors.create(request);
				errors.addErrorCode("error.noPermissionsView");
				return FrontUtils.showError(request, response, model, errors);
			}
		}
		return FrontUtils.showSuccess(request, model, nextUrl);
	}

	private WebErrors validateCaptcha(String captcha,
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
		return errors;
	}

	@Autowired
	private CmsMessageMng messageMng;
	@Autowired
	private CmsReceiverMessageMng receiverMessageMng;
	@Autowired
	private CmsUserMng userMng;
	@Autowired
	private ImageCaptchaService imageCaptchaService;
	@Autowired
	private SessionProvider session;
}
