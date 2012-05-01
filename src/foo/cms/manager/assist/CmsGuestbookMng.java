package foo.cms.manager.assist;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import foo.cms.entity.assist.CmsGuestbook;
import foo.cms.entity.assist.CmsGuestbookExt;
import foo.cms.entity.main.CmsUser;
import foo.common.page.Pagination;

public interface CmsGuestbookMng {
	public Pagination getPage(Integer siteId, Integer ctgId,Integer userId, Boolean recommend,
			Boolean checked, boolean desc, boolean cacheable, int pageNo,
			int pageSize);

	@Transactional(readOnly = true)
	public List<CmsGuestbook> getList(Integer siteId, Integer ctgId,
			Boolean recommend, Boolean checked, boolean desc,
			boolean cacheable, int first, int max);

	public CmsGuestbook findById(Integer id);

	public CmsGuestbook save(CmsGuestbook bean, CmsGuestbookExt ext,
			Integer ctgId, String ip);

	public CmsGuestbook save(CmsUser member, Integer siteId, Integer ctgId,
			String ip, String title, String content, String email,
			String phone, String qq);

	public CmsGuestbook update(CmsGuestbook bean, CmsGuestbookExt ext,
			Integer ctgId);

	public CmsGuestbook deleteById(Integer id);

	public CmsGuestbook[] deleteByIds(Integer[] ids);
}