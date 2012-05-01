package foo.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsGuestbookCtgDao;
import foo.cms.entity.assist.CmsGuestbookCtg;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsGuestbookCtgDaoImpl extends
		HibernateBaseDao<CmsGuestbookCtg, Integer> implements
		CmsGuestbookCtgDao {
	@SuppressWarnings("unchecked")
	public List<CmsGuestbookCtg> getList(Integer siteId) {
		String hql = "from CmsGuestbookCtg bean"
				+ " where bean.site.id=? order by bean.priority asc";
		return find(hql, siteId);
	}

	public CmsGuestbookCtg findById(Integer id) {
		CmsGuestbookCtg entity = get(id);
		return entity;
	}

	public CmsGuestbookCtg save(CmsGuestbookCtg bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsGuestbookCtg deleteById(Integer id) {
		CmsGuestbookCtg entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsGuestbookCtg> getEntityClass() {
		return CmsGuestbookCtg.class;
	}
}