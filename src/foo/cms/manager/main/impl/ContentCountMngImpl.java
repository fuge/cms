package foo.cms.manager.main.impl;

import java.util.Calendar;
import java.util.Date;

import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.ContentCountDao;
import foo.cms.entity.main.CmsConfig;
import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentCount;
import foo.cms.manager.main.CmsConfigMng;
import foo.cms.manager.main.ContentCountMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class ContentCountMngImpl implements ContentCountMng {
	public int contentUp(Integer id) {
		ContentCount c = dao.findById(id);
		if (c == null) {
			return 0;
		}
		int count = c.getUps() + 1;
		c.setUps(count);
		c.setUpsMonth(c.getUpsMonth() + 1);
		c.setUpsWeek((short) (c.getUpsWeek() + 1));
		c.setUpsDay((short) (c.getUpsDay() + 1));
		return count;
	}

	public int contentDown(Integer id) {
		ContentCount c = dao.findById(id);
		if (c == null) {
			return 0;
		}
		int count = c.getDowns() + 1;
		c.setDowns(count);
		return count;
	}

	public void downloadCount(Integer contentId) {
		ContentCount c = findById(contentId);
		c.setDownloads(c.getDownloads() + 1);
		c.setDownloadsMonth(c.getDownloadsMonth() + 1);
		c.setDownloadsWeek((short) (c.getCommentsWeek() + 1));
		c.setDownloadsDay((short) (c.getDownloadsDay() + 1));
	}

	public void commentCount(Integer contentId) {
		ContentCount c = findById(contentId);
		c.setComments(c.getComments() + 1);
		c.setCommentsMonth(c.getCommentsMonth() + 1);
		c.setCommentsWeek((short) (c.getCommentsWeek() + 1));
		c.setCommentsDay((short) (c.getCommentsDay() + 1));
	}

	public int freshCacheToDB(Ehcache cache) {
		CmsConfig config = cmsConfigMng.get();
		clearCount(config);
		int count = dao.freshCacheToDB(cache);
		copyCount(config);
		return count;
	}

	private int clearCount(CmsConfig config) {
		Calendar curr = Calendar.getInstance();
		Calendar last = Calendar.getInstance();
		last.setTime(config.getCountClearTime());
		int currDay = curr.get(Calendar.DAY_OF_YEAR);
		int lastDay = last.get(Calendar.DAY_OF_YEAR);
		if (currDay != lastDay) {
			int currWeek = curr.get(Calendar.WEEK_OF_YEAR);
			int lastWeek = last.get(Calendar.WEEK_OF_YEAR);
			int currMonth = curr.get(Calendar.MONTH);
			int lastMonth = last.get(Calendar.MONTH);
			cmsConfigMng.updateCountClearTime(curr.getTime());
			return dao.clearCount(currWeek != lastWeek, currMonth != lastMonth);
		} else {
			return 0;
		}
	}

	private int copyCount(CmsConfig config) {
		long curr = System.currentTimeMillis();
		long last = config.getCountCopyTime().getTime();
		if (curr > interval + last) {
			cmsConfigMng.updateCountCopyTime(new Date(curr));
			return dao.copyCount();
		} else {
			return 0;
		}
	}

	@Transactional(readOnly = true)
	public ContentCount findById(Integer id) {
		ContentCount entity = dao.findById(id);
		return entity;
	}

	public ContentCount save(ContentCount count, Content content) {
		count.setContent(content);
		count.init();
		dao.save(count);
		content.setContentCount(count);
		return count;
	}

	public ContentCount update(ContentCount bean) {
		Updater<ContentCount> updater = new Updater<ContentCount>(bean);
		ContentCount entity = dao.updateByUpdater(updater);
		return entity;
	}

	// 间隔时间
	private int interval = 60 * 60 * 1000; // 一小时
	private CmsConfigMng cmsConfigMng;
	private ContentCountDao dao;

	/**
	 * 设置拷贝间隔时间。默认一小时。
	 * 
	 * @param interval
	 *            单位分钟
	 */
	public void setInterval(int interval) {
		this.interval = interval * 60 * 1000;
	}

	@Autowired
	public void setCmsConfigMng(CmsConfigMng cmsConfigMng) {
		this.cmsConfigMng = cmsConfigMng;
	}

	@Autowired
	public void setDao(ContentCountDao dao) {
		this.dao = dao;
	}

}