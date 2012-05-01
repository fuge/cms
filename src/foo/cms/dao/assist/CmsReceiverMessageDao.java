package foo.cms.dao.assist;

import java.util.Date;
import java.util.List;

import foo.cms.entity.assist.CmsReceiverMessage;
import foo.common.page.Pagination;

/**
 *江西金磊科技发展有限公司jeecms研发
 */
public interface CmsReceiverMessageDao {

	public Pagination getPage(Integer siteId, Integer sendUserId,
			Integer receiverUserId, String title, Date sendBeginTime,
			Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
			int pageNo, int pageSize);
	
	public List getList(Integer siteId, Integer sendUserId,
			Integer receiverUserId, String title, Date sendBeginTime,
			Date sendEndTime, Boolean status, Integer box, Boolean cacheable);

	public CmsReceiverMessage findById(Integer id);

	public CmsReceiverMessage save(CmsReceiverMessage bean);

	public CmsReceiverMessage update(CmsReceiverMessage bean);

	public CmsReceiverMessage deleteById(Integer id);

	public CmsReceiverMessage[] deleteByIds(Integer[] ids);
}