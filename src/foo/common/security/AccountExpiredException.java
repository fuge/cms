package foo.common.security;

/**
 * 账号过期异常。如：改账号只缴纳了一年的费用，一年后没有续费。
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class AccountExpiredException extends AccountStatusException {
	public AccountExpiredException() {
	}

	public AccountExpiredException(String msg) {
		super(msg);
	}

	public AccountExpiredException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
