///*************************************************************************************************
// * 版权所有 (C)2015
// * 
// * 文件名称：MyInfoFragment.java
// * 内容摘要：MyInfoFragment.java
// * 当前版本：TODO
// * 作        者：李加蒙
// * 完成日期：2015-12-22 下午12:58:18
// * 修改记录：
// * 修改日期：2015-12-22 下午12:58:18
// * 版   本 号：
// * 修   改 人：
// * 修改内容：
// ************************************************************************************************/
//package com.xh.shopping.ui.fragment;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.provider.SyncStateContract.Constants;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.xh.shopping.R;
//import com.xh.shopping.ui.activity.ChangePasswordActivity;
//import com.xh.shopping.util.DialogUtil;
//import com.xh.shopping.util.ToastUtil;
//import com.xh.shopping.util.UIHelper;
//import com.xh.shopping.view.CircleImageView;
//
///**
// * @filename 文件名称：MyInfoFragment.java
// * @contents 内容摘要：
// */
//public class MyInfoFragment2 extends Fragment implements OnClickListener {
//	private Activity activity;
//	private View parent;
//
//	private RelativeLayout info_logo_r;
//	private CircleImageView info_logo_iv;
//
//	private RelativeLayout info_nname_r;
//	private TextView info_nname_tv;
//	private RelativeLayout info_cpwd_r;
//	private TextView info_phone_tv, info_name_tv, create_time_tv;
//	private TextView info_exit;
//
//	public static final int PHOTO_REQUEST_PHOTO = 110;
//	public static final int PHOTO_REQUEST_CAREMA = 111;
//	public static final int PHOTO_REQUEST_SHOT = 112;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	};
//
//	@Override
//	public View onCreateView(LayoutInflater inflater,
//			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.fragment_myinfo, container, false);
//	}
//
//	@Override
//	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		activity = getActivity();
//		parent = getView();
//
//		findView();
//		setListener();
//	}
//
//	// info_logo_r;
//	// info_logo_iv;
//	// info_nickname_r;
//	// info_nickname_tv;
//	// info_cpwd_r;
//	// info_phone_tv
//	// info_name_tv
//	// create_time_tv
//	// info_exit;
//
//	private void findView() {
//
//		info_logo_r = (RelativeLayout) parent.findViewById(R.id.info_logo_r);
//		info_logo_iv = (CircleImageView) parent.findViewById(R.id.info_logo_iv);
//
//		info_nname_r = (RelativeLayout) parent
//				.findViewById(R.id.info_nickname_r);
//		info_nname_tv = (TextView) parent.findViewById(R.id.info_nickname_tv);
//
//		info_cpwd_r = (RelativeLayout) parent.findViewById(R.id.info_cpwd_r);
//
//		info_phone_tv = (TextView) parent.findViewById(R.id.info_phone_tv);
//		info_name_tv = (TextView) parent.findViewById(R.id.info_name_tv);
//		create_time_tv = (TextView) parent.findViewById(R.id.create_time_tv);
//
//		info_exit = (TextView) parent.findViewById(R.id.info_exit);
//	}
//
//	private void setListener() {
//		info_logo_r.setOnClickListener(this);
//		info_nname_r.setOnClickListener(this);
//		info_cpwd_r.setOnClickListener(this);
//		info_exit.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.info_logo_r:
//			showPhoto();
//			break;
//		case R.id.info_nickname_r:
//
//			break;
//		case R.id.info_cpwd_r:
//			Intent intent = new Intent(activity, ChangePasswordActivity.class);
//			startActivity(intent);
//			break;
//		case R.id.info_exit:
//			DialogUtil.exitDialog(activity);
//			break;
//		default:
//			break;
//		}
//	}
//
//	private final static int REQUEST_CODE_1 = 1111; // 拍照
//	private final static int REQUEST_CODE_2 = 2222;
//	private final static int REQUEST_CODE_3 = 3333;
//	private final static int CROP_X = 150;
//	private final static int CROP_Y = 150;// 56
//
//	private static final String path=null; 
//	
//	private void showPhoto() {
//		// 拍照
//		if (!UIHelper.isSDPresent()) {
//			ToastUtil.makeToast(activity, "没有内存卡");
//			return;
//		}
//
//		// 保存裁剪后的图片文件
//		File sdPath = new File(Environment.getExternalStorageDirectory(),
//				"/upload");
//		if (!sdPath.exists()) {
//			sdPath.mkdirs();
//		}
//
//		File sdcardTempFile = new File(sdPath, System.currentTimeMillis()
//				+ "upload.jpg");
//		mUri = Uri.fromFile(sdcardTempFile);
//		Intent intentImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		intentImage.addCategory(Intent.CATEGORY_DEFAULT);
//		intentImage.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
//		startActivityForResult(intentImage, REQUEST_CODE_1);
//
//		// 图库选择
//		if (!UIHelper.isSDPresent()) {
//			ToastUtil.makeToast(activity, "没有内存卡");
//			return;
//		}
//		Intent intentPick = new Intent(Intent.ACTION_PICK);
//		intentPick.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
//				"image/*");
//		startActivityForResult(intentPick, REQUEST_CODE_2);
//
//	}
//
//	// 裁剪图片
//	private void startPhotoZoom(Uri uri) {
//		Intent intentPic = new Intent("com.android.camera.action.CROP");
//		intentPic.setDataAndType(uri, "image/*");
//		intentPic.putExtra("crop", "true");
//		intentPic.putExtra("aspectX", 1);
//		intentPic.putExtra("aspectY", 1);
//		intentPic.putExtra("outputX", CROP_X);
//		intentPic.putExtra("outputY", CROP_Y);
//		intentPic.putExtra("return-data", true);
//		startActivityForResult(intentPic, REQUEST_CODE_3);
//	}
//
//	private void getStartPhotoZoom(int requestCode, Intent data) {
//		switch (requestCode) {
//		case REQUEST_CODE_1:
//			startPhotoZoom(mUri);
//			break;
//		case REQUEST_CODE_2:
//			startPhotoZoom(data.getData());
//			break;
//		case REQUEST_CODE_3:
//			if (data != null) {
//				setPicToView(data);
//			}
//			break;
//		}
//	}
//
//	// 获取图片路径
//	private void setPicToView(Intent picdata) {
//		Bundle extras = picdata.getExtras();
//		if (extras != null) {
//			Bitmap photo = extras.getParcelable("data");
//			path = write(photo);
//			Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(
//					path).toString());
//			Bitmap createCircleImage = getRoundBitmap(bitmap, 100);
//			iv_img.setImageBitmap(createCircleImage);
//			File file = new File(path);
////			getModule().updateAvatar(file);
//			System.out.println("选择的图片路径:"+ path);
//
//		}
//	}
//
//	private static String mImageName = "laodongshebao" + System.currentTimeMillis() + ".png"; // 文件名;
//	
//	// 存入图片
//	private String write(Bitmap bm) {
//		File file = new File(Constants.getSDPath());
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		file = new File(Constants.getSDPath(), mImageName);
//		FileOutputStream out;
//		try {
//			out = new FileOutputStream(file);
//			out.write(Bitmap2Bytes(bm));
//			out.flush();
//			out.close();
//			return file.getPath();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private byte[] Bitmap2Bytes(Bitmap bm) {
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);// png类型
//		return baos.toByteArray();
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		switch (resultCode) {
//		case Activity.RESULT_OK:
//			getStartPhotoZoom(requestCode, data);
//			break;
//		}
//	}
//
//	public static String getSDPath() {
//		File sdDir = null;
//		if (isExistSD()) {
//			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
//		} else {
//			sdDir = Environment.getRootDirectory();
//		}
//		return sdDir.toString() + "/laodongshebao/user_icon";
//	}
//
//	public static boolean isExistSD() {
//		return Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED);
//	}
//
//	private static Uri mUri;
//
//}
