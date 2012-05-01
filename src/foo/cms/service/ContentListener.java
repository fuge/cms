package foo.cms.service;

import java.util.Map;

import foo.cms.entity.main.Content;

/**
 * 内容修改监听器
 * 
 * @author liufang
 * 
 */
public interface ContentListener {
	/**
	 * 保存之前执行
	 * 
	 * @param content
	 */
	public void preSave(Content content);

	/**
	 * 保存之后执行
	 * 
	 * @param content
	 */
	public void afterSave(Content content);

	/**
	 * 修改之前执行
	 * 
	 * @param content
	 *            修改前的Content
	 * @return 获取一些需要在afterChange中使用的值。
	 */
	public Map<String, Object> preChange(Content content);

	/**
	 * 修改之后执行
	 * 
	 * @param content
	 *            修改后的Content
	 * @param map
	 *            从{@link #preChange(Content)}方法返回的值。
	 */
	public void afterChange(Content content, Map<String, Object> map);

	/**
	 * 删除之前执行
	 * 
	 * @param content
	 */
	public void preDelete(Content content);

	/**
	 * 删除之后执行
	 * 
	 * @param content
	 */
	public void afterDelete(Content content);
}
