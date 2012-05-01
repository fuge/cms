package foo.cms.manager.main;

import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentTxt;

public interface ContentTxtMng {
	public ContentTxt save(ContentTxt txt, Content content);

	public ContentTxt update(ContentTxt txt, Content content);
}