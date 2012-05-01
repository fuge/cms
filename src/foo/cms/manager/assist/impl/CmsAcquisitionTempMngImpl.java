package foo.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.assist.CmsAcquisitionTempDao;
import foo.cms.entity.assist.CmsAcquisitionTemp;
import foo.cms.manager.assist.CmsAcquisitionTempMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class CmsAcquisitionTempMngImpl implements CmsAcquisitionTempMng {
	@Transactional(readOnly = true)
	public List<CmsAcquisitionTemp> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Transactional(readOnly = true)
	public CmsAcquisitionTemp findById(Integer id) {
		CmsAcquisitionTemp entity = dao.findById(id);
		return entity;
	}

	public CmsAcquisitionTemp save(CmsAcquisitionTemp bean) {
		clear(bean.getSite().getId(), bean.getChannelUrl());
		dao.save(bean);
		return bean;
	}

	public CmsAcquisitionTemp update(CmsAcquisitionTemp bean) {
		Updater<CmsAcquisitionTemp> updater = new Updater<CmsAcquisitionTemp>(
				bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsAcquisitionTemp deleteById(Integer id) {
		CmsAcquisitionTemp bean = dao.deleteById(id);
		return bean;
	}

	public CmsAcquisitionTemp[] deleteByIds(Integer[] ids) {
		CmsAcquisitionTemp[] beans = new CmsAcquisitionTemp[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public Integer getPercent(Integer siteId) {
		return dao.getPercent(siteId);
	}

	public void clear(Integer siteId) {
		dao.clear(siteId, null);
	}

	public void clear(Integer siteId, String channelUrl) {
		dao.clear(siteId, channelUrl);
	}

	private CmsAcquisitionTempDao dao;

	@Autowired
	public void setDao(CmsAcquisitionTempDao dao) {
		this.dao = dao;
	}
}