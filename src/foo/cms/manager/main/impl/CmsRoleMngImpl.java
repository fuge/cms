package foo.cms.manager.main.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.CmsRoleDao;
import foo.cms.entity.main.CmsRole;
import foo.cms.manager.main.CmsRoleMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class CmsRoleMngImpl implements CmsRoleMng {
	@Transactional(readOnly = true)
	public List<CmsRole> getList() {
		return dao.getList();
	}

	@Transactional(readOnly = true)
	public CmsRole findById(Integer id) {
		CmsRole entity = dao.findById(id);
		return entity;
	}

	public CmsRole save(CmsRole bean, Set<String> perms) {
		bean.setPerms(perms);
		dao.save(bean);
		return bean;
	}

	public CmsRole update(CmsRole bean, Set<String> perms) {
		Updater<CmsRole> updater = new Updater<CmsRole>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setPerms(perms);
		return bean;
	}

	public CmsRole deleteById(Integer id) {
		CmsRole bean = dao.deleteById(id);
		return bean;
	}

	public CmsRole[] deleteByIds(Integer[] ids) {
		CmsRole[] beans = new CmsRole[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsRoleDao dao;

	@Autowired
	public void setDao(CmsRoleDao dao) {
		this.dao = dao;
	}
}