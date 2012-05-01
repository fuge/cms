package foo.cms.service;

/**
 * 检查栏目是否可以删除的接口
 * 
 * @author liufang
 * 
 */
public interface ChannelDeleteChecker {
	/**
	 * 如不能删除，则返回国际化提示信息；否则返回null。
	 */
	public String checkForChannelDelete(Integer channelId);
}
