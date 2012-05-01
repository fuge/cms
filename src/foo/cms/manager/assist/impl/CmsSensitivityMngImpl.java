package foo.cms.manager.assist.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.dao.assist.CmsSensitivityDao;
import foo.cms.entity.assist.CmsSensitivity;
import foo.cms.manager.assist.CmsSensitivityMng;

@Service
@Transactional
public class CmsSensitivityMngImpl implements CmsSensitivityMng {
	@Transactional(readOnly = true)
	public String replaceSensitivity(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		List<CmsSensitivity> list = getList(true);
		for (CmsSensitivity sen : list) {
			s = StringUtils.replace(s, sen.getSearch(), sen.getReplacement());
		}
		return s;
	}

	@Transactional(readOnly = true)
	public List<CmsSensitivity> getList(boolean cacheable) {
		return dao.getList(cacheable);
	}

	@Transactional(readOnly = true)
	public CmsSensitivity findById(Integer id) {
		CmsSensitivity entity = dao.findById(id);
		return entity;
	}

	public CmsSensitivity save(CmsSensitivity bean) {
		dao.save(bean);
		return bean;
	}

	public void updateEnsitivity(Integer[] ids, String[] searchs,
			String[] replacements) {
		CmsSensitivity ensitivity;
		for (int i = 0, len = ids.length; i < len; i++) {
			ensitivity = findById(ids[i]);
			ensitivity.setSearch(searchs[i]);
			ensitivity.setReplacement(replacements[i]);
		}
	}

	public CmsSensitivity deleteById(Integer id) {
		CmsSensitivity bean = dao.deleteById(id);
		return bean;
	}

	public CmsSensitivity[] deleteByIds(Integer[] ids) {
		CmsSensitivity[] beans = new CmsSensitivity[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsSensitivityDao dao;

	@Autowired
	public void setDao(CmsSensitivityDao dao) {
		this.dao = dao;
	}
}