package foo.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsVoteRecordDao;
import foo.cms.entity.assist.CmsVoteRecord;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsVoteRecordDaoImpl extends
		HibernateBaseDao<CmsVoteRecord, Integer> implements CmsVoteRecordDao {

	public CmsVoteRecord save(CmsVoteRecord bean) {
		getSession().save(bean);
		return bean;
	}

	public int deleteByTopic(Integer topicId) {
		String hql = "delete from CmsVoteRecord bean"
				+ " where bean.topic.id=:topicId";
		return getSession().createQuery(hql).setParameter("topicId", topicId)
				.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public CmsVoteRecord findByUserId(Integer userId, Integer topicId) {
		String hql = "from CmsVoteRecord bean where bean.user.id=:userId"
				+ " and bean.topic.id=:topicId order by bean.time desc";
		List<CmsVoteRecord> list = getSession().createQuery(hql).setParameter(
				"userId", userId).setParameter("topicId", topicId)
				.setMaxResults(1).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public CmsVoteRecord findByIp(String ip, Integer topicId) {
		String hql = "from CmsVoteRecord bean where bean.ip=:ip"
				+ " and bean.topic.id=:topicId order by bean.time desc";
		List<CmsVoteRecord> list = getSession().createQuery(hql).setParameter(
				"ip", ip).setParameter("topicId", topicId).setMaxResults(1)
				.list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	public CmsVoteRecord findByCookie(String cookie, Integer topicId) {
		String hql = "from CmsVoteRecord bean where bean.cookie=:cookie"
				+ " and bean.topic.id=:topicId order by bean.time desc";
		List<CmsVoteRecord> list = getSession().createQuery(hql).setParameter(
				"cookie", cookie).setParameter("topicId", topicId)
				.setMaxResults(1).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	protected Class<CmsVoteRecord> getEntityClass() {
		return CmsVoteRecord.class;
	}
}