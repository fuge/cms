package foo.cms.staticpage.exception;

/**
 * 
 * 内容静态页未开启异常
 * 
 */
@SuppressWarnings("serial")
public class StaticPageNotOpenException extends StaticPageException {
	public StaticPageNotOpenException() {
	}

	public StaticPageNotOpenException(String msg, Integer generated,
			String errorTitle) {
		super(msg, generated, errorTitle);
	}
}
