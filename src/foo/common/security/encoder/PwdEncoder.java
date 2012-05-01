package foo.common.security.encoder;

/**
 * 密码加密接口
 * 
 * @author liufang
 * 
 */
public interface PwdEncoder {
	/**
	 * 密码加密
	 * 
	 * @param rawPass
	 *            未加密密码，null作为空串
	 * @return 加密后密码
	 */
	public String encodePassword(String rawPass);

	/**
	 * 密码加密
	 * 
	 * @param rawPass
	 *            未加密密码，null作为空串
	 * @param salt
	 *            混淆码
	 * @return
	 */
	public String encodePassword(String rawPass, String salt);

	/**
	 * 验证密码是否正确
	 * 
	 * @param encPass
	 *            加密密码
	 * @param rawPass
	 *            未加密密码，null作为空串
	 * @return true:密码正确；false:密码错误
	 */
	public boolean isPasswordValid(String encPass, String rawPass);

	/**
	 * 验证密码是否正确
	 * 
	 * @param encPass
	 *            加密密码
	 * @param rawPass
	 *            未加密密码，null作为空串
	 * @param salt
	 *            混淆码
	 * @return true:密码正确；false:密码错误
	 */
	public boolean isPasswordValid(String encPass, String rawPass, String salt);
}
