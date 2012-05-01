package foo.common.ipseek;

/**
 * 
 * IP解析异常
 *
 */
@SuppressWarnings("serial")
public class IPParseException extends RuntimeException {
	public IPParseException(String msg) {
		super(msg);
	}

	public IPParseException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
