package foo.common.security;

/**
 * 用户被禁用异常
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class DisabledException extends AccountStatusException {
	public DisabledException() {
	}

	public DisabledException(String msg) {
		super(msg);
	}

	public DisabledException(String msg, Object extraInformation) {
		super(msg, extraInformation);
	}
}
