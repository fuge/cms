package foo.cms.manager.assist;

import java.util.Collection;
import java.util.List;

import foo.cms.entity.assist.CmsVoteItem;
import foo.cms.entity.assist.CmsVoteTopic;
import foo.cms.entity.main.CmsUser;
import foo.common.page.Pagination;

public interface CmsVoteTopicMng {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize);

	public CmsVoteTopic findById(Integer id);

	public CmsVoteTopic getDefTopic(Integer siteId);

	public CmsVoteTopic vote(Integer topicId, Integer[] itemIds, CmsUser user,
			String ip, String cookie);

	public CmsVoteTopic save(CmsVoteTopic bean, List<CmsVoteItem> items);

	public CmsVoteTopic update(CmsVoteTopic bean, Collection<CmsVoteItem> items);

	public CmsVoteTopic deleteById(Integer id);

	public CmsVoteTopic[] deleteByIds(Integer[] ids);
}