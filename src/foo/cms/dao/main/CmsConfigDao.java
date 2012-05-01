package foo.cms.dao.main;

import foo.cms.entity.main.CmsConfig;
import foo.common.hibernate3.Updater;

public interface CmsConfigDao {
	public CmsConfig findById(Integer id);

	public CmsConfig updateByUpdater(Updater<CmsConfig> updater);
}