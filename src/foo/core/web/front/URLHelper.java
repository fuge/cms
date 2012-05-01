package foo.core.web.front;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UrlPathHelper;

/**
 * URI帮助类
 * 
 * @author liufang
 * 
 */
public class URLHelper {
	/**
	 * 获得页号
	 * 
	 * @param request
	 * @return
	 */
	public static int getPageNo(HttpServletRequest request) {
		return getPageNo(getURI(request));
	}

	/**
	 * 获得路径信息
	 * 
	 * @param request
	 * @return
	 */
	public static String[] getPaths(HttpServletRequest request) {
		return getPaths(getURI(request));
	}

	/**
	 * 获得路径参数
	 * 
	 * @param request
	 * @return
	 */
	public static String[] getParams(HttpServletRequest request) {
		return getParams(getURI(request));
	}

	private static String getURI(HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctx = helper.getOriginatingContextPath(request);
		if (!StringUtils.isBlank(ctx)) {
			return uri.substring(ctx.length());
		} else {
			return uri;
		}
	}

	/**
	 * 获得翻页信息
	 * 
	 * @param request
	 * @return
	 */
	public static PageInfo getPageInfo(HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String queryString = helper.getOriginatingQueryString(request);
		return getPageInfo(uri, queryString);
	}

	/**
	 * 获得页号
	 * 
	 * @param uri
	 *            URI {@link HttpServletRequest#getRequestURI()}
	 * @return
	 */
	public static int getPageNo(String uri) {
		if (uri == null) {
			throw new IllegalArgumentException("URI can not be null");
		}
		if (!uri.startsWith("/")) {
			throw new IllegalArgumentException("URI must start width '/'");
		}
		int pageNo = 1;
		int bi = uri.indexOf("_");
		int mi = uri.indexOf("-");
		int pi = uri.indexOf(".");
		if (bi != -1) {
			String pageNoStr;
			if (mi != -1) {
				pageNoStr = uri.substring(bi + 1, mi);
			} else {
				if (pi != -1) {
					pageNoStr = uri.substring(bi + 1, pi);
				} else {
					pageNoStr = uri.substring(bi + 1);
				}
			}
			try {
				pageNo = Integer.valueOf(pageNoStr);
			} catch (Exception e) {
			}
		}
		return pageNo;
	}

	/**
	 * 获得路径数组
	 * 
	 * @param uri
	 *            URI {@link HttpServletRequest#getRequestURI()}
	 * @return
	 */
	public static String[] getPaths(String uri) {
		if (uri == null) {
			throw new IllegalArgumentException("URI can not be null");
		}
		if (!uri.startsWith("/")) {
			throw new IllegalArgumentException("URI must start width '/'");
		}
		int bi = uri.indexOf("_");
		int mi = uri.indexOf("-");
		int pi = uri.indexOf(".");
		// 获得路径信息
		String pathStr;
		if (bi != -1) {
			pathStr = uri.substring(0, bi);
		} else if (mi != -1) {
			pathStr = uri.substring(0, mi);
		} else if (pi != -1) {
			pathStr = uri.substring(0, pi);
		} else {
			pathStr = uri;
		}
		String[] paths = StringUtils.split(pathStr, '/');
		return paths;
	}

	/**
	 * 获得路径参数
	 * 
	 * @param uri
	 *            URI {@link HttpServletRequest#getRequestURI()}
	 * @return
	 */
	public static String[] getParams(String uri) {
		if (uri == null) {
			throw new IllegalArgumentException("URI can not be null");
		}
		if (!uri.startsWith("/")) {
			throw new IllegalArgumentException("URI must start width '/'");
		}
		int mi = uri.indexOf("-");
		int pi = uri.indexOf(".");
		String[] params;
		if (mi != -1) {
			String paramStr;
			if (pi != -1) {
				paramStr = uri.substring(mi, pi);
			} else {
				paramStr = uri.substring(mi);
			}
			params = new String[StringUtils.countMatches(paramStr, "-")];
			int fromIndex = 1;
			int nextIndex = 0;
			int i = 0;
			while ((nextIndex = paramStr.indexOf("-", fromIndex)) != -1) {
				params[i++] = paramStr.substring(fromIndex, nextIndex);
				fromIndex = nextIndex + 1;
			}
			params[i++] = paramStr.substring(fromIndex);
		} else {
			params = new String[0];
		}
		return params;
	}

	/**
	 * 获得URL信息
	 * 
	 * @param uri
	 *            URI {@link HttpServletRequest#getRequestURI()}
	 * @param queryString
	 *            查询字符串 {@link HttpServletRequest#getQueryString()}
	 * @return
	 */
	public static PageInfo getPageInfo(String uri, String queryString) {
		if (uri == null) {
			return null;
		}
		if (!uri.startsWith("/")) {
			throw new IllegalArgumentException("URI must start width '/'");
		}
		int bi = uri.indexOf("_");
		int mi = uri.indexOf("-");
		int pi = uri.indexOf(".");
		int lastSpt = uri.lastIndexOf("/") + 1;
		String url;
		if (!StringUtils.isBlank(queryString)) {
			url = uri + "?" + queryString;
		} else {
			url = uri;
		}
		// 翻页前半部
		String urlFormer;
		if (bi != -1) {
			urlFormer = uri.substring(lastSpt, bi);
		} else if (mi != -1) {
			urlFormer = uri.substring(lastSpt, mi);
		} else if (pi != -1) {
			urlFormer = uri.substring(lastSpt, pi);
		} else {
			urlFormer = uri.substring(lastSpt);
		}
		// 翻页后半部
		String urlLater;
		if (mi != -1) {
			urlLater = url.substring(mi);
		} else if (pi != -1) {
			urlLater = url.substring(pi);
		} else {
			urlLater = url.substring(uri.length());
		}
		String href = url.substring(lastSpt);
		return new PageInfo(href, urlFormer, urlLater);
	}

	/**
	 * URI信息
	 * 
	 * @author liufang
	 * 
	 */
	public static class PageInfo {
		/**
		 * 页面地址
		 */
		private String href;
		/**
		 * href前半部（相对于分页）
		 */
		private String hrefFormer;
		/**
		 * href后半部（相对于分页）
		 */
		private String hrefLatter;

		public PageInfo(String href, String hrefFormer, String hrefLatter) {
			this.href = href;
			this.hrefFormer = hrefFormer;
			this.hrefLatter = hrefLatter;
		}

		public String getHref() {
			return href;
		}

		public void setHref(String href) {
			this.href = href;
		}

		public String getHrefFormer() {
			return hrefFormer;
		}

		public void setHrefFormer(String hrefFormer) {
			this.hrefFormer = hrefFormer;
		}

		public String getHrefLatter() {
			return hrefLatter;
		}

		public void setHrefLatter(String hrefLatter) {
			this.hrefLatter = hrefLatter;
		}

	}
}
