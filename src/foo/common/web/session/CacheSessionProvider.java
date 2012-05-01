package foo.common.web.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import foo.common.web.Constants;
import foo.common.web.RequestUtils;
import foo.common.web.session.cache.SessionCache;
import foo.common.web.session.id.SessionIdGenerator;

/**
 * 使用Memcached分布式缓存实现Session
 * 
 * @author liufang
 * 
 */
public class CacheSessionProvider implements SessionProvider, InitializingBean {
	public static final String CURRENT_SESSION = "_current_session";
	public static final String CURRENT_SESSION_ID = "_current_session_id";

	@SuppressWarnings("unchecked")
	public Serializable getAttribute(HttpServletRequest request, String name) {
		// 为了避免同一个请求多次获取缓存session，所以将缓存session保存至request中。
		Map<String, Serializable> session = (Map<String, Serializable>) request
				.getAttribute(CURRENT_SESSION);
		if (session != null) {
			return session.get(name);
		}

		String root = (String) request.getAttribute(CURRENT_SESSION_ID);
		if (root == null) {
			root = RequestUtils.getRequestedSessionId(request);
		}
		if (StringUtils.isBlank(root)) {
			request.setAttribute(CURRENT_SESSION,
					new HashMap<String, Serializable>());
			return null;
		}
		session = sessionCache.getSession(root);
		if (session != null) {
			request.setAttribute(CURRENT_SESSION_ID, root);
			request.setAttribute(CURRENT_SESSION, session);
			return session.get(name);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Serializable value) {
		Map<String, Serializable> session = (Map<String, Serializable>) request
				.getAttribute(CURRENT_SESSION);
		String root;
		if (session == null) {
			root = RequestUtils.getRequestedSessionId(request);
			if (root != null && root.length() == 32) {
				session = sessionCache.getSession(root);
			}
			if (session == null) {
				session = new HashMap<String, Serializable>();
				do {
					root = sessionIdGenerator.get();
				} while (sessionCache.exist(root));
				response.addCookie(createCookie(request, root));
			}
			request.setAttribute(CURRENT_SESSION, session);
			request.setAttribute(CURRENT_SESSION_ID, root);
		} else {
			root = (String) request.getAttribute(CURRENT_SESSION_ID);
			if (root == null) {
				do {
					root = sessionIdGenerator.get();
				} while (sessionCache.exist(root));
				response.addCookie(createCookie(request, root));
				request.setAttribute(CURRENT_SESSION_ID, root);
			}
		}
		session.put(name, value);
		sessionCache.setSession(root, session, sessionTimeout);
	}

	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		String root = (String) request.getAttribute(CURRENT_SESSION_ID);
		if (root != null) {
			return root;
		}
		root = RequestUtils.getRequestedSessionId(request);
		if (root == null || root.length() != 32 || !sessionCache.exist(root)) {
			do {
				root = sessionIdGenerator.get();
			} while (sessionCache.exist(root));
			sessionCache.setSession(root, new HashMap<String, Serializable>(),
					sessionTimeout);
			response.addCookie(createCookie(request, root));
		}
		request.setAttribute(CURRENT_SESSION_ID, root);
		return root;
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.removeAttribute(CURRENT_SESSION);
		request.removeAttribute(CURRENT_SESSION_ID);
		String root = RequestUtils.getRequestedSessionId(request);
		if (!StringUtils.isBlank(root)) {
			sessionCache.clear(root);
			Cookie cookie = createCookie(request, null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	private Cookie createCookie(HttpServletRequest request, String value) {
		Cookie cookie = new Cookie(Constants.JSESSION_COOKIE, value);
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		return cookie;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(sessionCache);
		Assert.notNull(sessionIdGenerator);
	}

	private SessionCache sessionCache;
	private SessionIdGenerator sessionIdGenerator;
	private int sessionTimeout = 30;

	public void setSessionCache(SessionCache sessionCache) {
		this.sessionCache = sessionCache;
	}

	/**
	 * 设置session过期时间
	 * 
	 * @param sessionTimeout
	 *            分钟
	 */
	public void setSessionTimeout(int sessionTimeout) {
		Assert.isTrue(sessionTimeout > 0);
		this.sessionTimeout = sessionTimeout;
	}

	public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
		this.sessionIdGenerator = sessionIdGenerator;
	}
}
