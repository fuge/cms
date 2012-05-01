package foo.common.ipseek;

/**
 * 
 * 封装ip相关信息，目前只有两个字段，ip所在的国家和地区
 */
public class IPLocation {
	private String country = "";
	private String area = "";

	public IPLocation() {}
	
	public IPLocation(String country, String area){
		this.country = country;
		this.area = area;
	}

	public IPLocation getCopy() {
		IPLocation ret = new IPLocation();
		ret.country = country;
		ret.area = area;
		return ret;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		// 如果为局域网，纯真IP地址库的地区会显示CZ88.NET,这里把它去掉
		if("CZ88.NET".equals(area.trim())){
			this.area = "局域网";
			return;
		}
		this.area = area;
	}
}