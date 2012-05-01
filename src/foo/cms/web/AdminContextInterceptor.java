package foo.cms.web;

import static foo.common.web.Constants.MESSAGE;
import static foo.core.action.front.LoginAct.PROCESS_URL;
import static foo.core.action.front.LoginAct.RETURN_URL;

import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.manager.main.CmsUserMng;
import foo.common.web.CookieUtils;
import foo.common.web.session.SessionProvider;
import foo.common.web.springmvc.MessageResolver;
import foo.core.manager.AuthenticationMng;

/**
 * CMS上下文信息拦截器
 * 
 * 包括登录信息、权限信息、站点信息
 * 
 * @author liufang
 * 
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = Logger
			.getLogger(AdminContextInterceptor.class);
	public static final String SITE_PARAM = "_site_id_param";
	public static final String SITE_COOKIE = "_site_id_cookie";
	public static final String PERMISSION_MODEL = "_permission_key";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 获得站点
		CmsSite site = getSite(request, response);
		CmsUtils.setSite(request, site);
		// Site加入线程变量
		CmsThreadVariable.setSite(site);

		// 获得用户
		CmsUser user = null;
		if (adminId != null) {
			// 指定管理员（开发状态）
			user = cmsUserMng.findById(adminId);
			if (user == null) {
				throw new IllegalStateException("User ID=" + adminId
						+ " not found!");
			}
		} else {
			// 正常状态
			Integer userId = authMng
					.retrieveUserIdFromSession(session, request);
			if (userId != null) {
				user = cmsUserMng.findById(userId);
			}
		}
		// 此时用户可以为null
		CmsUtils.setUser(request, user);
		// User加入线程变量
		CmsThreadVariable.setUser(user);
		
		// 设置后台页面目录名(cms_admin)
		CmsUtils.setAdminPageDir(request);

		String uri = getURI(request);
		// 不在验证的范围内
		if (exclude(uri)) {
			return true;
		}
		// 用户为null跳转到登陆页面
		if (user == null) {
			response.sendRedirect(getLoginUrl(request));
			return false;
		}
		// 用户不是管理员，提示无权限。
		if (!user.getAdmin()) {
			request.setAttribute(MESSAGE, MessageResolver.getMessage(request,
					"login.notAdmin"));
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		// 不属于该站点的管理员，提示无权限。
		if (!user.getSites().contains(site)) {
			request.setAttribute(MESSAGE, MessageResolver.getMessage(request,
					"login.notInSite"));
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		boolean viewonly = user.getViewonlyAdmin();
		// 没有访问权限，提示无权限。
		if (auth && !user.isSuper()
				&& !permistionPass(uri, user.getPerms(), viewonly)) {
			request.setAttribute(MESSAGE, MessageResolver.getMessage(request,
					"login.notPermission"));
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		CmsUser user = CmsUtils.getUser(request);
		// 不控制权限时perm为null，PermistionDirective标签将以此作为依据不处理权限问题。
		if (auth && user != null && !user.isSuper() && mav != null
				&& mav.getModelMap() != null && mav.getViewName() != null
				&& !mav.getViewName().startsWith("redirect:")) {
			mav.getModelMap().addAttribute(PERMISSION_MODEL, user.getPerms());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// Sevlet容器有可能使用线程池，所以必须手动清空线程变量。
		CmsThreadVariable.removeUser();
		CmsThreadVariable.removeSite();
	}

	private String getLoginUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(loginUrl).append("?");
		buff.append(RETURN_URL).append("=").append(returnUrl);
		if (!StringUtils.isBlank(processUrl)) {
			buff.append("&").append(PROCESS_URL).append("=").append(
					getProcessUrl(request));
		}
		return buff.toString();
	}

	private String getProcessUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(processUrl);
		return buff.toString();
	}

	/**
	 * 按参数、cookie、域名、默认。
	 * 
	 * @param request
	 * @return 不会返回null，如果站点不存在，则抛出异常。
	 */
	private CmsSite getSite(HttpServletRequest request,
			HttpServletResponse response) {
		CmsSite site = getByParams(request, response);
		if (site == null) {
			site = getByCookie(request);
		}
		if (site == null) {
			site = getByDomain(request);
		}
		if (site == null) {
			site = getByDefault();
		}
		if (site == null) {
			throw new RuntimeException("cannot get site!");
		} else {
			return site;
		}
	}

	private CmsSite getByParams(HttpServletRequest request,
			HttpServletResponse response) {
		String p = request.getParameter(SITE_PARAM);
		if (!StringUtils.isBlank(p)) {
			try {
				Integer siteId = Integer.parseInt(p);
				CmsSite site = cmsSiteMng.findById(siteId);
				if (site != null) {
					// 若使用参数选择站点，则应该把站点保存至cookie中才好。
					CookieUtils.addCookie(request, response, SITE_COOKIE, site
							.getId().toString(), null, null);
					return site;
				}
			} catch (NumberFormatException e) {
				log.warn("param site id format exception", e);
			}
		}
		return null;
	}

	private CmsSite getByCookie(HttpServletRequest request) {
		Cookie cookie = CookieUtils.getCookie(request, SITE_COOKIE);
		if (cookie != null) {
			String v = cookie.getValue();
			if (!StringUtils.isBlank(v)) {
				try {
					Integer siteId = Integer.parseInt(v);
					return cmsSiteMng.findById(siteId);
				} catch (NumberFormatException e) {
					log.warn("cookie site id format exception", e);
				}
			}
		}
		return null;
	}

	private CmsSite getByDomain(HttpServletRequest request) {
		String domain = request.getServerName();
		if (!StringUtils.isBlank(domain)) {
			return cmsSiteMng.findByDomain(domain, true);
		}
		return null;
	}

	private CmsSite getByDefault() {
		List<CmsSite> list = cmsSiteMng.getListFromCache();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean permistionPass(String uri, Set<String> perms,
			boolean viewOnly) {
		String u = null;
		int i;
		for (String perm : perms) {
			if (uri.startsWith(perm)) {
				// 只读管理员
				if (viewOnly) {
					// 获得最后一个 '/' 的URI地址。
					i = uri.lastIndexOf("/");
					if (i == -1) {
						throw new RuntimeException("uri must start width '/':"
								+ uri);
					}
					u = uri.substring(i + 1);
					// 操作型地址被禁止
					if (u.startsWith("o_")) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 获得第三个路径分隔符的位置
	 * 
	 * @param request
	 * @throws IllegalStateException
	 *             访问路径错误，没有三(四)个'/'
	 */
	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		int start = 0, i = 0, count = 2;
		if (!StringUtils.isBlank(ctxPath)) {
			count++;
		}
		while (i < count && start != -1) {
			start = uri.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException(
					"admin access path not like '/jeeadmin/jspgou/...' pattern: "
							+ uri);
		}
		return uri.substring(start);
	}

	private SessionProvider session;
	private AuthenticationMng authMng;
	private CmsSiteMng cmsSiteMng;
	private CmsUserMng cmsUserMng;
	private Integer adminId;
	private boolean auth = true;
	private String[] excludeUrls;

	private String loginUrl;
	private String processUrl;
	private String returnUrl;

	@Autowired
	public void setSession(SessionProvider session) {
		this.session = session;
	}

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setCmsUserMng(CmsUserMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}

	@Autowired
	public void setAuthMng(AuthenticationMng authMng) {
		this.authMng = authMng;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

}