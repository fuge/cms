package foo.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsSensitivityDao;
import foo.cms.entity.assist.CmsSensitivity;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsSensitivityDaoImpl extends
		HibernateBaseDao<CmsSensitivity, Integer> implements CmsSensitivityDao {
	@SuppressWarnings("unchecked")
	public List<CmsSensitivity> getList(boolean cacheable) {
		String hql = "from CmsSensitivity bean order by bean.id desc";
		return getSession().createQuery(hql).setCacheable(cacheable).list();
	}

	public CmsSensitivity findById(Integer id) {
		CmsSensitivity entity = get(id);
		return entity;
	}

	public CmsSensitivity save(CmsSensitivity bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsSensitivity deleteById(Integer id) {
		CmsSensitivity entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsSensitivity> getEntityClass() {
		return CmsSensitivity.class;
	}
}