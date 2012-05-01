package foo.cms.staticpage;

import java.io.IOException;
import java.util.Date;
import java.util.Map;


import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.Content;
import freemarker.template.TemplateException;

public interface StaticPageSvc {
	public int content(Integer siteId, Integer channelId, Date start)
			throws IOException, TemplateException;

	public boolean content(Content content) throws IOException, TemplateException;

	public void contentRelated(Content content) throws IOException,
			TemplateException;

	public void deleteContent(Content content);

	public int channel(Integer siteId, Integer channelId, boolean containChild)
			throws IOException, TemplateException;

	public void channel(Channel channel, boolean firstOnly) throws IOException,
			TemplateException;

	public void deleteChannel(Channel channel);

	public void index(CmsSite site) throws IOException, TemplateException;

	public void index(CmsSite site, String tpl, Map<String, Object> data)
			throws IOException, TemplateException;

	public boolean deleteIndex(CmsSite site);
}
