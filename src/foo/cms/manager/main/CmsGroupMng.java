package foo.cms.manager.main;

import java.util.List;

import foo.cms.entity.main.CmsGroup;

public interface CmsGroupMng {
	public List<CmsGroup> getList();

	public CmsGroup getRegDef();

	public CmsGroup findById(Integer id);

	public void updateRegDef(Integer regDefId);

	public CmsGroup save(CmsGroup bean);

	public CmsGroup update(CmsGroup bean);

	public CmsGroup deleteById(Integer id);

	public CmsGroup[] deleteByIds(Integer[] ids);

	public CmsGroup[] updatePriority(Integer[] ids, Integer[] priority);
}