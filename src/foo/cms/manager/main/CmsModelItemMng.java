package foo.cms.manager.main;

import java.util.List;

import foo.cms.entity.main.CmsModelItem;

public interface CmsModelItemMng {
	public List<CmsModelItem> getList(Integer modelId, boolean isChannel,
			boolean hasDisabled);

	public CmsModelItem findById(Integer id);

	public CmsModelItem save(CmsModelItem bean);

	public CmsModelItem save(CmsModelItem bean, Integer modelId);

	public void saveList(List<CmsModelItem> list);

	public void updatePriority(Integer[] wids, Integer[] priority,
			String[] label, Boolean[] single, Boolean[] display);

	public CmsModelItem update(CmsModelItem bean);

	public CmsModelItem deleteById(Integer id);

	public CmsModelItem[] deleteByIds(Integer[] ids);
}