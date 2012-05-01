package foo.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.ContentCheckDao;
import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentCheck;
import foo.cms.manager.main.ContentCheckMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class ContentCheckMngImpl implements ContentCheckMng {
	public ContentCheck save(ContentCheck check, Content content) {
		check.setContent(content);
		check.init();
		dao.save(check);
		content.setContentCheck(check);
		return check;
	}

	public ContentCheck update(ContentCheck bean) {
		Updater<ContentCheck> updater = new Updater<ContentCheck>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	private ContentCheckDao dao;

	@Autowired
	public void setDao(ContentCheckDao dao) {
		this.dao = dao;
	}
}