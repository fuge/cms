package foo.cms.action.front;

import static foo.cms.Constants.TPLDIR_SPECIAL;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import foo.cms.entity.assist.CmsVoteItem;
import foo.cms.entity.assist.CmsVoteTopic;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.assist.CmsVoteRecordMng;
import foo.cms.manager.assist.CmsVoteTopicMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.FrontUtils;
import foo.common.web.CookieUtils;
import foo.common.web.RequestUtils;

@Controller
public class VoteAct {
	private static final Logger log = LoggerFactory.getLogger(VoteAct.class);
	/**
	 * 投票cookie前缀
	 */
	public static final String VOTE_COOKIE_PREFIX = "_vote_cookie_";

	public static final String VOTE_INPUT = "tpl.voteInput";
	public static final String VOTE_RESULT = "tpl.voteResult";

	@RequestMapping(value = "/vote_result.jspx", method = RequestMethod.GET)
	public String result(Integer voteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsVoteTopic vote = cmsVoteTopicMng.findById(voteId);
		model.addAttribute("vote", vote);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, VOTE_RESULT);
	}

	@RequestMapping(value = "/vote.jspx", method = RequestMethod.GET)
	public String input(Integer voteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsVoteTopic vote = cmsVoteTopicMng.findById(voteId);
		model.addAttribute("vote", vote);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, VOTE_INPUT);
	}

	@RequestMapping(value = "/vote.jspx", method = RequestMethod.POST)
	public String submit(Integer voteId, Integer[] itemIds,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String ip = RequestUtils.getIpAddr(request);
		String cookieName = VOTE_COOKIE_PREFIX + voteId;
		Cookie cookie = CookieUtils.getCookie(request, cookieName);
		String cookieValue;
		if (cookie != null && !StringUtils.isBlank(cookie.getValue())) {
			cookieValue = cookie.getValue();
		} else {
			cookieValue = null;
		}
		if (!validateSubmit(voteId, itemIds, user, ip, cookieValue, model)) {
			if (cookieValue == null) {
				// 随机cookie
				cookieValue = StringUtils.remove(UUID.randomUUID().toString(),
						"-");
				// 写cookie
				CookieUtils.addCookie(request, response, cookieName,
						cookieValue, Integer.MAX_VALUE, null);
			}
			CmsVoteTopic vote = cmsVoteTopicMng.vote(voteId, itemIds, user, ip,
					cookieValue);
			model.addAttribute("status", 0);
			model.addAttribute("vote", vote);

			log.info("vote CmsVote id={}, name={}", vote.getId(), vote
					.getTitle());
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, VOTE_RESULT);
	}

	private boolean validateSubmit(Integer topicId, Integer[] itemIds,
			CmsUser user, String ip, String cookie, ModelMap model) {
		// 投票ID不能为空
		if (topicId == null) {
			model.addAttribute("status", 1);
			return true;
		}
		// 投票项不能为空
		if (itemIds == null || itemIds.length <= 0) {
			model.addAttribute("status", 2);
			return true;
		}

		CmsVoteTopic topic = cmsVoteTopicMng.findById(topicId);
		// 投票主题不存在
		if (topic == null) {
			model.addAttribute("status", 100);
			return true;
		}
		// 投票项不合法
		boolean contains;
		for (Integer itemId : itemIds) {
			contains = false;
			for (CmsVoteItem item : topic.getItems()) {
				if (item.getId().equals(itemId)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				model.addAttribute("status", 101);
				return true;
			}
		}

		// 需要登录才能投票
		if (topic.getRestrictMember() && user == null) {
			model.addAttribute("status", 501);
			return true;
		}

		// 投票主题已经关闭
		if (topic.getDisabled()) {
			model.addAttribute("status", 200);
			return true;
		}
		// 投票的选项个数大于允许的个数
		if (itemIds.length > topic.getMultiSelect()) {
			model.addAttribute("status", 201);
			return true;
		}
		long now = System.currentTimeMillis();
		// 投票还没有开始
		Date start = topic.getStartTime();
		if (start != null && now < start.getTime()) {
			model.addAttribute("status", 202);
			model.addAttribute("startTime", start);
			return true;
		}
		// 投票已经结束
		Date end = topic.getEndTime();
		if (end != null && now > end.getTime()) {
			model.addAttribute("status", 203);
			model.addAttribute("endTime", end);
			return true;
		}
		Integer hour = topic.getRepeateHour();
		if (hour == null || hour > 0) {
			Date vtime;
			// 规定时间内，同一会员不能重复投票
			if (topic.getRestrictMember()) {
				vtime = cmsVoteRecordMng.lastVoteTimeByUserId(user.getId(),
						topicId);
				if (hour == null
						|| vtime.getTime() + hour * 60 * 60 * 1000 > now) {
					model.addAttribute("status", 204);
					return true;
				}
			}
			// 规定时间内，同一IP不能重复投票
			if (topic.getRestrictIp()) {
				vtime = cmsVoteRecordMng.lastVoteTimeByIp(ip, topicId);
				if (hour == null
						|| vtime.getTime() + hour * 60 * 60 * 1000 > now) {
					model.addAttribute("status", 205);
					return true;
				}
			}
			// 规定时间内，同一COOKIE不能重复投票
			if (topic.getRestrictCookie() && cookie != null) {
				vtime = cmsVoteRecordMng.lastVoteTimeByCookie(cookie, topicId);
				if (hour == null
						|| vtime.getTime() + hour * 60 * 60 * 1000 > now) {
					model.addAttribute("status", 206);
					return true;
				}
			}
		}
		return false;
	}

	@Autowired
	private CmsVoteTopicMng cmsVoteTopicMng;
	@Autowired
	private CmsVoteRecordMng cmsVoteRecordMng;
}
