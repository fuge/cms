package foo.cms.staticpage.exception;

/**
 * 
 * 模板解析异常
 * 
 */
@SuppressWarnings("serial")
public class TemplateParseException extends StaticPageException {
	public TemplateParseException() {
	}

	public TemplateParseException(String msg, Integer generated, String errorTitle) {
		super(msg, generated, errorTitle);
	}
}
