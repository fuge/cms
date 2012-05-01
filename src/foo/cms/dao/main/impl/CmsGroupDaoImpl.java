package foo.cms.dao.main.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import foo.cms.dao.main.CmsGroupDao;
import foo.cms.entity.main.CmsGroup;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsGroupDaoImpl extends HibernateBaseDao<CmsGroup, Integer>
		implements CmsGroupDao {
	@SuppressWarnings("unchecked")
	public List<CmsGroup> getList() {
		String hql = "from CmsGroup bean order by bean.priority asc,bean.id asc";
		return find(hql);
	}

	public CmsGroup getRegDef() {
		String hql = "from CmsGroup bean where bean.regDef=true";
		return (CmsGroup) findUnique(hql);
	}

	public CmsGroup findById(Integer id) {
		CmsGroup entity = get(id);
		return entity;
	}

	public CmsGroup save(CmsGroup bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsGroup deleteById(Integer id) {
		CmsGroup entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsGroup> getEntityClass() {
		return CmsGroup.class;
	}
}