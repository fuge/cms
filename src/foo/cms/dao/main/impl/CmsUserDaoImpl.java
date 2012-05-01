package foo.cms.dao.main.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import foo.cms.dao.main.CmsUserDao;
import foo.cms.entity.main.CmsUser;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;
import foo.common.page.Pagination;

@Repository
public class CmsUserDaoImpl extends HibernateBaseDao<CmsUser, Integer>
		implements CmsUserDao {
	public Pagination getPage(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,
			int pageNo, int pageSize) {
		Finder f = Finder.create("select bean from CmsUser bean");
		if (siteId != null) {
			f.append(" join bean.userSites userSite");
			f.append(" where userSite.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username");
			f.setParam("username", "%" + username + "%");
		}
		if (!StringUtils.isBlank(email)) {
			f.append(" and bean.email like :email");
			f.setParam("email", "%" + email + "%");
		}
		if (groupId != null) {
			f.append(" and bean.group.id=:groupId");
			f.setParam("groupId", groupId);
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (admin != null) {
			f.append(" and bean.admin=:admin");
			f.setParam("admin", admin);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}
	
	public List getList(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank) {
		Finder f = Finder.create("select bean from CmsUser bean");
		if (siteId != null) {
			f.append(" join bean.userSites userSite");
			f.append(" where userSite.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if (!StringUtils.isBlank(username)) {
			f.append(" and bean.username like :username");
			f.setParam("username", "%" + username + "%");
		}
		if (!StringUtils.isBlank(email)) {
			f.append(" and bean.email like :email");
			f.setParam("email", "%" + email + "%");
		}
		if (groupId != null) {
			f.append(" and bean.group.id=:groupId");
			f.setParam("groupId", groupId);
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (admin != null) {
			f.append(" and bean.admin=:admin");
			f.setParam("admin", admin);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	@SuppressWarnings("unchecked")
	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank) {
		Finder f = Finder.create("select bean from CmsUser");
		f.append(" bean join bean.userSites us");
		f.append(" where us.site.id=:siteId");
		f.setParam("siteId", siteId);
		if (allChannel != null) {
			f.append(" and us.allChannel=:allChannel");
			f.setParam("allChannel", allChannel);
		}
		if (disabled != null) {
			f.append(" and bean.disabled=:disabled");
			f.setParam("disabled", disabled);
		}
		if (rank != null) {
			f.append(" and bean.rank<=:rank");
			f.setParam("rank", rank);
		}
		f.append(" and bean.admin=true");
		f.append(" order by bean.id asc");
		return find(f);
	}

	public CmsUser findById(Integer id) {
		CmsUser entity = get(id);
		return entity;
	}

	public CmsUser findByUsername(String username) {
		return findUniqueByProperty("username", username);
	}

	public int countByUsername(String username) {
		String hql = "select count(*) from CmsUser bean where bean.username=:username";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}
	public int countMemberByUsername(String username) {
		String hql = "select count(*) from CmsUser bean where bean.username=:username and bean.admin=false";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		return ((Number) query.iterate().next()).intValue();
	}

	public int countByEmail(String email) {
		String hql = "select count(*) from CmsUser bean where bean.email=:email";
		Query query = getSession().createQuery(hql);
		query.setParameter("email", email);
		return ((Number) query.iterate().next()).intValue();
	}

	public CmsUser save(CmsUser bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsUser deleteById(Integer id) {
		CmsUser entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsUser> getEntityClass() {
		return CmsUser.class;
	}
}