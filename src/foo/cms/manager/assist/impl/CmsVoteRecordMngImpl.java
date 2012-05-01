package foo.cms.manager.assist.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.assist.CmsVoteRecordDao;
import foo.cms.entity.assist.CmsVoteRecord;
import foo.cms.entity.assist.CmsVoteTopic;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.assist.CmsVoteRecordMng;

@Service
@Transactional
public class CmsVoteRecordMngImpl implements CmsVoteRecordMng {

	public CmsVoteRecord save(CmsVoteTopic topic, CmsUser user, String ip,
			String cookie) {
		CmsVoteRecord record = new CmsVoteRecord();
		record.setTopic(topic);
		record.setIp(ip);
		record.setCookie(cookie);
		record.setTime(new Timestamp(System.currentTimeMillis()));
		dao.save(record);
		return record;
	}

	public int deleteByTopic(Integer topicId) {
		return dao.deleteByTopic(topicId);
	}

	public Date lastVoteTimeByUserId(Integer userId, Integer topicId) {
		CmsVoteRecord record = dao.findByUserId(userId, topicId);
		return record != null ? record.getTime() : null;
	}

	public Date lastVoteTimeByIp(String ip, Integer topicId) {
		CmsVoteRecord record = dao.findByIp(ip, topicId);
		return record != null ? record.getTime() : null;
	}

	public Date lastVoteTimeByCookie(String cookie, Integer topicId) {
		CmsVoteRecord record = dao.findByCookie(cookie, topicId);
		return record != null ? record.getTime() : null;
	}

	private CmsVoteRecordDao dao;

	@Autowired
	public void setDao(CmsVoteRecordDao dao) {
		this.dao = dao;
	}
}