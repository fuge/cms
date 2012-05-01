package foo.cms.staticpage.exception;

/**
 * 
 * 未生成静态页异常
 * 
 */
@SuppressWarnings("serial")
public class GeneratedZeroStaticPageException extends StaticPageException {
	public GeneratedZeroStaticPageException() {
	}

	public GeneratedZeroStaticPageException(String msg) {
		super(msg, 0);
	}
}
