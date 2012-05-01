package foo.cms.staticpage;

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
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import foo.cms.entity.main.Channel;
import foo.cms.entity.main.CmsSite;
import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentCheck;
import foo.cms.manager.assist.CmsKeywordMng;
import foo.cms.web.FrontUtils;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateSimpleDao;
import foo.common.page.Paginable;
import foo.common.page.SimplePage;
import foo.common.web.springmvc.RealPathResolver;
import foo.core.web.front.URLHelper;
import foo.core.web.front.URLHelper.PageInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Repository
public class StaticPageDaoImpl extends HibernateSimpleDao implements
		StaticPageDao {
	public int channelStatic(Integer siteId, Integer channelId,
			boolean containChild, Configuration conf, Map<String, Object> data)
			throws IOException, TemplateException {
		Finder finder = Finder.create("select bean from Channel bean");
		if (channelId != null) {
			if (containChild) {
				finder.append(",Channel parent where").append(
						" bean.lft between parent.lft and parent.rgt").append(
						" and parent.site.id=bean.site.id").append(
						" and parent.id=:channelId");
				finder.setParam("channelId", channelId);
			} else {
				finder.append(" where bean.id=:channelId");
				finder.setParam("channelId", channelId);
			}
		} else if (siteId != null) {
			finder.append(" where bean.site.id=:siteId");
			finder.setParam("siteId", siteId);
		}
		Session session = getSession();
		ScrollableResults channels = finder.createQuery(session).setCacheMode(
				CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		CmsSite site;
		Channel c;
		PageInfo info;
		Writer out = null;
		Template tpl;
		String real, filename;
		File f, parent;
		int quantity, totalPage;
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		while (channels.next()) {
			c = (Channel) channels.get(0);
			site = c.getSite();
			FrontUtils.frontData(data, site, null, null, null);
			// 如果是外部链接或者不需要生产静态页，则不生成
			if (!StringUtils.isBlank(c.getLink()) || !c.getStaticChannel()) {
				continue;
			}
			// 没有内容或者有子栏目，则只生成一页
			int childs = childsOfChannel(c.getId());
			if (!c.getModel().getHasContent()
					|| (!c.getListChild() && childs > 0)) {
				totalPage = 1;
			} else {
				if (c.getListChild()) {
					quantity = childs;
				} else {
					quantity = contentsOfChannel(c.getId());
				}
				if (quantity <= 0) {
					totalPage = 1;
				} else {
					totalPage = (quantity - 1) / c.getPageSize() + 1;
				}
			}
			for (int i = 1; i <= totalPage; i++) {
				filename = c.getStaticFilename(i);
				real = realPathResolver.get(filename.toString());
				f = new File(real);
				parent = f.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
				tpl = conf.getTemplate(c.getTplChannelOrDef());
				String urlStatic = c.getUrlStatic(i);
				info = URLHelper.getPageInfo(filename.substring(filename
						.lastIndexOf("/")), null);
				FrontUtils.frontPageData(i, info.getHref(), info
						.getHrefFormer(), info.getHrefLatter(), data);
				FrontUtils.putLocation(data, urlStatic);
				data.put("channel", c);
				try {
					// FileWriter不能指定编码确实是个问题，只能用这个代替了。
					out = new OutputStreamWriter(new FileOutputStream(f), UTF8);
					tpl.process(data, out);
					log.info("create static file: {}", f.getAbsolutePath());
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
			if (++count % 20 == 0) {
				session.clear();
			}
		}
		return count;
	}

	public void channelStatic(Channel c, boolean firstOnly, Configuration conf,
			Map<String, Object> data) throws IOException, TemplateException {
		// 如果是外部链接或者不需要生产静态页，则不生成
		if (!StringUtils.isBlank(c.getLink()) || !c.getStaticChannel()) {
			return;
		}
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		// 没有内容或者有子栏目，则只生成一页
		int childs = childsOfChannel(c.getId());
		int quantity, totalPage;
		if (firstOnly || !c.getModel().getHasContent()
				|| (!c.getListChild() && childs > 0)) {
			totalPage = 1;
		} else {
			if (c.getListChild()) {
				quantity = childs;
			} else {
				quantity = contentsOfChannel(c.getId());
			}
			if (quantity <= 0) {
				totalPage = 1;
			} else {
				totalPage = (quantity - 1) / c.getPageSize() + 1;
			}
		}
		String real, filename;
		File f, parent;
		PageInfo info;
		Writer out = null;
		Template tpl;
		CmsSite site = c.getSite();
		FrontUtils.frontData(data, site, null, null, null);
		for (int i = 1; i <= totalPage; i++) {
			filename = c.getStaticFilename(i);
			real = realPathResolver.get(filename.toString());
			f = new File(real);
			parent = f.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			tpl = conf.getTemplate(c.getTplChannelOrDef());
			String urlStatic = c.getUrlStatic(i);
			info = URLHelper.getPageInfo(filename.substring(filename
					.lastIndexOf("/")), null);
			FrontUtils.frontPageData(i, info.getHref(), info.getHrefFormer(),
					info.getHrefLatter(), data);
			FrontUtils.putLocation(data, urlStatic);
			data.put("channel", c);
			try {
				// FileWriter不能指定编码确实是个问题，只能用这个代替了。
				out = new OutputStreamWriter(new FileOutputStream(f), UTF8);
				tpl.process(data, out);
				log.info("create static file: {}", f.getAbsolutePath());
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
	}

	public int contentsOfChannel(Integer channelId) {
		String hql = "select count(*) from Content bean"
				+ " join bean.channels as channel"
				+ " where channel.id=:channelId and bean.status="
				+ ContentCheck.CHECKED;
		Query query = getSession().createQuery(hql);
		query.setParameter("channelId", channelId);
		return ((Number) query.iterate().next()).intValue();
	}

	public int childsOfChannel(Integer channelId) {
		String hql = "select count(*) from Channel bean"
				+ " where bean.parent.id=:channelId";
		Query query = getSession().createQuery(hql);
		query.setParameter("channelId", channelId);
		return ((Number) query.iterate().next()).intValue();
	}

	public int contentStatic(Integer siteId, Integer channelId, Date start,
			Configuration conf, Map<String, Object> data) throws IOException,
			TemplateException {
		Finder f = Finder.create("select bean from Content bean");
		if (channelId != null) {
			f.append(" join bean.channel node,Channel parent");
			f.append(" where node.lft between parent.lft and parent.rgt");
			f.append(" and parent.id=:channelId");
			f.append(" and node.site.id=parent.site.id");
			f.setParam("channelId", channelId);
		} else if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if (start != null) {
			f.append(" and bean.sortDate>=:start");
			f.setParam("start", start);
		}
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		Session session = getSession();
		ScrollableResults contents = f.createQuery(session).setCacheMode(
				CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		int totalPage;
		String url, real;
		File file, parent;
		Content c;
		Channel chnl;
		CmsSite site;
		PageInfo info;
		Template tpl;
		Writer out = null;
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		while (contents.next()) {
			c = (Content) contents.get(0);
			chnl = c.getChannel();
			// 如果是外部链接或者不生成静态页面，则不生成
			if (!StringUtils.isBlank(c.getLink()) || !chnl.getStaticContent()) {
				continue;
			}
			// 如果不需要生成静态页面，则不生成
			if(!c.getNeedRegenerate()){
				continue;
			}
			site = c.getSite();
			tpl = conf.getTemplate(c.getTplContentOrDef());
			FrontUtils.frontData(data, site, null, null, null);
			data.put("content", c);
			data.put("channel", c.getChannel());
			totalPage = c.getPageCount();
			for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
				String txt = c.getTxtByNo(pageNo);
				// 内容加上关键字
				txt = cmsKeywordMng.attachKeyword(site.getId(), txt);
				Paginable pagination = new SimplePage(pageNo, 1, c
						.getPageCount());
				data.put("pagination", pagination);
				url = c.getUrlStatic(pageNo);
				info = URLHelper.getPageInfo(url
						.substring(url.lastIndexOf("/")), null);
				FrontUtils.putLocation(data, url);
				FrontUtils.frontPageData(pageNo, info.getHref(), info
						.getHrefFormer(), info.getHrefLatter(), data);
				data.put("title", c.getTitleByNo(pageNo));
				data.put("txt", txt);
				data.put("pic", c.getPictureByNo(pageNo));
				if (pageNo == 1) {
					real = realPathResolver.get(c.getStaticFilename(pageNo));
					file = new File(real);
					parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
				} else {
					real = realPathResolver.get(c.getStaticFilename(pageNo));
					file = new File(real);
				}
				try {
					// FileWriter不能指定编码确实是个问题，只能用这个代替了。
					out = new OutputStreamWriter(new FileOutputStream(file),
							UTF8);
					tpl.process(data, out);
					log.info("create static file: {}", file.getAbsolutePath());
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}
			c.setNeedRegenerate(false);
			if (++count % 20 == 0) {
				session.clear();
			}
		}
		return count;
	}
	
	public boolean contentStatic(Content c, Configuration conf,
			Map<String, Object> data) throws IOException, TemplateException {
		// 如果是外部链接或者不生成静态页面，则不生成
		Channel chnl = c.getChannel();
		if (!StringUtils.isBlank(c.getLink()) || !chnl.getStaticContent()) {
			return false;
		}
		// 如果不需要生成静态页面，则不生成
		if(!c.getNeedRegenerate()){
			return false;
		}
		if (data == null) {
			data = new HashMap<String, Object>();
		}
		int totalPage;
		String url, real;
		File file, parent;
		CmsSite site;
		PageInfo info;
		Template tpl;
		Writer out = null;
		site = c.getSite();
		tpl = conf.getTemplate(c.getTplContentOrDef());
		FrontUtils.frontData(data, site, null, null, null);
		data.put("content", c);
		data.put("channel", chnl);
		totalPage = c.getPageCount();
		for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
			String txt = c.getTxtByNo(pageNo);
			// 内容加上关键字
			txt = cmsKeywordMng.attachKeyword(site.getId(), txt);
			Paginable pagination = new SimplePage(pageNo, 1, c.getPageCount());
			data.put("pagination", pagination);
			url = c.getUrlStatic(pageNo);
			info = URLHelper.getPageInfo(url.substring(url.lastIndexOf("/")),
					null);
			FrontUtils.putLocation(data, url);
			FrontUtils.frontPageData(pageNo, info.getHref(), info
					.getHrefFormer(), info.getHrefLatter(), data);
			data.put("title", c.getTitleByNo(pageNo));
			data.put("txt", txt);
			data.put("pic", c.getPictureByNo(pageNo));
			real = realPathResolver.get(c.getStaticFilename(pageNo));
			file = new File(real);
			if (pageNo == 1) {
				parent = file.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}
			}
			try {
				// FileWriter不能指定编码确实是个问题，只能用这个代替了。
				out = new OutputStreamWriter(new FileOutputStream(file), UTF8);
				tpl.process(data, out);
				log.info("create static file: {}", file.getAbsolutePath());
			} finally {
				if (out != null) {
					out.close();
				}
			}
		}
		c.setNeedRegenerate(false);
		return true;
	}

	private CmsKeywordMng cmsKeywordMng;
	private RealPathResolver realPathResolver;

	@Autowired
	public void setCmsKeywordMng(CmsKeywordMng cmsKeywordMng) {
		this.cmsKeywordMng = cmsKeywordMng;
	}

	@Autowired
	public void setRealPathResolver(RealPathResolver realPathResolver) {
		this.realPathResolver = realPathResolver;
	}
}
