/*************************************************************************************************
 * 版权所有 (C)2015,  
 * 
 * 文件名称：UIHelper.java
 * 内容摘要：UIHelper.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-11-4 下午7:47:01
 * 修改记录：
 * 修改日期：2015-11-4 下午7:47:01
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.xh.shopping.R;
import com.xh.shopping.setting.SettingHelper;

/**
 @filename文件名称：UIHelper.java
 @content
 */
/**
 * @FileName 文件名称：UIHelper.java
 * @Contents 内容摘要：界面帮助类
 */
public class UIHelper {
	private static UIHelper mUIHelper = null;

	private UIHelper() {
		super();
	}

	public static synchronized UIHelper getInstance() {
		if (mUIHelper == null) {
			mUIHelper = new UIHelper();
		}
		return mUIHelper;
	}

	/**
	 * 设置系统状态栏
	 */
	public void setSystemBar() {
		// 修改沉浸式状态栏 要大于Android系统4.4 版本API19
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		Activity activity = SettingHelper.getInstance().getCurrentActivity();
		// 获取系统状态栏管理者
		SystemBarTintManager manager = new SystemBarTintManager(activity);
		// 是否修改
		manager.setStatusBarTintEnabled(true);
		// 修改的颜色
		manager.setStatusBarTintResource(R.color.app_overall);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void setTranslucentStatus(boolean is) {
		// 获取当前Activity
		Activity activity = SettingHelper.getInstance().getCurrentActivity();
		Window window = activity.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (is) {
			// 或之后赋予值
			params.flags |= bits;
		} else {
			// 与之后赋予值
			params.flags &= ~bits;
		}
		// 设置状态参数
		window.setAttributes(params);
	}

}
