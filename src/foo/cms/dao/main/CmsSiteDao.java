package foo.cms.dao.main;

import java.util.List;

import foo.cms.entity.main.CmsSite;
import foo.common.hibernate3.Updater;

/**
 * 站点DAO接口
 * 
 * @author liufang
 * 
 */
public interface CmsSiteDao {
	/**
	 * 获得站点数量
	 * 
	 * @param cacheable
	 * @return
	 */
	public int siteCount(boolean cacheable);

	/**
	 * 获得所有站点
	 * 
	 * @param cacheable
	 * @return
	 */
	public List<CmsSite> getList(boolean cacheable);

	public CmsSite findByDomain(String domain, boolean cacheable);

	public CmsSite findById(Integer id);

	public CmsSite save(CmsSite bean);

	public CmsSite updateByUpdater(Updater<CmsSite> updater);

	public CmsSite deleteById(Integer id);
}