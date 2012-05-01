package foo.cms.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import foo.cms.entity.main.Channel;
import foo.cms.entity.main.Content;
import foo.common.page.Pagination;

public class LuceneContent {

	/**
	 * 获得Lucene格式的Document
	 * 
	 * @param c
	 *            文章对象
	 * @return
	 */
	public static Document createDocument(Content c) {
		Document doc = new Document();
		doc.add(new Field(ID, c.getId().toString(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field(SITE_ID, c.getSite().getId().toString(),
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field(RELEASE_DATE, DateTools.dateToString(c
				.getReleaseDate(), Resolution.DAY), Field.Store.NO,
				Field.Index.NOT_ANALYZED));
		Channel channel = c.getChannel();
		while (channel != null) {
			doc.add(new Field(CHANNEL_ID_ARRAY, channel.getId().toString(),
					Field.Store.NO, Field.Index.NOT_ANALYZED));
			channel = channel.getParent();
		}
		doc.add(new Field(TITLE, c.getTitle(), Field.Store.NO,
				Field.Index.ANALYZED));
		if (!StringUtils.isBlank(c.getTxt())) {
			doc.add(new Field(CONTENT, c.getTxt(), Field.Store.NO,
					Field.Index.ANALYZED));
		}
		return doc;
	}

	public static Query createQuery(String queryString, Integer siteId,
			Integer channelId, Date startDate, Date endDate, Analyzer analyzer)
			throws ParseException {
		BooleanQuery bq = new BooleanQuery();
		Query q;
		if (!StringUtils.isBlank(queryString)) {
			q = MultiFieldQueryParser.parse(Version.LUCENE_30, queryString,
					QUERY_FIELD, QUERY_FLAGS, analyzer);
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (siteId != null) {
			q = new TermQuery(new Term(SITE_ID, siteId.toString()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (channelId != null) {
			q = new TermQuery(new Term(CHANNEL_ID_ARRAY, channelId.toString()));
			bq.add(q, BooleanClause.Occur.MUST);
		}
		if (startDate != null || endDate != null) {
			String start = null;
			String end = null;
			if (startDate != null) {
				start = DateTools.dateToString(startDate, Resolution.DAY);
			}
			if (endDate != null) {
				end = DateTools.dateToString(endDate, Resolution.DAY);
			}
			q = new TermRangeQuery(RELEASE_DATE, start, end, true, true);
			bq.add(q, BooleanClause.Occur.MUST);
		}
		return bq;
	}

	public static void delete(Integer siteId, Integer channelId,
			Date startDate, Date endDate, IndexWriter writer)
			throws CorruptIndexException, IOException, ParseException {
		writer.deleteDocuments(createQuery(null, siteId, channelId, startDate,
				endDate, null));
	}

	public static void delete(Integer contentId, IndexWriter writer)
			throws CorruptIndexException, IOException, ParseException {
		writer.deleteDocuments(new Term(ID, contentId.toString()));
	}

	public static Pagination getResultPage(Searcher searcher, TopDocs docs,
			int pageNo, int pageSize) throws CorruptIndexException, IOException {
		List<Integer> list = new ArrayList<Integer>(pageSize);
		ScoreDoc[] hits = docs.scoreDocs;
		int endIndex = pageNo * pageSize;
		int len = hits.length;
		if (endIndex > len) {
			endIndex = len;
		}
		for (int i = (pageNo - 1) * pageSize; i < endIndex; i++) {
			Document d = searcher.doc(hits[i].doc);
			list.add(Integer.valueOf(d.getField(ID).stringValue()));
		}
		return new Pagination(pageNo, pageSize, docs.totalHits, list);
	}

	public static List<Integer> getResultList(Searcher searcher, TopDocs docs,
			int first, int max) throws CorruptIndexException, IOException {
		List<Integer> list = new ArrayList<Integer>(max);
		ScoreDoc[] hits = docs.scoreDocs;
		if (first < 0) {
			first = 0;
		}
		if (max < 0) {
			max = 0;
		}
		int last = first + max;
		int len = hits.length;
		if (last > len) {
			last = len;
		}
		for (int i = first; i < last; i++) {
			Document d = searcher.doc(hits[i].doc);
			list.add(Integer.valueOf(d.getField(ID).stringValue()));
		}
		return list;
	}

	public static final String ID = "id";
	public static final String SITE_ID = "siteId";
	public static final String CHANNEL_ID_ARRAY = "channelIdArray";
	public static final String RELEASE_DATE = "releaseDate";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String[] QUERY_FIELD = { TITLE, CONTENT };
	public static final BooleanClause.Occur[] QUERY_FLAGS = {
			BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD };
}