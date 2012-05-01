package foo.cms.dao.assist.impl;

import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsVoteTopicDao;
import foo.cms.entity.assist.CmsVoteTopic;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;
import foo.common.page.Pagination;

@Repository
public class CmsVoteTopicDaoImpl extends
		HibernateBaseDao<CmsVoteTopic, Integer> implements CmsVoteTopicDao {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize) {
		Finder f = Finder.create("from CmsVoteTopic bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	public CmsVoteTopic getDefTopic(Integer siteId) {
		Finder f = Finder.create("from CmsVoteTopic bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" and bean.def=true");
		f.setMaxResults(1);
		return (CmsVoteTopic) f.createQuery(getSession()).uniqueResult();
	}

	public CmsVoteTopic findById(Integer id) {
		CmsVoteTopic entity = get(id);
		return entity;
	}

	public CmsVoteTopic save(CmsVoteTopic bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsVoteTopic deleteById(Integer id) {
		CmsVoteTopic entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsVoteTopic> getEntityClass() {
		return CmsVoteTopic.class;
	}
}