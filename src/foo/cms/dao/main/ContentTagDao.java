package foo.cms.dao.main;

import java.util.List;

import foo.cms.entity.main.ContentTag;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;

public interface ContentTagDao {
	public List<ContentTag> getList(Integer count, boolean cacheable);

	public Pagination getPage(String name, int pageNo, int pageSize,
			boolean cacheable);

	public ContentTag findById(Integer id);

	public ContentTag findByName(String name, boolean cacheable);

	public ContentTag save(ContentTag bean);

	public ContentTag updateByUpdater(Updater<ContentTag> updater);

	public ContentTag deleteById(Integer id);

	public int deleteContentRef(Integer id);

	public int countContentRef(Integer id);
}