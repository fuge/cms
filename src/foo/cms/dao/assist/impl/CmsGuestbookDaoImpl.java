package foo.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsGuestbookDao;
import foo.cms.entity.assist.CmsGuestbook;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;
import foo.common.page.Pagination;

@Repository
public class CmsGuestbookDaoImpl extends
		HibernateBaseDao<CmsGuestbook, Integer> implements CmsGuestbookDao {
	public Pagination getPage(Integer siteId, Integer ctgId,Integer userId,Boolean recommend,
			Boolean checked, boolean asc, boolean cacheable, int pageNo,
			int pageSize) {
		Finder f = createFinder(siteId, ctgId, userId,recommend, checked, asc,
				cacheable);
		return find(f, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<CmsGuestbook> getList(Integer siteId, Integer ctgId,
			Boolean recommend, Boolean checked, boolean desc,
			boolean cacheable, int first, int max) {
		Finder f = createFinder(siteId, ctgId, null,recommend, checked, desc,
				cacheable);
		f.setFirstResult(first);
		f.setMaxResults(max);
		return find(f);
	}

	private Finder createFinder(Integer siteId, Integer ctgId,Integer userId,
			Boolean recommend, Boolean checked, boolean desc, boolean cacheable) {
		Finder f = Finder.create("from CmsGuestbook bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (ctgId != null) {
			f.append(" and bean.ctg.id=:ctgId");
			f.setParam("ctgId", ctgId);
		}
		if (userId != null) {
			f.append(" and bean.member.id=:userId");
			f.setParam("userId", userId);
		}
		if (recommend != null) {
			f.append(" and bean.recommend=:recommend");
			f.setParam("recommend", recommend);
		}
		if (checked != null) {
			f.append(" and bean.checked=:checked");
			f.setParam("checked", checked);
		}
		if (desc) {
			f.append(" order by bean.id desc");
		} else {
			f.append(" order by bean.id asc");
		}
		f.setCacheable(cacheable);
		return f;
	}

	public CmsGuestbook findById(Integer id) {
		CmsGuestbook entity = get(id);
		return entity;
	}

	public CmsGuestbook save(CmsGuestbook bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsGuestbook deleteById(Integer id) {
		CmsGuestbook entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsGuestbook> getEntityClass() {
		return CmsGuestbook.class;
	}
}