package foo.cms.dao.main.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import foo.cms.dao.main.CmsLogDao;
import foo.cms.entity.main.CmsLog;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;
import foo.common.page.Pagination;

@Repository
public class CmsLogDaoImpl extends HibernateBaseDao<CmsLog, Integer> implements
		CmsLogDao {
	public Pagination getPage(Integer category, Integer siteId, Integer userId,
			String title, String ip, int pageNo, int pageSize) {
		Finder f = Finder.create("from CmsLog bean where 1=1");
		if (category != null) {
			f.append(" and bean.category=:category");
			f.setParam("category", category);
		}
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (userId != null) {
			f.append(" and bean.user.id=:userId");
			f.setParam("userId", userId);
		}
		if (StringUtils.isNotBlank(title)) {
			f.append(" and bean.title like :title");
			f.setParam("title", "%" + title + "%");
		}
		if (StringUtils.isNotBlank(ip)) {
			f.append(" and bean.ip like :ip");
			f.setParam("ip", ip + "%");
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	public CmsLog findById(Integer id) {
		CmsLog entity = get(id);
		return entity;
	}

	public CmsLog save(CmsLog bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsLog deleteById(Integer id) {
		CmsLog entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public int deleteBatch(Integer category, Integer siteId, Date before) {
		Finder f = Finder.create("delete CmsLog bean where 1=1");
		if (category != null) {
			f.append(" and bean.category=:category");
			f.setParam("category", category);
		}
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (before != null) {
			f.append(" and bean.time<=:before");
			f.setParam("before", before);
		}
		Query q = f.createQuery(getSession());
		return q.executeUpdate();
	}

	@Override
	protected Class<CmsLog> getEntityClass() {
		return CmsLog.class;
	}
}