package foo.cms.entity.main;

import static foo.cms.Constants.RES_PATH;
import static foo.cms.Constants.TPL_BASE;
import static foo.cms.Constants.UPLOAD_PATH;
import static foo.common.web.Constants.DEFAULT;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

public class CmsSite implements Serializable{
	private static final long serialVersionUID = 3126397197280400787L;

	/**
	 * 获得站点url
	 * 
	 * @return
	 */
	public String getUrl() {
		if (getStaticIndex()) {
			return getUrlStatic();
		} else {
			return getUrlDynamic();
		}
	}

	/**
	 * 获得完整路径。在给其他网站提供客户端包含时也可以使用。
	 * 
	 * @return
	 */
	public String getUrlWhole() {
		if (getStaticIndex()) {
			return getUrlBuffer(false, true, false).append("/").toString();
		} else {
			return getUrlBuffer(true, true, false).append("/").toString();
		}
	}

	public String getUrlDynamic() {
		return getUrlBuffer(true, null, false).append("/").toString();
	}

	public String getUrlStatic() {
		return getUrlBuffer(false, null, true).append("/").toString();
	}

	public StringBuilder getUrlBuffer(boolean dynamic, Boolean whole,
			boolean forIndex) {
		boolean relative = whole != null ? !whole : getRelativePath();
		String ctx = getContextPath();
		StringBuilder url = new StringBuilder();
		if (!relative) {
			url.append(getProtocol()).append(getDomain());
			if (getPort() != null) {
				url.append(":").append(getPort());
			}
		}
		if (!StringUtils.isBlank(ctx)) {
			url.append(ctx);
		}
		if (dynamic) {
			String servlet = getServletPoint();
			if (!StringUtils.isBlank(servlet)) {
				url.append(servlet);
			}
		} else {
			if (!forIndex) {
				String staticDir = getStaticDir();
				if (!StringUtils.isBlank(staticDir)) {
					url.append(staticDir);
				}
			}
		}
		return url;
	}

	/**
	 * 获得模板路径。如：/WEB-INF/template/www
	 * 
	 * @return
	 */
	public String getTplPath() {
		return TPL_BASE + "/" + getPath();
	}

	/**
	 * 获得模板方案路径。如：/WEB-INF/template/www/default
	 * 
	 * @return
	 */
	public String getSolutionPath() {
		return TPL_BASE + "/" + getPath() + "/" + getTplSolution();
	}

	/**
	 * 获得模板资源路径。如：/r/cms/www
	 * 
	 * @return
	 */
	public String getResPath() {
		return RES_PATH + "/" + getPath();
	}

	/**
	 * 获得上传路径。如：/u/jeecms/path
	 * 
	 * @return
	 */
	public String getUploadPath() {
		return UPLOAD_PATH + getPath();
	}

	/**
	 * 获得上传访问前缀。
	 * 
	 * 根据配置识别上传至数据、FTP和本地
	 * 
	 * @return
	 */
	public String getUploadBase() {
		CmsConfig config = getConfig();
		String ctx = config.getContextPath();
		if (config.getUploadToDb()) {
			if (!StringUtils.isBlank(ctx)) {
				return ctx + config.getDbFileUri();
			} else {
				return config.getDbFileUri();
			}
		} else if (getUploadFtp() != null) {
			return getUploadFtp().getUrl();
		} else {
			if (!StringUtils.isBlank(ctx)) {
				return ctx;
			} else {
				return "";
			}
		}
	}

	public String getServletPoint() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getServletPoint();
		} else {
			return null;
		}
	}

	public String getContextPath() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getContextPath();
		} else {
			return null;
		}
	}

	public Integer getPort() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getPort();
		} else {
			return null;
		}
	}

	public String getDefImg() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getDefImg();
		} else {
			return null;
		}
	}

	public String getLoginUrl() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getLoginUrl();
		} else {
			return null;
		}
	}

	public String getProcessUrl() {
		CmsConfig config = getConfig();
		if (config != null) {
			return config.getProcessUrl();
		} else {
			return null;
		}
	}

	public int getUsernameMinLen() {
		return getConfig().getMemberConfig().getUsernameMinLen();
	}

	public int getPasswordMinLen() {
		return getConfig().getMemberConfig().getPasswordMinLen();
	}

	public static Integer[] fetchIds(Collection<CmsSite> sites) {
		if (sites == null) {
			return null;
		}
		Integer[] ids = new Integer[sites.size()];
		int i = 0;
		for (CmsSite s : sites) {
			ids[i++] = s.getId();
		}
		return ids;
	}

	public void init() {
		if (StringUtils.isBlank(getProtocol())) {
			setProtocol("http://");
		}
		if (StringUtils.isBlank(getTplSolution())) {
			setTplSolution(DEFAULT);
		}
		if (getFinalStep() == null) {
			byte step = 2;
			setFinalStep(step);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsSite () {
		super();
	}

	/** Constructor for primary key */
	public CmsSite (Integer id) {
		this.setId(id);
	}

	/** Constructor for required fields */
	public CmsSite(Integer id, foo.cms.entity.main.CmsConfig config, String domain,
			String path, String name, String protocol, String dynamicSuffix,
			String staticSuffix, Boolean indexToRoot, Boolean staticIndex,
			String localeAdmin, String localeFront, String tplSolution, Byte finalStep,
			Byte afterCheck, Boolean relativePath, Boolean resycleOn) {
		this.setId(id);
		this.setConfig(config);
		this.setDomain(domain);
		this.setPath(path);
		this.setName(name);
		this.setProtocol(protocol);
		this.setDynamicSuffix(dynamicSuffix);
		this.setStaticSuffix(staticSuffix);
		this.setIndexToRoot(indexToRoot);
		this.setStaticIndex(staticIndex);
		this.setLocaleAdmin(localeAdmin);
		this.setLocaleFront(localeFront);
		this.setTplSolution(tplSolution);
		this.setFinalStep(finalStep);
		this.setAfterCheck(afterCheck);
		this.setRelativePath(relativePath);
		this.setResycleOn(resycleOn);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String domain;
	private String path;
	private String name;
	private String shortName;
	private String protocol;
	private String dynamicSuffix;
	private String staticSuffix;
	private String staticDir;
	private Boolean indexToRoot;
	private Boolean staticIndex;
	private String localeAdmin;
	private String localeFront;
	private String tplSolution;
	private Byte finalStep;
	private Byte afterCheck;
	private Boolean relativePath;
	private Boolean resycleOn;
	private String domainAlias;
	private String domainRedirect;

	// many to one
	private foo.core.entity.Ftp uploadFtp;
	private foo.cms.entity.main.CmsConfig config;

	// collections
	private java.util.Map<String, String> attr;
	private java.util.Map<String, String> txt;
	private java.util.Map<String, String> cfg;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="site_id"
     */
	public Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}


	/**
	 * Return the value associated with the column: domain
	 */
	public String getDomain () {
		return domain;
	}

	/**
	 * Set the value related to the column: domain
	 * @param domain the domain value
	 */
	public void setDomain (String domain) {
		this.domain = domain;
	}


	/**
	 * Return the value associated with the column: site_path
	 */
	public String getPath () {
		return path;
	}

	/**
	 * Set the value related to the column: site_path
	 * @param path the site_path value
	 */
	public void setPath (String path) {
		this.path = path;
	}


	/**
	 * Return the value associated with the column: site_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: site_name
	 * @param name the site_name value
	 */
	public void setName (String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: short_name
	 */
	public String getShortName () {
		return shortName;
	}

	/**
	 * Set the value related to the column: short_name
	 * @param shortName the short_name value
	 */
	public void setShortName (String shortName) {
		this.shortName = shortName;
	}


	/**
	 * Return the value associated with the column: protocol
	 */
	public String getProtocol () {
		return protocol;
	}

	/**
	 * Set the value related to the column: protocol
	 * @param protocol the protocol value
	 */
	public void setProtocol (String protocol) {
		this.protocol = protocol;
	}


	/**
	 * Return the value associated with the column: dynamic_suffix
	 */
	public String getDynamicSuffix () {
		return dynamicSuffix;
	}

	/**
	 * Set the value related to the column: dynamic_suffix
	 * @param dynamicSuffix the dynamic_suffix value
	 */
	public void setDynamicSuffix (String dynamicSuffix) {
		this.dynamicSuffix = dynamicSuffix;
	}


	/**
	 * Return the value associated with the column: static_suffix
	 */
	public String getStaticSuffix () {
		return staticSuffix;
	}

	/**
	 * Set the value related to the column: static_suffix
	 * @param staticSuffix the static_suffix value
	 */
	public void setStaticSuffix (String staticSuffix) {
		this.staticSuffix = staticSuffix;
	}


	/**
	 * Return the value associated with the column: static_dir
	 */
	public String getStaticDir () {
		return staticDir;
	}

	/**
	 * Set the value related to the column: static_dir
	 * @param staticDir the static_dir value
	 */
	public void setStaticDir (String staticDir) {
		this.staticDir = staticDir;
	}


	/**
	 * Return the value associated with the column: is_index_to_root
	 */
	public Boolean getIndexToRoot () {
		return indexToRoot;
	}

	/**
	 * Set the value related to the column: is_index_to_root
	 * @param indexToRoot the is_index_to_root value
	 */
	public void setIndexToRoot (Boolean indexToRoot) {
		this.indexToRoot = indexToRoot;
	}


	/**
	 * Return the value associated with the column: is_static_index
	 */
	public Boolean getStaticIndex () {
		return staticIndex;
	}

	/**
	 * Set the value related to the column: is_static_index
	 * @param staticIndex the is_static_index value
	 */
	public void setStaticIndex (Boolean staticIndex) {
		this.staticIndex = staticIndex;
	}


	/**
	 * Return the value associated with the column: locale_admin
	 */
	public String getLocaleAdmin () {
		return localeAdmin;
	}

	/**
	 * Set the value related to the column: locale_admin
	 * @param localeAdmin the locale_admin value
	 */
	public void setLocaleAdmin (String localeAdmin) {
		this.localeAdmin = localeAdmin;
	}


	/**
	 * Return the value associated with the column: locale_front
	 */
	public String getLocaleFront () {
		return localeFront;
	}

	/**
	 * Set the value related to the column: locale_front
	 * @param localeFront the locale_front value
	 */
	public void setLocaleFront (String localeFront) {
		this.localeFront = localeFront;
	}


	/**
	 * Return the value associated with the column: tpl_solution
	 */
	public String getTplSolution () {
		return tplSolution;
	}

	/**
	 * Set the value related to the column: tpl_solution
	 * @param tplSolution the tpl_solution value
	 */
	public void setTplSolution (String tplSolution) {
		this.tplSolution = tplSolution;
	}


	/**
	 * Return the value associated with the column: final_step
	 */
	public Byte getFinalStep () {
		return finalStep;
	}

	/**
	 * Set the value related to the column: final_step
	 * @param finalStep the final_step value
	 */
	public void setFinalStep (Byte finalStep) {
		this.finalStep = finalStep;
	}


	/**
	 * Return the value associated with the column: after_check
	 */
	public Byte getAfterCheck () {
		return afterCheck;
	}

	/**
	 * Set the value related to the column: after_check
	 * @param afterCheck the after_check value
	 */
	public void setAfterCheck (Byte afterCheck) {
		this.afterCheck = afterCheck;
	}


	/**
	 * Return the value associated with the column: is_relative_path
	 */
	public Boolean getRelativePath () {
		return relativePath;
	}

	/**
	 * Set the value related to the column: is_relative_path
	 * @param relativePath the is_relative_path value
	 */
	public void setRelativePath (Boolean relativePath) {
		this.relativePath = relativePath;
	}


	/**
	 * Return the value associated with the column: is_recycle_on
	 */
	public Boolean getResycleOn () {
		return resycleOn;
	}

	/**
	 * Set the value related to the column: is_recycle_on
	 * @param resycleOn the is_recycle_on value
	 */
	public void setResycleOn (Boolean resycleOn) {
		this.resycleOn = resycleOn;
	}


	/**
	 * Return the value associated with the column: domain_alias
	 */
	public String getDomainAlias () {
		return domainAlias;
	}

	/**
	 * Set the value related to the column: domain_alias
	 * @param domainAlias the domain_alias value
	 */
	public void setDomainAlias (String domainAlias) {
		this.domainAlias = domainAlias;
	}


	/**
	 * Return the value associated with the column: domain_redirect
	 */
	public String getDomainRedirect () {
		return domainRedirect;
	}

	/**
	 * Set the value related to the column: domain_redirect
	 * @param domainRedirect the domain_redirect value
	 */
	public void setDomainRedirect (String domainRedirect) {
		this.domainRedirect = domainRedirect;
	}


	/**
	 * Return the value associated with the column: ftp_upload_id
	 */
	public foo.core.entity.Ftp getUploadFtp () {
		return uploadFtp;
	}

	/**
	 * Set the value related to the column: ftp_upload_id
	 * @param uploadFtp the ftp_upload_id value
	 */
	public void setUploadFtp (foo.core.entity.Ftp uploadFtp) {
		this.uploadFtp = uploadFtp;
	}


	/**
	 * Return the value associated with the column: config_id
	 */
	public foo.cms.entity.main.CmsConfig getConfig () {
		return config;
	}

	/**
	 * Set the value related to the column: config_id
	 * @param config the config_id value
	 */
	public void setConfig (foo.cms.entity.main.CmsConfig config) {
		this.config = config;
	}


	/**
	 * Return the value associated with the column: attr
	 */
	public java.util.Map<String, String> getAttr () {
		return attr;
	}

	/**
	 * Set the value related to the column: attr
	 * @param attr the attr value
	 */
	public void setAttr (java.util.Map<String, String> attr) {
		this.attr = attr;
	}


	/**
	 * Return the value associated with the column: txt
	 */
	public java.util.Map<String, String> getTxt () {
		return txt;
	}

	/**
	 * Set the value related to the column: txt
	 * @param txt the txt value
	 */
	public void setTxt (java.util.Map<String, String> txt) {
		this.txt = txt;
	}


	/**
	 * Return the value associated with the column: cfg
	 */
	public java.util.Map<String, String> getCfg () {
		return cfg;
	}

	/**
	 * Set the value related to the column: cfg
	 * @param cfg the cfg value
	 */
	public void setCfg (java.util.Map<String, String> cfg) {
		this.cfg = cfg;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof foo.cms.entity.main.CmsSite)) return false;
		else {
			foo.cms.entity.main.CmsSite cmsSite = (foo.cms.entity.main.CmsSite) obj;
			if (null == this.getId() || null == cmsSite.getId()) return false;
			else return (this.getId().equals(cmsSite.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

}