package foo.cms.staticpage;

import java.io.IOException;
import java.util.Date;
import java.util.Map;


import foo.cms.entity.main.Channel;
import foo.cms.entity.main.Content;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public interface StaticPageDao {

	public int channelStatic(Integer siteId, Integer channelId,
			boolean containChild, Configuration conf, Map<String, Object> data)
			throws IOException, TemplateException;

	public void channelStatic(Channel c, boolean firstOnly, Configuration conf,
			Map<String, Object> data) throws IOException, TemplateException;

	public int contentStatic(Integer siteId, Integer channelId, Date start,
			Configuration conf, Map<String, Object> data) throws IOException,
			TemplateException;
	
	public boolean contentStatic(Content c, Configuration conf,
			Map<String, Object> data) throws IOException, TemplateException;

	public int contentsOfChannel(Integer channelId);

	public int childsOfChannel(Integer channelId);
}
