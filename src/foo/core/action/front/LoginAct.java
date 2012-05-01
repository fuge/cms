package foo.core.action.front;

import static foo.core.manager.AuthenticationMng.AUTH_KEY;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import foo.common.security.BadCredentialsException;
import foo.common.security.UsernameNotFoundException;
import foo.common.web.RequestUtils;
import foo.common.web.session.SessionProvider;
import foo.core.entity.Authentication;
import foo.core.manager.AuthenticationMng;
import foo.core.web.WebErrors;

/**
 * 统一认证中心Action
 * 
 * 统一认证中心由两个方面组成。
 * <ul>
 * <li>
 * 用户信息来源。 可以有多种来源，最合适的来源是LDAP，也可以是数据库。本认证中心为实现更实用于互联网的、更轻量级的单点登录，使用数据库作为用户信息来源。
 * 只要在同一数据库实例下，即可访问到用户信息。各系统可以自行实现注册、修改密码、禁用等功能。如果是用一体系下的应用，这些功能都提供了统一接口。
 * <li>
 * 用户认证信息。用户登录成功后，需要保存登录信息，最合适的保存技术是memcached，也可以是其他集群缓存或数据库。本认证中心使用数据库保存登录信息。
 * 只要在同一数据库实例下，即可访问。各系统可以自行实现退出登录。
 * </ul>
 * 
 * @author liufang
 * 
 */
@Controller
public class LoginAct {
	public static final String PROCESS_URL = "processUrl";
	public static final String RETURN_URL = "returnUrl";
	public static final String MESSAGE = "message";

	public static final String LOGIN_INPUT = "/WEB-INF/t/jeecore/login.html";
	public static final String LOGIN_SUCCESS = "/WEB-INF/t/jeecore/login_success.html";

	/**
	 * 统一登录入口
	 * 
	 * 入口有三种作用
	 * <ul>
	 * <li>统一登录中心测试用。直接输入登录地址，登录成功后返回登录成功界面。如果processUrl为空，则认为使用该功能。</li>
	 * <li>统一登录。其他系统使用该入口统一登录。使用此功能proseccUrl不能为空。</li>
	 * <li>单点登录。多系统通过该入口实现单点登录。如果session中AUTH_KEY存在，则直接重定向至proseccUrl。</li>
	 * </ul>
	 * 
	 * @param processUrl
	 *            登录成功后的处理地址。登录成功后即重定向到该页面，并将returnUrl、auth_key作为参数。
	 * @param returnUrl
	 *            登录成功，并处理后，返回到该地址。
	 * @param message
	 *            登录是提示的信息，比如：“您需要登录后才能继续刚才的操作”，该信息必须用UTF-8编码进行URLEncode。
	 * @param request
	 * @param model
	 * @return 重定向至processUrl，如prosessUrl不存在，则返回登录成功界面。
	 */
	@RequestMapping(value = "/login.jspx", method = RequestMethod.GET)
	public String input(HttpServletRequest request, ModelMap model) {
		String processUrl = RequestUtils.getQueryParam(request, PROCESS_URL);
		String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);
		String message = RequestUtils.getQueryParam(request, MESSAGE);
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId != null) {
			// 存在认证ID
			Authentication auth = authMng.retrieve(authId);
			// 存在认证信息，且未过期
			if (auth != null) {
				String view = getView(processUrl, returnUrl, auth.getId());
				if (view != null) {
					return view;
				} else {
					model.addAttribute("auth", auth);
					return LOGIN_SUCCESS;
				}
			}
		}
		if (!StringUtils.isBlank(processUrl)) {
			model.addAttribute(PROCESS_URL, processUrl);
		}
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute(RETURN_URL, returnUrl);
		}
		if (!StringUtils.isBlank(message)) {
			model.addAttribute(MESSAGE, message);
		}
		return LOGIN_INPUT;
	}

	@RequestMapping(value = "/login.jspx", method = RequestMethod.POST)
	public String submit(String username, String password, String processUrl,
			String returnUrl, String message, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		WebErrors errors = validateSubmit(username, password, request);
		if (!errors.hasErrors()) {
			try {
				Authentication auth = authMng.login(username, password,
						RequestUtils.getIpAddr(request), request, response,
						session);
				String view = getView(processUrl, returnUrl, auth.getId());
				if (view != null) {
					return view;
				} else {
					model.addAttribute("auth", auth);
					return LOGIN_SUCCESS;
				}
			} catch (UsernameNotFoundException e) {
				errors.addErrorString(e.getMessage());
			} catch (BadCredentialsException e) {
				errors.addErrorString(e.getMessage());
			}
		}
		errors.toModel(model);
		if (!StringUtils.isBlank(processUrl)) {
			model.addAttribute(PROCESS_URL, processUrl);
		}
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute(RETURN_URL, returnUrl);
		}
		if (!StringUtils.isBlank(message)) {
			model.addAttribute(MESSAGE, message);
		}
		return LOGIN_INPUT;

	}

	@RequestMapping(value = "/logout.jspx")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId != null) {
			authMng.deleteById(authId);
			session.logout(request, response);
		}
		String processUrl = RequestUtils.getQueryParam(request, PROCESS_URL);
		String returnUrl = RequestUtils.getQueryParam(request, RETURN_URL);
		String view = getView(processUrl, returnUrl, authId);
		if (view != null) {
			return view;
		} else {
			return "redirect:login.jspx";
		}
	}

	/**
	 * 获得地址
	 * 
	 * @param processUrl
	 * @param returnUrl
	 * @param authId
	 * @return
	 */
	private String getView(String processUrl, String returnUrl, String authId) {
		if (!StringUtils.isBlank(processUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(processUrl).append("?").append(AUTH_KEY).append("=")
					.append(authId);
			if (!StringUtils.isBlank(returnUrl)) {
				sb.append("&").append(RETURN_URL).append("=").append(returnUrl);
			}
			return sb.toString();
		} else if (!StringUtils.isBlank(returnUrl)) {
			StringBuilder sb = new StringBuilder("redirect:");
			sb.append(returnUrl);
			if (!StringUtils.isBlank(authId)) {
				sb.append("?").append(AUTH_KEY).append("=").append(authId);
			}
			return sb.toString();
		} else {
			return null;
		}
	}

	private WebErrors validateSubmit(String username, String password,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifOutOfLength(username, "username", 3, 100)) {
			return errors;
		}
		if (errors.ifOutOfLength(password, "password", 3, 32)) {
			return errors;
		}
		return errors;
	}

	@Autowired
	private AuthenticationMng authMng;
	@Autowired
	private SessionProvider session;

}
