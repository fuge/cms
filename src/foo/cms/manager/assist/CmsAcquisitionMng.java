package foo.cms.manager.assist;

import java.util.List;

import foo.cms.entity.assist.CmsAcquisition;
import foo.cms.entity.assist.CmsAcquisitionHistory;
import foo.cms.entity.assist.CmsAcquisitionTemp;
import foo.cms.entity.assist.CmsAcquisition.AcquisitionResultType;
import foo.cms.entity.main.Content;

public interface CmsAcquisitionMng {
	public List<CmsAcquisition> getList(Integer siteId);

	public CmsAcquisition findById(Integer id);

	public void stop(Integer id);

	public void pause(Integer id);

	public CmsAcquisition start(Integer id);

	public void end(Integer id);

	public boolean isNeedBreak(Integer id, int currNum, int currItem,
			int totalItem);

	public CmsAcquisition save(CmsAcquisition bean, Integer channelId,
			Integer typeId, Integer userId, Integer siteId);

	public CmsAcquisition update(CmsAcquisition bean, Integer channelId,
			Integer typeId);

	public CmsAcquisition deleteById(Integer id);

	public CmsAcquisition[] deleteByIds(Integer[] ids);

	public Content saveContent(String title, String txt, Integer acquId,
			AcquisitionResultType resultType, CmsAcquisitionTemp temp,
			CmsAcquisitionHistory history);

	public CmsAcquisition getStarted(Integer siteId);
	
	public Integer getMaxQueue(Integer siteId);

	public Integer hasStarted(Integer siteId);
	
	public void addToQueue(Integer[] ids, Integer queueNum);
	
	public void cancel(Integer siteId, Integer id);
	
	public List<CmsAcquisition> getLargerQueues(Integer siteId, Integer queueNum);
	
	public CmsAcquisition popAcquFromQueue(Integer siteId);
}