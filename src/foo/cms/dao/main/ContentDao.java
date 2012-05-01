package foo.cms.dao.main;

import java.util.List;

import foo.cms.entity.main.Content;
import foo.cms.entity.main.Content.ContentStatus;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;

/**
 * 内容DAO接口。
 * 
 * 用于的排序参数
 * <ul>
 * <li>0：ID降序
 * <li>1：ID升序
 * <li>2：发布时间降序
 * <li>3：发布时间升序
 * <li>4：固顶级别降序,发布时间降序
 * <li>5：固顶级别降序,发布时间升序
 * <li>6：日访问降序
 * <li>7：周访问降序
 * <li>8：月访问降序
 * <li>9：总访问降序
 * <li>10：日评论降序
 * <li>11：周评论降序
 * <li>12：月评论降序
 * <li>13：总评论降序
 * <li>14：日下载降序
 * <li>15：周下载降序
 * <li>16：月下载降序
 * <li>17：总下载降序
 * <li>18：日顶降序
 * <li>19：周顶降序
 * <li>20：月顶降序
 * <li>21：总顶降序
 * </ul>
 * 
 * @author liufang
 * 
 */
public interface ContentDao {

	/**
	 * 获得内容列表
	 * 
	 * @param title
	 *            标题。支持模糊搜索，可以为null。
	 * @param typeId
	 *            内容类型ID。可以为null。
	 * @param inputUserId
	 *            内容录入员。可以为null。
	 * @param topLevel
	 *            是否固顶。
	 * @param recommend
	 *            是否推荐。
	 * @param status
	 *            状态。
	 * @param checkStep
	 *            审核步骤。当状态为prepared、passed、rejected时，不能为null。
	 * @param siteId
	 *            站点ID。可以为null。
	 * @param channelId
	 *            栏目ID。可以为null。
	 * @param orderBy
	 *            排序方式
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getPage(String title, Integer typeId,
			Integer inputUserId, boolean topLevel, boolean recommend,
			ContentStatus status, Byte checkStep, Integer siteId,
			Integer channelId, int orderBy, int pageNo, int pageSize);

	/**
	 * 获得自己发布的内容列表
	 * 
	 * @param title
	 *            标题。支持模糊搜索，可以为null。
	 * @param typeId
	 *            内容类型ID。可以为null。
	 * @param inputUserId
	 *            内容录入员。可以为null。
	 * @param topLevel
	 *            是否固顶。
	 * @param recommend
	 *            是否推荐。
	 * @param status
	 *            状态。
	 * @param checkStep
	 *            审核步骤。当状态为prepared、passed、rejected时，不能为null。
	 * @param siteId
	 *            站点ID。可以为null。
	 * @param channelId
	 *            站点ID。可以为null。
	 * @param userId
	 *            用户ID
	 * @param orderBy
	 *            排序方式
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getPageBySelf(String title, Integer typeId,
			Integer inputUserId, boolean topLevel, boolean recommend,
			ContentStatus status, Byte checkStep, Integer siteId,
			Integer channelId, Integer userId, int orderBy, int pageNo,
			int pageSize);

	/**
	 * 获得有权限的内容列表
	 * 
	 * @param title
	 *            标题。支持模糊搜索，可以为null。
	 * @param typeId
	 *            内容类型ID。可以为null。
	 * @param inputUserId
	 *            内容录入员。可以为null。
	 * @param topLevel
	 *            是否固顶。
	 * @param recommend
	 *            是否推荐。
	 * @param status
	 *            状态。
	 * @param checkStep
	 *            审核步骤。当状态为prepared、passed、rejected时，不能为null。
	 * @param siteId
	 *            站点ID。可以为null。
	 * @param channelId
	 *            栏目ID。可以为null。
	 * @param userId
	 *            用户ID。
	 * @param selfData
	 *            是否只获取自己发表的数据。
	 * @param orderBy
	 *            排序方式
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination getPageByRight(String title, Integer typeId,
			Integer inputUserId, boolean topLevel, boolean recommend,
			ContentStatus status, Byte checkStep, Integer siteId,
			Integer channelId, Integer userId, boolean selfData, int orderBy,
			int pageNo, int pageSize);

	/**
	 * 获得一篇内容的上一篇或下一篇内容
	 * 
	 * @param id
	 *            文章ID。
	 * @param siteId
	 *            站点ID。可以为null。
	 * @param channelId
	 *            栏目ID。可以为null。
	 * @param next
	 *            根据文章ID，大者为下一篇，小者为上一篇。true：下一篇；fasle：上一篇。
	 * @param cacheable
	 *            是否使用缓存。
	 * @return
	 */
	public Content getSide(Integer id, Integer siteId, Integer channelId,
			boolean next, boolean cacheable);

	/**
	 * 根据内容ID数组获取内容列表
	 * 
	 * @param ids
	 * @param orderBy
	 * @return
	 */
	public List<Content> getListByIdsForTag(Integer[] ids, int orderBy);

	public Pagination getPageBySiteIdsForTag(Integer[] siteIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, int pageNo, int pageSize);

	public List<Content> getListBySiteIdsForTag(Integer[] siteIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, Integer first, Integer count);

	public Pagination getPageByChannelIdsForTag(Integer[] channelIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, int option, int pageNo, int pageSize);

	public List<Content> getListByChannelIdsForTag(Integer[] channelIds,
			Integer[] typeIds, Boolean titleImg, Boolean recommend,
			String title, int orderBy, int option, Integer first, Integer count);

	public Pagination getPageByChannelPathsForTag(String[] paths,
			Integer[] siteIds, Integer[] typeIds, Boolean titleImg,
			Boolean recommend, String title, int orderBy, int pageNo,
			int pageSize);

	public List<Content> getListByChannelPathsForTag(String[] paths,
			Integer[] siteIds, Integer[] typeIds, Boolean titleImg,
			Boolean recommend, String title, int orderBy, Integer first,
			Integer count);

	public Pagination getPageByTopicIdForTag(Integer topicId,
			Integer[] siteIds, Integer[] channelIds, Integer[] typeIds,
			Boolean titleImg, Boolean recommend, String title, int orderBy,
			int pageNo, int pageSize);

	public List<Content> getListByTopicIdForTag(Integer topicId,
			Integer[] siteIds, Integer[] channelIds, Integer[] typeIds,
			Boolean titleImg, Boolean recommend, String title, int orderBy,
			Integer first, Integer count);

	public Pagination getPageByTagIdsForTag(Integer[] tagIds,
			Integer[] siteIds, Integer[] channelIds, Integer[] typeIds,
			Integer excludeId, Boolean titleImg, Boolean recommend,
			String title, int orderBy, int pageNo, int pageSize);

	public List<Content> getListByTagIdsForTag(Integer[] tagIds,
			Integer[] siteIds, Integer[] channelIds, Integer[] typeIds,
			Integer excludeId, Boolean titleImg, Boolean recommend,
			String title, int orderBy, Integer first, Integer count);
	
	public Pagination getPageForCollection(Integer siteId, Integer memberId, int pageNo, int pageSize);

	public int countByChannelId(int channelId);

	public Content findById(Integer id);

	public Content save(Content bean);

	public Content updateByUpdater(Updater<Content> updater);

	public Content deleteById(Integer id);
}