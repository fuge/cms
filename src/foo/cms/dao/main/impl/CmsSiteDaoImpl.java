package foo.cms.dao.main.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import foo.cms.dao.main.CmsSiteDao;
import foo.cms.entity.main.CmsSite;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsSiteDaoImpl extends HibernateBaseDao<CmsSite, Integer>
		implements CmsSiteDao {

	public int siteCount(boolean cacheable) {
		String hql = "select count(*) from CmsSite bean";
		return ((Number) getSession().createQuery(hql).setCacheable(cacheable)
				.iterate().next()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<CmsSite> getList(boolean cacheable) {
		String hql = "from CmsSite bean order by bean.id asc";
		return getSession().createQuery(hql).setCacheable(cacheable).list();
	}

	public CmsSite findByDomain(String domain, boolean cacheable) {
		String hql = "from CmsSite bean where domain=:domain";
		Query query = getSession().createQuery(hql).setString("domain", domain);
		query.setCacheable(cacheable);
		return (CmsSite) query.uniqueResult();
	}

	public CmsSite findById(Integer id) {
		CmsSite entity = get(id);
		return entity;
	}

	public CmsSite save(CmsSite bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsSite deleteById(Integer id) {
		CmsSite entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public CmsSite getByDomain(String domain) {
		String hql = "from CmsSite bean where bean.domain=?";
		return findUniqueByProperty(hql, domain);
	}

	@Override
	protected Class<CmsSite> getEntityClass() {
		return CmsSite.class;
	}
}