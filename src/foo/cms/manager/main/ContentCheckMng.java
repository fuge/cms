package foo.cms.manager.main;

import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentCheck;

/**
 * 内容审核Manager接口
 * 
 * '内容'数据存在，则'内容审核'数据必须存在。
 * 
 * @author liufang
 * 
 */
public interface ContentCheckMng {
	public ContentCheck save(ContentCheck check, Content content);

	public ContentCheck update(ContentCheck bean);
}