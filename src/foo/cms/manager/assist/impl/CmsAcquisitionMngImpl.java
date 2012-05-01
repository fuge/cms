package foo.cms.manager.assist.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.assist.CmsAcquisitionDao;
import foo.cms.entity.assist.CmsAcquisition;
import foo.cms.entity.assist.CmsAcquisitionHistory;
import foo.cms.entity.assist.CmsAcquisitionTemp;
import foo.cms.entity.assist.CmsAcquisition.AcquisitionResultType;
import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentExt;
import foo.cms.entity.main.ContentTxt;
import foo.cms.manager.assist.CmsAcquisitionMng;
import foo.cms.manager.main.ChannelMng;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.manager.main.ContentMng;
import foo.cms.manager.main.ContentTypeMng;
import foo.cms.service.ChannelDeleteChecker;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class CmsAcquisitionMngImpl implements CmsAcquisitionMng,
		ChannelDeleteChecker {
	@Transactional(readOnly = true)
	public List<CmsAcquisition> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Transactional
	public CmsAcquisition findById(Integer id) {
		CmsAcquisition entity = dao.findById(id);
		return entity;
	}

	public void stop(Integer id) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return;
		}
		if (acqu.getStatus() == CmsAcquisition.START) {
			acqu.setStatus(CmsAcquisition.STOP);
		} else if (acqu.getStatus() == CmsAcquisition.PAUSE) {
			acqu.setCurrNum(0);
			acqu.setCurrItem(0);
			acqu.setTotalItem(0);
		}
	}

	public void pause(Integer id) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return;
		}
		if (acqu.getStatus() == CmsAcquisition.START) {
			acqu.setStatus(CmsAcquisition.PAUSE);
		}
	}

	public CmsAcquisition start(Integer id) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return acqu;
		}
		acqu.setStatus(CmsAcquisition.START);
		acqu.setStartTime(new Date());
		acqu.setEndTime(null);
		if (acqu.getCurrNum() <= 0) {
			acqu.setCurrNum(1);
		}
		if (acqu.getCurrItem() <= 0) {
			acqu.setCurrItem(1);
		}
		acqu.setTotalItem(0);
		return acqu;
	}

	public void end(Integer id) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return;
		}
		acqu.setStatus(CmsAcquisition.STOP);
		acqu.setEndTime(new Date());
		acqu.setCurrNum(0);
		acqu.setCurrItem(0);
		acqu.setTotalItem(0);
		acqu.setTotalItem(0);
	}

	public boolean isNeedBreak(Integer id, int currNum, int currItem,
			int totalItem) {
		CmsAcquisition acqu = findById(id);
		if (acqu == null) {
			return true;
		} else if (acqu.isPuase()) {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			acqu.setEndTime(new Date());
			return true;
		} else if (acqu.isStop()) {
			acqu.setCurrNum(0);
			acqu.setCurrItem(0);
			acqu.setTotalItem(0);
			acqu.setEndTime(new Date());
			return true;
		} else {
			acqu.setCurrNum(currNum);
			acqu.setCurrItem(currItem);
			acqu.setTotalItem(totalItem);
			return false;
		}
	}

	public CmsAcquisition save(CmsAcquisition bean, Integer channelId,
			Integer typeId, Integer userId, Integer siteId) {
		bean.setChannel(channelMng.findById(channelId));
		bean.setType(contentTypeMng.findById(typeId));
		bean.setUser(cmsUserMng.findById(userId));
		bean.setSite(cmsSiteMng.findById(siteId));
		bean.init();
		dao.save(bean);
		return bean;
	}

	public CmsAcquisition update(CmsAcquisition bean, Integer channelId,
			Integer typeId) {
		Updater<CmsAcquisition> updater = new Updater<CmsAcquisition>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setChannel(channelMng.findById(channelId));
		bean.setType(contentTypeMng.findById(typeId));
		return bean;
	}

	public CmsAcquisition deleteById(Integer id) {
		CmsAcquisition bean = dao.deleteById(id);
		return bean;
	}

	public CmsAcquisition[] deleteByIds(Integer[] ids) {
		CmsAcquisition[] beans = new CmsAcquisition[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public Content saveContent(String title, String txt, Integer acquId,
			AcquisitionResultType resultType, CmsAcquisitionTemp temp,
			CmsAcquisitionHistory history) {
		CmsAcquisition acqu = findById(acquId);
		Content c = new Content();
		c.setSite(acqu.getSite());
		ContentExt cext = new ContentExt();
		ContentTxt ctxt = new ContentTxt();
		cext.setTitle(title);
		ctxt.setTxt(txt);
		Content content = contentMng.save(c, cext, ctxt, null, null, null,
				null, null, null, null, null, null, acqu.getChannel().getId(),
				acqu.getType().getId(), false, acqu.getUser(), false);
		history.setTitle(title);
		history.setContent(content);
		history.setDescription(resultType.name());
		temp.setTitle(title);
		temp.setDescription(resultType.name());
		return content;
	}

	public String checkForChannelDelete(Integer channelId) {
		if (dao.countByChannelId(channelId) > 0) {
			return "cmsAcquisition.error.cannotDeleteChannel";
		} else {
			return null;
		}
	}

	public CmsAcquisition getStarted(Integer siteId) {
		return dao.getStarted(siteId);
	}

	public Integer hasStarted(Integer siteId) {
		return getStarted(siteId) == null ? 0 : getMaxQueue(siteId) + 1;
	}

	public Integer getMaxQueue(Integer siteId) {
		return dao.getMaxQueue(siteId);
	}

	public void addToQueue(Integer[] ids, Integer queueNum) {
		for (Integer id : ids) {
			CmsAcquisition acqu = findById(id);
			if (acqu.getStatus() == CmsAcquisition.START || acqu.getQueue() > 0) {
				continue;
			}
			acqu.setQueue(queueNum++);
		}
	}

	public void cancel(Integer siteId, Integer id) {
		CmsAcquisition acqu = findById(id);
		Integer queue = acqu.getQueue();
		for (CmsAcquisition c : getLargerQueues(siteId, queue)) {
			c.setQueue(c.getQueue() - 1);
		}
		acqu.setQueue(0);
	}

	public List<CmsAcquisition> getLargerQueues(Integer siteId, Integer queueNum) {
		return dao.getLargerQueues(siteId, queueNum);
	}

	public CmsAcquisition popAcquFromQueue(Integer siteId) {
		CmsAcquisition acquisition = dao.popAcquFromQueue(siteId);
		if (acquisition != null) {
			Integer id = acquisition.getId();
			cancel(siteId, id);
		}
		return acquisition;
	}

	private ChannelMng channelMng;
	private ContentMng contentMng;
	private ContentTypeMng contentTypeMng;
	private CmsSiteMng cmsSiteMng;
	private CmsUserMng cmsUserMng;
	private CmsAcquisitionDao dao;

	@Autowired
	public void setChannelMng(ChannelMng channelMng) {
		this.channelMng = channelMng;
	}

	@Autowired
	public void setContentMng(ContentMng contentMng) {
		this.contentMng = contentMng;
	}

	@Autowired
	public void setContentTypeMng(ContentTypeMng contentTypeMng) {
		this.contentTypeMng = contentTypeMng;
	}

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setCmsUserMng(CmsUserMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}

	@Autowired
	public void setDao(CmsAcquisitionDao dao) {
		this.dao = dao;
	}

}