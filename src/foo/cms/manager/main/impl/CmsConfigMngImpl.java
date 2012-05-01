package foo.cms.manager.main.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.CmsConfigDao;
import foo.cms.entity.main.CmsConfig;
import foo.cms.entity.main.MarkConfig;
import foo.cms.entity.main.MemberConfig;
import foo.cms.manager.main.CmsConfigMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class CmsConfigMngImpl implements CmsConfigMng {
	@Transactional(readOnly = true)
	public CmsConfig get() {
		CmsConfig entity = dao.findById(1);
		return entity;
	}

	public void updateCountCopyTime(Date d) {
		dao.findById(1).setCountCopyTime(d);
	}

	public void updateCountClearTime(Date d) {
		dao.findById(1).setCountClearTime(d);
	}

	public CmsConfig update(CmsConfig bean) {
		Updater<CmsConfig> updater = new Updater<CmsConfig>(bean);
		CmsConfig entity = dao.updateByUpdater(updater);
		entity.blankToNull();
		return entity;
	}

	public MarkConfig updateMarkConfig(MarkConfig mark) {
		get().setMarkConfig(mark);
		return mark;
	}

	public void updateMemberConfig(MemberConfig memberConfig) {
		get().getAttr().putAll(memberConfig.getAttr());
	}

	private CmsConfigDao dao;

	@Autowired
	public void setDao(CmsConfigDao dao) {
		this.dao = dao;
	}
}