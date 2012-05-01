package foo.common.web.session.id;

import org.apache.commons.lang.StringUtils;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

/**
 * 通过UUID生成SESSION ID
 * 
 * @author liufang
 * 
 */
public class JugUUIDGenerator implements SessionIdGenerator {
	public String get() {
		UUID uuid = UUIDGenerator.getInstance().generateRandomBasedUUID();
		return StringUtils.remove(uuid.toString(), '-');
	}

	public static void main(String[] args) {
		UUIDGenerator.getInstance().generateRandomBasedUUID();
		long time = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			UUIDGenerator.getInstance().generateRandomBasedUUID();
		}
		time = System.currentTimeMillis() - time;
		System.out.println(time);
	}
}
