package foo.cms.dao.assist.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsAcquisitionHistoryDao;
import foo.cms.entity.assist.CmsAcquisitionHistory;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;
import foo.common.page.Pagination;

@Repository
public class CmsAcquisitionHistoryDaoImpl extends
		HibernateBaseDao<CmsAcquisitionHistory, Integer> implements
		CmsAcquisitionHistoryDao {
	@SuppressWarnings("unchecked")
	public List<CmsAcquisitionHistory> getList(Integer siteId, Integer acquId) {
		Finder f = Finder.create("from CmsAcquisitionHistory bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.acquisition.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (acquId != null) {
			f.append(" and bean.acquisition.id=:acquId");
			f.setParam("acquId", acquId);
		}
		f.append(" order by bean.id asc");
		return find(f);
	}

	public Pagination getPage(Integer siteId, Integer acquId, Integer pageNo,
			Integer pageSize) {
		Finder f = Finder.create("from CmsAcquisitionHistory bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.acquisition.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (acquId != null) {
			f.append(" and bean.acquisition.id=:acquId");
			f.setParam("acquId", acquId);
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	public CmsAcquisitionHistory findById(Integer id) {
		CmsAcquisitionHistory entity = get(id);
		return entity;
	}

	public CmsAcquisitionHistory save(CmsAcquisitionHistory bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsAcquisitionHistory deleteById(Integer id) {
		CmsAcquisitionHistory entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public Boolean checkExistByProperties(Boolean title, String value) {
		Criteria crit = createCriteria();
		if (title) {
			crit.add(Restrictions.eq("title", value));
		} else {
			crit.add(Restrictions.eq("contentUrl", value));
		}
		return crit.list().size() > 0 ? true : false;
	}

	@Override
	protected Class<CmsAcquisitionHistory> getEntityClass() {
		return CmsAcquisitionHistory.class;
	}

}