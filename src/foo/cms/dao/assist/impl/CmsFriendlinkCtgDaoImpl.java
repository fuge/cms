package foo.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsFriendlinkCtgDao;
import foo.cms.entity.assist.CmsFriendlinkCtg;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsFriendlinkCtgDaoImpl extends
		HibernateBaseDao<CmsFriendlinkCtg, Integer> implements
		CmsFriendlinkCtgDao {
	@SuppressWarnings("unchecked")
	public List<CmsFriendlinkCtg> getList(Integer siteId) {
		Finder f = Finder.create("from CmsFriendlinkCtg bean");
		if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.priority asc");
		return find(f);
	}

	public int countBySiteId(Integer siteId) {
		String hql = "select count(*) from CmsFriendlinkCtg bean where bean.site.id=:siteId";
		return ((Number) getSession().createQuery(hql).setParameter("siteId",
				siteId).iterate().next()).intValue();
	}

	public CmsFriendlinkCtg findById(Integer id) {
		CmsFriendlinkCtg entity = get(id);
		return entity;
	}

	public CmsFriendlinkCtg save(CmsFriendlinkCtg bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsFriendlinkCtg deleteById(Integer id) {
		CmsFriendlinkCtg entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsFriendlinkCtg> getEntityClass() {
		return CmsFriendlinkCtg.class;
	}
}