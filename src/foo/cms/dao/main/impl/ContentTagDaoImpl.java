package foo.cms.dao.main.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import foo.cms.dao.main.ContentTagDao;
import foo.cms.entity.main.ContentTag;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;
import foo.common.page.Pagination;

@Repository
public class ContentTagDaoImpl extends HibernateBaseDao<ContentTag, Integer>
		implements ContentTagDao {
	@SuppressWarnings("unchecked")
	public List<ContentTag> getList(Integer count, boolean cacheable) {
		String hql = "from ContentTag bean order by bean.count desc";
		Query query = getSession().createQuery(hql);
		if (count != null) {
			query.setMaxResults(count);
		}
		query.setCacheable(cacheable);
		return query.list();
	}

	public Pagination getPage(String name, int pageNo, int pageSize,
			boolean cacheable) {
		Finder f = Finder.create("from ContentTag bean");
		if (!StringUtils.isBlank(name)) {
			f.append(" where bean.name like :name");
			f.setParam("name", "%" + name + "%");
		}
		f.append(" order by bean.count desc");
		f.setCacheable(cacheable);
		return find(f, pageNo, pageSize);
	}

	public ContentTag findById(Integer id) {
		ContentTag entity = get(id);
		return entity;
	}

	public ContentTag findByName(String name, boolean cacheable) {
		String hql = "from ContentTag bean where bean.name=:name";
		return (ContentTag) getSession().createQuery(hql).setParameter("name",
				name).setCacheable(cacheable).uniqueResult();
	}

	public ContentTag save(ContentTag bean) {
		getSession().save(bean);
		return bean;
	}

	public ContentTag deleteById(Integer id) {
		ContentTag entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public int deleteContentRef(Integer id) {
		Query query = getSession().getNamedQuery("ContentTag.deleteContentRef");
		return query.setParameter(0, id).executeUpdate();
	}

	public int countContentRef(Integer id) {
		Query query = getSession().getNamedQuery("ContentTag.countContentRef");
		return ((Number) (query.setParameter(0, id).list().iterator().next()))
				.intValue();
	}

	@Override
	protected Class<ContentTag> getEntityClass() {
		return ContentTag.class;
	}
}