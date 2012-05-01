package foo.cms.staticpage.exception;

/**
 * 
 * 内容未审核异常
 *
 */
@SuppressWarnings("serial")
public class ContentNotCheckedException extends StaticPageException {
	public ContentNotCheckedException() {
	}

	public ContentNotCheckedException(String msg, Integer generated,
			String errorTitle) {
		super(msg, generated, errorTitle);
	}
}
