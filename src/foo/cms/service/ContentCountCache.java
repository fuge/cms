package foo.cms.service;

/**
 * 内容计数器缓存接口
 * 
 * @author liufang
 * 
 */
public interface ContentCountCache {

	/**
	 * 浏览一次
	 * 
	 * @param id
	 *            内容ID
	 * @return 返回浏览次数，评论次数，顶次数，踩次数。
	 */
	public int[] viewAndGet(Integer id);
}
