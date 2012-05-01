package foo.common.web.freemarker;

import freemarker.template.TemplateModelException;

/**
 * 非数字参数异常
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class MustNumberException extends TemplateModelException {
	public MustNumberException(String paramName) {
		super("The \"" + paramName + "\" parameter must be a number.");
	}
}
