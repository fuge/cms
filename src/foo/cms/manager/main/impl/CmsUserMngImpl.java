package foo.cms.manager.main.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import foo.cms.dao.main.CmsUserDao;
import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsGroup;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.CmsUserExt;
import foo.cms.manager.main.ChannelMng;
import foo.cms.manager.main.CmsGroupMng;
import foo.cms.manager.main.CmsRoleMng;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.manager.main.CmsUserExtMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.manager.main.CmsUserSiteMng;
import foo.cms.manager.main.ContentMng;
import foo.common.email.EmailSender;
import foo.common.email.MessageTemplate;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;
import foo.core.entity.UnifiedUser;
import foo.core.manager.UnifiedUserMng;

@Service
@Transactional
public class CmsUserMngImpl implements CmsUserMng {
	@Transactional(readOnly = true)
	public Pagination getPage(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank,
			int pageNo, int pageSize) {
		Pagination page = dao.getPage(username, email, siteId, groupId,
				disabled, admin, rank, pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List getList(String username, String email, Integer siteId,
			Integer groupId, Boolean disabled, Boolean admin, Integer rank) {
		List list = dao.getList(username, email, siteId, groupId,
				disabled, admin, rank);
		return list;
	}

	@Transactional(readOnly = true)
	public List<CmsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank) {
		return dao.getAdminList(siteId, allChannel, disabled, rank);
	}

	@Transactional(readOnly = true)
	public CmsUser findById(Integer id) {
		CmsUser entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public CmsUser findByUsername(String username) {
		CmsUser entity = dao.findByUsername(username);
		return entity;
	}

	public CmsUser registerMember(String username, String email,
			String password, String ip, Integer groupId, CmsUserExt userExt) {
		return registerMember(username, email, password, ip, groupId, userExt,
				true, null, null);
	}

	public CmsUser registerMember(String username, String email,
			String password, String ip, Integer groupId, CmsUserExt userExt,
			Boolean activation, EmailSender sender, MessageTemplate msgTpl) {
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip, activation, sender, msgTpl);
		CmsUser user = new CmsUser();
		user.forMember(unifiedUser);

		CmsGroup group = null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not found!");
		}
		user.setGroup(group);
		user.init();
		dao.save(user);
		cmsUserExtMng.save(userExt, user);
		return user;
	}

	public void updateLoginInfo(Integer userId, String ip) {
		Date now = new Timestamp(System.currentTimeMillis());
		CmsUser user = findById(userId);
		if (user != null) {
			user.setLoginCount(user.getLoginCount() + 1);
			user.setLastLoginIp(ip);
			user.setLastLoginTime(now);
		}
	}

	public void updateUploadSize(Integer userId, Integer size) {
		CmsUser user = findById(userId);
		user.setUploadTotal(user.getUploadTotal() + size);
		if (user.getUploadDate() != null) {
			if (CmsUser.isToday(user.getUploadDate())) {
				size += user.getUploadSize();
			}
		}
		user.setUploadDate(new java.sql.Date(System.currentTimeMillis()));
		user.setUploadSize(size);
	}

	public boolean isPasswordValid(Integer id, String password) {
		return unifiedUserMng.isPasswordValid(id, password);
	}

	public void updatePwdEmail(Integer id, String password, String email) {
		CmsUser user = findById(id);
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		} else {
			user.setEmail(null);
		}
		unifiedUserMng.update(id, password, email);
	}

	public CmsUser saveAdmin(String username, String email, String password,
			String ip, boolean viewOnly, boolean selfAdmin, int rank,
			Integer groupId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels,
			CmsUserExt userExt) {
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip);
		CmsUser user = new CmsUser();
		user.forAdmin(unifiedUser, viewOnly, selfAdmin, rank);
		CmsGroup group = null;
		if (groupId != null) {
			group = cmsGroupMng.findById(groupId);
		} else {
			group = cmsGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not setted!");
		}
		user.setGroup(group);
		user.init();
		dao.save(user);
		cmsUserExtMng.save(userExt, user);
		if (roleIds != null) {
			for (Integer rid : roleIds) {
				user.addToRoles(cmsRoleMng.findById(rid));
			}
		}
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				channel.addToUsers(user);
			}
		}
		if (siteIds != null) {
			CmsSite site;
			for (int i = 0, len = siteIds.length; i < len; i++) {
				site = cmsSiteMng.findById(siteIds[i]);
				cmsUserSiteMng.save(site, user, steps[i], allChannels[i]);
			}
		}
		return user;
	}

	public void addSiteToUser(CmsUser user, CmsSite site, Byte checkStep) {
		cmsUserSiteMng.save(site, user, checkStep, true);
	}

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId, Integer[] roleIds, Integer[] channelIds,
			Integer siteId, Byte step, Boolean allChannel) {
		CmsUser user = updateAdmin(bean, ext, password, groupId, roleIds,
				channelIds);
		// 更新所属站点
		cmsUserSiteMng.updateByUser(user, siteId, step, allChannel);
		return user;
	}

	public CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels) {
		CmsUser user = updateAdmin(bean, ext, password, groupId, roleIds,
				channelIds);
		// 更新所属站点
		cmsUserSiteMng.updateByUser(user, siteIds, steps, allChannels);
		return user;
	}

	private CmsUser updateAdmin(CmsUser bean, CmsUserExt ext, String password,
			Integer groupId, Integer[] roleIds, Integer[] channelIds) {
		Updater<CmsUser> updater = new Updater<CmsUser>(bean);
		updater.include("email");
		CmsUser user = dao.updateByUpdater(updater);
		user.setGroup(cmsGroupMng.findById(groupId));
		cmsUserExtMng.update(ext, user);
		// 更新角色
		user.getRoles().clear();
		if (roleIds != null) {
			for (Integer rid : roleIds) {
				user.addToRoles(cmsRoleMng.findById(rid));
			}
		}
		// 更新栏目权限
		Set<Channel> channels = user.getChannels();
		// 清除
		for (Channel channel : channels) {
			channel.getUsers().remove(user);
		}
		user.getChannels().clear();
		// 添加
		if (channelIds != null) {
			Channel channel;
			for (Integer cid : channelIds) {
				channel = channelMng.findById(cid);
				channel.addToUsers(user);
			}
		}
		unifiedUserMng.update(bean.getId(), password, bean.getEmail());
		return user;
	}

	public CmsUser updateMember(Integer id, String email, String password,
			Boolean isDisabled, CmsUserExt ext, Integer groupId) {
		CmsUser entity = findById(id);
		if (!StringUtils.isBlank(email)) {
			entity.setEmail(email);
		}
		if (isDisabled != null) {
			entity.setDisabled(isDisabled);
		}
		if (groupId != null) {
			entity.setGroup(cmsGroupMng.findById(groupId));
		}
		cmsUserExtMng.update(ext, entity);
		unifiedUserMng.update(id, password, email);
		return entity;
	}
	
	public CmsUser updateUserConllection(CmsUser user,Integer cid,Integer operate){
		Updater<CmsUser> updater = new Updater<CmsUser>(user);
		user = dao.updateByUpdater(updater);
		if (operate.equals(1)) {
			user.addToCollection(contentMng.findById(cid));
		}// 取消收藏
		else if (operate.equals(0)) {
			user.delFromCollection(contentMng.findById(cid));
		}
		return user;
	}

	public CmsUser deleteById(Integer id) {
		unifiedUserMng.deleteById(id);
		CmsUser bean = dao.deleteById(id);
		//删除收藏信息
		bean.clearCollection();
		return bean;
	}

	public CmsUser[] deleteByIds(Integer[] ids) {
		CmsUser[] beans = new CmsUser[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public boolean usernameNotExist(String username) {
		return dao.countByUsername(username) <= 0;
	}
	
	public boolean usernameNotExistInMember(String username){
		return dao.countMemberByUsername(username)<= 0;
	}

	public boolean emailNotExist(String email) {
		return dao.countByEmail(email) <= 0;
	}

	private CmsUserSiteMng cmsUserSiteMng;
	private CmsSiteMng cmsSiteMng;
	private ChannelMng channelMng;
	private CmsRoleMng cmsRoleMng;
	private CmsGroupMng cmsGroupMng;
	private UnifiedUserMng unifiedUserMng;
	private CmsUserExtMng cmsUserExtMng;
	private CmsUserDao dao;
	@Autowired
	private ContentMng contentMng;

	@Autowired
	public void setCmsUserSiteMng(CmsUserSiteMng cmsUserSiteMng) {
		this.cmsUserSiteMng = cmsUserSiteMng;
	}

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setChannelMng(ChannelMng channelMng) {
		this.channelMng = channelMng;
	}

	@Autowired
	public void setCmsRoleMng(CmsRoleMng cmsRoleMng) {
		this.cmsRoleMng = cmsRoleMng;
	}

	@Autowired
	public void setUnifiedUserMng(UnifiedUserMng unifiedUserMng) {
		this.unifiedUserMng = unifiedUserMng;
	}

	@Autowired
	public void setCmsUserExtMng(CmsUserExtMng cmsUserExtMng) {
		this.cmsUserExtMng = cmsUserExtMng;
	}

	@Autowired
	public void setCmsGroupMng(CmsGroupMng cmsGroupMng) {
		this.cmsGroupMng = cmsGroupMng;
	}

	@Autowired
	public void setDao(CmsUserDao dao) {
		this.dao = dao;
	}

}