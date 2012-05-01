package foo.cms.manager.main;

import foo.cms.entity.main.Channel;
import foo.cms.entity.main.ChannelTxt;

/**
 * 栏目文本Manager接口
 * 
 * '栏目'数据存在，'栏目文本'数据可以不存在，所以在save和update中需要处理这个问题。
 * 
 * @author liufang
 * 
 */
public interface ChannelTxtMng {
	/**
	 * 保存栏目文本对象。如果txt所有字段都为空，则不保存。
	 * 
	 * @param txt
	 *            栏目文本对象
	 * @param channel
	 *            栏目持久化对象
	 * @return 如果不保存则返回null，否则返回持久化对象。
	 */
	public ChannelTxt save(ChannelTxt txt, Channel channel);

	/**
	 * 更新栏目文本对象。
	 * <ul>
	 * <li>
	 * 如果txt数据原本不存在，则保存；
	 * <li>
	 * 如果txt所有字段为空，则删除；
	 * <li>
	 * 否则更新。
	 * </ul>
	 * 
	 * @param txt
	 *            栏目文本对象
	 * @param channel
	 *            栏目持久化对象
	 * @return 如果删除或不保存则返回null，否则返回持久化对象。
	 */
	public ChannelTxt update(ChannelTxt txt, Channel channel);
}