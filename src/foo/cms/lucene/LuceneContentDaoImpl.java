package foo.cms.lucene;

import java.io.IOException;
import java.util.Date;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import foo.cms.entity.main.Content;
import foo.cms.entity.main.ContentCheck;
import foo.common.hibernate3.Finder;
import foo.common.hibernate3.HibernateBaseDao;

@Repository
public class LuceneContentDaoImpl extends HibernateBaseDao<Content, Integer>
		implements LuceneContentDao {
	public Integer index(IndexWriter writer, Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer max)
			throws CorruptIndexException, IOException {
		Finder f = Finder.create("select bean from Content bean");
		if (channelId != null) {
			f.append(" join bean.channel channel, Channel parent");
			f.append(" where channel.lft between parent.lft and parent.rgt");
			f.append(" and channel.site.id=parent.site.id");
			f.append(" and parent.id=:parentId");
			f.setParam("parentId", channelId);
		} else if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		if (startId != null) {
			f.append(" and bean.id > :startId");
			f.setParam("startId", startId);
		}
		if (startDate != null) {
			f.append(" and bean.contentExt.releaseDate >= :startDate");
			f.setParam("startDate", startDate);
		}
		if (endDate != null) {
			f.append(" and bean.contentExt.releaseDate <= :endDate");
			f.setParam("endDate", endDate);
		}
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		f.append(" order by bean.id asc");
		if (max != null) {
			f.setMaxResults(max);
		}
		Session session = getSession();
		ScrollableResults contents = f.createQuery(getSession()).setCacheMode(
				CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		Content content = null;
		while (contents.next()) {
			content = (Content) contents.get(0);
			writer.addDocument(LuceneContent.createDocument(content));
			if (++count % 20 == 0) {
				session.clear();
			}
		}
		if (count < max || content == null) {
			// 实际数量小于指定数量，代表生成结束。如果没有任何数据，也代表生成结束。
			return null;
		} else {
			// 返回最后一个ID
			return content.getId();
		}

	}

	@Override
	protected Class<Content> getEntityClass() {
		return Content.class;
	}
}
