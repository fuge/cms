package foo.cms.manager.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.CmsModelDao;
import foo.cms.entity.main.CmsModel;
import foo.cms.manager.main.CmsModelMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class CmsModelMngImpl implements CmsModelMng {
	@Transactional(readOnly = true)
	public List<CmsModel> getList(boolean containDisabled) {
		return dao.getList(containDisabled);
	}

	public CmsModel getDefModel() {
		return dao.getDefModel();
	}

	@Transactional(readOnly = true)
	public CmsModel findById(Integer id) {
		CmsModel entity = dao.findById(id);
		return entity;
	}

	public CmsModel save(CmsModel bean) {
		bean.init();
		dao.save(bean);
		return bean;
	}

	public CmsModel update(CmsModel bean) {
		Updater<CmsModel> updater = new Updater<CmsModel>(bean);
		CmsModel entity = dao.updateByUpdater(updater);
		return entity;
	}

	public CmsModel deleteById(Integer id) {
		CmsModel bean = dao.deleteById(id);
		return bean;
	}

	public CmsModel[] deleteByIds(Integer[] ids) {
		CmsModel[] beans = new CmsModel[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public CmsModel[] updatePriority(Integer[] ids, Integer[] priority,
			Boolean[] disabled, Integer defId) {
		int len = ids.length;
		CmsModel[] beans = new CmsModel[len];
		for (int i = 0; i < len; i++) {
			beans[i] = findById(ids[i]);
			beans[i].setPriority(priority[i]);
			beans[i].setDisabled(disabled[i]);
			if (beans[i].getId().equals(defId)) {
				beans[i].setDef(true);
			} else {
				beans[i].setDef(false);
			}
		}
		return beans;
	}

	private CmsModelDao dao;

	@Autowired
	public void setDao(CmsModelDao dao) {
		this.dao = dao;
	}
}