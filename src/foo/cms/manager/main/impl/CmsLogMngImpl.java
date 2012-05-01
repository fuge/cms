package foo.cms.manager.main.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UrlPathHelper;

import foo.cms.dao.main.CmsLogDao;
import foo.cms.entity.main.CmsLog;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.web.CmsUtils;
import foo.common.page.Pagination;
import foo.common.web.RequestUtils;
import foo.common.web.springmvc.MessageResolver;

@Service
@Transactional
public class CmsLogMngImpl implements CmsLogMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer category, Integer siteId,
			String username, String title, String ip, int pageNo, int pageSize) {
		Pagination page;
		if (StringUtils.isBlank(username)) {
			page = dao.getPage(category, siteId, null, title, ip, pageNo,
					pageSize);
		} else {
			CmsUser user = cmsUserMng.findByUsername(username);
			if (user != null) {
				page = dao.getPage(category, siteId, user.getId(), title, ip,
						pageNo, pageSize);
			} else {
				page = new Pagination(1, pageSize, 0, new ArrayList<CmsLog>(0));
			}
		}
		return page;
	}

	@Transactional(readOnly = true)
	public CmsLog findById(Integer id) {
		CmsLog entity = dao.findById(id);
		return entity;
	}

	public CmsLog save(Integer category, CmsSite site, CmsUser user,
			String url, String ip, Date date, String title, String content) {
		CmsLog log = new CmsLog();
		log.setSite(site);
		log.setUser(user);
		log.setCategory(category);
		log.setIp(ip);
		log.setTime(date);
		log.setUrl(url);
		log.setTitle(title);
		log.setContent(content);
		save(log);
		return log;
	}

	public CmsLog loginSuccess(HttpServletRequest request, CmsUser user,
			String title) {
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		Date date = new Date();
		CmsLog log = save(CmsLog.LOGIN_SUCCESS, null, user, uri, ip, date,
				MessageResolver.getMessage(request, title), null);
		return log;
	}

	public CmsLog loginFailure(HttpServletRequest request, String title,
			String content) {
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		Date date = new Date();
		CmsLog log = save(CmsLog.LOGIN_FAILURE, null, null, uri, ip, date,
				MessageResolver.getMessage(request, title), content);
		return log;
	}

	public CmsLog operating(HttpServletRequest request, String title,
			String content) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String ip = RequestUtils.getIpAddr(request);
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		Date date = new Date();
		CmsLog log = save(CmsLog.OPERATING, site, user, uri, ip, date,
				MessageResolver.getMessage(request, title), content);
		return log;
	}

	public CmsLog save(CmsLog bean) {
		dao.save(bean);
		return bean;
	}

	public int deleteBatch(Integer category, Integer siteId, Integer days) {
		Date date = null;
		if (days != null && days > 0) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -days);
			date = cal.getTime();
		}
		return dao.deleteBatch(category, siteId, date);
	}

	public CmsLog deleteById(Integer id) {
		CmsLog bean = dao.deleteById(id);
		return bean;
	}

	public CmsLog[] deleteByIds(Integer[] ids) {
		CmsLog[] beans = new CmsLog[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsUserMng cmsUserMng;
	private CmsLogDao dao;

	@Autowired
	public void setCmsUserMng(CmsUserMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}

	@Autowired
	public void setDao(CmsLogDao dao) {
		this.dao = dao;
	}

}