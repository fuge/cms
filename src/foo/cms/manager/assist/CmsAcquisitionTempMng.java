package foo.cms.manager.assist;

import java.util.List;

import foo.cms.entity.assist.CmsAcquisitionTemp;

public interface CmsAcquisitionTempMng {
	public List<CmsAcquisitionTemp> getList(Integer siteId);

	public CmsAcquisitionTemp findById(Integer id);

	public CmsAcquisitionTemp save(CmsAcquisitionTemp bean);

	public CmsAcquisitionTemp update(CmsAcquisitionTemp bean);

	public CmsAcquisitionTemp deleteById(Integer id);

	public CmsAcquisitionTemp[] deleteByIds(Integer[] ids);
	
	public Integer getPercent(Integer siteId);
	
	public void clear(Integer siteId);
	
	public void clear(Integer siteId, String channelUrl);
}