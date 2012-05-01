package foo.cms.manager.main.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.ChannelDao;
import foo.cms.entity.main.Channel;
import foo.cms.entity.main.ChannelExt;
import foo.cms.entity.main.ChannelTxt;
import foo.cms.entity.main.CmsGroup;
import foo.cms.entity.main.CmsModel;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.CmsUserSite;
import foo.cms.manager.main.ChannelExtMng;
import foo.cms.manager.main.ChannelMng;
import foo.cms.manager.main.ChannelTxtMng;
import foo.cms.manager.main.CmsGroupMng;
import foo.cms.manager.main.CmsModelMng;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.service.ChannelDeleteChecker;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;

@Service
@Transactional
public class ChannelMngImpl implements ChannelMng {
	@Transactional(readOnly = true)
	public List<Channel> getTopList(Integer siteId, boolean hasContentOnly) {
		return dao.getTopList(siteId, hasContentOnly, false, false);
	}

	@Transactional(readOnly = true)
	public List<Channel> getTopListByRigth(Integer userId, Integer siteId,
			boolean hasContentOnly) {
		CmsUser user = cmsUserMng.findById(userId);
		CmsUserSite us = user.getUserSite(siteId);
		if (us.getAllChannel()) {
			return getTopList(siteId, hasContentOnly);
		} else {
			return dao.getTopListByRigth(userId, siteId, hasContentOnly);
		}
	}

	@Transactional(readOnly = true)
	public List<Channel> getTopListForTag(Integer siteId, boolean hasContentOnly) {
		return dao.getTopList(siteId, hasContentOnly, true, true);
	}

	@Transactional(readOnly = true)
	public Pagination getTopPageForTag(Integer siteId, boolean hasContentOnly,
			int pageNo, int pageSize) {
		return dao.getTopPage(siteId, hasContentOnly, false, false, pageNo,
				pageSize);
	}

	@Transactional(readOnly = true)
	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly) {
		return dao.getChildList(parentId, hasContentOnly, false, false);
	}

	@Transactional(readOnly = true)
	public List<Channel> getChildListByRight(Integer userId, Integer siteId,
			Integer parentId, boolean hasContentOnly) {
		CmsUser user = cmsUserMng.findById(userId);
		CmsUserSite us = user.getUserSite(siteId);
		if (us.getAllChannel()) {
			return getChildList(parentId, hasContentOnly);
		} else {
			return dao.getChildListByRight(userId, parentId, hasContentOnly);
		}
	}

	@Transactional(readOnly = true)
	public List<Channel> getChildListForTag(Integer parentId,
			boolean hasContentOnly) {
		return dao.getChildList(parentId, hasContentOnly, true, true);
	}

	@Transactional(readOnly = true)
	public Pagination getChildPageForTag(Integer parentId,
			boolean hasContentOnly, int pageNo, int pageSize) {
		return dao.getChildPage(parentId, hasContentOnly, true, true, pageNo,
				pageSize);
	}

	@Transactional(readOnly = true)
	public Channel findById(Integer id) {
		Channel entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public Channel findByPath(String path, Integer siteId) {
		return dao.findByPath(path, siteId, false);
	}

	@Transactional(readOnly = true)
	public Channel findByPathForTag(String path, Integer siteId) {
		return dao.findByPath(path, siteId, true);
	}

	public Channel save(Channel bean, ChannelExt ext, ChannelTxt txt,
			Integer[] viewGroupIds, Integer[] contriGroupIds,
			Integer[] userIds, Integer siteId, Integer parentId, Integer modelId) {
		if (parentId != null) {
			bean.setParent(findById(parentId));
		}
		bean.setSite(cmsSiteMng.findById(siteId));
		CmsModel model = cmsModelMng.findById(modelId);
		bean.setModel(model);
		bean.setHasContent(model.getHasContent());
		bean.init();
		dao.save(bean);
		channelExtMng.save(ext, bean);
		channelTxtMng.save(txt, bean);
		CmsGroup g;
		if (viewGroupIds != null && viewGroupIds.length > 0) {
			for (Integer gid : viewGroupIds) {
				g = cmsGroupMng.findById(gid);
				bean.addToViewGroups(g);
			}
		}
		if (contriGroupIds != null && contriGroupIds.length > 0) {
			for (Integer gid : contriGroupIds) {
				g = cmsGroupMng.findById(gid);
				bean.addToContriGroups(g);
			}
		}
		CmsUser u;
		if (userIds != null && userIds.length > 0) {
			for (Integer uid : userIds) {
				u = cmsUserMng.findById(uid);
				bean.addToUsers(u);
			}
		}
		return bean;
	}

	public Channel update(Channel bean, ChannelExt ext, ChannelTxt txt,
			Integer[] viewGroupIds, Integer[] contriGroupIds,
			Integer[] userIds, Integer parentId, Map<String, String> attr) {
		// 更新主表
		Updater<Channel> updater = new Updater<Channel>(bean);
		bean = dao.updateByUpdater(updater);
		// 更新父栏目
		Channel parent;
		if (parentId != null) {
			parent = findById(parentId);
		} else {
			parent = null;
		}
		bean.setParent(parent);
		// 更新扩展表
		channelExtMng.update(ext);
		// 更新文本表
		channelTxtMng.update(txt, bean);
		// 更新属性表
		Map<String, String> attrOrig = bean.getAttr();
		attrOrig.clear();
		attrOrig.putAll(attr);
		// 更新浏览会员组关联
		for (CmsGroup g : bean.getViewGroups()) {
			g.getViewChannels().remove(bean);
		}
		bean.getViewGroups().clear();
		if (viewGroupIds != null && viewGroupIds.length > 0) {
			CmsGroup g;
			for (Integer gid : viewGroupIds) {
				g = cmsGroupMng.findById(gid);
				bean.addToViewGroups(g);
			}
		}
		// 更新投稿会员组关联
		for (CmsGroup g : bean.getContriGroups()) {
			g.getContriChannels().remove(bean);
		}
		bean.getContriGroups().clear();
		if (contriGroupIds != null && contriGroupIds.length > 0) {
			CmsGroup g;
			for (Integer gid : contriGroupIds) {
				g = cmsGroupMng.findById(gid);
				bean.addToContriGroups(g);
			}
		}
		// 更新管理员关联
		for (CmsUser u : bean.getUsers()) {
			u.getChannels().remove(bean);
		}
		bean.getUsers().clear();
		if (userIds != null && userIds.length > 0) {
			CmsUser u;
			for (Integer uid : userIds) {
				u = cmsUserMng.findById(uid);
				bean.addToUsers(u);
			}
		}
		return bean;
	}

	public Channel deleteById(Integer id) {
		Channel entity = dao.findById(id);
		for (CmsGroup group : entity.getViewGroups()) {
			group.getViewChannels().remove(entity);
		}
		for (CmsGroup group : entity.getContriGroups()) {
			group.getContriChannels().remove(entity);
		}
		entity = dao.deleteById(id);
		return entity;
	}

	public Channel[] deleteByIds(Integer[] ids) {
		Channel[] beans = new Channel[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public String checkDelete(Integer id) {
		String msg = null;
		for (ChannelDeleteChecker checker : deleteCheckerList) {
			msg = checker.checkForChannelDelete(id);
			if (msg != null) {
				return msg;
			}
		}
		return msg;
	}

	public Channel[] updatePriority(Integer[] ids, Integer[] priority) {
		int len = ids.length;
		Channel[] beans = new Channel[len];
		for (int i = 0; i < len; i++) {
			beans[i] = findById(ids[i]);
			beans[i].setPriority(priority[i]);
		}
		return beans;
	}

	private List<ChannelDeleteChecker> deleteCheckerList;

	public void setDeleteCheckerList(
			List<ChannelDeleteChecker> deleteCheckerList) {
		this.deleteCheckerList = deleteCheckerList;
	}

	private CmsSiteMng cmsSiteMng;
	private CmsModelMng cmsModelMng;
	private ChannelExtMng channelExtMng;
	private ChannelTxtMng channelTxtMng;
	private CmsUserMng cmsUserMng;
	private CmsGroupMng cmsGroupMng;
	private ChannelDao dao;

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setCmsModelMng(CmsModelMng cmsModelMng) {
		this.cmsModelMng = cmsModelMng;
	}

	@Autowired
	public void setChannelExtMng(ChannelExtMng channelExtMng) {
		this.channelExtMng = channelExtMng;
	}

	@Autowired
	public void setChannelTxtMng(ChannelTxtMng channelTxtMng) {
		this.channelTxtMng = channelTxtMng;
	}

	@Autowired
	public void setCmsUserMng(CmsUserMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}

	@Autowired
	public void setCmsGroupMng(CmsGroupMng cmsGroupMng) {
		this.cmsGroupMng = cmsGroupMng;
	}

	@Autowired
	public void setDao(ChannelDao dao) {
		this.dao = dao;
	}

}