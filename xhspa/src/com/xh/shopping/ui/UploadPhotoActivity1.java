/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：UploadPhotoActivity1.java
 * 内容摘要：UploadPhotoActivity1.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-13 上午11:49:31
 * 修改记录：
 * 修改日期：2016-4-13 上午11:49:31
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.xh.shopping.R;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.util.FileUtil;
import com.xh.shopping.util.ImageUtil;
import com.xh.shopping.util.SDUtil;
import com.xh.shopping.util.ToastUtil;
import com.xh.shopping.util.UriUtil;

/**
 * 
 * @author LI
 * @intent 必传theme 1为相机 2为图库
 * @intent 选传startPhotoZoom false为不截图true为截图 默认为截图
 * @intent 选传addTime false为不添加时间true为添加时间 默认为添加时间
 */
public class UploadPhotoActivity1 extends Activity {
	/**
	 * 截图
	 */
	private static final int PHOTORESOULT = 0x3452;
	/**
	 * 图片选择成功以后返回来的URIcom.ttuhui.ttyh.util.iosdate.PhotoAction.
	 * CaptureImagePathDelegate为
	 */
	protected Uri imageUri = null;
	public static List<String> imagePathList = new ArrayList<String>();
	protected String lastImageFilePath = null;
	private Uri uri;

	// private ShopEditInfoService shEditService;

	private Intent intent;
	private int theme = -1;
	private boolean startPhotoZoom = true;
	private boolean addTime = true;

	/**
	 * 相机
	 */
	public static final int REQUEST_IMAGE_CAPTURE = 2;
	/**
	 * 相册
	 */
	public static final int REQUEST_PHOTO_LIBRARY = 3;

	SharedPreferences.Editor editor;

	private int uploadID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		SettingHelper.getInstance().setCurrentActivity(this);
		intent = getIntent();
		theme = intent.getIntExtra("theme", -1);
		startPhotoZoom = intent.getBooleanExtra("startPhotoZoom", true);
		addTime = intent.getBooleanExtra("addTime", true);
		editor = getSharedPreferences("upload", 0).edit();
		SharedPreferences preferences = getSharedPreferences("upload", 0);
		int uploadID = preferences.getInt("uploadID", 0);
		System.out.println("uploadID1:" + uploadID);
		if (uploadID == 0) {
			if (theme == 2) {
				System.out.println("图库theme=" + theme);
				choosePhotoActionDialog();
			} else if (theme == 1) {
				System.out.println("相机theme=" + theme);
				takePhotoActionDialog();
			} else {
				System.out.println("系统错误/ntheme=" + theme);
			}
		} else {
			System.out.println("系统错误/ntheme=" + theme);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.i("TAG", "ThirdActivity onSaveInstanceState");
		outState.putInt("theme", -1);
	}

	@Override
	protected void onPause() {
		super.onPause();
		editor.putInt("uploadID", uploadID);
		System.out.println("onPause:" + uploadID);
		editor.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uploadID = 0;
		editor.putInt("uploadID", uploadID);
		editor.commit();
		if (resultCode == 0) {
			imagePathList.clear();
			UploadPhotoActivity1.this.finish();
		}
		System.out.println("requestCode:" + requestCode + "\nresultCode:"
				+ resultCode + "\ndata:" + (data == null));
		SDUtil sdUtil = new SDUtil(this);
		sdUtil.outDeveil();
		/**
		 * If the request code is from requesting photo library or image
		 * capture, we get the file path of image and then start ScaleImage
		 * Activity to scale the image. If the request code is from scale image,
		 * then it means we could get scaled image and do the rest of work
		 * 如果请求从请求代码库或照片图像捕获,我们得到图像的文件路径,然后开始ScaleImage
		 * *活动规模的形象。如果请求代码从规模图像,*就意味着我们会按比例缩小的形象和做其他的工作
		 */
		if (requestCode == REQUEST_PHOTO_LIBRARY && resultCode == RESULT_OK) {
			if (data == null) {
				postHandlePictureEx();
				return;
			}

			imageUri = data.getData();
			if (imageUri.getScheme().equals("content")) {
				lastImageFilePath = UriUtil.getAbsoluteImagePath(imageUri,
						UploadPhotoActivity1.this);
				System.out.println("LIBRARYcontent:" + lastImageFilePath);
			} else {
				lastImageFilePath = imageUri.getPath();
				System.out.println("LIBRARY!content:" + lastImageFilePath);
			}
			lastImageFilePath = ImageUtil
					.generateCompressedImage(lastImageFilePath);
			uri = Uri.fromFile(new File(lastImageFilePath));

			if (startPhotoZoom) {
				startPhotoZoom(uri);
			} else {
				lastImageFilePath = ImageUtil
						.generateCompressedImagea(lastImageFilePath);
				Bitmap photo = ImageUtil.getBitmap(lastImageFilePath);
				PS(photo, lastImageFilePath);
			}
			System.out.println("LIBRARY:" + lastImageFilePath);
		} else if (requestCode == REQUEST_IMAGE_CAPTURE
				&& resultCode == RESULT_OK) {
			System.out.println("1lastImageFilePath:" + lastImageFilePath);

			File file = new File(lastImageFilePath);
			if (!file.exists()) {
				postHandlePictureEx();
				return;
			}
			uri = Uri.fromFile(new File(lastImageFilePath));
			if (startPhotoZoom) {
				startPhotoZoom(uri);
			} else {
				lastImageFilePath = ImageUtil
						.generateCompressedImagea(lastImageFilePath);
				Bitmap photo = ImageUtil.getBitmap(lastImageFilePath);
				PS(photo, lastImageFilePath);
			}
			System.out.println("CAPTURE:" + lastImageFilePath);
		} else if (requestCode == PHOTORESOULT && resultCode != 0) {
			if (uri != null) {
				Bitmap photo = decodeUriAsBitmap(uri);
				PS(photo, lastImageFilePath);
			}
		}
	}

	public void PS(final Bitmap photo, final String lastImageFilePath) {
		System.out.println("PS&lastImageFilePath:" + lastImageFilePath);
		if (photo == null) {
			System.out.println("nullphoto=" + photo);
		} else {
			System.out.println("nullphoto=");
		}
		new Thread() {
			@Override
			public void run() {
				String string = FileUtil.getBasePath().getAbsolutePath() + "/"
						+ System.currentTimeMillis() + ".jpg";
				try {
					File imageFile = new File(string);
					if (!imageFile.exists()) {
						imageFile.createNewFile();
						System.out.println("创建水印截图文件..." + imageFile.getPath());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (addTime) {
					ImageUtil.AddTime(UploadPhotoActivity1.this, photo, string,
							lastImageFilePath);
				} else {
					ImageUtil.compressImage(photo, string);
				}
				if (photo != null && !photo.isRecycled()) {
					// 回收并且置为null
					photo.recycle();
				}
				System.out.println("string:" + string);
				imagePathList.add(string);
				for (int i = 0; i < imagePathList.size(); i++) {
					System.out.println(i + "imagePathList:"
							+ imagePathList.get(i));
				}
				System.out.println("相机为截图但压缩或者加水印路径:" + string);
				postHandlePicture();
			}
		}.start();
	}

	protected void postHandlePicture() {
		System.out.println("postHandlePicture");
		setResult(RESULT_OK);
		uploadID = 0;
		editor.putInt("uploadID", uploadID);
		editor.commit();
		this.finish();
		return;
	}

	protected void postHandlePictureEx() {
		uploadID = 0;
		System.out.println("postHandlePictureEx");
		ToastUtil.makeToast(this, "图片异常");
		editor.putInt("uploadID", uploadID);
		editor.commit();
		this.finish();
		return;
	}

	@Override
	public void onBackPressed() {
		uploadID = 0;
		editor.putInt("uploadID", uploadID);
		editor.commit();
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 400);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, PHOTORESOULT);
	}

	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/**
	 * 选择图库图片
	 */
	public void choosePhotoActionDialog() {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, REQUEST_PHOTO_LIBRARY);
	}

	/**
	 * 照相机照相
	 */
	public void takePhotoActionDialog() {
		Boolean isSDPresent = android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED);
		System.out.println("相机");
		theme = -1;
		if (isSDPresent) {
			System.out.println("sd");
			try {
				final String imagePath = FileUtil.getBasePath()
						.getAbsolutePath()
						+ "/"
						+ System.currentTimeMillis()
						+ ".jpg";
				System.out.println("sd:" + imagePath);
				lastImageFilePath = imagePath;
				File imageFile = new File(imagePath);
				if (!imageFile.exists()) {
					imageFile.createNewFile();
					System.out.println("sd创建文件:" + imageFile.getPath());
				}
				System.out.println("imageFile:" + imageFile.getPath());
				Uri imageUri = Uri.fromFile(imageFile);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				System.out.println("imageUri:" + imageUri.getPath());
				startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("拍照时异常sd");
			}
		} else {
			System.out.println("data");
			runOnUiThread(new Runnable() {
				public void run() {
					ToastUtil.makeToast(UploadPhotoActivity1.this,
							R.string.no_use_sd_card);
				}
			});
			try {
				String imagePath = FileUtil.getPhoneBasePath()
						.getAbsolutePath()
						+ "/"
						+ System.currentTimeMillis()
						+ ".jpg";
				System.out.println("data:" + imagePath);
				lastImageFilePath = imagePath;
				File imageFile = new File(imagePath);
				if (!imageFile.exists()) {
					imageFile.createNewFile();
					System.out.println("data创建文件:" + imageFile.getPath());
				}
				Uri imageUri = Uri.fromFile(imageFile);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("拍照时异常sd");
			}
		}
	}

}
