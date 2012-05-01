package foo.common.ipseek;


public interface IPSeeker {
	public static final int IP_RECORD_LENGTH = 7;
	public static final byte REDIRECT_MODE_1 = 0x01;
	public static final byte REDIRECT_MODE_2 = 0x02;
	
	public IPLocation getIPLocation(String ip);
}
