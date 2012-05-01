package foo.cms.manager.main.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.CmsGroupDao;
import foo.cms.entity.main.CmsGroup;
import foo.cms.manager.main.CmsGroupMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class CmsGroupMngImpl implements CmsGroupMng {
	@Transactional(readOnly = true)
	public List<CmsGroup> getList() {
		return dao.getList();
	}

	@Transactional(readOnly = true)
	public CmsGroup findById(Integer id) {
		CmsGroup entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public CmsGroup getRegDef() {
		return dao.getRegDef();
	}

	public void updateRegDef(Integer regDefId) {
		if (regDefId != null) {
			for (CmsGroup g : getList()) {
				if (g.getId().equals(regDefId)) {
					g.setRegDef(true);
				} else {
					g.setRegDef(false);
				}
			}
		}
	}

	public CmsGroup save(CmsGroup bean) {
		bean.init();
		dao.save(bean);
		return bean;
	}

	public CmsGroup update(CmsGroup bean) {
		Updater<CmsGroup> updater = new Updater<CmsGroup>(bean);
		CmsGroup entity = dao.updateByUpdater(updater);
		return entity;
	}

	public CmsGroup deleteById(Integer id) {
		CmsGroup bean = dao.deleteById(id);
		return bean;
	}

	public CmsGroup[] deleteByIds(Integer[] ids) {
		CmsGroup[] beans = new CmsGroup[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public CmsGroup[] updatePriority(Integer[] ids, Integer[] priority) {
		int len = ids.length;
		CmsGroup[] beans = new CmsGroup[len];
		for (int i = 0; i < len; i++) {
			beans[i] = findById(ids[i]);
			beans[i].setPriority(priority[i]);
		}
		return beans;
	}

	private CmsGroupDao dao;

	@Autowired
	public void setDao(CmsGroupDao dao) {
		this.dao = dao;
	}
}