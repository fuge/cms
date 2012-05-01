package foo.cms.service;

import java.sql.Timestamp;
import java.util.Date;

import static foo.common.util.ParseURLKeyword.getKeyword;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import foo.cms.entity.assist.CmsSiteFlow;
import foo.cms.entity.main.CmsSite;
import foo.cms.manager.assist.CmsSiteFlowMng;
import foo.cms.statistic.FlowBean;
import foo.common.ipseek.IPSeeker;
import foo.common.util.DateFormatUtils;

@Service
public class CmsSiteFlowCacheImpl implements CmsSiteFlowCache, DisposableBean {
	private Logger log = LoggerFactory.getLogger(CmsSiteFlowCacheImpl.class);

	public void flow(CmsSite site, String ip, String sessionId, String page, String referer) {
		CmsSiteFlow cmsSiteFlow = create(site, ip, sessionId, page, referer);
		FlowBean flowBean = new FlowBean(cmsSiteFlow.getAccessDate(), sessionId, page);
		if(cache.get(flowBean) == null){
			CmsSiteFlow bean = null;
			try {
				bean = manager.findUniqueByProperties(site.getId(), cmsSiteFlow.getAccessDate(),
						sessionId, page);
			} catch (HibernateException e) {
				cache.remove(flowBean);
			}
			if(bean!=null){
				cache.put(new Element(flowBean,bean));
			}else{
				cache.put(new Element(flowBean, cmsSiteFlow));
			}
		}
		refreshToDB();
	}

	private CmsSiteFlow create(CmsSite site, String ip, String sessionId,
			String page, String referer) {
		CmsSiteFlow bean = new CmsSiteFlow();
		Date now = new Timestamp(System.currentTimeMillis());
		bean.setSite(site);
		bean.setAccessIp(ip);
		bean.setAccessPage(page);
		bean.setAccessTime(now);
		bean.setAccessDate(DateFormatUtils.formatDate(now));
		bean.setSessionId(sessionId);
		bean.setRefererPage(referer);
		bean.setRefererWebSite(getRefererWebSite(referer));
		bean.setArea(ipSeeker.getIPLocation(ip).getCountry());
		bean.setRefererKeyword(getKeyword(referer));
		return bean;
	}
	
	private void refreshToDB() {
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval) {
			refreshTime = time;
			int count = manager.freshCacheToDB(cache);
			// 清除缓存
			cache.removeAll();
			log.info("refresh cache flows to DB: {}", count);
		}
	}

	/**
	 * 销毁BEAN时，缓存入库。
	 */
	public void destroy() throws Exception {
		int count = manager.freshCacheToDB(cache);
		log.info("Bean destroy.refresh cache flows to DB: {}", count);
	}

	
	private static String getRefererWebSite(String referer){
		if(StringUtils.isBlank(referer)){
			return "";
		}
		int start = 0, i = 0, count = 3;
		while (i < count && start != -1) {
			start = referer.indexOf('/', start + 1);
			i++;
		}
		if (start <= 0) {
			throw new IllegalStateException(
					"referer website uri not like 'http://.../...' pattern: "
							+ referer);
		}
		return referer.substring(0, start);
	}
	
	// 间隔时间
	private int interval = 1 * 30 * 1000; // 30秒
	// 最后刷新时间
	private long refreshTime = System.currentTimeMillis();
	
	/**
	 * 刷新间隔时间
	 * 
	 * @param interval
	 *            单位秒
	 */
	public void setInterval(int interval) {
		this.interval = interval * 1000;
	}

	@Autowired
	private CmsSiteFlowMng manager;
	@Autowired
	private IPSeeker ipSeeker;

	@Autowired 
	@Qualifier("cmsSiteFlow")
	private Ehcache cache;

}
