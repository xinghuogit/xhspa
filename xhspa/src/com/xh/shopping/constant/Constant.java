/*************************************************************************************************
 * 
 * 文件名称：Constant.java
 * 内容摘要：Constant.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-18 下午3:03:03
 * 修改记录：
 * 修改日期：2015-12-18 下午3:03:03
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.constant;

import java.io.Serializable;

/**
 * @filename 文件名称：Constant.java
 * @contents 内容摘要：常量
 */
public class Constant implements Serializable {
	public static boolean SHOW = true;

	/**
	 * 设置编码
	 */
	public static final String UTF_8 = "UTF-8";

	/**
	 * HTTP 网络超时连接时间 20秒
	 */
	public static final int TIMEOUT_CONNECTION = 20000;

	/**
	 * HTTPS 网络超时连接时间 20秒
	 */
	public static final int TIMEOUT_SOCKET = 20000;

	/**
	 * get请求
	 */
	public static final String GET = "GET";
	/**
	 * post请求
	 */
	public static final String POST = "POST";

	/**
	 * 请求数据成功
	 */
	public static final String CODE_NORMAL = "0000";

	/**
	 * 短信验证
	 */
	public static final String SMS_APPKEY = "d84ab355d37a";
	public static final String SMS_APPSECRET = "57a8d7c06507c07f8ee40874586bebd4";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String BOUNDARY__ = "--";

	/**
	 * 公司http请求boundary
	 */
	public static final String BOUNDARY = "----WebKitFormBoundaryEnPfBCbCqpmt7O2G";

	/**
	 * 家
	 */

	// private static String START_SERVICE = "http://192.168.1.7:8080/xhsp/";
	// private static String START_SERVICE = "http://192.168.1.100:8080/xhsp/";

	/**
	 * 公司
	 */
	// public static final String START_SERVICE =
	// "http://192.168.56.1:8080/xhsp/";
	public static final String START_SERVICE = "http://192.168.31.108:8080/xhsp/";

	public static String getService(String str) {
		return START_SERVICE + str;
	}

	/**
	 * 注册
	 */
	public static final String API_REGISTER = "userregister.json";

	/**
	 * 登录
	 */
	public static final String API_LOGIN = "login.json";

	/**
	 * 修改密码
	 */
	public static final String API_CHANGEPASSWORD = "changepassword.json";

}
