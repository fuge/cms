package foo.common.web.springmvc;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class ServletContextRealPathResolver implements RealPathResolver,
		ServletContextAware {
	public String get(String path) {
		return context.getRealPath(path);
	}

	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;
	}

	private ServletContext context;
}
