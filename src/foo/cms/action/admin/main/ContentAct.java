package foo.cms.action.admin.main;

import static foo.common.page.SimplePage.cpn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsGroup;
import foo.cms.entity.main.CmsModel;
import foo.cms.entity.main.CmsModelItem;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.CmsTopic;
import foo.cms.entity.main.CmsUser;
import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentExt;
import foo.cms.entity.main.ContentTxt;
import foo.cms.entity.main.ContentType;
import foo.cms.entity.main.Content.ContentStatus;
import foo.cms.manager.main.ChannelMng;
import foo.cms.manager.main.CmsGroupMng;
import foo.cms.manager.main.CmsLogMng;
import foo.cms.manager.main.CmsModelItemMng;
import foo.cms.manager.main.CmsModelMng;
import foo.cms.manager.main.CmsTopicMng;
import foo.cms.manager.main.CmsUserMng;
import foo.cms.manager.main.ContentMng;
import foo.cms.manager.main.ContentTypeMng;
import foo.cms.staticpage.exception.ContentNotCheckedException;
import foo.cms.staticpage.exception.GeneratedZeroStaticPageException;
import foo.cms.staticpage.exception.StaticPageNotOpenException;
import foo.cms.staticpage.exception.TemplateNotFoundException;
import foo.cms.staticpage.exception.TemplateParseException;
import foo.cms.web.CmsUtils;
import foo.cms.web.WebErrors;
import foo.common.page.Pagination;
import foo.common.upload.FileRepository;
import foo.common.util.StrUtils;
import foo.common.web.CookieUtils;
import foo.common.web.RequestUtils;
import foo.common.web.ResponseUtils;
import foo.common.web.springmvc.MessageResolver;
import foo.core.entity.Ftp;
import foo.core.manager.DbFileMng;
import foo.core.tpl.TplManager;
import foo.core.web.CoreUtils;

@Controller
public class ContentAct {
	private static final Logger log = LoggerFactory.getLogger(ContentAct.class);

	@RequestMapping("/content/v_left.do")
	public String left() {
		return "content/left";
	}

	/**
	 * 栏目导航
	 * 
	 * @param root
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/content/v_tree.do")
	public String tree(String root, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		log.debug("tree path={}", root);
		boolean isRoot;
		// jquery treeview的根请求为root=source
		if (StringUtils.isBlank(root) || "source".equals(root)) {
			isRoot = true;
		} else {
			isRoot = false;
		}
		model.addAttribute("isRoot", isRoot);
		WebErrors errors = validateTree(root, request);
		if (errors.hasErrors()) {
			log.error(errors.getErrors().get(0));
			ResponseUtils.renderJson(response, "[]");
			return null;
		}
		Integer siteId = CmsUtils.getSiteId(request);
		Integer userId = CmsUtils.getUserId(request);
		List<Channel> list;
		if (isRoot) {
			list = channelMng.getTopListByRigth(userId, siteId, true);
		} else {
			list = channelMng.getChildListByRight(userId, siteId, Integer
					.parseInt(root), true);
		}
		model.addAttribute("list", list);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "content/tree";
	}

	/**
	 * 副栏目树
	 * 
	 * @param root
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/content/v_tree_channels.do")
	public String treeChannels(String root, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		tree(root, request, response, model);
		return "content/tree_channels";
	}

	@RequestMapping("/content/v_list.do")
	public String list(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		String queryTitle = RequestUtils.getQueryParam(request, "queryTitle");
		queryTitle = StringUtils.trim(queryTitle);
		String queryInputUsername = RequestUtils.getQueryParam(request,
				"queryInputUsername");
		queryInputUsername = StringUtils.trim(queryInputUsername);
		if (queryTopLevel == null) {
			queryTopLevel = false;
		}
		if (queryRecommend == null) {
			queryRecommend = false;
		}
		if (queryOrderBy == null) {
			queryOrderBy = 0;
		}
		ContentStatus status;
		if (!StringUtils.isBlank(queryStatus)) {
			status = ContentStatus.valueOf(queryStatus);
		} else {
			status = ContentStatus.all;
		}
		Integer queryInputUserId = null;
		if (!StringUtils.isBlank(queryInputUsername)) {
			CmsUser u = cmsUserMng.findByUsername(queryInputUsername);
			if (u != null) {
				queryInputUserId = u.getId();
			} else {
				// 用户名不存在，清空。
				queryInputUsername = null;
			}
		}
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsUser user = CmsUtils.getUser(request);
		Integer userId = user.getId();
		byte currStep = user.getCheckStep(siteId);
		Pagination p = manager.getPageByRight(queryTitle, queryTypeId,
				queryInputUserId, queryTopLevel, queryRecommend, status, user
						.getCheckStep(siteId), siteId, cid, userId,
				queryOrderBy, cpn(pageNo), CookieUtils.getPageSize(request));
		List<ContentType> typeList = contentTypeMng.getList(true);
		model.addAttribute("pagination", p);
		model.addAttribute("cid", cid);
		model.addAttribute("typeList", typeList);
		model.addAttribute("currStep", currStep);

		addAttibuteForQuery(model, queryTitle, queryInputUsername, queryStatus,
				queryTypeId, queryTopLevel, queryRecommend, queryOrderBy,
				pageNo);

		return "content/list";
	}

	@RequestMapping("/content/v_add.do")
	public String add(Integer cid, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateAdd(cid, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsUser user = CmsUtils.getUser(request);
		Integer userId = user.getId();
		// 栏目
		Channel c;
		if (cid != null) {
			c = channelMng.findById(cid);
		} else {
			c = null;
		}
		// 模型
		CmsModel m;
		if (c != null) {
			m = c.getModel();
		} else {
			m = cmsModelMng.getDefModel();
			// TODO m==null给出错误提示
			if (m == null) {
				throw new RuntimeException("default model not found!");
			}
		}
		// 模型项列表
		List<CmsModelItem> itemList = cmsModelItemMng.getList(m.getId(), false,
				false);
		// 栏目列表
		List<Channel> channelList;
		Set<Channel> rights;
		if (user.getUserSite(siteId).getAllChannel()) {
			// 拥有所有栏目权限
			rights = null;
		} else {
			rights = user.getChannels(siteId);
		}
		if (c != null) {
			channelList = c.getListForSelect(rights, true);
		} else {
			List<Channel> topList = channelMng.getTopListByRigth(userId,
					siteId, true);
			channelList = Channel.getListForSelect(topList, rights, true);
		}

		// 专题列表
		List<CmsTopic> topicList;
		if (c != null) {
			topicList = cmsTopicMng.getListByChannel(c.getId());
		} else {
			topicList = new ArrayList<CmsTopic>();
		}
		// 内容模板列表
		List<String> tplList = getTplContent(site, m, null);
		// 会员组列表
		List<CmsGroup> groupList = cmsGroupMng.getList();
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);

		model.addAttribute("model", m);
		model.addAttribute("itemList", itemList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("topicList", topicList);
		model.addAttribute("tplList", tplList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("typeList", typeList);
		if (cid != null) {
			model.addAttribute("cid", cid);
		}
		if (c != null) {
			model.addAttribute("channel", c);
		}
		return "content/add";
	}

	@RequestMapping("/content/v_view.do")
	public String view(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer pageNo, Integer cid, Integer id,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateView(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		byte currStep = user.getCheckStep(site.getId());
		Content content = manager.findById(id);

		model.addAttribute("content", content);
		model.addAttribute("currStep", currStep);

		if (cid != null) {
			model.addAttribute("cid", cid);
		}
		String queryTitle = RequestUtils.getQueryParam(request, "queryTitle");
		String queryInputUsername = RequestUtils.getQueryParam(request,
				"queryInputUsername");
		addAttibuteForQuery(model, queryTitle, queryInputUsername, queryStatus,
				queryTypeId, queryTopLevel, queryRecommend, queryOrderBy,
				pageNo);
		return "content/view";
	}

	@RequestMapping("/content/v_edit.do")
	public String edit(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer pageNo, Integer cid, Integer id,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsUser user = CmsUtils.getUser(request);
		// 内容
		Content content = manager.findById(id);
		// 栏目
		Channel channel = content.getChannel();
		// 模型
		CmsModel m = channel.getModel();
		// 模型项列表
		List<CmsModelItem> itemList = cmsModelItemMng.getList(m.getId(), false,
				false);
		// 栏目列表
		Set<Channel> rights;
		if (user.getUserSite(siteId).getAllChannel()) {
			// 拥有所有栏目权限
			rights = null;
		} else {
			rights = user.getChannels(siteId);
		}

		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, rights,
				true);

		// 专题列表
		List<CmsTopic> topicList = cmsTopicMng
				.getListByChannel(channel.getId());
		Set<CmsTopic> topics = content.getTopics();
		for (CmsTopic t : topics) {
			if (!topicList.contains(t)) {
				topicList.add(t);
			}
		}
		Integer[] topicIds = CmsTopic.fetchIds(content.getTopics());
		// 内容模板列表
		List<String> tplList = getTplContent(site, m, content.getTplContent());
		// 会员组列表
		List<CmsGroup> groupList = cmsGroupMng.getList();
		Integer[] groupIds = CmsGroup.fetchIds(content.getViewGroups());
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);
		// 当前模板，去除基本路径
		int tplPathLength = site.getTplPath().length();
		String tplContent = content.getTplContent();
		if (!StringUtils.isBlank(tplContent)) {
			tplContent = tplContent.substring(tplPathLength);
		}

		model.addAttribute("content", content);
		model.addAttribute("channel", channel);
		model.addAttribute("model", m);
		model.addAttribute("itemList", itemList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("topicList", topicList);
		model.addAttribute("topicIds", topicIds);
		model.addAttribute("tplList", tplList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("groupIds", groupIds);
		model.addAttribute("typeList", typeList);
		model.addAttribute("tplContent", tplContent);
		if (cid != null) {
			model.addAttribute("cid", cid);
		}

		String queryTitle = RequestUtils.getQueryParam(request, "queryTitle");
		String queryInputUsername = RequestUtils.getQueryParam(request,
				"queryInputUsername");
		addAttibuteForQuery(model, queryTitle, queryInputUsername, queryStatus,
				queryTypeId, queryTopLevel, queryRecommend, queryOrderBy,
				pageNo);

		return "content/edit";
	}

	@RequestMapping("/content/o_save.do")
	public String save(Content bean, ContentExt ext, ContentTxt txt,
			Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds,
			String[] attachmentPaths, String[] attachmentNames,
			String[] attachmentFilenames, String[] picPaths, String[] picDescs,
			Integer channelId, Integer typeId, String tagStr, Boolean draft,
			Integer cid, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, channelId, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		// 加上模板前缀
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String tplPath = site.getTplPath();
		if (!StringUtils.isBlank(ext.getTplContent())) {
			ext.setTplContent(tplPath + ext.getTplContent());
		}
		bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", MessageResolver
				.getMessage(request, "content.tagStr.split"));
		bean = manager.save(bean, ext, txt, channelIds, topicIds, viewGroupIds,
				tagArr, attachmentPaths, attachmentNames, attachmentFilenames,
				picPaths, picDescs, channelId, typeId, draft, user, false);
		log.info("save Content id={}", bean.getId());
		cmsLogMng.operating(request, "content.log.save", "id=" + bean.getId()
				+ ";title=" + bean.getTitle());
		if (cid != null) {
			model.addAttribute("cid", cid);
		}
		model.addAttribute("message", "global.success");
		return add(cid, request, model);
	}

	@RequestMapping("/content/o_update.do")
	public String update(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Content bean, ContentExt ext, ContentTxt txt,
			Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds,
			String[] attachmentPaths, String[] attachmentNames,
			String[] attachmentFilenames, String[] picPaths, String[] picDescs,
			Integer channelId, Integer typeId, String tagStr, Boolean draft,
			Integer cid, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		// 加上模板前缀
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String tplPath = site.getTplPath();
		if (!StringUtils.isBlank(ext.getTplContent())) {
			ext.setTplContent(tplPath + ext.getTplContent());
		}
		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", MessageResolver
				.getMessage(request, "content.tagStr.split"));
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		bean = manager.update(bean, ext, txt, tagArr, channelIds, topicIds,
				viewGroupIds, attachmentPaths, attachmentNames,
				attachmentFilenames, picPaths, picDescs, attr, channelId,
				typeId, draft, user, false);
		log.info("update Content id={}.", bean.getId());
		cmsLogMng.operating(request, "content.log.update", "id=" + bean.getId()
				+ ";title=" + bean.getTitle());
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequestMapping("/content/o_delete.do")
	public String delete(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Content[] beans;
		// 是否开启回收站
		if (site.getResycleOn()) {
			beans = manager.cycle(ids);
			for (Content bean : beans) {
				log.info("delete to cycle, Content id={}", bean.getId());
			}
		} else {
			beans = manager.deleteByIds(ids);
			for (Content bean : beans) {
				log.info("delete Content id={}", bean.getId());
				cmsLogMng.operating(request, "content.log.delete", "id="
						+ bean.getId() + ";title=" + bean.getTitle());
			}
		}
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequestMapping("/content/o_check.do")
	public String check(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateCheck(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser user = CmsUtils.getUser(request);
		Content[] beans = manager.check(ids, user);
		for (Content bean : beans) {
			log.info("check Content id={}", bean.getId());
		}
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequestMapping("/content/o_static.do")
	public String contentStatic(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateStatic(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		try {
			Content[] beans = manager.contentStatic(ids);
			for (Content bean : beans) {
				log.info("static Content id={}", bean.getId());
			}
			model.addAttribute("message", errors.getMessage(
					"content.staticGenerated", beans.length));
		} catch (TemplateNotFoundException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(),
					new Object[] { e.getErrorTitle(), e.getGenerated() }));
		} catch (TemplateParseException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(),
					new Object[] { e.getErrorTitle(), e.getGenerated() }));
		} catch (GeneratedZeroStaticPageException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(), e
					.getGenerated()));
		} catch (StaticPageNotOpenException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(),
					new Object[] { e.getErrorTitle(), e.getGenerated() }));
		} catch (ContentNotCheckedException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(),
					new Object[] { e.getErrorTitle(), e.getGenerated() }));
		}
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequestMapping("/content/o_reject.do")
	public String reject(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Byte rejectStep,
			String rejectOpinion, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateReject(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser user = CmsUtils.getUser(request);
		Content[] beans = manager.reject(ids, user, rejectStep, rejectOpinion);
		for (Content bean : beans) {
			log.info("reject Content id={}", bean.getId());
		}
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequestMapping("/content/o_upload_attachment.do")
	public String uploadAttachment(
			@RequestParam(value = "attachmentFile", required = false) MultipartFile file,
			String attachmentNum, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpload(file, request);
		if (errors.hasErrors()) {
			model.addAttribute("error", errors.getErrors().get(0));
			return "content/attachment_iframe";
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
			model.addAttribute("attachmentPath", fileUrl);
			model.addAttribute("attachmentName", origName);
			model.addAttribute("attachmentNum", attachmentNum);
		} catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		} catch (IOException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		}
		return "content/attachment_iframe";
	}

	@RequestMapping("/content/o_upload_media.do")
	public String uploadMedia(
			@RequestParam(value = "mediaFile", required = false) MultipartFile file,
			String filename, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpload(file, request);
		if (errors.hasErrors()) {
			model.addAttribute("error", errors.getErrors().get(0));
			return "content/media_iframe";
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
				if (!StringUtils.isBlank(filename)
						&& FilenameUtils.getExtension(filename).equals(ext)) {
					filename = filename.substring(dbFilePath.length());
					fileUrl = dbFileMng.storeByFilename(filename, file
							.getInputStream());
				} else {
					fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext,
							file.getInputStream());
					// 加上访问地址
					fileUrl = request.getContextPath() + dbFilePath + fileUrl;
				}
			} else if (site.getUploadFtp() != null) {
				Ftp ftp = site.getUploadFtp();
				String ftpUrl = ftp.getUrl();
				if (!StringUtils.isBlank(filename)
						&& FilenameUtils.getExtension(filename).equals(ext)) {
					filename = filename.substring(ftpUrl.length());
					fileUrl = ftp.storeByFilename(filename, file
							.getInputStream());
				} else {
					fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file
							.getInputStream());
					// 加上url前缀
					fileUrl = ftpUrl + fileUrl;
				}
			} else {
				String ctx = request.getContextPath();
				if (!StringUtils.isBlank(filename)
						&& FilenameUtils.getExtension(filename).equals(ext)) {
					filename = filename.substring(ctx.length());
					fileUrl = fileRepository.storeByFilename(filename, file);
				} else {
					fileUrl = fileRepository.storeByExt(site.getUploadPath(),
							ext, file);
					// 加上部署路径
					fileUrl = ctx + fileUrl;
				}
			}
			model.addAttribute("mediaPath", fileUrl);
			model.addAttribute("mediaExt", ext);
		} catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		} catch (IOException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		}
		return "content/media_iframe";
	}

	@RequestMapping("/content_cycle/v_list.do")
	public String cycleList(Integer queryTypeId, Boolean queryTopLevel,
			Boolean queryRecommend, Integer queryOrderBy, Integer cid,
			Integer pageNo, HttpServletRequest request, ModelMap model) {
		list(ContentStatus.recycle.toString(), queryTypeId, queryTopLevel,
				queryRecommend, queryOrderBy, cid, pageNo, request, model);
		return "content/cycle_list";
	}

	@RequestMapping("/content_cycle/o_recycle.do")
	public String cycleRecycle(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Content[] beans = manager.recycle(ids);
		for (Content bean : beans) {
			log.info("delete Content id={}", bean.getId());
		}
		return cycleList(queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequestMapping("/content_cycle/o_delete.do")
	public String cycleDelete(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Content[] beans = manager.deleteByIds(ids);
		for (Content bean : beans) {
			log.info("delete Content id={}", bean.getId());
		}
		return cycleList(queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	private void addAttibuteForQuery(ModelMap model, String queryTitle,
			String queryInputUsername, String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer pageNo) {
		if (!StringUtils.isBlank(queryTitle)) {
			model.addAttribute("queryTitle", queryTitle);
		}
		if (!StringUtils.isBlank(queryInputUsername)) {
			model.addAttribute("queryInputUsername", queryInputUsername);
		}
		if (queryTypeId != null) {
			model.addAttribute("queryTypeId", queryTypeId);
		}
		if (queryStatus != null) {
			model.addAttribute("queryStatus", queryStatus);
		}
		if (queryTopLevel != null) {
			model.addAttribute("queryTopLevel", queryTopLevel);
		}
		if (queryRecommend != null) {
			model.addAttribute("queryRecommend", queryRecommend);
		}
		if (queryOrderBy != null) {
			model.addAttribute("queryOrderBy", queryOrderBy);
		}
		if (pageNo != null) {
			model.addAttribute("pageNo", pageNo);
		}
	}

	private List<String> getTplContent(CmsSite site, CmsModel model, String tpl) {
		String sol = site.getSolutionPath();
		String tplPath = site.getTplPath();
		List<String> tplList = tplManager.getNameListByPrefix(model
				.getTplContent(sol, false));
		tplList = CoreUtils.tplTrim(tplList, tplPath, tpl);
		return tplList;
	}

	private WebErrors validateTree(String path, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		// if (errors.ifBlank(path, "path", 255)) {
		// return errors;
		// }
		return errors;
	}

	private WebErrors validateAdd(Integer cid, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (cid == null) {
			return errors;
		}
		Channel c = channelMng.findById(cid);
		if (errors.ifNotExist(c, Channel.class, cid)) {
			return errors;
		}
		Integer siteId = CmsUtils.getSiteId(request);
		if (!c.getSite().getId().equals(siteId)) {
			errors.notInSite(Channel.class, cid);
			return errors;
		}
		return errors;
	}

	private WebErrors validateSave(Content bean, Integer channelId,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		if (errors.ifNull(channelId, "channelId")) {
			return errors;
		}
		Channel channel = channelMng.findById(channelId);
		if (errors.ifNotExist(channel, Channel.class, channelId)) {
			return errors;
		}
		if (channel.getChild().size() > 0) {
			errors.addErrorCode("content.error.notLeafChannel");
		}
		return errors;
	}

	private WebErrors validateView(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		// Content content = manager.findById(id);
		// TODO 是否有编辑的数据权限。
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		Content content = manager.findById(id);
		// TODO 是否有编辑的数据权限。
		// 是否有审核后更新权限。
		if (!content.isHasUpdateRight()) {
			errors.addErrorCode("content.error.afterCheckUpdate");
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			if (vldExist(id, site.getId(), errors)) {
				return errors;
			}
			Content content = manager.findById(id);
			// TODO 是否有编辑的数据权限。
			// 是否有审核后删除权限。
			if (!content.isHasDeleteRight()) {
				errors.addErrorCode("content.error.afterCheckDelete");
				return errors;
			}

		}
		return errors;
	}

	private WebErrors validateCheck(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private WebErrors validateStatic(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private WebErrors validateReject(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private WebErrors validateUpload(MultipartFile file,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifNull(file, "file")) {
			return errors;
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		Content entity = manager.findById(id);
		if (errors.ifNotExist(entity, Content.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(Content.class, id);
			return true;
		}
		return false;
	}

	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsModelMng cmsModelMng;
	@Autowired
	private CmsModelItemMng cmsModelItemMng;
	@Autowired
	private CmsTopicMng cmsTopicMng;
	@Autowired
	private CmsGroupMng cmsGroupMng;
	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private TplManager tplManager;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private DbFileMng dbFileMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private ContentMng manager;

}