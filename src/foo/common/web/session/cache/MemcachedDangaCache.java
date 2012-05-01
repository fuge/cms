package foo.common.web.session.cache;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcachedDangaCache implements SessionCache, InitializingBean {
	private MemCachedClient client;
	private String[] servers;
	private Integer[] weights;

	@SuppressWarnings("unchecked")
	public HashMap<String, Serializable> getSession(String root) {
		return (HashMap<String, Serializable>) client.get(root);
	}

	public void setSession(String root, Map<String, Serializable> session,
			int exp) {
		client.set(root, session, new Date(System.currentTimeMillis() + exp
				* 60 * 1000));
	}

	public Serializable getAttribute(String root, String name) {
		HashMap<String, Serializable> session = getSession(root);
		return session != null ? session.get(name) : null;
	}

	public void setAttribute(String root, String name, Serializable value,
			int exp) {
		HashMap<String, Serializable> session = getSession(root);
		if (session == null) {
			session = new HashMap<String, Serializable>();
		}
		session.put(name, value);
		Date expDate = new Date(System.currentTimeMillis() + exp * 60 * 1000);
		client.set(root, session, expDate);
	}

	public void clear(String root) {
		client.delete(root);
	}

	public boolean exist(String root) {
		return client.keyExists(root);
	}

	public void afterPropertiesSet() throws Exception {
		client = new MemCachedClient();
		// grab an instance of our connection pool
		SockIOPool pool = SockIOPool.getInstance();

		// set the servers and the weights
		pool.setServers(servers);
		pool.setWeights(weights);

		// set some basic pool settings
		// 5 initial, 5 min, and 250 max conns
		// and set the max idle time for a conn
		// to 6 hours
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaxIdle(1000 * 60 * 60 * 6);

		// set the sleep for the maint thread
		// it will wake up every x seconds and
		// maintain the pool size
		pool.setMaintSleep(30);

		// set some TCP settings
		// disable nagle
		// set the read timeout to 3 secs
		// and don't set a connect timeout
		pool.setNagle(false);
		pool.setSocketTO(3000);
		pool.setSocketConnectTO(0);

		// initialize the connection pool
		pool.initialize();

		// lets set some compression on for the client
		// compress anything larger than 64k
		client.setCompressEnable(true);
		client.setCompressThreshold(64 * 1024);
	}

	public String[] getServers() {
		return servers;
	}

	public void setServers(String[] servers) {
		this.servers = servers;
	}

	public Integer[] getWeights() {
		return weights;
	}

	public void setWeights(Integer[] weights) {
		this.weights = weights;
	}
}
