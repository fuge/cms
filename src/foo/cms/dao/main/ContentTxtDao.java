package foo.cms.dao.main;

import foo.cms.entity.main.ContentTxt;
import foo.common.hibernate3.Updater;

public interface ContentTxtDao {
	public ContentTxt findById(Integer id);

	public ContentTxt save(ContentTxt bean);

	public ContentTxt updateByUpdater(Updater<ContentTxt> updater);
}