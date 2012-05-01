package foo.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.main.ContentExtDao;
import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentExt;
import foo.cms.manager.main.ContentExtMng;
import foo.common.hibernate3.Updater;

@Service
@Transactional
public class ContentExtMngImpl implements ContentExtMng {
	public ContentExt save(ContentExt ext, Content content) {
		content.setContentExt(ext);
		ext.setContent(content);
		if (ext.getReleaseDate() == null) {
			ext.setReleaseDate(content.getSortDate());
		}
		ext.init();
		dao.save(ext);
		content.setContentExt(ext);
		return ext;
	}

	public ContentExt update(ContentExt bean) {
		Updater<ContentExt> updater = new Updater<ContentExt>(bean);
		bean = dao.updateByUpdater(updater);
		bean.blankToNull();
		// 修改后需要重新生成静态页
		bean.setNeedRegenerate(true);
		return bean;
	}

	private ContentExtDao dao;

	@Autowired
	public void setDao(ContentExtDao dao) {
		this.dao = dao;
	}
}