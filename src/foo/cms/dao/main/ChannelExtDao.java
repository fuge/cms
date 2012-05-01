package foo.cms.dao.main;

import foo.cms.entity.main.ChannelExt;
import foo.common.hibernate3.Updater;

public interface ChannelExtDao {
	public ChannelExt save(ChannelExt bean);

	public ChannelExt updateByUpdater(Updater<ChannelExt> updater);
}