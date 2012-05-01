package foo.cms.statistic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.main.ChannelMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.statistic.CmsStatistic.CmsStatisticModel;
import foo.cms.web.CmsUtils;
import foo.common.page.Pagination;
import foo.common.web.CookieUtils;
import foo.common.web.RequestUtils;

import static foo.cms.statistic.CmsStatistic.ACCESS_PAGE;
import static foo.cms.statistic.CmsStatistic.AREA;
import static foo.cms.statistic.CmsStatistic.AVGVIEWS;
import static foo.cms.statistic.CmsStatistic.CHANNELID;
import static foo.cms.statistic.CmsStatistic.COMMENT;
import static foo.cms.statistic.CmsStatistic.CONTENT;
import static foo.cms.statistic.CmsStatistic.GUESTBOOK;
import static foo.cms.statistic.CmsStatistic.ISREPLYED;
import static foo.cms.statistic.CmsStatistic.MEMBER;
import static foo.cms.statistic.CmsStatistic.PV;
import static foo.cms.statistic.CmsStatistic.REFERER_KEYWORD;
import static foo.cms.statistic.CmsStatistic.REFERER_PAGE;
import static foo.cms.statistic.CmsStatistic.REFERER_WEBSITE;
import static foo.cms.statistic.CmsStatistic.SITEID;
import static foo.cms.statistic.CmsStatistic.UNIQUEIP;
import static foo.cms.statistic.CmsStatistic.UNIQUEVISITOR;
import static foo.cms.statistic.CmsStatistic.USERID;
import static foo.common.page.SimplePage.cpn;

@Controller
public class CmsStatisticAct {
	@RequestMapping("/statistic_member/v_list.do")
	public String memberList(String queryModel, Integer year, Integer month,
			Integer day, ModelMap model) {
		CmsStatisticModel statisticModel = getStatisticModel(queryModel);
		List<CmsStatistic> list = cmsStatisticSvc.statisticByModel(MEMBER,
				statisticModel, year, month, day, null);
		putCommonData(statisticModel, list, year, month, day, model);
		return "statistic/member/list";
	}

	@RequestMapping("/statistic_content/v_list.do")
	public String contentList(HttpServletRequest request, String queryModel,
			Integer channelId, Integer year, Integer month, Integer day,
			ModelMap model) {
		String queryInputUsername = RequestUtils.getQueryParam(request,
				"queryInputUsername");
		Integer queryInputUserId = null;
		if (!StringUtils.isBlank(queryInputUsername)) {
			CmsUser u = cmsUserMng.findByUsername(queryInputUsername);
			if (u != null) {
				queryInputUserId = u.getId();
			} else {
				// 用户名不存在，清空。
				queryInputUsername = null;
			}
		}
		Map<String, Object> restrictions = new HashMap<String, Object>();
		Integer siteId = CmsUtils.getSiteId(request);
		restrictions.put(SITEID, siteId);
		restrictions.put(USERID, queryInputUserId);
		restrictions.put(CHANNELID, channelId);
		CmsStatisticModel statisticModel = getStatisticModel(queryModel);
		List<CmsStatistic> list = cmsStatisticSvc.statisticByModel(CONTENT,
				statisticModel, year, month, day, restrictions);
		List<Channel> topList = channelMng.getTopList(siteId, true);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				true);
		putCommonData(statisticModel, list, year, month, day, model);
		model.addAttribute("queryInputUsername", queryInputUsername);
		model.addAttribute("channelList", channelList);
		model.addAttribute("channelId", channelId);
		return "statistic/content/list";
	}

	@RequestMapping("/statistic_comment/v_list.do")
	public String commentList(HttpServletRequest request, String queryModel,
			Integer year, Integer month, Integer day, Boolean isReplyed,
			ModelMap model) {
		Map<String, Object> restrictions = new HashMap<String, Object>();
		Integer siteId = CmsUtils.getSiteId(request);
		restrictions.put(SITEID, siteId);
		restrictions.put(ISREPLYED, isReplyed);
		CmsStatisticModel statisticModel = getStatisticModel(queryModel);
		List<CmsStatistic> list = cmsStatisticSvc.statisticByModel(COMMENT,
				statisticModel, year, month, day, restrictions);
		putCommonData(statisticModel, list, year, month, day, model);
		model.addAttribute("isReplyed", isReplyed);
		return "statistic/comment/list";
	}

	@RequestMapping("/statistic_guestbook/v_list.do")
	public String guestbookList(HttpServletRequest request, String queryModel,
			Integer year, Integer month, Integer day, Boolean isReplyed,
			ModelMap model) {
		Map<String, Object> restrictions = new HashMap<String, Object>();
		Integer siteId = CmsUtils.getSiteId(request);
		restrictions.put(SITEID, siteId);
		restrictions.put(ISREPLYED, isReplyed);
		CmsStatisticModel statisticModel = getStatisticModel(queryModel);
		List<CmsStatistic> list = cmsStatisticSvc.statisticByModel(GUESTBOOK,
				statisticModel, year, month, day, restrictions);
		putCommonData(statisticModel, list, year, month, day, model);
		model.addAttribute("isReplyed", isReplyed);
		return "statistic/guestbook/list";
	}

	@RequestMapping("/flow_pv/v_list.do")
	public String pageViewList(HttpServletRequest request, ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		List<CmsStatistic> list = cmsStatisticSvc.flowStatistic(PV, siteId);
		model.addAttribute("list", list);
		return "statistic/pv/list";
	}

	@RequestMapping("/flow_uniqueIp/v_list.do")
	public String uniqueIpList(HttpServletRequest request, ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		List<CmsStatistic> list = cmsStatisticSvc.flowStatistic(UNIQUEIP,
				siteId);
		model.addAttribute("list", list);
		return "statistic/uniqueIp/list";
	}

	@RequestMapping("/flow_uniqueVisitor/v_list.do")
	public String uniqueVisitorList(HttpServletRequest request, ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		List<CmsStatistic> list = cmsStatisticSvc.flowStatistic(UNIQUEVISITOR,
				siteId);
		model.addAttribute("list", list);
		return "statistic/uniqueVisitor/list";
	}

	@RequestMapping("/flow_avgViews/v_list.do")
	public String avgViewsList(HttpServletRequest request, ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		List<CmsStatistic> list = cmsStatisticSvc.flowStatistic(AVGVIEWS,
				siteId);
		model.addAttribute("list", list);
		return "statistic/avgViews/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/flow_refererWebSite/v_list.do")
	public String refererWebSite(HttpServletRequest request, Integer pageNo,
			ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		Pagination pagination = cmsStatisticSvc.flowAnalysisPage(
				REFERER_WEBSITE, siteId, cpn(pageNo), CookieUtils
						.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("total", getTotal((List<CmsStatistic>) pagination
				.getList()));
		return "statistic/refererWebSite/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/flow_refererPage/v_list.do")
	public String refererPage(HttpServletRequest request, Integer pageNo,
			ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		Pagination pagination = cmsStatisticSvc.flowAnalysisPage(REFERER_PAGE,
				siteId, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("total", getTotal((List<CmsStatistic>) pagination
				.getList()));
		return "statistic/refererPage/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/flow_accessPage/v_list.do")
	public String accessPage(HttpServletRequest request, Integer pageNo,
			ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		Pagination pagination = cmsStatisticSvc.flowAnalysisPage(ACCESS_PAGE,
				siteId, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("total", getTotal((List<CmsStatistic>) pagination
				.getList()));
		return "statistic/accessPage/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/flow_area/v_list.do")
	public String area(HttpServletRequest request, Integer pageNo,
			ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		Pagination pagination = cmsStatisticSvc.flowAnalysisPage(AREA, siteId,
				cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("total", getTotal((List<CmsStatistic>) pagination
				.getList()));
		return "statistic/area/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/flow_refererKeyword/v_list.do")
	public String refererKeyword(HttpServletRequest request, Integer pageNo,
			ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		Pagination pagination = cmsStatisticSvc.flowAnalysisPage(
				REFERER_KEYWORD, siteId, cpn(pageNo), CookieUtils
						.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("total", getTotal((List<CmsStatistic>) pagination
				.getList()));
		return "statistic/refererKeyword/list";
	}

	@RequestMapping("/statistic_flow/v_init.do")
	public String flowInitView(HttpServletRequest request, Integer pageNo,
			ModelMap model) {
		return "statistic/init";
	}

	@RequestMapping("/statistic_flow/o_init.do")
	public String flowInit(HttpServletRequest request, Date startDate,
			Date endDate, Integer pageNo, ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		cmsStatisticSvc.flowInit(siteId, startDate, endDate);
		model.addAttribute("message", "global.success");
		return "statistic/init";
	}

	private CmsStatisticModel getStatisticModel(String queryModel) {
		if (!StringUtils.isBlank(queryModel)) {
			return CmsStatisticModel.valueOf(queryModel);
		}
		return CmsStatisticModel.year;
	}

	private void putCommonData(CmsStatisticModel statisticModel,
			List<CmsStatistic> list, Integer year, Integer month, Integer day,
			ModelMap model) {
		model.addAttribute("list", list);
		model.addAttribute("total", getTotal(list));
		model.addAttribute("statisticModel", statisticModel.name());
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
	}

	private Long getTotal(List<CmsStatistic> list) {
		return list.size() > 0 ? list.iterator().next().getTotal() : 0L;
	}

	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsStatisticSvc cmsStatisticSvc;
}
