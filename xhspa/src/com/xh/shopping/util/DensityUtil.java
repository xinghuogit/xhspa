/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：DensityUtil.java
 * 内容摘要：DensityUtil.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-13 上午10:48:57
 * 修改记录：
 * 修改日期：2016-4-13 上午10:48:57
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：DensityUtil.java
 * @contents 内容摘要：
 */
public class DensityUtil {
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (pxValue / scale + 0.5f);
	}

	public static int[] getScreenWidthAndHeight(Activity activity) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		int[] result = new int[2];
		result[0] = displayMetrics.widthPixels;
		result[1] = displayMetrics.heightPixels;
		return result;
	}

	public static int[] getScreenWidthAndHeight(Context context) {

		DisplayMetrics displayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		int[] result = new int[2];
		result[0] = displayMetrics.widthPixels;
		result[1] = displayMetrics.heightPixels;
		return result;
	}
}
