package foo.cms.manager.main;

import java.util.List;

import foo.cms.entity.main.CmsModel;

/**
 * 模型Manager接口
 * 
 * @author liufang
 * 
 */
public interface CmsModelMng {
	/**
	 * 获得模型列表
	 * 
	 * @param containDisabled
	 *            是否所有模型（即包含禁用模型）
	 * @return
	 */
	public List<CmsModel> getList(boolean containDisabled);

	/**
	 * 获得默认模型
	 * 
	 * @return
	 */
	public CmsModel getDefModel();

	public CmsModel findById(Integer id);

	public CmsModel save(CmsModel bean);

	public CmsModel update(CmsModel bean);

	public CmsModel deleteById(Integer id);

	public CmsModel[] deleteByIds(Integer[] ids);

	public CmsModel[] updatePriority(Integer[] ids, Integer[] priority,
			Boolean[] disabled, Integer defId);
}