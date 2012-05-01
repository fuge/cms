package foo.cms.manager.main;

import java.util.Collection;
import java.util.List;

import foo.cms.entity.main.ContentTag;
import foo.common.page.Pagination;

public interface ContentTagMng {

	public List<ContentTag> getListForTag(Integer count);

	public Pagination getPageForTag(int pageNo, int pageSize);

	public Pagination getPage(String name, int pageNo, int pageSize);

	public ContentTag findById(Integer id);

	public ContentTag findByName(String name);

	public ContentTag findByNameForTag(String name);

	/**
	 * 保存标签。不存在的保存，存在的数量加一。
	 * 
	 * @param tagArr
	 *            标签名数组
	 * @return 标签列表
	 */
	public List<ContentTag> saveTags(String[] tagArr);

	public ContentTag saveTag(String name);

	public List<ContentTag> updateTags(List<ContentTag> tags, String[] tagArr);

	public void removeTags(Collection<ContentTag> tags);

	public ContentTag save(ContentTag bean);

	public ContentTag update(ContentTag bean);

	public ContentTag deleteById(Integer id);

	public ContentTag[] deleteByIds(Integer[] ids);
}