package foo.cms.dao.assist;

import java.util.List;

import foo.cms.entity.assist.CmsAcquisitionTemp;
import foo.common.hibernate3.Updater;

public interface CmsAcquisitionTempDao {
	public List<CmsAcquisitionTemp> getList(Integer siteId);

	public CmsAcquisitionTemp findById(Integer id);

	public CmsAcquisitionTemp save(CmsAcquisitionTemp bean);

	public CmsAcquisitionTemp updateByUpdater(Updater<CmsAcquisitionTemp> updater);

	public CmsAcquisitionTemp deleteById(Integer id);
	
	public Integer getPercent(Integer siteId);
	
	public void clear(Integer siteId,String channelUrl);
}