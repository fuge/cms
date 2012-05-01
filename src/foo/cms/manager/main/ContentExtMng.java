package foo.cms.manager.main;

import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentExt;

public interface ContentExtMng {
	public ContentExt save(ContentExt ext, Content content);

	public ContentExt update(ContentExt ext);
}