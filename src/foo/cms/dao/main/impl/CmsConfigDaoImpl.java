package foo.cms.dao.main.impl;

import org.springframework.stereotype.Repository;

import foo.cms.dao.main.CmsConfigDao;
import foo.cms.entity.main.CmsConfig;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsConfigDaoImpl extends HibernateBaseDao<CmsConfig, Integer>
		implements CmsConfigDao {
	public CmsConfig findById(Integer id) {
		CmsConfig entity = get(id);
		return entity;
	}

	@Override
	protected Class<CmsConfig> getEntityClass() {
		return CmsConfig.class;
	}
}