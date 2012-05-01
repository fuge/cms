package foo.core.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.SocketException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import foo.common.upload.UploadUtils;

public class Ftp implements Serializable{
	private static final long serialVersionUID = 2131205836362518744L;
	private static final Logger log = LoggerFactory.getLogger(Ftp.class);

	public String storeByExt(String path, String ext, InputStream in)
			throws IOException {
		String filename = UploadUtils.generateFilename(path, ext);
		store(filename, in);
		return filename;
	}

	public String storeByFilename(String filename, InputStream in)
			throws IOException {
		store(filename, in);
		return filename;
	}

	public File retrieve(String name) throws IOException {
		String path = System.getProperty("java.io.tmpdir");
		File file = new File(path, name);
		file = UploadUtils.getUniqueFile(file);

		FTPClient ftp = getClient();
		OutputStream output = new FileOutputStream(file);
		ftp.retrieveFile(getPath() + name, output);
		output.close();
		ftp.logout();
		ftp.disconnect();
		return file;
	}

	public boolean restore(String name, File file) throws IOException {
		store(name, FileUtils.openInputStream(file));
		file.deleteOnExit();
		return true;
	}

	private int store(String remote, InputStream in) {
		try {
			FTPClient ftp = getClient();
			if (ftp != null) {
				String filename = getPath() + remote;
				String name = FilenameUtils.getName(filename);
				String path = FilenameUtils.getFullPath(filename);
				if (!ftp.changeWorkingDirectory(path)) {
					String[] ps = StringUtils.split(path, '/');
					String p = "/";
					ftp.changeWorkingDirectory(p);
					for (String s : ps) {
						p += s + "/";
						if (!ftp.changeWorkingDirectory(p)) {
							ftp.makeDirectory(s);
							ftp.changeWorkingDirectory(p);
						}
					}
				}
				ftp.storeFile(name, in);
				ftp.logout();
				ftp.disconnect();
			}
			in.close();
			return 0;
		} catch (SocketException e) {
			log.error("ftp store error", e);
			return 3;
		} catch (IOException e) {
			log.error("ftp store error", e);
			return 4;
		}
	}

	private FTPClient getClient() throws SocketException, IOException {
		FTPClient ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out)));
		ftp.setDefaultPort(getPort());
		ftp.connect(getIp());
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			log.warn("FTP server refused connection: {}", getIp());
			ftp.disconnect();
			return null;
		}
		if (!ftp.login(getUsername(), getPassword())) {
			log.warn("FTP server refused login: {}, user: {}", getIp(),
					getUsername());
			ftp.logout();
			ftp.disconnect();
			return null;
		}
		ftp.setControlEncoding(getEncoding());
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.enterLocalPassiveMode();
		return ftp;
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public Ftp () {
	}

	/**
	 * Constructor for primary key
	 */
	public Ftp (Integer id) {
		this.setId(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Ftp(Integer id, String name, String ip, Integer port, String encoding,
			String url) {
		this.setId(id);
		this.setName(name);
		this.setIp(ip);
		this.setPort(port);
		this.setEncoding(encoding);
		this.setUrl(url);
	}

	/* [CONSTRUCTOR MARKER END] */
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Integer id;

	// fields
	private String name, ip;
	private Integer port;
	private String username, password, encoding;
	private Integer timeout;
	private String path, url;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="ftp_id"
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
	 * Return the value associated with the column: ftp_name
	 */
	public String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: ftp_name
	 * @param name the ftp_name value
	 */
	public void setName (String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: ip
	 */
	public String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: ip
	 * @param ip the ip value
	 */
	public void setIp (String ip) {
		this.ip = ip;
	}


	/**
	 * Return the value associated with the column: port
	 */
	public Integer getPort () {
		return port;
	}

	/**
	 * Set the value related to the column: port
	 * @param port the port value
	 */
	public void setPort (Integer port) {
		this.port = port;
	}


	/**
	 * Return the value associated with the column: username
	 */
	public String getUsername () {
		return username;
	}

	/**
	 * Set the value related to the column: username
	 * @param username the username value
	 */
	public void setUsername (String username) {
		this.username = username;
	}


	/**
	 * Return the value associated with the column: password
	 */
	public String getPassword () {
		return password;
	}

	/**
	 * Set the value related to the column: password
	 * @param password the password value
	 */
	public void setPassword (String password) {
		this.password = password;
	}


	/**
	 * Return the value associated with the column: encoding
	 */
	public String getEncoding () {
		return encoding;
	}

	/**
	 * Set the value related to the column: encoding
	 * @param encoding the encoding value
	 */
	public void setEncoding (String encoding) {
		this.encoding = encoding;
	}


	/**
	 * Return the value associated with the column: timeout
	 */
	public Integer getTimeout () {
		return timeout;
	}

	/**
	 * Set the value related to the column: timeout
	 * @param timeout the timeout value
	 */
	public void setTimeout (Integer timeout) {
		this.timeout = timeout;
	}


	/**
	 * Return the value associated with the column: ftp_path
	 */
	public String getPath () {
		return path;
	}

	/**
	 * Set the value related to the column: ftp_path
	 * @param path the ftp_path value
	 */
	public void setPath (String path) {
		this.path = path;
	}


	/**
	 * Return the value associated with the column: url
	 */
	public String getUrl () {
		return url;
	}

	/**
	 * Set the value related to the column: url
	 * @param url the url value
	 */
	public void setUrl (String url) {
		this.url = url;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof Ftp)) return false;
		else {
			Ftp ftp = (Ftp) obj;
			if (null == this.getId() || null == ftp.getId()) return false;
			else return (this.getId().equals(ftp.getId()));
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