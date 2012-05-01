package foo.cms.action.admin.main;

import static foo.common.web.Constants.MESSAGE;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import foo.cms.entity.main.CmsModel;
import foo.cms.entity.main.CmsModelItem;
import foo.cms.manager.main.CmsModelItemMng;
import foo.cms.manager.main.CmsModelMng;
import foo.cms.web.WebErrors;
import foo.common.web.springmvc.MessageResolver;

@Controller
public class CmsModelItemAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsModelItemAct.class);

	@RequestMapping("/item/v_list.do")
	public String list(Integer modelId, Boolean isChannel,
			HttpServletRequest request, ModelMap model) {
		CmsModel m = cmsModelMng.findById(modelId);
		List<CmsModelItem> list = manager.getList(modelId, isChannel, true);
		model.addAttribute("model", m);
		model.addAttribute("fieldList", getFieldList(list));
		model.addAttribute("modelId", modelId);
		model.addAttribute("isChannel", isChannel);
		model.addAttribute("list", list);
		if (isChannel) {
			return "item/list_channel";
		} else {
			return "item/list_content";
		}
	}

	@RequestMapping("/item/v_add.do")
	public String add(Integer modelId, Boolean isChannel, ModelMap model) {
		CmsModel m = cmsModelMng.findById(modelId);
		model.addAttribute("model", m);
		model.addAttribute("modelId", modelId);
		model.addAttribute("isChannel", isChannel);
		return "item/add";
	}

	@RequestMapping("/item/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsModelItem item = manager.findById(id);
		model.addAttribute("cmsModelItem", item);
		return "item/edit";
	}

	@RequestMapping("/item/o_priority.do")
	public String priority(Integer[] wids, Integer[] priority, String[] label,
			Boolean[] single, Boolean[] display, Integer modelId,
			Boolean isChannel, HttpServletRequest request, ModelMap model) {
		if (wids != null && wids.length > 0) {
			manager.updatePriority(wids, priority, label, single, display);
		}
		model.addAttribute(MESSAGE, "global.success");
		return list(modelId, isChannel, request, model);
	}

	@RequestMapping("/item/o_save_list.do")
	public String saveList(Integer modelId, Boolean isChannel, String[] fields,
			String[] labels, Integer[] dataTypes, Integer[] prioritys,
			Boolean[] singles, Boolean[] displays, HttpServletRequest request,
			ModelMap model) {
		CmsModel m = cmsModelMng.findById(modelId);
		List<CmsModelItem> itemList = getItems(m, isChannel, fields, labels,
				dataTypes, prioritys, singles, displays);
		manager.saveList(itemList);
		log.info("save CmsModelItem count={}", itemList.size());
		model.addAttribute("modelId", modelId);
		model.addAttribute("isChannel", isChannel);
		return "redirect:v_list.do";
	}

	@RequestMapping("/item/o_save.do")
	public String save(CmsModelItem bean, Integer modelId, Boolean isChannel,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, modelId, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean, modelId);
		log.info("update CmsModelItem id={}.", bean.getId());
		model.addAttribute("modelId", bean.getModel().getId());
		model.addAttribute("isChannel", bean.getChannel());
		return "redirect:v_list.do";
	}

	@RequestMapping("/item/o_update.do")
	public String update(CmsModelItem bean, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update CmsModelItem id={}.", bean.getId());
		model.addAttribute("modelId", bean.getModel().getId());
		model.addAttribute("isChannel", bean.getChannel());
		return "redirect:v_list.do";
	}

	@RequestMapping("/item/o_delete.do")
	public String delete(Integer[] ids, Integer modelId, Boolean isChannel,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsModelItem[] beans = manager.deleteByIds(ids);
		for (CmsModelItem bean : beans) {
			log.info("delete CmsModelItem id={}", bean.getId());
		}
		model.addAttribute("modelId", modelId);
		model.addAttribute("isChannel", isChannel);
		return "redirect:v_list.do";
	}

	private List<String> getFieldList(List<CmsModelItem> items) {
		List<String> list = new ArrayList<String>(items.size());
		for (CmsModelItem item : items) {
			list.add(item.getField());
		}
		return list;
	}

	private List<CmsModelItem> getItems(CmsModel model, boolean isChannel,
			String[] fields, String[] labels, Integer[] dataTypes,
			Integer[] prioritys, Boolean[] singles, Boolean[] displays) {
		List<CmsModelItem> list = new ArrayList<CmsModelItem>();
		CmsModelItem item;
		for (int i = 0, len = fields.length; i < len; i++) {
			if (!StringUtils.isBlank(fields[i])) {
				item = new CmsModelItem();
				item.setCustom(false);
				item.setModel(model);
				item.setChannel(isChannel);

				item.setField(fields[i]);
				item.setLabel(labels[i]);
				item.setPriority(prioritys[i]);
				item.setDataType(dataTypes[i]);
				item.setSingle(singles[i]);
				item.setDisplay(displays[i]);

				list.add(item);
			}
		}
		return list;
	}

	private WebErrors validateSave(CmsModelItem bean, Integer modelId,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (!StringUtils.isBlank(bean.getOptValue())) {
			bean.setOptValue(replaceLocaleSplit(bean.getOptValue(), request));
		}
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, CmsModelItem bean,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (!StringUtils.isBlank(bean.getOptValue())) {
			bean.setOptValue(replaceLocaleSplit(bean.getOptValue(), request));
		}
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsModelItem entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsModelItem.class, id)) {
			return true;
		}
		return false;
	}

	private String replaceLocaleSplit(String s, HttpServletRequest request) {
		String split = MessageResolver.getMessage(request,
				"cmsModelItem.optValue.split");
		return StringUtils.replace(s, split, ",");
	}

	@Autowired
	private CmsModelMng cmsModelMng;
	@Autowired
	private CmsModelItemMng manager;
}