package foo.cms.lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.queryParser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import foo.cms.entity.main.Content;
import foo.cms.service.ContentListenerAbstract;

@Component
public class ContentLuceneListener extends ContentListenerAbstract {
	private static final Logger log = LoggerFactory
			.getLogger(ContentLuceneListener.class);
	/**
	 * 是否已审核
	 */
	private static final String IS_CHECKED = "isChecked";

	@Override
	public void afterSave(Content content) {
		if (content.isChecked()) {
			try {
				luceneContentSvc.createIndex(content);
			} catch (IOException e) {
				log.error("", e);
			}
		}
	}

	@Override
	public Map<String, Object> preChange(Content content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IS_CHECKED, content.isChecked());
		return map;
	}

	@Override
	public void afterChange(Content content, Map<String, Object> map) {
		boolean pre = (Boolean) map.get(IS_CHECKED);
		boolean curr = content.isChecked();
		try {
			if (pre && !curr) {
				luceneContentSvc.deleteIndex(content.getId());
			} else if (!pre && curr) {
				luceneContentSvc.createIndex(content);
			} else if (pre && curr) {
				luceneContentSvc.updateIndex(content);
			}
		} catch (IOException e) {
			log.error("", e);
		} catch (ParseException e) {
			log.error("", e);
		}
	}

	@Override
	public void afterDelete(Content content) {
		try {
			luceneContentSvc.deleteIndex(content.getId());
		} catch (IOException e) {
			log.error("", e);
		} catch (ParseException e) {
			log.error("", e);
		}
	}

	private LuceneContentSvc luceneContentSvc;

	@Autowired
	public void setLuceneContentSvc(LuceneContentSvc luceneContentSvc) {
		this.luceneContentSvc = luceneContentSvc;
	}
}
