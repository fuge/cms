package foo.cms.dao.assist.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import foo.cms.dao.assist.CmsAcquisitionTempDao;
import foo.cms.entity.assist.CmsAcquisitionTemp;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class CmsAcquisitionTempDaoImpl extends
		HibernateBaseDao<CmsAcquisitionTemp, Integer> implements
		CmsAcquisitionTempDao {
	@SuppressWarnings("unchecked")
	public List<CmsAcquisitionTemp> getList(Integer siteId) {
		Finder f = Finder.create("from CmsAcquisitionTemp bean where 1=1");
		if (siteId != null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		f.append(" order by bean.id desc");
		return find(f);
	}

	public CmsAcquisitionTemp findById(Integer id) {
		CmsAcquisitionTemp entity = get(id);
		return entity;
	}

	public CmsAcquisitionTemp save(CmsAcquisitionTemp bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsAcquisitionTemp deleteById(Integer id) {
		CmsAcquisitionTemp entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public Integer getPercent(Integer siteId) {
		Query query = getSession()
				.createQuery(
						"select max(percent) from CmsAcquisitionTemp where site.id=:siteId")
				.setParameter("siteId", siteId);
		return (Integer) (query.uniqueResult() == null ? 0 : query
				.uniqueResult());
	}

	public void clear(Integer siteId, String channelUrl) {
		StringBuilder sb = new StringBuilder(100);
		Map<String, Object> params = new HashMap<String, Object>();
		sb.append("delete from CmsAcquisitionTemp where site.id=:siteId");
		params.put("siteId", siteId);
		if (StringUtils.isNotBlank(channelUrl)) {
			sb.append(" and channelUrl!=:channelUrl");
			params.put("channelUrl", channelUrl);
		}
		Query query = getSession().createQuery(sb.toString());
		for (Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		query.executeUpdate();
	}

	@Override
	protected Class<CmsAcquisitionTemp> getEntityClass() {
		return CmsAcquisitionTemp.class;
	}

}