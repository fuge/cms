package foo.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import foo.common.hibernate3.HibernateBaseDao;
import foo.core.dao.ConfigDao;
import foo.core.entity.Config;

@Repository
public class ConfigDaoImpl extends HibernateBaseDao<Config, String> implements
		ConfigDao {
	@SuppressWarnings("unchecked")
	public List<Config> getList() {
		String hql = "from Config";
		return find(hql);
	}

	public Config findById(String id) {
		Config entity = get(id);
		return entity;
	}

	public Config save(Config bean) {
		getSession().save(bean);
		return bean;
	}

	public Config deleteById(String id) {
		Config entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<Config> getEntityClass() {
		return Config.class;
	}
}