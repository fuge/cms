package foo.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsKeywordDao;
import foo.cms.entity.assist.CmsKeyword;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsKeywordDaoImpl extends HibernateBaseDao<CmsKeyword, Integer>
		implements CmsKeywordDao {
	@SuppressWarnings("unchecked")
	public List<CmsKeyword> getList(Integer siteId, boolean onlyEnabled,
			boolean cacheable) {
		Finder f = Finder.create("from CmsKeyword bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		if (onlyEnabled) {
			f.append(" and bean.disabled=false");
		}
		f.append(" order by bean.id desc");
		f.setCacheable(cacheable);
		return find(f);
	}

	@SuppressWarnings("unchecked")
	public List<CmsKeyword> getListGlobal(boolean onlyEnabled, boolean cacheable) {
		Finder f = Finder
				.create("from CmsKeyword bean where bean.site.id is null");
		if (onlyEnabled) {
			f.append(" and bean.disabled=false");
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	public CmsKeyword findById(Integer id) {
		CmsKeyword entity = get(id);
		return entity;
	}

	public CmsKeyword save(CmsKeyword bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsKeyword deleteById(Integer id) {
		CmsKeyword entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsKeyword> getEntityClass() {
		return CmsKeyword.class;
	}
}