package foo.cms.manager.assist.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.assist.CmsAdvertisingDao;
import foo.cms.entity.assist.CmsAdvertising;
import foo.cms.manager.assist.CmsAdvertisingMng;
import foo.cms.manager.assist.CmsAdvertisingSpaceMng;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;

@Service
@Transactional
public class CmsAdvertisingMngImpl implements CmsAdvertisingMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer adspaceId,
			Boolean enabled, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, adspaceId, enabled, pageNo,
				pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public List<CmsAdvertising> getList(Integer adspaceId, Boolean enabled) {
		return dao.getList(adspaceId, enabled);
	}

	@Transactional(readOnly = true)
	public CmsAdvertising findById(Integer id) {
		CmsAdvertising entity = dao.findById(id);
		return entity;
	}

	public CmsAdvertising save(CmsAdvertising bean, Integer adspaceId,
			Map<String, String> attr) {
		bean.setAdspace(cmsAdvertisingSpaceMng.findById(adspaceId));
		bean.setAttr(attr);
		bean.init();
		dao.save(bean);
		return bean;
	}

	public CmsAdvertising update(CmsAdvertising bean, Integer adspaceId,
			Map<String, String> attr) {
		Updater<CmsAdvertising> updater = new Updater<CmsAdvertising>(bean);
		updater.include(CmsAdvertising.PROP_CODE);
		bean = dao.updateByUpdater(updater);
		bean.setAdspace(cmsAdvertisingSpaceMng.findById(adspaceId));
		bean.getAttr().clear();
		bean.getAttr().putAll(attr);
		return bean;
	}

	public CmsAdvertising deleteById(Integer id) {
		CmsAdvertising bean = dao.deleteById(id);
		return bean;
	}

	public CmsAdvertising[] deleteByIds(Integer[] ids) {
		CmsAdvertising[] beans = new CmsAdvertising[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public void display(Integer id) {
		CmsAdvertising ad = findById(id);
		if (ad != null) {
			ad.setDisplayCount(ad.getDisplayCount() + 1);
		}
	}

	public void click(Integer id) {
		CmsAdvertising ad = findById(id);
		if (ad != null) {
			ad.setClickCount(ad.getClickCount() + 1);
		}
	}

	private CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng;
	private CmsAdvertisingDao dao;

	@Autowired
	public void setCmsAdvertisingSpaceMng(
			CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng) {
		this.cmsAdvertisingSpaceMng = cmsAdvertisingSpaceMng;
	}

	@Autowired
	public void setDao(CmsAdvertisingDao dao) {
		this.dao = dao;
	}
}