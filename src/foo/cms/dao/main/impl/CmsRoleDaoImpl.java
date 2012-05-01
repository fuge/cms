package foo.cms.dao.main.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import foo.cms.dao.main.CmsRoleDao;
import foo.cms.entity.main.CmsRole;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsRoleDaoImpl extends HibernateBaseDao<CmsRole, Integer>
		implements CmsRoleDao {
	@SuppressWarnings("unchecked")
	public List<CmsRole> getList() {
		String hql = "from CmsRole bean order by bean.priority asc";
		return find(hql);
	}

	public CmsRole findById(Integer id) {
		CmsRole entity = get(id);
		return entity;
	}

	public CmsRole save(CmsRole bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsRole deleteById(Integer id) {
		CmsRole entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsRole> getEntityClass() {
		return CmsRole.class;
	}
}