package foo.core.tpl;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 模板Service
 * 
 * @author liufang
 * 
 */
public interface TplManager {
	/**
	 * 获得模板列表。根据前缀，用于选择模板。
	 * 
	 * @param prefix
	 *            前缀
	 * @return
	 */
	public List<? extends Tpl> getListByPrefix(String prefix);

	public List<String> getNameListByPrefix(String prefix);

	/**
	 * 获得下级模板列表。根据路径，用于展现下级目录和文件。
	 * 
	 * @param path
	 *            路径
	 * @return
	 */
	public List<? extends Tpl> getChild(String path);

	/**
	 * 保存模板
	 * 
	 * @param name
	 *            模板名称
	 * @param source
	 *            模板内容
	 * @param isDirecotry
	 *            是否目录
	 */
	public void save(String name, String source, boolean isDirectory);

	/**
	 * 保存模板
	 * 
	 * @param path
	 * @param file
	 */
	public void save(String path, MultipartFile file);

	/**
	 * 获得模板
	 * 
	 * @param name
	 * @return
	 */
	public Tpl get(String name);

	/**
	 * 更新模板
	 * 
	 * @param name
	 *            模板名称
	 * @param source
	 *            模板内容
	 */
	public void update(String name, String source);

	/**
	 * 修改模板名称或路径
	 * 
	 * @param orig
	 * @param dist
	 */
	public void rename(String orig, String dist);

	/**
	 * 删除模板
	 * 
	 * @param names
	 *            模板名称数组
	 * @return 被删除的模板数量
	 */
	public int delete(String[] names);

}
