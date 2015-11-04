/*************************************************************************************************
 * 版权所有 (C)2015,  
 * 
 * 文件名称：SettingHelper.java
 * 内容摘要：SettingHelper.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-11-4 下午7:56:54
 * 修改记录：
 * 修改日期：2015-11-4 下午7:56:54
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.setting;

import android.app.Activity;
import android.content.Context;

/**
 @filename文件名称：SettingHelper.java
 @content
 */
/**
 * @FileName 文件名称：SettingHelper.java
 * @Contents 内容摘要：设置
 */
public class SettingHelper {
	private Context ApplicationContext;
	private Activity currentActivity;

	public Context getApplicationContext() {
		return ApplicationContext;
	}

	public void setApplicationContext(Context applicationContext) {
		ApplicationContext = applicationContext;
	}

	public Activity getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}

	private static SettingHelper mSettingHelper;

	private SettingHelper() {
	}

	public static synchronized SettingHelper getInstance() {
		if (mSettingHelper == null) {
			mSettingHelper = new SettingHelper();
		}
		return mSettingHelper;
	}

}
