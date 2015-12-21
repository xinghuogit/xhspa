/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：NetworkUtil.java
 * 内容摘要：NetworkUtil.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-21 下午3:57:24
 * 修改记录：
 * 修改日期：2015-12-21 下午3:57:24
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.xh.shopping.setting.SettingHelper;

/**
 * @filename 文件名称：NetworkUtil.java
 * @contents 内容摘要：
 */
public class NetworkUtil {
	/**
	 * 
	 * @return 网络是否可用
	 */
	public static boolean isNetworkAvailable() {
		try {
			Context context = SettingHelper.getInstance()
					.getApplicationContext();
			if (context == null) {
				return false;
			} else {
				ConnectivityManager connectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager
						.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isAvailable()) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @return 用的网络是否是WiFi
	 */
	public static boolean isWifiAvailable() {
		Context context = SettingHelper.getInstance().getApplicationContext();
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}
}
