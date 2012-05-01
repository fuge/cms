package foo.cms.dao.main;

import foo.cms.entity.main.ChannelTxt;
import foo.common.hibernate3.Updater;

public interface ChannelTxtDao {
	public ChannelTxt findById(Integer id);

	public ChannelTxt save(ChannelTxt bean);

	public ChannelTxt updateByUpdater(Updater<ChannelTxt> updater);
}