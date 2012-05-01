package foo.cms.dao.main.impl;

import org.springframework.stereotype.Repository;

import foo.cms.dao.main.ChannelExtDao;
import foo.cms.entity.main.ChannelExt;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class ChannelExtDaoImpl extends HibernateBaseDao<ChannelExt, Integer>
		implements ChannelExtDao {
	public ChannelExt save(ChannelExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ChannelExt> getEntityClass() {
		return ChannelExt.class;
	}
}