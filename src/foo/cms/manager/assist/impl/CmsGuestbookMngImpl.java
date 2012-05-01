package foo.cms.manager.assist.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.assist.CmsGuestbookDao;
import foo.cms.entity.assist.CmsGuestbook;
import foo.cms.entity.assist.CmsGuestbookExt;
import foo.cms.entity.main.CmsUser;
import foo.cms.manager.assist.CmsGuestbookCtgMng;
import foo.cms.manager.assist.CmsGuestbookExtMng;
import foo.cms.manager.assist.CmsGuestbookMng;
import foo.cms.manager.main.CmsSiteMng;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;

@Service
@Transactional
public class CmsGuestbookMngImpl implements CmsGuestbookMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer ctgId,Integer userId, Boolean recommend,
			Boolean checked, boolean desc, boolean cacheable, int pageNo,
			int pageSize) {
		return dao.getPage(siteId, ctgId,userId, recommend, checked, desc, cacheable,
				pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public List<CmsGuestbook> getList(Integer siteId, Integer ctgId,
			Boolean recommend, Boolean checked, boolean desc,
			boolean cacheable, int first, int max) {
		return dao.getList(siteId, ctgId, recommend, checked, desc, cacheable,
				first, max);
	}

	@Transactional(readOnly = true)
	public CmsGuestbook findById(Integer id) {
		CmsGuestbook entity = dao.findById(id);
		return entity;
	}

	public CmsGuestbook save(CmsGuestbook bean, CmsGuestbookExt ext,
			Integer ctgId, String ip) {
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		bean.setIp(ip);
		bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
		bean.init();
		dao.save(bean);
		cmsGuestbookExtMng.save(ext, bean);
		return bean;
	}

	public CmsGuestbook save(CmsUser member, Integer siteId, Integer ctgId,
			String ip, String title, String content, String email,
			String phone, String qq) {
		CmsGuestbook guestbook = new CmsGuestbook();
		guestbook.setMember(member);
		guestbook.setSite(cmsSiteMng.findById(siteId));
		guestbook.setIp(ip);
		CmsGuestbookExt ext = new CmsGuestbookExt();
		ext.setTitle(title);
		ext.setContent(content);
		ext.setEmail(email);
		ext.setPhone(phone);
		ext.setQq(qq);
		return save(guestbook, ext, ctgId, ip);
	}

	public CmsGuestbook update(CmsGuestbook bean, CmsGuestbookExt ext,
			Integer ctgId) {
		Updater<CmsGuestbook> updater = new Updater<CmsGuestbook>(bean);
		bean = dao.updateByUpdater(updater);
		bean.setCtg(cmsGuestbookCtgMng.findById(ctgId));
		cmsGuestbookExtMng.update(ext);
		return bean;
	}

	public CmsGuestbook deleteById(Integer id) {
		CmsGuestbook bean = dao.deleteById(id);
		return bean;
	}

	public CmsGuestbook[] deleteByIds(Integer[] ids) {
		CmsGuestbook[] beans = new CmsGuestbook[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsGuestbookCtgMng cmsGuestbookCtgMng;
	private CmsGuestbookExtMng cmsGuestbookExtMng;
	private CmsSiteMng cmsSiteMng;
	private CmsGuestbookDao dao;

	@Autowired
	public void setDao(CmsGuestbookDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setCmsGuestbookExtMng(CmsGuestbookExtMng cmsGuestbookExtMng) {
		this.cmsGuestbookExtMng = cmsGuestbookExtMng;
	}

	@Autowired
	public void setCmsGuestbookCtgMng(CmsGuestbookCtgMng cmsGuestbookCtgMng) {
		this.cmsGuestbookCtgMng = cmsGuestbookCtgMng;
	}

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}
}