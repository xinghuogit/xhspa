/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：ToastUtil.java
 * 内容摘要：ToastUtil.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-18 下午3:41:40
 * 修改记录：
 * 修改日期：2015-12-18 下午3:41:40
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @filename 文件名称：ToastUtil.java
 * @contents 内容摘要：Toast工具类
 */
public class ToastUtil {

	private static Toast toast;

	public static void makeToast(Context context, String title) {
		if (context != null) {
			if (!StringUtil.isEmpty(title)) {
				if (toast != null) {
					toast.cancel();
				}
				toast = android.widget.Toast.makeText(context, title,
						android.widget.Toast.LENGTH_SHORT);
				TextView tv = (TextView) toast.getView().findViewById(
						android.R.id.message);
				tv.setGravity(Gravity.CENTER_HORIZONTAL);
				toast.show();
			}
		}
	}

	public static void makeToast(Context context, int rid) {
		if (context != null) {
			if (toast != null) {
				toast.cancel();
			}
			toast = android.widget.Toast.makeText(context, rid,
					android.widget.Toast.LENGTH_SHORT);
			TextView tv = (TextView) toast.getView().findViewById(
					android.R.id.message);
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			toast.show();
		}
	}

	public static void makeToast(Context context, int rid, int time) {
		if (context != null) {
			if (toast != null) {
				toast.cancel();
			}
			toast = android.widget.Toast.makeText(context, rid,
					android.widget.Toast.LENGTH_SHORT);
			TextView tv = (TextView) toast.getView().findViewById(
					android.R.id.message);
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			toast.show();
		}
	}

	public static void makeToast(Context context, String rid, int time) {
		if (context != null) {
			if (toast != null) {
				toast.cancel();
			}
			toast = android.widget.Toast.makeText(context, rid,
					android.widget.Toast.LENGTH_SHORT);
			TextView tv = (TextView) toast.getView().findViewById(
					android.R.id.message);
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			toast.show();
		}
	}

}
