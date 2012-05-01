package foo.cms.manager.assist.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.assist.CmsVoteTopicDao;
import foo.cms.entity.assist.CmsVoteItem;
import foo.cms.entity.assist.CmsVoteTopic;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.assist.CmsVoteItemMng;
import foo.cms.manager.assist.CmsVoteRecordMng;
import foo.cms.manager.assist.CmsVoteTopicMng;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;

@Service
@Transactional
public class CmsVoteTopicMngImpl implements CmsVoteTopicMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public CmsVoteTopic findById(Integer id) {
		CmsVoteTopic entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public CmsVoteTopic getDefTopic(Integer siteId) {
		return dao.getDefTopic(siteId);
	}

	public CmsVoteTopic save(CmsVoteTopic bean, List<CmsVoteItem> items) {
		int totalCount = 0;
		for (CmsVoteItem item : items) {
			if (item.getVoteCount() != null) {
				totalCount += item.getVoteCount();
			}
		}
		bean.setTotalCount(totalCount);
		bean.init();
		dao.save(bean);
		cmsVoteItemMng.save(items, bean);
		return bean;
	}

	public CmsVoteTopic update(CmsVoteTopic bean, Collection<CmsVoteItem> items) {
		Updater<CmsVoteTopic> updater = new Updater<CmsVoteTopic>(bean);
		updater.include(CmsVoteTopic.PROP_START_TIME);
		updater.include(CmsVoteTopic.PROP_END_TIME);
		bean = dao.updateByUpdater(updater);
		int totalCount = 0;
		for (CmsVoteItem item : items) {
			totalCount += item.getVoteCount();
		}
		bean.setTotalCount(totalCount);
		cmsVoteItemMng.update(items, bean);
		return bean;
	}

	public CmsVoteTopic vote(Integer topicId, Integer[] itemIds, CmsUser user,
			String ip, String cookie) {
		CmsVoteTopic topic = findById(topicId);
		Set<CmsVoteItem> items = topic.getItems();
		int totalCount = topic.getTotalCount();
		for (CmsVoteItem item : items) {
			if (ArrayUtils.contains(itemIds, item.getId())) {
				item.setVoteCount(item.getVoteCount() + 1);
				totalCount++;
			}
		}
		topic.setTotalCount(totalCount);
		// 如果需要限制投票，则需保存投票记录。
		if ((topic.getRepeateHour() == null || topic.getRepeateHour() > 0)
				&& (topic.getRestrictMember() || topic.getRestrictIp() || topic
						.getRestrictCookie())) {
			cmsVoteRecordMng.save(topic, user, ip, cookie);
		}
		return topic;
	}

	public CmsVoteTopic deleteById(Integer id) {
		CmsVoteTopic bean = dao.deleteById(id);
		return bean;
	}

	public CmsVoteTopic[] deleteByIds(Integer[] ids) {
		CmsVoteTopic[] beans = new CmsVoteTopic[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsVoteItemMng cmsVoteItemMng;
	private CmsVoteRecordMng cmsVoteRecordMng;
	private CmsVoteTopicDao dao;

	@Autowired
	public void setCmsVoteItemMng(CmsVoteItemMng cmsVoteItemMng) {
		this.cmsVoteItemMng = cmsVoteItemMng;
	}

	@Autowired
	public void setCmsVoteRecordMng(CmsVoteRecordMng cmsVoteRecordMng) {
		this.cmsVoteRecordMng = cmsVoteRecordMng;
	}

	@Autowired
	public void setDao(CmsVoteTopicDao dao) {
		this.dao = dao;
	}

}