package foo.cms.staticpage.exception;

/**
 * 
 * 模板未找到异常
 * 
 */
@SuppressWarnings("serial")
public class TemplateNotFoundException extends StaticPageException {
	public TemplateNotFoundException() {
	}

	public TemplateNotFoundException(String msg, Integer generated, String errorTitle) {
		super(msg, generated, errorTitle);
	}
}
