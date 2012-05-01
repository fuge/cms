package foo.cms.dao.main;

import java.util.List;

import foo.cms.entity.main.CmsRole;
import foo.common.hibernate3.Updater;

public interface CmsRoleDao {
	public List<CmsRole> getList();

	public CmsRole findById(Integer id);

	public CmsRole save(CmsRole bean);

	public CmsRole updateByUpdater(Updater<CmsRole> updater);

	public CmsRole deleteById(Integer id);
}