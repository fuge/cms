package foo.cms.staticpage.exception;

/**
 * 
 * 生成静态页异常
 * 
 */
@SuppressWarnings("serial")
public class StaticPageException extends Exception {
	private Integer generated;
	private String errorTitle;

	public StaticPageException() {
	}

	public StaticPageException(String msg) {
		super(msg);
	}

	public StaticPageException(String msg, Integer generated) {
		super(msg);
		this.generated = generated;
	}

	public StaticPageException(String msg, Integer generated, String errorTitle) {
		super(msg);
		this.generated = generated;
		this.errorTitle = errorTitle;
	}

	public Integer getGenerated() {
		return generated;
	}

	public String getErrorTitle() {
		return errorTitle;
	}

}
