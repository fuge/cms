package foo.cms.manager.main.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.CmsUserSiteDao;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.CmsUserSite;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.manager.main.CmsUserSiteMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class CmsUserSiteMngImpl implements CmsUserSiteMng {
	@Transactional(readOnly = true)
	public CmsUserSite findById(Integer id) {
		CmsUserSite entity = dao.findById(id);
		return entity;
	}

	public CmsUserSite save(CmsSite site, CmsUser user, Byte step,
			Boolean allChannel) {
		CmsUserSite bean = new CmsUserSite();
		bean.setSite(site);
		bean.setUser(user);
		bean.setCheckStep(step);
		bean.setAllChannel(allChannel);
		dao.save(bean);
		return bean;
	}

	public CmsUserSite update(CmsUserSite bean) {
		Updater<CmsUserSite> updater = new Updater<CmsUserSite>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public void updateByUser(CmsUser user, Integer siteId, Byte step,
			Boolean allChannel) {
		Set<CmsUserSite> uss = user.getUserSites();
		if (siteId == null || step == null || allChannel == null) {
			return;
		}
		// 只更新单站点信息
		for (CmsUserSite us : uss) {
			if (siteId.equals(us.getSite().getId())) {
				us.setCheckStep(step);
				us.setAllChannel(allChannel);
			}
		}
	}

	public void updateByUser(CmsUser user, Integer[] siteIds, Byte[] steps,
			Boolean[] allChannels) {
		Set<CmsUserSite> uss = user.getUserSites();
		// 全删
		if (siteIds == null) {
			user.getUserSites().clear();
			for (CmsUserSite us : uss) {
				dao.delete(us);
			}
			return;
		}
		// 先删除、更新
		Set<CmsUserSite> toDel = new HashSet<CmsUserSite>();
		boolean contains;
		int i;
		for (CmsUserSite us : uss) {
			contains = false;
			for (i = 0; i < siteIds.length; i++) {
				if (siteIds[i].equals(us.getSite().getId())) {
					contains = true;
					break;
				}
			}
			if (contains) {
				us.setCheckStep(steps[i]);
				us.setAllChannel(allChannels[i]);
			} else {
				toDel.add(us);
			}
		}
		delete(toDel, uss);
		// 再增加
		i = 0;
		Set<CmsUserSite> toSave = new HashSet<CmsUserSite>();
		for (Integer sid : siteIds) {
			contains = false;
			for (CmsUserSite us : uss) {
				if (us.getSite().getId().equals(sid)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				toSave.add(save(cmsSiteMng.findById(sid), user, steps[i],
						allChannels[i]));
			}
			i++;
		}
		uss.addAll(toSave);
	}

	public int deleteBySiteId(Integer siteId) {
		return dao.deleteBySiteId(siteId);
	}

	private void delete(Collection<CmsUserSite> coll, Set<CmsUserSite> set) {
		if (coll == null) {
			return;
		}
		for (CmsUserSite us : coll) {
			dao.delete(us);
			set.remove(us);
		}
	}

	public CmsUserSite deleteById(Integer id) {
		CmsUserSite bean = dao.deleteById(id);
		return bean;
	}

	public CmsUserSite[] deleteByIds(Integer[] ids) {
		CmsUserSite[] beans = new CmsUserSite[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsSiteMng cmsSiteMng;
	private CmsUserSiteDao dao;

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setDao(CmsUserSiteDao dao) {
		this.dao = dao;
	}
}