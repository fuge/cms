package foo.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.ChannelExtDao;
import foo.cms.entity.main.Channel;
import foo.cms.entity.main.ChannelExt;
import foo.cms.manager.main.ChannelExtMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class ChannelExtMngImpl implements ChannelExtMng {
	public ChannelExt save(ChannelExt ext, Channel channel) {
		channel.setChannelExt(ext);
		ext.setChannel(channel);
		ext.init();
		dao.save(ext);
		return ext;
	}

	public ChannelExt update(ChannelExt ext) {
		Updater<ChannelExt> updater = new Updater<ChannelExt>(ext);
		updater.include(ChannelExt.PROP_FINAL_STEP);
		updater.include(ChannelExt.PROP_AFTER_CHECK);
		ChannelExt entity = dao.updateByUpdater(updater);
		entity.blankToNull();
		return entity;
	}

	private ChannelExtDao dao;

	@Autowired
	public void setDao(ChannelExtDao dao) {
		this.dao = dao;
	}
}