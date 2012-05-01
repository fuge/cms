package foo.core.tpl;

import java.util.Date;

/**
 * 模板接口
 * 
 * @author liufang
 * 
 */
public interface Tpl {
	/**
	 * 获得模板完整名称，是文件的唯一标识。
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 获得路径，不包含文件名的路径。
	 * 
	 * @return
	 */
	public String getPath();

	/**
	 * 获得模板名称，不包含路径的文件名。
	 * 
	 * @return
	 */
	public String getFilename();

	/**
	 * 获得模板内容
	 * 
	 * @return
	 */
	public String getSource();

	/**
	 * 获得最后修改时间（毫秒）
	 * 
	 * @return
	 */
	public long getLastModified();

	/**
	 * 获得最后修改时间（日期）
	 * 
	 * @return
	 */
	public Date getLastModifiedDate();

	/**
	 * 获得文件大小，单位bytes
	 * 
	 * @return
	 */
	public long getLength();

	/**
	 * 获得文件大小，单位K bytes
	 * 
	 * @return
	 */
	public int getSize();

	/**
	 * 是否目录
	 * 
	 * @return
	 */
	public boolean isDirectory();
}
