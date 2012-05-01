package foo.cms.manager.assist;

import java.util.List;

import foo.cms.entity.assist.CmsFriendlinkCtg;

public interface CmsFriendlinkCtgMng {
	public List<CmsFriendlinkCtg> getList(Integer siteId);

	public int countBySiteId(Integer siteId);

	public CmsFriendlinkCtg findById(Integer id);

	public CmsFriendlinkCtg save(CmsFriendlinkCtg bean);

	public CmsFriendlinkCtg update(CmsFriendlinkCtg bean);

	public void updateFriendlinkCtgs(Integer[] ids, String[] names,
			Integer[] priorities);

	public CmsFriendlinkCtg deleteById(Integer id);

	public CmsFriendlinkCtg[] deleteByIds(Integer[] ids);
}