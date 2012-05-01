package foo.common.web.freemarker;

import freemarker.template.TemplateModelException;

/**
 * 非布尔参数异常
 * 
 * @author liufang
 * 
 */
@SuppressWarnings("serial")
public class MustBooleanException extends TemplateModelException {
	public MustBooleanException(String paramName) {
		super("The \"" + paramName + "\" parameter must be a boolean.");
	}
}
