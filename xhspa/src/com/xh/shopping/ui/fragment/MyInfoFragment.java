/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：MyInfoFragment.java
 * 内容摘要：MyInfoFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-22 下午12:58:18
 * 修改记录：
 * 修改日期：2015-12-22 下午12:58:18
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xh.shopping.R;
import com.xh.shopping.icon.ImageController;
import com.xh.shopping.icon.LocalCacheData;
import com.xh.shopping.ui.activity.ChangePasswordActivity;
import com.xh.shopping.util.DialogUtil;
import com.xh.shopping.util.ToastUtil;
import com.xh.shopping.util.UIHelper;
import com.xh.shopping.view.CircleImageView;

/**
 * @filename 文件名称：MyInfoFragment.java
 * @contents 内容摘要：
 */
public class MyInfoFragment extends Fragment implements OnClickListener {
	private Activity activity;
	private View parent;

	private RelativeLayout info_logo_r;
	private CircleImageView info_logo_iv;

	private RelativeLayout info_nname_r;
	private TextView info_nname_tv;
	private RelativeLayout info_cpwd_r;
	private TextView info_phone_tv, info_name_tv, create_time_tv;
	private TextView info_exit;

	public static final int PHOTO_REQUEST_PHOTO = 110;
	public static final int PHOTO_REQUEST_CAREMA = 111;
	public static final int PHOTO_REQUEST_SHOT = 112;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_myinfo, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		parent = getView();

		findView();
		setListener();
	}

	// info_logo_r;
	// info_logo_iv;
	// info_nickname_r;
	// info_nickname_tv;
	// info_cpwd_r;
	// info_phone_tv
	// info_name_tv
	// create_time_tv
	// info_exit;

	private void findView() {

		info_logo_r = (RelativeLayout) parent.findViewById(R.id.info_logo_r);
		info_logo_iv = (CircleImageView) parent.findViewById(R.id.info_logo_iv);

		info_nname_r = (RelativeLayout) parent
				.findViewById(R.id.info_nickname_r);
		info_nname_tv = (TextView) parent.findViewById(R.id.info_nickname_tv);

		info_cpwd_r = (RelativeLayout) parent.findViewById(R.id.info_cpwd_r);

		info_phone_tv = (TextView) parent.findViewById(R.id.info_phone_tv);
		info_name_tv = (TextView) parent.findViewById(R.id.info_name_tv);
		create_time_tv = (TextView) parent.findViewById(R.id.create_time_tv);

		info_exit = (TextView) parent.findViewById(R.id.info_exit);
	}

	private void setListener() {
		info_logo_r.setOnClickListener(this);
		info_nname_r.setOnClickListener(this);
		info_cpwd_r.setOnClickListener(this);
		info_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.info_logo_r:
			showPhoto();
			break;
		case R.id.info_nickname_r:

			break;
		case R.id.info_cpwd_r:
			Intent intent = new Intent(activity, ChangePasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.info_exit:
			DialogUtil.exitDialog(activity);
			break;
		default:
			break;
		}
	}

	public void showPhoto() {
//		new ActionSheetDialog(activity)
//				.builder()
//				.setCancelable(true)
//				.setCanceledOnTouchOutside(true)
//				.addSheetItem(getString(R.string.change_icon_photo),
//						SheetItemColor.Blue, new OnSheetItemClickListener() {
//							@Override
//							public void onClick(int which) {
//								// 激活系统图库，选择一张图片
//								Intent intent = new Intent(Intent.ACTION_PICK);
//								intent.setType("image/*");
//								// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_PHOTOv
//								startActivityForResult(intent,
//										PHOTO_REQUEST_PHOTO);
//							}
//						})
//				.addSheetItem(getString(R.string.change_icon_camera),
//						SheetItemColor.Blue, new OnSheetItemClickListener() {
//							@Override
//							public void onClick(int which) {
//								// 激活相机
//								Intent intent = new Intent(
//										"android.media.action.IMAGE_CAPTURE");
//								// 判断存储卡是否可以用，可用进行存储
//								LocalCacheData.REPLACE_AVATAR_FILE = new File(
//										Environment
//												.getExternalStorageDirectory()
//												+ "/pintx2.0/", System
//												.currentTimeMillis() + ".jpg");
//								if (UIHelper.isSDPresent()) {
//									// if(!LocalCacheData.REPLACE_AVATAR_FILE.exists()){
//									// LocalCacheData.REPLACE_AVATAR_FILE.mkdirs();
//									// }
//									// 从文件中创建uri
//									Uri uri = Uri
//											.fromFile(LocalCacheData.REPLACE_AVATAR_FILE);
//									intent.putExtra(MediaStore.EXTRA_OUTPUT,
//											uri);
//								}
//								// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
//								startActivityForResult(intent,
//										PHOTO_REQUEST_CAREMA);
//							}
//						}).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PHOTO_REQUEST_CAREMA:
			// 从相机返回的数据
			if (UIHelper.isSDPresent()) {// 如果存储卡存在
				crop(Uri.fromFile(LocalCacheData.REPLACE_AVATAR_FILE));
			} else {
				ToastUtil.makeToast(activity, "未找到存储卡，无法存储照片！");
			}
			break;
		case PHOTO_REQUEST_PHOTO:
			if (data == null || data.getData() == null) {
				break;
			}
			Uri originalUri = data.getData();
			crop(originalUri);
			return;
		case PHOTO_REQUEST_SHOT:
			// 从剪切图片返回的数据
			if (data != null) {
				Bitmap bitmap = data.getParcelableExtra("data");
				// 在剪切图片界面取消选择
				if (bitmap == null)
					break;
				Bitmap newbitmap = ImageController.getImageController()
						.imageZoom(bitmap, (double) 100.0);
				LocalCacheData.REPLACE_AVATAR_PATH = ImageController
						.getImageController().saveBitmap(newbitmap, "iconUser");
				info_logo_iv.setImageBitmap(newbitmap);
				// RequestParams params = new RequestParams(
				// Task.API_SYS_QINIU_TOKEN);
				// params.addHeader("Authorization",
				// mMyApplication.getAccessToken(this));
				// HttpUtil httpUtil = new HttpUtil(this, httpCallback,
				// Task.TASK_SYS_QINIU_TOKEN, true, true);
				// httpUtil.HttpGet(params);
			}
			try {
				// 将临时文件删除
				LocalCacheData.REPLACE_AVATAR_FILE.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/* * 剪切图片 */
	private void crop(Uri uri) { // 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("scale", true);
		intent.putExtra("crop", "true");// 发送裁剪信号
		// 裁剪后输出图片的尺寸大小
		// if (false) {
		// intent.putExtra("aspectX", 2);
		// intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", Helper.dip2px(this, 100));
		// intent.putExtra("outputY", Helper.dip2px(this, 50));
		// } else {
		// // 裁剪框的比例，1：1
		// intent.putExtra("aspectX", 1);
		// intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", Helper.dip2px(this, 100));
		// intent.putExtra("outputY", Helper.dip2px(this, 100));
		// }
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 600);
		intent.putExtra("outputY", 600);
		intent.putExtra("outputFormat", "JPEG");
		// 图片格式
		intent.putExtra("noFaceDetection", true);
		// 取消人脸识别
		intent.putExtra("return-data", true);
		// 是否返回数据
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
		startActivityForResult(intent, PHOTO_REQUEST_SHOT);
	}

}
