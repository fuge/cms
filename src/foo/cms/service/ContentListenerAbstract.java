package foo.cms.service;

import java.util.Map;

import foo.cms.entity.main.Content;

/**
 * ContentListener的抽象实现
 * 
 * @author liufang
 * 
 */
public class ContentListenerAbstract implements ContentListener {
	public void afterChange(Content content, Map<String, Object> map) {
	}

	public void afterDelete(Content content) {
	}

	public void afterSave(Content content) {
	}

	public Map<String, Object> preChange(Content content) {
		return null;
	}

	public void preDelete(Content content) {
	}

	public void preSave(Content content) {
	}
}
