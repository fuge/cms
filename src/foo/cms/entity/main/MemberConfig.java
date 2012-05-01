package foo.cms.entity.main;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import foo.common.util.StrUtils;

public class MemberConfig {
	public MemberConfig() {
	}

	public MemberConfig(Map<String, String> attr) {
		this.attr = attr;
	}

	private Map<String, String> attr;

	public Map<String, String> getAttr() {
		if (attr == null) {
			attr = new HashMap<String, String>();
		}
		return attr;
	}

	public void setAttr(Map<String, String> attr) {
		this.attr = attr;
	}

	/**
	 * 是否开启注册
	 */
	public static String REGISTER_ON = "register_on";
	/**
	 * 是否开启会员功能
	 */
	public static String MEMBER_ON = "member_on";
	/**
	 * 用户名保留字
	 */
	public static String USERNAME_RESERVED = "username_reserved";
	/**
	 * 用户名最小长度
	 */
	public static String USERNAME_MIN_LEN = "username_min_len";
	/**
	 * 密码最小长度
	 */
	public static String PASSWORD_MIN_LEN = "password_min_len";
	/**
	 * 用户头像宽度
	 */
	public static String USERIMG_WIDTH = "user_img_width";
	/**
	 * 用户头像高度
	 */
	public static String USERIMG_HEIGHT= "user_img_height";

	/**
	 * 是否开启用户注册
	 * 
	 * @return
	 */
	public boolean isRegisterOn() {
		String registerOn = getAttr().get(REGISTER_ON);
		return !"false".equals(registerOn);
	}

	/**
	 * 设置是否开启用户注册
	 * 
	 * @param registerOn
	 */
	public void setRegisterOn(boolean registerOn) {
		getAttr().put(REGISTER_ON, String.valueOf(registerOn));
	}

	/**
	 * 是否开启会员功能
	 * 
	 * @return
	 */
	public boolean isMemberOn() {
		String memberOn = getAttr().get(MEMBER_ON);
		return !"false".equals(memberOn);
	}

	/**
	 * 设置是否开启会员功能
	 * 
	 * @param memberOn
	 */
	public void setMemberOn(boolean memberOn) {
		getAttr().put(MEMBER_ON, String.valueOf(memberOn));
	}

	/**
	 * 获取用户名保留字
	 * 
	 * 用','分开，'*'代表任意字符。
	 * 
	 * @return
	 */
	public String getUsernameReserved() {
		return getAttr().get(USERNAME_RESERVED);
	}

	/**
	 * 设置用户名保留字
	 */
	public void setUsernameReserved(String usernameReserved) {
		getAttr().put(USERNAME_RESERVED, usernameReserved);
	}

	/**
	 * 检查用户名是否为保留字
	 * 
	 * @return 如果通过返回true，否则返回false。
	 */
	public boolean checkUsernameReserved(String name) {
		if (StringUtils.isBlank(name)) {
			return false;
		}
		String reserved = getUsernameReserved();
		if (StringUtils.isBlank(reserved)) {
			return true;
		}
		for (String search : StringUtils.split(reserved)) {
			if (StrUtils.contains(name, search)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取用户名最小长度
	 * 
	 * @return
	 */
	public int getUsernameMinLen() {
		String len = getAttr().get(USERNAME_MIN_LEN);
		if (!StringUtils.isBlank(len)) {
			try {
				return Integer.valueOf(len);
			} catch (NumberFormatException e) {
			}
		}
		// 默认最小长度为3
		return 3;
	}

	/**
	 * 设置用户名最小长度
	 * 
	 * @param len
	 */
	public void setUsernameMinLen(int len) {
		getAttr().put(USERNAME_MIN_LEN, String.valueOf(len));
	}

	/**
	 * 获取密码最小长度
	 * 
	 * @return
	 */
	public int getPasswordMinLen() {
		String len = getAttr().get(PASSWORD_MIN_LEN);
		if (!StringUtils.isBlank(len)) {
			try {
				return Integer.valueOf(len);
			} catch (NumberFormatException e) {
			}
		}
		return 3;
	}

	/**
	 * 设置密码最小长度
	 * 
	 * @param len
	 */
	public void setPasswordMinLen(int len) {
		getAttr().put(PASSWORD_MIN_LEN, String.valueOf(len));
	}
	
	/**
	 * 获取用户头像宽度
	 * 
	 * @return
	 */
	public int getUserImgWidth(){
		String len = getAttr().get(USERIMG_WIDTH);
		if (!StringUtils.isBlank(len)) {
			try {
				return Integer.valueOf(len);
			} catch (NumberFormatException e) {
			}
		}
		// 默认最小长度为3
		return 143;
	}

	/**
	 * 设置用户头像高宽度
	 * 
	 * @param width
	 */
	public void setUserImgWidth(int width) {
		getAttr().put(USERIMG_WIDTH, String.valueOf(width));
	}
	/**
	 * 获取用户头像高度
	 * 
	 * @return
	 */
	public int getUserImgHeight(){
		String len = getAttr().get(USERIMG_HEIGHT);
		if (!StringUtils.isBlank(len)) {
			try {
				return Integer.valueOf(len);
			} catch (NumberFormatException e) {
			}
		}
		// 默认最小长度为3
		return 98;
	}

	/**
	 * 设置用户头像高高度
	 * 
	 * @param height
	 */
	public void setUserImgHeight(int height) {
		getAttr().put(USERIMG_HEIGHT, String.valueOf(height));
	}

}
