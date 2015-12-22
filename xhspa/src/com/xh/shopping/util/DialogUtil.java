/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：DialogUtil.java
 * 内容摘要：DialogUtil.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-22 下午1:33:09
 * 修改记录：
 * 修改日期：2015-12-22 下午1:33:09
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.xh.shopping.R;
import com.xh.shopping.setting.SettingHelper;

/**
 * @filename 文件名称：DialogUtil.java
 * @contents 内容摘要：显示提示框工具类
 */
public class DialogUtil {

	/**
	 * 退出账号工具类
	 * 
	 * @param context
	 * @param string
	 */
	public static void exitDialog(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.exit_title);
		builder.setMessage(R.string.exit_msg);
		builder.setNegativeButton(R.string.cancel, null);
		builder.setPositiveButton(R.string.confirm, new OnClickListener() {
			@SuppressWarnings("static-access")
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SettingHelper.getInstance().setUserInfo(null);
				((Activity) context).setResult(((Activity) context).RESULT_OK);
				((Activity) context).finish();
			}
		});
		builder.show();
	}
}
