package foo.common.lucene;

import java.math.BigDecimal;

import org.apache.lucene.util.NumericUtils;
import org.springframework.util.Assert;

/**
 * 将BigDecimal类型的金额转换成String类型，便于Lucene搜索。
 * 
 * @author liufang
 * 
 */
public class MoneyTools {
	private static final BigDecimal MULTIPLE = new BigDecimal(1000);

	/**
	 * 将money*1000，转换成long，再转换成string。
	 * 
	 * @param money
	 * @return
	 * @see NumberTools#longToString(long)
	 */
	public static String moneyToString(BigDecimal money) {
		Assert.notNull(money);
		return NumericUtils.longToPrefixCoded(money.multiply(MULTIPLE)
				.longValue());
	}

	/**
	 * 将s转换成long，再转换成BigDecimal，除以1000。
	 * 
	 * @param s
	 * @return
	 */
	public static BigDecimal stringToMoney(String s) {
		BigDecimal number = new BigDecimal(NumericUtils.prefixCodedToLong(s));
		return number.divide(MULTIPLE);
	}
}
