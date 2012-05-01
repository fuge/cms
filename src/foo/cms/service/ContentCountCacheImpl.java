package foo.cms.service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import foo.cms.entity.main.ContentCount;
import foo.cms.manager.main.ContentCountMng;

/**
 * 内容计数器缓存实现
 * 
 * @author liufang
 * 
 */
@Service
public class ContentCountCacheImpl implements ContentCountCache, DisposableBean {
	private Logger log = LoggerFactory.getLogger(ContentCountCacheImpl.class);

	/**
	 * @see ContentCountCache#viewAndGet(Integer)
	 */
	public int[] viewAndGet(Integer id) {
		ContentCount count = contentCountMng.findById(id);
		if (count == null) {
			return null;
		}
		Element e = cache.get(id);
		Integer views;
		if (e != null) {
			views = (Integer) e.getValue() + 1;
		} else {
			views = 1;
		}
		cache.put(new Element(id, views));
		refreshToDB();
		return new int[] { views + count.getViews(), count.getComments(),
				count.getDownloads(), count.getUps(), count.getDowns() };
	}

	private void refreshToDB() {
		long time = System.currentTimeMillis();
		if (time > refreshTime + interval) {
			refreshTime = time;
			int count = contentCountMng.freshCacheToDB(cache);
			// 清除缓存
			cache.removeAll();
			log.info("refresh cache views to DB: {}", count);
		}
	}

	/**
	 * 销毁BEAN时，缓存入库。
	 */
	public void destroy() throws Exception {
		int count = contentCountMng.freshCacheToDB(cache);
		log.info("Bean destroy.refresh cache views to DB: {}", count);
	}

	// 间隔时间
	private int interval = 10 * 60 * 1000; // 10分钟
	// 最后刷新时间
	private long refreshTime = System.currentTimeMillis();

	private ContentCountMng contentCountMng;

	private Ehcache cache;

	/**
	 * 刷新间隔时间
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
	}

	@Autowired
	public void setContentCountMng(ContentCountMng contentCountMng) {
		this.contentCountMng = contentCountMng;
	}

	@Autowired
	public void setCache(@Qualifier("contentCount") Ehcache cache) {
		this.cache = cache;
	}

}
