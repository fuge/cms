package foo.common.web.freemarker;

import freemarker.template.TemplateModelException;

/**
 * 非数字参数异常
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class MustStringException extends TemplateModelException {
	public MustStringException(String paramName) {
		super("The \"" + paramName + "\" parameter must be a string.");
	}
}
