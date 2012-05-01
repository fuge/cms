package foo.common.util;

public class ArithmeticUtils {
	private ArithmeticUtils() {
	}

	public static int dividend(int dividend) {
		return dividend == 0 ? 1 : dividend;
	}

	public static long dividend(long dividend) {
		return dividend == 0 ? 1 : dividend;
	}
}
