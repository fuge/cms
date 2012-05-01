package foo.core.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.common.hibernate3.Updater;
import foo.core.dao.FtpDao;
import foo.core.entity.Ftp;
import foo.core.manager.FtpMng;

@Service
@Transactional
public class FtpMngImpl implements FtpMng {
	@Transactional(readOnly = true)
	public List<Ftp> getList() {
		return dao.getList();
	}

	@Transactional(readOnly = true)
	public Ftp findById(Integer id) {
		Ftp entity = dao.findById(id);
		return entity;
	}

	public Ftp save(Ftp bean) {
		dao.save(bean);
		return bean;
	}

	public Ftp update(Ftp bean) {
		Updater<Ftp> updater = new Updater<Ftp>(bean);
		Ftp entity = dao.updateByUpdater(updater);
		return entity;
	}

	public Ftp deleteById(Integer id) {
		Ftp bean = dao.deleteById(id);
		return bean;
	}

	public Ftp[] deleteByIds(Integer[] ids) {
		Ftp[] beans = new Ftp[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private FtpDao dao;

	@Autowired
	public void setDao(FtpDao dao) {
		this.dao = dao;
	}
}