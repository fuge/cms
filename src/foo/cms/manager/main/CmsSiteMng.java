package foo.cms.manager.main;

import java.io.IOException;
import java.util.List;

import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsUser;

public interface CmsSiteMng {
	public List<CmsSite> getList();

	public List<CmsSite> getListFromCache();

	public CmsSite findByDomain(String domain, boolean cacheable);

	public CmsSite findById(Integer id);

	public CmsSite save(CmsSite currSite, CmsUser currUser, CmsSite bean,
			Integer uploadFtpId) throws IOException;

	public CmsSite update(CmsSite bean, Integer uploadFtpId);

	public void updateTplSolution(Integer siteId, String solution);

	public CmsSite deleteById(Integer id);

	public CmsSite[] deleteByIds(Integer[] ids);
}