package foo.cms.staticpage;

import static foo.cms.Constants.TPLDIR_INDEX;
import static foo.cms.action.front.DynamicPageAct.TPL_INDEX;
import static foo.common.web.Constants.UTF8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.Content;
import foo.cms.web.FrontUtils;
import foo.common.web.Constants;
import foo.common.web.springmvc.RealPathResolver;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class StaticPageSvcImpl implements StaticPageSvc, InitializingBean {
	private Logger log = LoggerFactory.getLogger(StaticPageSvcImpl.class);

	@Transactional
	public int content(Integer siteId, Integer channelId, Date start)
			throws IOException, TemplateException {
		long time = System.currentTimeMillis();
		Map<String, Object> data = new HashMap<String, Object>();
		int count = staticPageDao.contentStatic(siteId, channelId, start, conf,
				data);
		time = System.currentTimeMillis() - time;
		log.info("create content page count {}, in {} ms", count, time);
		return count;
	}

	@Transactional
	public boolean content(Content content) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
		boolean generated = staticPageDao.contentStatic(content, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create content page in {} ms", time);
		return generated;
	}

	@Transactional(readOnly = true)
	public void contentRelated(Content content) throws IOException,
			TemplateException {
		content(content);
		Channel channel = content.getChannel();
		while (channel != null) {
			channel(channel, true);
			channel = channel.getParent();
		}
		if (content.getSite().getStaticIndex()) {
			index(content.getSite());
		}
	}

	@Transactional(readOnly = true)
	public void deleteContent(Content content) {
		String real;
		File file;
		int totalPage = content.getPageCount();
		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			real = realPathResolver.get(content.getStaticFilename(pageNo));
			file = new File(real);
			file.delete();
		}
	}

	@Transactional(readOnly = true)
	public int channel(Integer siteId, Integer channelId, boolean containChild)
			throws IOException, TemplateException {
		long time = System.currentTimeMillis();
		Map<String, Object> data = new HashMap<String, Object>();
		int count = staticPageDao.channelStatic(siteId, channelId,
				containChild, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create channel page count {}, in {} ms", count, time);
		return count;
	}

	@Transactional(readOnly = true)
	public void channel(Channel channel, boolean firstOnly) throws IOException,
			TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		long time = System.currentTimeMillis();
		staticPageDao.channelStatic(channel, firstOnly, conf, data);
		time = System.currentTimeMillis() - time;
		log.info("create channel page in {} ms", time);
	}

	@Transactional(readOnly = true)
	public void deleteChannel(Channel channel) {
		// 如果是外部链接或者不需要生产静态页，则不删除
		if (!StringUtils.isBlank(channel.getLink())
				|| !channel.getStaticChannel()) {
			return;
		}
		// 没有内容或者有子栏目，则只删除一页
		int childs = staticPageDao.childsOfChannel(channel.getId());
		int quantity, totalPage;
		if (!channel.getModel().getHasContent()
				|| (!channel.getListChild() && childs > 0)) {
			totalPage = 1;
		} else {
			if (channel.getListChild()) {
				quantity = childs;
			} else {
				quantity = staticPageDao.contentsOfChannel(channel.getId());
			}
			if (quantity <= 0) {
				totalPage = 1;
			} else {
				totalPage = (quantity - 1) / channel.getPageSize() + 1;
			}
		}
		String real, filename;
		File f;
		for (int i = 1; i <= totalPage; i++) {
			filename = channel.getStaticFilename(i);
			real = realPathResolver.get(filename.toString());
			f = new File(real);
			f.delete();
		}
	}

	@Transactional(readOnly = true)
	public void index(CmsSite site) throws IOException, TemplateException {
		Map<String, Object> data = new HashMap<String, Object>();
		FrontUtils.frontData(data, site, null, site.getUrlStatic(), null);
		String tpl = FrontUtils.getTplPath(tplMessageSource, site
				.getLocaleAdmin(), site.getSolutionPath(), TPLDIR_INDEX,
				TPL_INDEX);
		index(site, tpl, data);
	}

	@Transactional(readOnly = true)
	public void index(CmsSite site, String tpl, Map<String, Object> data)
			throws IOException, TemplateException {
		long time = System.currentTimeMillis();
		File f = new File(getIndexPath(site));
		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		Writer out = null;
		try {
			// FileWriter不能指定编码确实是个问题，只能用这个代替了。
			out = new OutputStreamWriter(new FileOutputStream(f), UTF8);
			Template template = conf.getTemplate(tpl);
			template.process(data, out);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		time = System.currentTimeMillis() - time;
		log.info("create index page, in {} ms", time);
	}

	@Transactional(readOnly = true)
	public boolean deleteIndex(CmsSite site) {
		File f = new File(getIndexPath(site));
		return f.delete();
	}

	private String getIndexPath(CmsSite site) {
		StringBuilder pathBuff = new StringBuilder();
		if (!site.getIndexToRoot()) {
			if (!StringUtils.isBlank(site.getStaticDir())) {
				pathBuff.append(site.getStaticDir());
			}
		}
		pathBuff.append("/").append(Constants.INDEX).append(
				site.getStaticSuffix());
		return realPathResolver.get(pathBuff.toString());
	}

	private MessageSource tplMessageSource;
	private RealPathResolver realPathResolver;
	private StaticPageDao staticPageDao;
	private Configuration conf;

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(conf, "freemarker configuration cannot be null!");
		Assert.notNull(tplMessageSource,
				"tplMessageSource configuration cannot be null!");
	}

	public void setFreeMarkerConfigurer(
			FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}

	public void setTplMessageSource(MessageSource tplMessageSource) {
		this.tplMessageSource = tplMessageSource;
	}

	@Autowired
	public void setStaticPageDao(StaticPageDao staticPageDao) {
		this.staticPageDao = staticPageDao;
	}

	@Autowired
	public void setRealPathResolver(RealPathResolver realPathResolver) {
		this.realPathResolver = realPathResolver;
	}

}
