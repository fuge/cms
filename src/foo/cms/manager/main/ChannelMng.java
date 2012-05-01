package foo.cms.manager.main;

import java.util.List;
import java.util.Map;

import foo.cms.entity.main.Channel;
import foo.cms.entity.main.ChannelExt;
import foo.cms.entity.main.ChannelTxt;
import foo.common.page.Pagination;

/**
 * 栏目管理接口
 * 
 * @author liufang
 * 
 */
public interface ChannelMng {
	/**
	 * 获得顶级栏目
	 * 
	 * @param siteId
	 *            站点ID
	 * @param hasContentOnly
	 *            是否只获得有内容的栏目
	 * @return
	 */
	public List<Channel> getTopList(Integer siteId, boolean hasContentOnly);

	public List<Channel> getTopListByRigth(Integer userId, Integer siteId,
			boolean hasContentOnly);

	public List<Channel> getTopListForTag(Integer siteId, boolean hasContentOnly);

	public Pagination getTopPageForTag(Integer siteId, boolean hasContentOnly,
			int pageNo, int pageSize);

	public List<Channel> getChildList(Integer parentId, boolean hasContentOnly);

	public List<Channel> getChildListByRight(Integer userId, Integer siteId,
			Integer parentId, boolean hasContentOnly);

	public List<Channel> getChildListForTag(Integer parentId,
			boolean hasContentOnly);

	public Pagination getChildPageForTag(Integer parentId,
			boolean hasContentOnly, int pageNo, int pageSize);

	public Channel findByPath(String path, Integer siteId);

	public Channel findByPathForTag(String path, Integer siteId);

	public Channel findById(Integer id);

	public Channel save(Channel bean, ChannelExt ext, ChannelTxt txt,
			Integer[] viewGroupIds, Integer[] contriGroupIds,
			Integer[] userIds, Integer siteId, Integer parentId, Integer modelId);

	public Channel update(Channel bean, ChannelExt ext, ChannelTxt txt,
			Integer[] viewGroupIds, Integer[] contriGroupIds,
			Integer[] userIds, Integer parentId, Map<String, String> attr);

	public Channel deleteById(Integer id);

	public Channel[] deleteByIds(Integer[] ids);

	public Channel[] updatePriority(Integer[] ids, Integer[] priority);

	public String checkDelete(Integer id);
}