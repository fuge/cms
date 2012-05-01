package foo.common.fck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractFckAct {
	/**
	 * 可供子类使用的日志
	 */
	protected Logger log = LoggerFactory.getLogger(getClass());
}
