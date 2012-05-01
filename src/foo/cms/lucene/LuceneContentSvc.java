package foo.cms.lucene;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.Directory;

import foo.cms.entity.main.Content;
import foo.common.page.Pagination;

public interface LuceneContentSvc {
	public Integer createIndex(Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer max)
			throws IOException, ParseException;

	public Integer createIndex(Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer max,
			Directory dir) throws IOException, ParseException;

	public void createIndex(Content content, Directory dir) throws IOException;

	public void createIndex(Content content) throws IOException;

	public void deleteIndex(Integer contentId) throws IOException,
			ParseException;

	public void deleteIndex(Integer contentId, Directory dir)
			throws IOException, ParseException;

	public void updateIndex(Content content) throws IOException, ParseException;

	public void updateIndex(Content content, Directory dir) throws IOException,
			ParseException;

	public Pagination searchPage(String path, String queryString,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int pageNo, int pageSize) throws CorruptIndexException,
			IOException, ParseException;

	public Pagination searchPage(Directory dir, String queryString,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int pageNo, int pageSize) throws CorruptIndexException,
			IOException, ParseException;

	public List<Content> searchList(String path, String queryString,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int pageNo, int pageSize) throws CorruptIndexException,
			IOException, ParseException;

	public List<Content> searchList(Directory dir, String queryString,
			Integer siteId, Integer channelId, Date startDate, Date endDate,
			int first, int max) throws CorruptIndexException, IOException,
			ParseException;

}
