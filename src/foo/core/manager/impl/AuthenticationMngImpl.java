package foo.core.manager.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.common.page.Pagination;
import foo.common.security.BadCredentialsException;
import foo.common.security.UsernameNotFoundException;
import foo.common.web.session.SessionProvider;
import foo.core.dao.AuthenticationDao;
import foo.core.entity.Authentication;
import foo.core.entity.UnifiedUser;
import foo.core.manager.AuthenticationMng;
import foo.core.manager.UnifiedUserMng;

@Service
@Transactional
public class AuthenticationMngImpl implements AuthenticationMng {
	private Logger log = LoggerFactory.getLogger(AuthenticationMngImpl.class);

	public Authentication login(String username, String password, String ip,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session) throws UsernameNotFoundException,
			BadCredentialsException {
		UnifiedUser user = unifiedUserMng.login(username, password, ip);
		Authentication auth = new Authentication();
		auth.setUid(user.getId());
		auth.setUsername(user.getUsername());
		auth.setEmail(user.getEmail());
		auth.setLoginIp(ip);
		save(auth);
		session.setAttribute(request, response, AUTH_KEY, auth.getId());
		return auth;
	}
	
	public Authentication activeLogin(UnifiedUser user, String ip,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session) {
		unifiedUserMng.activeLogin(user, ip);
		Authentication auth = new Authentication();
		auth.setUid(user.getId());
		auth.setUsername(user.getUsername());
		auth.setEmail(user.getEmail());
		auth.setLoginIp(ip);
		save(auth);
		session.setAttribute(request, response, AUTH_KEY, auth.getId());
		return auth;
	}

	public Authentication retrieve(String authId) {
		long current = System.currentTimeMillis();
		// 是否刷新数据库
		if (refreshTime < current) {
			refreshTime = getNextRefreshTime(current, interval);
			int count = dao.deleteExpire(new Date(current - timeout));
			log.info("refresh Authentication, delete count: {}", count);
		}
		Authentication auth = findById(authId);
		if (auth != null && auth.getUpdateTime().getTime() + timeout > current) {
			auth.setUpdateTime(new Timestamp(current));
			return auth;
		} else {
			return null;
		}
	}

	public Integer retrieveUserIdFromSession(SessionProvider session,
			HttpServletRequest request) {
		String authId = (String) session.getAttribute(request, AUTH_KEY);
		if (authId == null) {
			return null;
		}
		Authentication auth = retrieve(authId);
		if (auth == null) {
			return null;
		}
		return auth.getUid();
	}

	public void storeAuthIdToSession(SessionProvider session,
			HttpServletRequest request, HttpServletResponse response,
			String authId) {
		session.setAttribute(request, response, AUTH_KEY, authId);
	}

	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public Authentication findById(String id) {
		Authentication entity = dao.findById(id);
		return entity;
	}

	public Authentication save(Authentication bean) {
		bean.setId(StringUtils.remove(UUID.randomUUID().toString(), '-'));
		bean.init();
		dao.save(bean);
		return bean;
	}

	public Authentication deleteById(String id) {
		Authentication bean = dao.deleteById(id);
		return bean;
	}

	public Authentication[] deleteByIds(String[] ids) {
		Authentication[] beans = new Authentication[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	// 过期时间
	private int timeout = 30 * 60 * 1000; // 30分钟

	// 间隔时间
	private int interval = 4 * 60 * 60 * 1000; // 4小时

	// 刷新时间。
	private long refreshTime = getNextRefreshTime(System.currentTimeMillis(),
			this.interval);

	private UnifiedUserMng unifiedUserMng;
	private AuthenticationDao dao;

	@Autowired
	public void setDao(AuthenticationDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setUserMng(UnifiedUserMng unifiedUserMng) {
		this.unifiedUserMng = unifiedUserMng;
	}

	/**
	 * 设置认证过期时间。默认30分钟。
	 * 
	 * @param timeout
	 *            单位分钟
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout * 60 * 1000;
	}

	/**
	 * 设置刷新数据库时间。默认4小时。
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
		this.refreshTime = getNextRefreshTime(System.currentTimeMillis(),
				this.interval);
	}

	/**
	 * 获得下一个刷新时间。
	 * 
	 * 
	 * 
	 * @param current
	 * @param interval
	 * @return 随机间隔时间
	 */
	private long getNextRefreshTime(long current, int interval) {
		return current + interval;
		// 为了防止多个应用同时刷新，间隔时间=interval+RandomUtils.nextInt(interval/4);
		// return current + interval + RandomUtils.nextInt(interval / 4);
	}
}