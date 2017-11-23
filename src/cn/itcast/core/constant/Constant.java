package cn.itcast.core.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量公共类
 * @author Administrator
 *
 */
public class Constant {
	/*-------------------------------系统权限集合-------------------------------*/
	public static String PRIVILEGE_XZGL="xzgl";      //行政管理(XZGL)常量
	public static String PRIVILEGE_HQFW="hqgl";      //后勤管理
	public static String PRIVILEGE_ZXXX="zxxx";      //在线服务
	public static String PRIVILEGE_NSFW="nsfw";      //纳税服务
	public static String PRIVILEGE_SPACE="space";    //我的空间

	//权限的集合Map, 常量Map,显示中文
	public static Map<String, String> PRIVILEGE_MAP;
	//初始化值
	static {
		PRIVILEGE_MAP = new HashMap<String, String>();
		PRIVILEGE_MAP.put(PRIVILEGE_XZGL, "行政管理");
		PRIVILEGE_MAP.put(PRIVILEGE_HQFW, "后勤服务");
		PRIVILEGE_MAP.put(PRIVILEGE_ZXXX, "在线学习");
		PRIVILEGE_MAP.put(PRIVILEGE_NSFW, "纳税服务");
		PRIVILEGE_MAP.put(PRIVILEGE_SPACE, "我的空间");
	}
	
	
	//系统中用户User在Session中的标识符(哪个用户登录)
	public static String USER="SYS_USER";
	
	
}
