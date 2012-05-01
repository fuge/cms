package foo.core.web;

import java.util.Date;

/**
 * 内容接口
 * 
 * 定义统一的内容显示接口，便于所有内容可以采用统一的显示方式。
 * 
 * @author liufang
 * 
 */
public interface ContentInterface {
	/**
	 * 标题
	 * 
	 * @return
	 */
	public String getTit();

	/**
	 * 简短标题
	 * 
	 * @return
	 */
	public String getStit();

	/**
	 * 描述
	 * 
	 * @return
	 */
	public String getDesc();

	/**
	 * 图片地址
	 * 
	 * @return
	 */
	public String getImgUrl();

	/**
	 * 图片地址2
	 * 
	 * @return
	 */
	public String getImgUrl2();

	/**
	 * 内容链接
	 * 
	 * @return
	 */
	public String getUrl();

	/**
	 * 是否新窗口打开
	 * 
	 * @return true,false or null
	 */
	public Boolean getTarget();

	/**
	 * 标题颜色
	 * 
	 * @return
	 */
	public String getTitCol();

	/**
	 * 标题是否加粗
	 * 
	 * @return
	 */
	public boolean isTitBold();

	/**
	 * 类别名称。如栏目、留言类别、论坛板块等。
	 * 
	 * @return
	 */
	public String getCtgName();

	/**
	 * 类别URL地址。
	 * 
	 * @return
	 */
	public String getCtgUrl();

	/**
	 * 站点名称
	 * 
	 * @return
	 */
	public String getSiteName();

	/**
	 * 站点URL
	 * 
	 * @return
	 */
	public String getSiteUrl();

	/**
	 * 获得日期
	 * 
	 * @return
	 */
	public Date getDate();
}
