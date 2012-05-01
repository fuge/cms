package foo.common.ipseek;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import foo.common.web.springmvc.RealPathResolver;

public class IPSeekerImpl implements IPSeeker{
	// 用来做为cache，查询一个ip时首先查看cache，以减少不必要的重复查找
	private Map<String, IPLocation> ipCache;
	// 随机文件访问类
	private RandomAccessFile ipFile;
	// 起始地区的开始和结束的绝对偏移
	private long ipBegin, ipEnd;
	// 为提高效率而采用的临时变量
	private IPLocation loc;
	private byte[] b4;
	private byte[] b3;
	private String dir;
	private String filename;

	public void init(){
		String file = realPathResolver.get(dir) + File.separator + filename;
		ipCache = new HashMap<String, IPLocation>();
		loc = new IPLocation();
		b4 = new byte[4];
		b3 = new byte[3];
		try {
			ipFile = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			throw new IPParseException("ip data file not found!", e);
		}
		// 如果打开文件成功，读取文件头信息
		if (ipFile != null) {
			try {
				ipBegin = readLong4(0);
				ipEnd = readLong4(4);
				if (ipBegin == -1 || ipEnd == -1) {
					ipFile.close();
					ipFile = null;
				}
			} catch (IOException e) {
				// LogFactory.log("IP地址信息文件格式有错误，IP显示功能将无法使用", Level.ERROR, e);
				ipFile = null;
			}
		}
	}

	public IPLocation getIPLocation(String ip) {
		return new IPLocation(getCountry(ip), getArea(ip));
	}

	/**
	 * 根据IP得到国家名
	 * 
	 * @param ip
	 *            ip的字节数组形式
	 * @return 国家名字符串
	 */
	public String getCountry(byte[] ip) {
		// 检查ip地址文件是否正常
		if (ipFile == null)
			return Message.bad_ip_file;
		// 保存ip，转换ip字节数组为字符串形式
		String ipStr = Util.getIpStringFromBytes(ip);
		// 先检查cache中是否已经包含有这个ip的结果，没有再搜索文件
		if (ipCache.containsKey(ipStr)) {
			IPLocation ipLoc = ipCache.get(ipStr);
			return ipLoc.getCountry();
		} else {
			IPLocation ipLoc = getIPLocation(ip);
			ipCache.put(ipStr, ipLoc.getCopy());
			return ipLoc.getCountry();
		}
	}

	/**
	 * 根据IP得到国家名
	 * 
	 * @param ip
	 *            IP的字符串形式
	 * @return 国家名字符串
	 */
	public String getCountry(String ip) {
		return getCountry(Util.getIpByteArrayFromString(ip));
	}

	/**
	 * 根据IP得到地区名
	 * 
	 * @param ip
	 *            ip的字节数组形式
	 * @return 地区名字符串
	 */
	public String getArea(byte[] ip) {
		// 检查ip地址文件是否正常
		if (ipFile == null)
			return Message.bad_ip_file;
		// 保存ip，转换ip字节数组为字符串形式
		String ipStr = Util.getIpStringFromBytes(ip);
		// 先检查cache中是否已经包含有这个ip的结果，没有再搜索文件
		if (ipCache.containsKey(ipStr)) {
			IPLocation ipLoc = ipCache.get(ipStr);
			return ipLoc.getArea();
		} else {
			IPLocation ipLoc = getIPLocation(ip);
			ipCache.put(ipStr, ipLoc.getCopy());
			return ipLoc.getArea();
		}
	}

	/**
	 * 根据IP得到地区名
	 * 
	 * @param ip
	 *            IP的字符串形式
	 * @return 地区名字符串
	 */
	public String getArea(String ip) {
		return getArea(Util.getIpByteArrayFromString(ip));
	}

	/**
	 * 根据ip搜索ip信息文件，得到IPLocation结构，所搜索的ip参数从类成员ip中得到
	 * 
	 * @param ip
	 *            要查询的IP
	 * @return IPLocation结构
	 */
	private IPLocation getIPLocation(byte[] ip) {
		IPLocation info = null;
		long offset = locateIP(ip);
		if (offset != -1){
			info = getIPLocation(offset);
		}
		if (info == null) {
			info = new IPLocation();
			info.setCountry(Message.unknown_country);
			info.setArea(Message.unknown_area);
		}
		return info;
	}

	/**
	 * 从offset位置读取4个字节为一个long，因为java为big-endian格式，所以没办法 用了这么一个函数来做转换
	 * 
	 * @param offset
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private long readLong4(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ret |= (ipFile.readByte() & 0xFF);
			ret |= ((ipFile.readByte() << 8) & 0xFF00);
			ret |= ((ipFile.readByte() << 16) & 0xFF0000);
			ret |= ((ipFile.readByte() << 24) & 0xFF000000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * 从offset位置读取3个字节为一个long，因为java为big-endian格式，所以没办法 用了这么一个函数来做转换
	 * 
	 * @param offset
	 *            整数的起始偏移
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private long readLong3(long offset) {
		long ret = 0;
		try {
			ipFile.seek(offset);
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * 从当前位置读取3个字节转换成long
	 * 
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private long readLong3() {
		long ret = 0;
		try {
			ipFile.readFully(b3);
			ret |= (b3[0] & 0xFF);
			ret |= ((b3[1] << 8) & 0xFF00);
			ret |= ((b3[2] << 16) & 0xFF0000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}

	/**
	 * 从offset位置读取四个字节的ip地址放入ip数组中，读取后的ip为big-endian格式，但是
	 * 文件中是little-endian形式，将会进行转换
	 * 
	 * @param offset
	 * @param ip
	 */
	private void readIP(long offset, byte[] ip) {
		try {
			ipFile.seek(offset);
			ipFile.readFully(ip);
			byte temp = ip[0];
			ip[0] = ip[3];
			ip[3] = temp;
			temp = ip[1];
			ip[1] = ip[2];
			ip[2] = temp;
		} catch (IOException e) {
			// LogFactory.log("", Level.ERROR, e);
		}
	}

	/**
	 * 把类成员ip和beginIp比较，注意这个beginIp是big-endian的
	 * 
	 * @param ip
	 *            要查询的IP
	 * @param beginIp
	 *            和被查询IP相比较的IP
	 * @return 相等返回0，ip大于beginIp则返回1，小于返回-1。
	 */
	private int compareIP(byte[] ip, byte[] beginIp) {
		for (int i = 0; i < 4; i++) {
			int r = compareByte(ip[i], beginIp[i]);
			if (r != 0)
				return r;
		}
		return 0;
	}

	/**
	 * 把两个byte当作无符号数进行比较
	 * 
	 * @param b1
	 * @param b2
	 * @return 若b1大于b2则返回1，相等返回0，小于返回-1
	 */
	private int compareByte(byte b1, byte b2) {
		if ((b1 & 0xFF) > (b2 & 0xFF)) // 比较是否大于
			return 1;
		else if ((b1 ^ b2) == 0)// 判断是否相等
			return 0;
		else
			return -1;
	}

	/**
	 * 这个方法将根据ip的内容，定位到包含这个ip国家地区的记录处，返回一个绝对偏移 方法使用二分法查找。
	 * 
	 * @param ip
	 *            要查询的IP
	 * @return 如果找到了，返回结束IP的偏移，如果没有找到，返回-1
	 */
	private long locateIP(byte[] ip) {
		long m = 0;
		int r;
		// 比较第一个ip项
		readIP(ipBegin, b4);
		r = compareIP(ip, b4);
		if (r == 0)
			return ipBegin;
		else if (r < 0)
			return -1;
		// 开始二分搜索
		for (long i = ipBegin, j = ipEnd; i < j;) {
			m = getMiddleOffset(i, j);
			readIP(m, b4);
			r = compareIP(ip, b4);
			// log.debug(Utils.getIpStringFromBytes(b));
			if (r > 0)
				i = m;
			else if (r < 0) {
				if (m == j) {
					j -= IP_RECORD_LENGTH;
					m = j;
				} else
					j = m;
			} else
				return readLong3(m + 4);
		}
		// 如果循环结束了，那么i和j必定是相等的，这个记录为最可能的记录，但是并非
		// 肯定就是，还要检查一下，如果是，就返回结束地址区的绝对偏移
		m = readLong3(m + 4);
		readIP(m, b4);
		r = compareIP(ip, b4);
		if (r <= 0)
			return m;
		else
			return -1;
	}

	/**
	 * 得到begin偏移和end偏移中间位置记录的偏移
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private long getMiddleOffset(long begin, long end) {
		long records = (end - begin) / IP_RECORD_LENGTH;
		records >>= 1;
		if (records == 0)
			records = 1;
		return begin + records * IP_RECORD_LENGTH;
	}

	/**
	 * 给定一个ip国家地区记录的偏移，返回一个IPLocation结构
	 * 
	 * @param offset
	 *            国家记录的起始偏移
	 * @return IPLocation对象
	 */
	private IPLocation getIPLocation(long offset) {
		try {
			// 跳过4字节ip
			ipFile.seek(offset + 4);
			// 读取第一个字节判断是否标志字节
			byte b = ipFile.readByte();
			if (b == REDIRECT_MODE_1) {
				// 读取国家偏移
				long countryOffset = readLong3();
				// 跳转至偏移处
				ipFile.seek(countryOffset);
				// 再检查一次标志字节，因为这个时候这个地方仍然可能是个重定向
				b = ipFile.readByte();
				if (b == REDIRECT_MODE_2) {
					loc.setCountry(readString(readLong3()));
					ipFile.seek(countryOffset + 4);
				} else
					loc.setCountry(readString(countryOffset));
				// 读取地区标志
				loc.setArea(readArea(ipFile.getFilePointer()));
			} else if (b == REDIRECT_MODE_2) {
				loc.setCountry(readString(readLong3()));
				loc.setArea(readArea(offset + 8));
			} else {
				loc.setCountry(readString(ipFile.getFilePointer() - 1));
				loc.setArea(readArea(ipFile.getFilePointer()));
			}
			return loc;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 从offset偏移开始解析后面的字节，读出一个地区名
	 * 
	 * @param offset
	 *            地区记录的起始偏移
	 * @return 地区名字符串
	 * @throws IOException
	 */
	private String readArea(long offset) throws IOException {
		ipFile.seek(offset);
		byte b = ipFile.readByte();
		if (b == REDIRECT_MODE_1 || b == REDIRECT_MODE_2) {
			long areaOffset = readLong3(offset + 1);
			if (areaOffset == 0)
				return Message.unknown_area;
			else
				return readString(areaOffset);
		} else
			return readString(offset);
	}

	/**
	 * 从offset偏移处读取一个以0结束的字符串
	 * 
	 * @param offset
	 *            字符串起始偏移
	 * @return 读取的字符串，出错返回空字符串
	 */
	private String readString(long offset) {
		try {
			ipFile.seek(offset);
			int i = 0;
			byte[] buf = new byte[100];
			while ((buf[i] = ipFile.readByte()) != 0) {
				++i;
				if (i >= buf.length) {
					byte[] tmp = new byte[i + 100];
					System.arraycopy(buf, 0, tmp, 0, i);
					buf = tmp;
				}
			}
			// for(i = 0, buf[i] = ipFile.readByte(); buf[i] != 0; buf[++i] =
			// ipFile.readByte());
			if (i != 0){
				return Util.getString(buf, 0, i, "GBK");
			}
		} catch (IOException e) {
			// LogFactory.log("", Level.ERROR, e);
		}
		return "";
	}
	
	@Autowired
	private RealPathResolver realPathResolver;

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
