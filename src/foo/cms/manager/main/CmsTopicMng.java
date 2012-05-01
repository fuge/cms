package foo.cms.manager.main;

import java.util.List;

import foo.cms.entity.main.CmsTopic;
import foo.common.page.Pagination;

public interface CmsTopicMng {

	public List<CmsTopic> getListForTag(Integer channelId, boolean recommend,
			Integer count);

	public Pagination getPageForTag(Integer channelId, boolean recommend,
			int pageNo, int pageSize);

	public Pagination getPage(int pageNo, int pageSize);

	public List<CmsTopic> getListByChannel(Integer channelId);

	public CmsTopic findById(Integer id);

	public CmsTopic save(CmsTopic bean, Integer channelId);

	public CmsTopic update(CmsTopic bean, Integer channelId);

	public CmsTopic deleteById(Integer id);

	public CmsTopic[] deleteByIds(Integer[] ids);

	public CmsTopic[] updatePriority(Integer[] ids, Integer[] priority);
}