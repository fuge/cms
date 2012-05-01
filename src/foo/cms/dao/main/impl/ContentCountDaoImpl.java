package foo.cms.dao.main.impl;

import java.util.List;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import foo.cms.dao.main.ContentCountDao;
import foo.cms.entity.main.ContentCount;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class ContentCountDaoImpl extends
		HibernateBaseDao<ContentCount, Integer> implements ContentCountDao {
	@SuppressWarnings("unchecked")
	public int freshCacheToDB(Ehcache cache) {
		List<Integer> keys = cache.getKeys();
		if (keys.size() <= 0) {
			return 0;
		}
		Element e;
		Integer views;
		int i = 0;
		String hql = "update ContentCount bean"
				+ " set bean.views=bean.views+:views"
				+ ",bean.viewsMonth=bean.viewsMonth+:views"
				+ ",bean.viewsWeek=bean.viewsWeek+:views"
				+ ",bean.viewsDay=bean.viewsDay+:views" + " where bean.id=:id";
		Query query = getSession().createQuery(hql);
		for (Integer id : keys) {
			e = cache.get(id);
			if (e != null) {
				views = (Integer) e.getValue();
				if (views != null) {
					query.setParameter("views", views);
					query.setParameter("id", id);
					i += query.executeUpdate();
				}
			}
		}
		return i;
	}

	public int clearCount(boolean week, boolean month) {
		StringBuilder hql = new StringBuilder("update ContentCount bean");
		hql.append(" set bean.viewsDay=0,commentsDay=0,upsDay=0");
		if (week) {
			hql.append(",bean.viewsWeek=0,commentsWeek=0,upsWeek=0");
		}
		if (month) {
			hql.append(",bean.viewsMonth=0,commentsMonth=0,upsMonth=0");
		}
		return getSession().createQuery(hql.toString()).executeUpdate();
	}

	public int copyCount() {
		String hql = "update Content a set"
				+ " a.viewsDay=(select b.viewsDay from ContentCount b where a.id=b.id)"
				+ ",a.commentsDay=(select b.commentsDay from ContentCount b where a.id=b.id)"
				+ ",a.downloadsDay=(select b.downloadsDay from ContentCount b where a.id=b.id)"
				+ ",a.upsDay=(select b.upsDay from ContentCount b where a.id=b.id)";
		return getSession().createQuery(hql).executeUpdate();
	}

	public ContentCount findById(Integer id) {
		ContentCount entity = get(id);
		return entity;
	}

	public ContentCount save(ContentCount bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentCount> getEntityClass() {
		return ContentCount.class;
	}
}