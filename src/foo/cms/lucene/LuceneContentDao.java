package foo.cms.lucene;

import java.io.IOException;
import java.util.Date;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;

public interface LuceneContentDao {

	public Integer index(IndexWriter writer, Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer max)
			throws CorruptIndexException, IOException;
}
