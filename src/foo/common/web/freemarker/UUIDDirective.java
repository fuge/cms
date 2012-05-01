package foo.common.web.freemarker;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * UUID生成器
 * 
 * @author liufang
 * 
 */
public class UUIDDirective implements TemplateDirectiveModel {
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String uuid = UUID.randomUUID().toString();
		uuid = StringUtils.remove(uuid, '-');
		env.getOut().append(uuid);
	}
}
