package foo.cms.dao.assist.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import foo.cms.dao.assist.CmsMessageDao;
import foo.cms.entity.assist.CmsGuestbook;
import foo.cms.entity.assist.CmsMessage;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;

/**
 *江西金磊科技发展有限公司jeecms研发
 */

public class CmsMessageDaoImpl extends HibernateBaseDao<CmsMessage, Integer>
		implements CmsMessageDao {

	public Pagination getPage(Integer siteId, Integer sendUserId,
			Integer receiverUserId, String title, Date sendBeginTime,
			Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
			int pageNo, int pageSize) {
		String hql = " select msg from CmsMessage msg where 1=1 ";
		Finder finder = Finder.create(hql);
		if (siteId != null) {
			finder.append(" and msg.site.id=:siteId")
					.setParam("siteId", siteId);
		}
		if(sendUserId != null&&receiverUserId != null){
			finder.append(" and (msg.msgSendUser.id=:sendUserId or msg.msgReceiverUser.id=:receiverUserId)").setParam(
					"sendUserId", sendUserId).setParam("receiverUserId", receiverUserId);
		}else{
			if (sendUserId != null) {
				finder.append(" and msg.msgSendUser.id=:sendUserId").setParam(
						"sendUserId", sendUserId);
			}
			if (receiverUserId != null) {
				finder.append(" and msg.msgReceiverUser.id=:receiverUserId")
						.setParam("receiverUserId", receiverUserId);
			}
		}
		
		if (StringUtils.isNotBlank(title)) {
			finder.append(" and msg.msgTitle like:title").setParam("title",
					"%" + title + "%");
		}
		if (sendBeginTime != null) {
			finder.append(" and msg.sendTime >=:sendBeginTime").setParam(
					"sendBeginTime", sendBeginTime);
		}
		if (sendEndTime != null) {
			finder.append(" and msg.sendTime <=:sendEndTime").setParam(
					"sendEndTime", sendEndTime);
		}
		if (status != null) {
			if (status) {
				finder.append(" and msg.msgStatus =true");
			} else {
				finder.append(" and msg.msgStatus =false");
			}
		}
		if (box != null) {
			finder.append(" and msg.msgBox =:box").setParam("box", box);
		}
		finder.append(" order by msg.id desc");
		return find(finder, pageNo, pageSize);
	}

	public CmsMessage findById(Integer id) {
		return super.get(id);
	}

	public CmsMessage save(CmsMessage bean) {
		getSession().save(bean);
		return bean;
	}
	
	public CmsMessage update(CmsMessage bean){
		getSession().update(bean);
		return bean;
	}

	public CmsMessage deleteById(Integer id) {
		CmsMessage entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public CmsMessage[] deleteByIds(Integer[] ids) {
		CmsMessage[] messages = new CmsMessage[ids.length];
		for (int i = 0; i < ids.length; i++) {
			messages[i] = get(ids[i]);
			deleteById(ids[i]);
		}
		return messages;
	}

	@Override
	protected Class<CmsMessage> getEntityClass() {
		return CmsMessage.class;
	}

}
