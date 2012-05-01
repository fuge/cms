package foo.cms.manager.assist;

import java.util.Date;

import foo.cms.entity.assist.CmsMessage;
import foo.common.page.Pagination;

/**
 *江西金磊科技发展有限公司jeecms研发
 */
public interface CmsMessageMng {
	public Pagination getPage(Integer siteId, Integer sendUserId,
			Integer receiverUserId, String title, Date sendBeginTime,
			Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
			int pageNo, int pageSize);

	public CmsMessage findById(Integer id);

	public CmsMessage save(CmsMessage bean);

	public CmsMessage update(CmsMessage bean);

	public CmsMessage deleteById(Integer id);

	public CmsMessage[] deleteByIds(Integer[] ids);
}