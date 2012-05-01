package foo.cms.dao.assist.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsVoteItemDao;
import foo.cms.entity.assist.CmsVoteItem;
import foo.common.hibernate3.HibernateBaseDao;
import foo.common.page.Pagination;

@Repository
public class CmsVoteItemDaoImpl extends HibernateBaseDao<CmsVoteItem, Integer>
		implements CmsVoteItemDao {
	public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	public CmsVoteItem findById(Integer id) {
		CmsVoteItem entity = get(id);
		return entity;
	}

	public CmsVoteItem save(CmsVoteItem bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsVoteItem deleteById(Integer id) {
		CmsVoteItem entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsVoteItem> getEntityClass() {
		return CmsVoteItem.class;
	}
}