package foo.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.assist.CmsGuestbookCtgDao;
import foo.cms.entity.assist.CmsGuestbookCtg;
import foo.cms.manager.assist.CmsGuestbookCtgMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class CmsGuestbookCtgMngImpl implements CmsGuestbookCtgMng {
	@Transactional(readOnly = true)
	public List<CmsGuestbookCtg> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Transactional(readOnly = true)
	public CmsGuestbookCtg findById(Integer id) {
		CmsGuestbookCtg entity = dao.findById(id);
		return entity;
	}

	public CmsGuestbookCtg save(CmsGuestbookCtg bean) {
		dao.save(bean);
		return bean;
	}

	public CmsGuestbookCtg update(CmsGuestbookCtg bean) {
		Updater<CmsGuestbookCtg> updater = new Updater<CmsGuestbookCtg>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsGuestbookCtg deleteById(Integer id) {
		CmsGuestbookCtg bean = dao.deleteById(id);
		return bean;
	}

	public CmsGuestbookCtg[] deleteByIds(Integer[] ids) {
		CmsGuestbookCtg[] beans = new CmsGuestbookCtg[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsGuestbookCtgDao dao;

	@Autowired
	public void setDao(CmsGuestbookCtgDao dao) {
		this.dao = dao;
	}
}