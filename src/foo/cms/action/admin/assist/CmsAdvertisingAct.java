package foo.cms.action.admin.assist;

import static foo.common.page.SimplePage.cpn;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import foo.cms.entity.assist.CmsAdvertising;
import foo.cms.entity.assist.CmsAdvertisingSpace;
import foo.cms.entity.main.CmsSite;
import foo.cms.manager.assist.CmsAdvertisingMng;
import foo.cms.manager.assist.CmsAdvertisingSpaceMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.upload.FileRepository;
import foo.common.web.CookieUtils;
import foo.common.web.RequestUtils;
import foo.core.entity.Ftp;
import foo.core.manager.DbFileMng;

@Controller
public class CmsAdvertisingAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsAdvertisingAct.class);

	@RequestMapping("/advertising/v_list.do")
	public String list(Integer queryAdspaceId, Boolean queryEnabled,
			Integer pageNo, HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Pagination pagination = manager.getPage(site.getId(), queryAdspaceId,
				queryEnabled, cpn(pageNo), CookieUtils.getPageSize(request));
		List<CmsAdvertisingSpace> adspaceList = cmsAdvertisingSpaceMng
				.getList(site.getId());
		model.addAttribute("pagination", pagination);
		model.addAttribute("adspaceList", adspaceList);
		model.addAttribute("pageNo", pagination.getPageNo());
		if (queryAdspaceId != null) {
			model.addAttribute("queryAdspaceId", queryAdspaceId);
		}
		if (queryEnabled != null) {
			model.addAttribute("queryEnabled", queryEnabled);
		}
		return "advertising/list";
	}

	@RequestMapping("/advertising/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<CmsAdvertisingSpace> adspaceList = cmsAdvertisingSpaceMng
				.getList(site.getId());
		model.addAttribute("adspaceList", adspaceList);
		return "advertising/add";
	}

	@RequestMapping("/advertising/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsAdvertising cmsAdvertising = manager.findById(id);
		model.addAttribute("cmsAdvertising", cmsAdvertising);
		model.addAttribute("attr", cmsAdvertising.getAttr());
		model.addAttribute("adspaceList", cmsAdvertisingSpaceMng.getList(site
				.getId()));
		model.addAttribute("pageNo", pageNo);
		return "advertising/edit";
	}

	@RequestMapping("/advertising/o_save.do")
	public String save(CmsAdvertising bean, Integer adspaceId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		// 去除为空串的属性
		Set<String> toRemove = new HashSet<String>();
		for (Entry<String, String> entry : attr.entrySet()) {
			if (StringUtils.isBlank(entry.getValue())) {
				toRemove.add(entry.getKey());
			}
		}
		for (String key : toRemove) {
			attr.remove(key);
		}
		bean = manager.save(bean, adspaceId, attr);
		log.info("save CmsAdvertising id={}", bean.getId());
		cmsLogMng.operating(request, "cmsAdvertising.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequestMapping("/advertising/o_update.do")
	public String update(Integer queryAdspaceId, Boolean queryEnabled,
			CmsAdvertising bean, Integer adspaceId, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		// 去除为空串的属性
		Set<String> toRemove = new HashSet<String>();
		for (Entry<String, String> entry : attr.entrySet()) {
			if (StringUtils.isBlank(entry.getValue())) {
				toRemove.add(entry.getKey());
			}
		}
		for (String key : toRemove) {
			attr.remove(key);
		}
		bean = manager.update(bean, adspaceId, attr);
		log.info("update CmsAdvertising id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsAdvertising.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(queryAdspaceId, queryEnabled, pageNo, request, model);
	}

	@RequestMapping("/advertising/o_delete.do")
	public String delete(Integer[] ids, Integer queryAdspaceId,
			Boolean queryEnabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsAdvertising[] beans = manager.deleteByIds(ids);
		for (CmsAdvertising bean : beans) {
			log.info("delete CmsAdvertising id={}", bean.getId());
			cmsLogMng.operating(request, "cmsAdvertising.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(queryAdspaceId, queryEnabled, pageNo, request, model);
	}

	@RequestMapping("/advertising/o_upload_flash.do")
	public String uploadFlash(
			@RequestParam(value = "flashFile", required = false) MultipartFile file,
			String flashNum, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpload(file, request);
		if (errors.hasErrors()) {
			model.addAttribute("error", errors.getErrors().get(0));
			return "advertising/flash_iframe";
		}
		CmsSite site = CmsUtils.getSite(request);
		String origName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(origName).toLowerCase(
				Locale.ENGLISH);
		// TODO 检查允许上传的后缀
		try {
			String fileUrl;
			if (site.getConfig().getUploadToDb()) {
				String dbFilePath = site.getConfig().getDbFileUri();
				fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext, file
						.getInputStream());
				// 加上访问地址
				fileUrl = request.getContextPath() + dbFilePath + fileUrl;
			} else if (site.getUploadFtp() != null) {
				Ftp ftp = site.getUploadFtp();
				String ftpUrl = ftp.getUrl();
				fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file
						.getInputStream());
				// 加上url前缀
				fileUrl = ftpUrl + fileUrl;
			} else {
				String ctx = request.getContextPath();
				fileUrl = fileRepository.storeByExt(site.getUploadPath(), ext,
						file);
				// 加上部署路径
				fileUrl = ctx + fileUrl;
			}
			model.addAttribute("flashPath", fileUrl);
			model.addAttribute("flashName", origName);
			model.addAttribute("flashNum", flashNum);
		} catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		} catch (IOException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		}
		return "advertising/flash_iframe";
	}

	private WebErrors validateSave(CmsAdvertising bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsAdvertising entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsAdvertising.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsAdvertising.class, id);
			return true;
		}
		return false;
	}

	private WebErrors validateUpload(MultipartFile file,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifNull(file, "file")) {
			return errors;
		}
		return errors;
	}

	@Autowired
	private CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private DbFileMng dbFileMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsAdvertisingMng manager;
}