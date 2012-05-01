package foo.cms.dao.assist;

import foo.cms.entity.assist.CmsVoteTopic;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;

public interface CmsVoteTopicDao {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize);

	public CmsVoteTopic getDefTopic(Integer siteId);

	public CmsVoteTopic findById(Integer id);

	public CmsVoteTopic save(CmsVoteTopic bean);

	public CmsVoteTopic updateByUpdater(Updater<CmsVoteTopic> updater);

	public CmsVoteTopic deleteById(Integer id);
}