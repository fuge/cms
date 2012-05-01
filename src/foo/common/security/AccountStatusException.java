package foo.common.security;

/**
 * 账号状态异常
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class AccountStatusException extends AuthenticationException {
	public AccountStatusException() {
	}

	public AccountStatusException(String msg) {
		super(msg);
	}

	public AccountStatusException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
