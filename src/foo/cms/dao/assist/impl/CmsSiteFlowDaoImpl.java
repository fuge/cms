package foo.cms.dao.assist.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsSiteFlowDao;
import foo.cms.entity.assist.CmsSiteFlow;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsSiteFlowDaoImpl extends HibernateBaseDao<CmsSiteFlow, Integer>
		implements CmsSiteFlowDao {
	public CmsSiteFlow save(CmsSiteFlow cmsSiteFlow) {
		getSession().save(cmsSiteFlow);
		return cmsSiteFlow;
	}

	public CmsSiteFlow findUniqueByProperties(Integer siteId, String accessDate,
			String sessionId, String accessPage) {
		String hql = "from CmsSiteFlow bean where bean.site.id=:siteId and bean.accessDate=:accessDate and bean.sessionId=:sessionId and bean.accessPage=:accessPage";
		Query query = getSession().createQuery(hql);
		query.setParameter("siteId", siteId);
		query.setParameter("accessDate", accessDate);
		query.setParameter("sessionId", sessionId);
		query.setParameter("accessPage", accessPage);
		query.setMaxResults(1);
		query.setCacheable(true);
		return (CmsSiteFlow) query.uniqueResult();
	}

	protected Class<CmsSiteFlow> getEntityClass() {
		return CmsSiteFlow.class;
	}

}
