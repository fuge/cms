package foo.cms.action.admin;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.statistic.CmsStatisticSvc;
import foo.cms.web.AdminContextInterceptor;
import foo.cms.web.CmsUtils;

@Controller
public class WelcomeAct {
	@RequestMapping("/index.do")
	public String index() {
		return "index";
	}

	@RequestMapping("/top.do")
	public String top(HttpServletRequest request, ModelMap model) {
		// 需要获得站点列表
		List<CmsSite> siteList = cmsSiteMng.getList();
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		model.addAttribute("siteList", siteList);
		model.addAttribute("site", site);
		model.addAttribute("siteParam", AdminContextInterceptor.SITE_PARAM);
		model.addAttribute("user", user);
		return "top";
	}

	@RequestMapping("/main.do")
	public String main() {
		return "main";
	}

	@RequestMapping("/left.do")
	public String left() {
		return "left";
	}

	@RequestMapping("/right.do")
	public String right(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String version = site.getConfig().getVersion();
		Properties props = System.getProperties();
		Runtime runtime = Runtime.getRuntime();
		long freeMemoery = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long usedMemory = totalMemory - freeMemoery;
		long maxMemory = runtime.maxMemory();
		long useableMemory = maxMemory - totalMemory + freeMemoery;
		model.addAttribute("props", props);
		model.addAttribute("freeMemoery", freeMemoery);
		model.addAttribute("totalMemory", totalMemory);
		model.addAttribute("usedMemory", usedMemory);
		model.addAttribute("maxMemory", maxMemory);
		model.addAttribute("useableMemory", useableMemory);
		model.addAttribute("version", version);
		model.addAttribute("user", user);
		model.addAttribute("flowMap", cmsStatisticSvc.getWelcomeSiteFlowData(site.getId()));
		return "right";
	}

	@Autowired
	private CmsSiteMng cmsSiteMng;
	@Autowired
	private CmsStatisticSvc cmsStatisticSvc;
}
