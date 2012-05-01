package foo.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.assist.CmsCommentDao;
import foo.cms.entity.assist.CmsComment;
import foo.cms.entity.assist.CmsCommentExt;
import foo.cms.manager.assist.CmsCommentExtMng;
import foo.cms.manager.assist.CmsCommentMng;
import foo.cms.manager.assist.CmsSensitivityMng;
import foo.cms.manager.main.CmsSiteMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.manager.main.ContentCountMng;
import foo.cms.manager.main.ContentMng;
import foo.common.hibernate3.Updater;
import foo.common.page.Pagination;

@Service
@Transactional
public class CmsCommentMngImpl implements CmsCommentMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer contentId,
			Integer greaterThen, Boolean checked, boolean recommend,
			boolean desc, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, contentId, greaterThen, checked,
				recommend, desc, pageNo, pageSize, false);
		return page;
	}

	@Transactional(readOnly = true)
	public Pagination getPageForTag(Integer siteId, Integer contentId,
			Integer greaterThen, Boolean checked, boolean recommend,
			boolean desc, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, contentId, greaterThen, checked,
				recommend, desc, pageNo, pageSize, true);
		return page;
	}
	public Pagination getPageForMember(Integer siteId, Integer contentId,Integer toUserId,Integer fromUserId,
			Integer greaterThen, Boolean checked, Boolean recommend,
			boolean desc, int pageNo, int pageSize){
		Pagination page = dao.getPageForMember(siteId, contentId,toUserId,fromUserId, greaterThen, checked,
				recommend, desc, pageNo, pageSize, false);
		return page;
	}
	
	@Transactional(readOnly = true)
	public List<CmsComment> getListForDel(Integer siteId, Integer userId,Integer commentUserId,String ip){
		return dao.getListForDel(siteId,userId,commentUserId,ip);
	}

	@Transactional(readOnly = true)
	public List<CmsComment> getListForTag(Integer siteId, Integer contentId,
			Integer greaterThen, Boolean checked, boolean recommend,
			boolean desc, int count) {
		return dao.getList(siteId, contentId, greaterThen, checked, recommend,
				desc, count, true);
	}

	@Transactional(readOnly = true)
	public CmsComment findById(Integer id) {
		CmsComment entity = dao.findById(id);
		return entity;
	}

	public CmsComment comment(String text, String ip, Integer contentId,
			Integer siteId, Integer userId, boolean checked, boolean recommend) {
		CmsComment comment = new CmsComment();
		comment.setContent(contentMng.findById(contentId));
		comment.setSite(cmsSiteMng.findById(siteId));
		if (userId != null) {
			comment.setCommentUser(cmsUserMng.findById(userId));
		}
		comment.setChecked(checked);
		comment.setRecommend(recommend);
		comment.init();
		dao.save(comment);
		text = cmsSensitivityMng.replaceSensitivity(text);
		cmsCommentExtMng.save(ip, text, comment);
		contentCountMng.commentCount(contentId);
		return comment;
	}

	public CmsComment update(CmsComment bean, CmsCommentExt ext) {
		Updater<CmsComment> updater = new Updater<CmsComment>(bean);
		bean = dao.updateByUpdater(updater);
		cmsCommentExtMng.update(ext);
		return bean;
	}

	public int deleteByContentId(Integer contentId) {
		cmsCommentExtMng.deleteByContentId(contentId);
		return dao.deleteByContentId(contentId);
	}

	public CmsComment deleteById(Integer id) {
		CmsComment bean = dao.deleteById(id);
		return bean;
	}

	public CmsComment[] deleteByIds(Integer[] ids) {
		CmsComment[] beans = new CmsComment[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public void ups(Integer id) {
		CmsComment comment = findById(id);
		comment.setUps((short) (comment.getUps() + 1));
	}

	public void downs(Integer id) {
		CmsComment comment = findById(id);
		comment.setDowns((short) (comment.getDowns() + 1));
	}

	private CmsSensitivityMng cmsSensitivityMng;
	private CmsUserMng cmsUserMng;
	private CmsSiteMng cmsSiteMng;
	private ContentMng contentMng;
	private ContentCountMng contentCountMng;
	private CmsCommentExtMng cmsCommentExtMng;
	private CmsCommentDao dao;

	@Autowired
	public void setCmsSensitivityMng(CmsSensitivityMng cmsSensitivityMng) {
		this.cmsSensitivityMng = cmsSensitivityMng;
	}

	@Autowired
	public void setCmsUserMng(CmsUserMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setContentMng(ContentMng contentMng) {
		this.contentMng = contentMng;
	}

	@Autowired
	public void setCmsCommentExtMng(CmsCommentExtMng cmsCommentExtMng) {
		this.cmsCommentExtMng = cmsCommentExtMng;
	}

	@Autowired
	public void setContentCountMng(ContentCountMng contentCountMng) {
		this.contentCountMng = contentCountMng;
	}

	@Autowired
	public void setDao(CmsCommentDao dao) {
		this.dao = dao;
	}

}