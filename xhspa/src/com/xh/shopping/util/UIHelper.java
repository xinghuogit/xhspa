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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xh.ios.view.SheetDialog;
import com.xh.ios.view.SheetDialog.OnSheetItemClickListener;
import com.xh.ios.view.SheetDialog.SheetItemColor;
import com.xh.shopping.R;
import com.xh.shopping.constant.Constant;
import com.xh.shopping.ui.UploadPhotoActivity1;

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

	@SuppressWarnings("unused")
	private static ProgressDialog dialog;
	private static Activity mContext;

	private static Dialog mDialog;

	// /**
	// * 设置系统状态栏
	// */
	// public void setSystemBar() {
	// // 修改沉浸式状态栏 要大于Android系统4.4 版本API19
	// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	// setTranslucentStatus(true);
	// }
	// Activity activity = SettingHelper.getInstance().getCurrentActivity();
	// // 获取系统状态栏管理者
	// SystemBarTintManager manager = new SystemBarTintManager(activity);
	// // 是否修改
	// manager.setStatusBarTintEnabled(true);
	// // 修改的颜色
	// manager.setStatusBarTintResource(R.color.app_overall);
	// }
	//
	// @TargetApi(Build.VERSION_CODES.KITKAT)
	// private void setTranslucentStatus(boolean is) {
	// // 获取当前Activity
	// Activity activity = SettingHelper.getInstance().getCurrentActivity();
	// Window window = activity.getWindow();
	// WindowManager.LayoutParams params = window.getAttributes();
	// final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
	// if (is) {
	// // 或之后赋予值
	// params.flags |= bits;
	// } else {
	// // 与之后赋予值
	// params.flags &= ~bits;
	// }
	// // 设置状态参数
	// window.setAttributes(params);
	// }

	public static void showProgressDialog(final Activity context,
			final int resId) {
		showProgressDialog(context, context.getResources().getString(resId));
	}

	public static void showProgressDialog(final Activity context,
			final int resId, boolean canable) {
		showProgressDialog(context, context.getResources().getString(resId),
				canable);
	}

	public static void showProgressDialog(final Activity context,
			final String msg) {
		showProgressDialog(context, msg, true);
	}

	@SuppressLint("InflateParams")
	public static void showProgressDialog(final Activity context,
			final String msg, boolean canable) {
		dismissProgressDialog();
		mContext = context;
		View view = LayoutInflater.from(context).inflate(
				R.layout.layout_show_progress, null);
		LinearLayout dialogLL = (LinearLayout) view;
		TextView tv_msg = (TextView) dialogLL
				.findViewById(R.id.show_progress_tv);
		tv_msg.setText(msg);
		mDialog = new Dialog(mContext, R.style.dialog_mass);
		Window win = mDialog.getWindow();// 获取所在window
		android.view.WindowManager.LayoutParams params = win.getAttributes();// 获取LayoutParams
		params.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		params.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		win.setAttributes(params);// 设置生效
		mDialog.setContentView(dialogLL);
		mDialog.setCancelable(canable);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.show();

	}

	public static void dismissProgressDialog() {
		if (mContext == null)
			return;
		mContext.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (mDialog != null && mDialog.isShowing()) {
					if (mContext != null && !mContext.isFinishing()) {
						mDialog.dismiss();
					}
					mDialog = null;
				}
			}
		});
	}

	/**
	 * 检查是否是正确的电话号码
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isPhoneNumbe(String s) {
		if (s == null) {
			return false;
		}
		String regexp = "((13[0-9])|(15[012356789])|(17[678])|(18[0-9])|(14[57]))[0-9]{8}";
		Pattern regex = Pattern.compile(regexp);
		Matcher matcher = regex.matcher(s);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断EditText是否为空
	 * 
	 * @param et
	 * @return
	 */
	public static boolean isEdittextHasData(EditText et) {
		if (null == et || et.getText().toString().trim().equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断SD卡是否存在
	 * 
	 * @return
	 */
	public static boolean isSDPresent() {
		Boolean isSDPresent = android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED);
		return isSDPresent;
	}

	@SuppressWarnings("unchecked")
	/**
	 * 获取id
	 * @param parent
	 * @param id
	 * @return
	 */
	public static <T extends View> T getfinaView(View parent, int id) {
		return (T) parent.findViewById(id);
	}

	public static void closeInput(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive() && activity.getCurrentFocus() != null) {
			if (activity.getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(activity.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	/**
	 * 
	 * @param activity
	 * @param isAddTime
	 *            是否添加时间水印 true为是 false为不是
	 * @param isstartPhotoZoom
	 *            是否要截图 true为是 false为不是
	 * @param istheme1
	 *            true是用相机 false为不用相机
	 * @param istheme2
	 *            true是用图库 false为不用图库
	 */
	public static void showImage(final Activity activity,
			final boolean isAddTime, final boolean isstartPhotoZoom,
			final boolean istheme1, final boolean istheme2) {
		UIHelper.closeInput(activity);
		SheetDialog dialog = new SheetDialog(activity).builder();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		if (istheme1) {
			dialog.addSheetItem("用相机选择图片", SheetItemColor.Blue,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Intent intent1 = new Intent(activity,
									UploadPhotoActivity1.class);

							intent1.putExtra("service", "service1");
							intent1.putExtra("theme", 1);
							intent1.putExtra("addTime", isAddTime);
							intent1.putExtra("startPhotoZoom", isstartPhotoZoom);
							activity.startActivityForResult(intent1,
									Constant.PHOTO);
						}
					});
		}
		if (istheme2) {
			dialog.addSheetItem("去相册选择图片", SheetItemColor.Blue,
					new OnSheetItemClickListener() {

						@Override
						public void onClick(int which) {

							Intent intent1 = new Intent(activity,
									UploadPhotoActivity1.class);
							intent1.putExtra("service", "service1");
							intent1.putExtra("theme", 2);
							intent1.putExtra("addTime", isAddTime);
							intent1.putExtra("startPhotoZoom", isstartPhotoZoom);
							activity.startActivityForResult(intent1,
									Constant.PHOTO);
						}
					});
		}
		dialog.show();
	}
}
