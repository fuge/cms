package foo.cms.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import foo.cms.Constants;
import foo.cms.entity.main.Content;
import foo.cms.manager.main.ContentMng;
import foo.common.page.Pagination;
import foo.common.web.springmvc.RealPathResolver;

@Service
@Transactional
public class LuceneContentSvcImpl implements LuceneContentSvc {
	@Transactional(readOnly = true)
	public Integer createIndex(Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer max)
			throws IOException, ParseException {
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		return createIndex(siteId, channelId, startDate, endDate, startId, max,
				dir);
	}

	@Transactional(readOnly = true)
	public Integer createIndex(Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer max,
			Directory dir) throws IOException, ParseException {
		boolean exist = IndexReader.indexExists(dir);
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(
				Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
		try {
			if (exist) {
				LuceneContent.delete(siteId, channelId, startDate, endDate,
						writer);
			}
			Integer lastId = luceneContentDao.index(writer, siteId, channelId,
					startDate, endDate, startId, max);
			writer.optimize();
			return lastId;
		} finally {
			writer.close();
		}
	}

	@Transactional(readOnly = true)
	public void createIndex(Content content) throws IOException {
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		createIndex(content, dir);
	}

	@Transactional(readOnly = true)
	public void createIndex(Content content, Directory dir) throws IOException {
		boolean exist = IndexReader.indexExists(dir);
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(
				Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
		try {
			writer.addDocument(LuceneContent.createDocument(content));
		} finally {
			writer.close();
		}
	}

	@Transactional(readOnly = true)
	public void deleteIndex(Integer contentId) throws IOException,
			ParseException {
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		deleteIndex(contentId, dir);
	}

	@Transactional(readOnly = true)
	public void deleteIndex(Integer contentId, Directory dir)
			throws IOException, ParseException {
		boolean exist = IndexReader.indexExists(dir);
		if (exist) {
			IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(
					Version.LUCENE_30), false,
					IndexWriter.MaxFieldLength.LIMITED);
			try {
				LuceneContent.delete(contentId, writer);
			} finally {
				writer.close();
			}
		}
	}

	public void updateIndex(Content content) throws IOException, ParseException {
		String path = realPathResolver.get(Constants.LUCENE_PATH);
		Directory dir = new SimpleFSDirectory(new File(path));
		updateIndex(content, dir);
	}

	public void updateIndex(Content content, Directory dir) throws IOException,
			ParseException {
		boolean exist = IndexReader.indexExists(dir);
		IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(
				Version.LUCENE_30), !exist, IndexWriter.MaxFieldLength.LIMITED);
		try {
			if (exist) {
				LuceneContent.delete(content.getId(), writer);
			}
			writer.addDocument(LuceneContent.createDocument(content));
		} finally {
			writer.close();
		}
	}

	@Transactional(readOnly = true)
	public Pagination searchPage(String path, String queryString,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int pageNo, int pageSize) throws CorruptIndexException,
			IOException, ParseException {
		Directory dir = new SimpleFSDirectory(new File(path));
		return searchPage(dir, queryString, siteId, channelId, startDate,
				endDate, pageNo, pageSize);
	}

	@Transactional(readOnly = true)
	public Pagination searchPage(Directory dir, String queryString,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int pageNo, int pageSize) throws CorruptIndexException,
			IOException, ParseException {
		Searcher searcher = new IndexSearcher(dir);
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			Query query = LuceneContent.createQuery(queryString, siteId,
					channelId, startDate, endDate, analyzer);
			TopDocs docs = searcher.search(query, pageNo * pageSize);
			Pagination p = LuceneContent.getResultPage(searcher, docs, pageNo,
					pageSize);
			List<?> ids = p.getList();
			List<Content> contents = new ArrayList<Content>(ids.size());
			for (Object id : ids) {
				contents.add(contentMng.findById((Integer) id));
			}
			p.setList(contents);
			return p;
		} finally {
			searcher.close();
		}
	}

	@Transactional(readOnly = true)
	public List<Content> searchList(String path, String queryString,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int first, int max) throws CorruptIndexException, IOException,
			ParseException {
		Directory dir = new SimpleFSDirectory(new File(path));
		return searchList(dir, queryString, siteId, channelId, startDate,
				endDate, first, max);
	}

	@Transactional(readOnly = true)
	public List<Content> searchList(Directory dir, String queryString,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int first, int max) throws CorruptIndexException, IOException,
			ParseException {
		Searcher searcher = new IndexSearcher(dir);
		try {
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
			Query query = LuceneContent.createQuery(queryString, siteId,
					channelId, startDate, endDate, analyzer);
			if (first < 0) {
				first = 0;
			}
			if (max < 0) {
				max = 0;
			}
			TopDocs docs = searcher.search(query, first + max);
			List<Integer> ids = LuceneContent.getResultList(searcher, docs,
					first, max);
			List<Content> contents = new ArrayList<Content>(ids.size());
			for (Object id : ids) {
				contents.add(contentMng.findById((Integer) id));
			}
			return contents;
		} finally {
			searcher.close();
		}
	}

	private RealPathResolver realPathResolver;
	private ContentMng contentMng;
	private LuceneContentDao luceneContentDao;

	@Autowired
	public void setRealPathResolver(RealPathResolver realPathResolver) {
		this.realPathResolver = realPathResolver;
	}

	@Autowired
	public void setLuceneContentDao(LuceneContentDao luceneContentDao) {
		this.luceneContentDao = luceneContentDao;
	}

	@Autowired
	public void setContentMng(ContentMng contentMng) {
		this.contentMng = contentMng;
	}

}
