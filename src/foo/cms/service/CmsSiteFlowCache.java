package foo.cms.service;

import foo.cms.entity.main.CmsSite;

/**
 * 站点流量缓存接口
 */
public interface CmsSiteFlowCache {
	public void flow(CmsSite site, String ip, String sessionId, String page, String referrer);
}
