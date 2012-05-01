package foo.common.security;

/**
 * 认证信息过期异常。如：规定密码必须在一个月后修改，但是没有修改。
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class CredentialsExpiredException extends AccountStatusException {
	public CredentialsExpiredException() {
	}

	public CredentialsExpiredException(String msg) {
		super(msg);
	}

	public CredentialsExpiredException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
