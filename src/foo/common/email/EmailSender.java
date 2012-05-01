package foo.common.email;

/**
 * 邮件发送者
 * 
 * @author liufang
 * 
 */
public interface EmailSender {
	/**
	 * 发送服务器IP
	 * 
	 * @return
	 */
	public String getHost();

	/**
	 * 发送服务器端口
	 * 
	 * @return
	 */
	public Integer getPort();

	/**
	 * 发送编码
	 * 
	 * @return
	 */
	public String getEncoding();

	/**
	 * 登录名
	 * 
	 * @return
	 */
	public String getUsername();

	/**
	 * 密码
	 * 
	 * @return
	 */
	public String getPassword();

	/**
	 * 发送人
	 * 
	 * @return
	 */
	public String getPersonal();
}
