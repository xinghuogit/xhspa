//package com.pintx.view.activity;
//
//import java.io.File;
//import java.net.URLEncoder;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.xutils.x;
//import org.xutils.http.RequestParams;
//import org.xutils.image.ImageOptions;
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//
//import com.google.gson.Gson;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.pintx.MyApplication;
//import com.pintx.R;
//import com.pintx.model.UserInfoDetailData;
//import com.pintx.model.UserInfoModel;
//import com.pintx.util.http.HttpUtil;
//import com.pintx.utils.Global;
//import com.pintx.utils.Helper;
//import com.pintx.utils.ImageController;
//import com.pintx.utils.JsonUtil;
//import com.pintx.utils.LocalCacheData;
//import com.pintx.view.base.Task;
//import com.pintx.view.base.TitleBaseActivity;
//import com.pintx.view.custom.ActionSheetDialog;
//import com.pintx.view.custom.ActionSheetDialog.OnSheetItemClickListener;
//import com.pintx.view.custom.ActionSheetDialog.SheetItemColor;
//import com.pintx.view.custom.CircleImageView;
//import com.qiniu.android.http.ResponseInfo;
//import com.qiniu.android.storage.UpCompletionHandler;
//import com.qiniu.android.storage.UploadManager;
//import com.pintx.view.custom.RoundImageView;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//@ContentView(R.layout.act_my_data)
//public class ActMyData extends TitleBaseActivity {
//
//	@ViewInject(R.id.et_mydata_nickname)
//	TextView et_mydata_nickname;
//
//	@ViewInject(R.id.et_mydata_email)
//	TextView et_mydata_email;
//
//	@ViewInject(R.id.et_mydata_phone)
//	TextView et_mydata_phone;
//
//	@ViewInject(R.id.tv_mydata_sex)
//	TextView tv_mydata_sex;
//
//	@ViewInject(R.id.iv_mydata_avatar)
//	CircleImageView iv_mydata_avatar;
//
//	private UserInfoDetailData userInfoDetailData;
//
//	private int userId = 0;
//
//	@Event(value = R.id.tv_mydata_sex)
//	private void onUpdateSex(View view) {
//		new ActionSheetDialog(this).builder().setCancelable(true).setCanceledOnTouchOutside(true)
//				.addSheetItem(getString(R.string.sex_0), SheetItemColor.Blank, new OnSheetItemClickListener() {
//					@Override
//					public void onClick(int which) {
//						tv_mydata_sex.setText(R.string.sex_0);
//					}
//				}).addSheetItem(getString(R.string.sex_1), SheetItemColor.Blank, new OnSheetItemClickListener() {
//					@Override
//					public void onClick(int which) {
//						tv_mydata_sex.setText(R.string.sex_1);
//					}
//				}).show();
//	}
//
//	@Event(value = R.id.iv_mydata_avatar)
//	private void onReplaceAvatarClick(View view) {
//		new ActionSheetDialog(this).builder().setCancelable(true).setCanceledOnTouchOutside(true)
//				.addSheetItem(getString(R.string.mydata_dialog_album), SheetItemColor.Blank,
//						new OnSheetItemClickListener() {
//							@Override
//							public void onClick(int which) {
//								// 激活系统图库，选择一张图片
//								Intent intent = new Intent(Intent.ACTION_PICK);
//								intent.setType("image/*");
//								// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
//								startActivityForResult(intent, Global.PICTURE_CHOOSE);
//							}
//						})
//				.addSheetItem(getString(R.string.mydata_dialog_camera), SheetItemColor.Blank,
//						new OnSheetItemClickListener() {
//							@Override
//							public void onClick(int which) {
//								// 激活相机
//								Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//								// 判断存储卡是否可以用，可用进行存储
//								LocalCacheData.REPLACE_AVATAR_FILE = new File(
//										Environment.getExternalStorageDirectory() + "/pintx2.0/",
//										Global.REPLACE_AVATAR_NAME);
//								if (Helper.hasSdcard()) {
//									// if(!LocalCacheData.REPLACE_AVATAR_FILE.exists()){
//									// LocalCacheData.REPLACE_AVATAR_FILE.mkdirs();
//									// }
//									// 从文件中创建uri
//									Uri uri = Uri.fromFile(LocalCacheData.REPLACE_AVATAR_FILE);
//									intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//								}
//								// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
//								startActivityForResult(intent, Global.PICTURE_TAKE);
//							}
//						})
//				.show();
//	}
//
//	@Override
//	public void onClickLeftIBtn() {
//		String nickname = et_mydata_nickname.getText().toString().trim();
//		String email = et_mydata_email.getText().toString().trim();
//		if (nickname == null) {
//			nickname = "";
//		}
//		if (TextUtils.isEmpty(email)) {
//			email = "";
//		} else if (!Helper.isEmail(email)) {
//			showToast("请输入正确的邮箱格式");
//			return;
//		}
//		int sex = 0;
//		if (tv_mydata_sex.getText().toString().equals(getString(R.string.sex_0))) {
//			sex = 0;
//		} else {
//			sex = 1;
//		}
//		/**
//		 * 隐藏软键盘
//		 */
//		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
//			if (getCurrentFocus() != null)
//				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//		}
//		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//
//		RequestParams params = new RequestParams(Task.API_USER_EDIT);
//		params.addHeader("Authorization", mMyApplication.getAccessToken(this));
//		params.addBodyParameter("userId", userInfoDetailData.getUserId() + "");
//		params.addBodyParameter("nickname", nickname);
//		params.addBodyParameter("email", email);
//		params.addBodyParameter("sex", sex + "");
//		params.addBodyParameter("trueName", "");
//		HttpUtil httpUtil = new HttpUtil(this, httpCallback, Task.TASK_USER_EDIT, true, true);
//		httpUtil.HttpPost(params);
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (requestCode) {
//		case Global.PICTURE_TAKE:
//			// 从相机返回的数据
//			if (Helper.hasSdcard()) {// 如果存储卡存在
//				crop(Uri.fromFile(LocalCacheData.REPLACE_AVATAR_FILE));
//			} else {
//				showToast("未找到存储卡，无法存储照片！");
//			}
//			break;
//		case Global.PICTURE_CHOOSE:
//			if (data == null || data.getData() == null) {
//				break;
//			}
//			Uri originalUri = data.getData();
//			crop(originalUri);
//			return;
//		case Global.PICTURE_CUT:
//			// 从剪切图片返回的数据
//			if (data != null) {
//				Bitmap bitmap = data.getParcelableExtra("data");
//				// 在剪切图片界面取消选择
//				if (bitmap == null)
//					break;
//				Bitmap newbitmap = ImageController.getImageController().imageZoom(bitmap, (double) 100.0);
//				LocalCacheData.REPLACE_AVATAR_PATH = ImageController.getImageController().saveBitmap(newbitmap,
//						"iconUser");
//				iv_mydata_avatar.setImageBitmap(newbitmap);
//				RequestParams params = new RequestParams(Task.API_SYS_QINIU_TOKEN);
//				params.addHeader("Authorization", mMyApplication.getAccessToken(this));
//				HttpUtil httpUtil = new HttpUtil(this, httpCallback, Task.TASK_SYS_QINIU_TOKEN, true, true);
//				httpUtil.HttpGet(params);
//			}
//			try {
//				// 将临时文件删除
//				LocalCacheData.REPLACE_AVATAR_FILE.delete();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return;
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}
//    
// 	public static boolean isLogin;
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//    
//	 @Override
//	protected void onResume() {
//		 //如果是登录界面返回 则刷新界面 
//		 if(isLogin){
//			 RequestParams params = new RequestParams(Task.API_USER_GET);
//				params.addHeader("Authorization", mMyApplication.getAccessToken(this));
//				HttpUtil httpUtil = new HttpUtil(this, httpCallback, Task.TASK_USER_GET, true, true);
//				httpUtil.HttpGet(params);	 
//				isLogin=false;}
//		super.onResume();
//	}
//	 @Override
//	protected void onDestroy() {
//		System.gc();
//		super.onDestroy();
//	}
//	 
//	 
//	@Override
//	protected void initData() {
//		setTitleCenterTxt(R.string.mydata_title);
//		UserInfoModel userInfo = mMyApplication.getUserInfo(this);
//		RequestParams params = new RequestParams(Task.API_USER_GET+userInfo.getUserId());
//		params.addHeader("Authorization", mMyApplication.getAccessToken(this));
//		HttpUtil httpUtil = new HttpUtil(this, httpCallback, Task.TASK_USER_GET, true, true);
//		httpUtil.HttpGet(params);
//		super.initData();
//	}
//	@Override
//	public void onHandleHttpCallback(int taskId, Object... params) {
//		switch (taskId) {
//		case Task.TASK_USER_UPDATE_AVATAR:
//			try {
//				JSONObject jsonObject = new JSONObject((String) params[3]);
//				UserInfoModel userInfo = mMyApplication.getUserInfo(this);
//				userInfo.setAvatar_66x66(jsonObject.getString("avatar_66x66"));
//				userInfo.setAvatar_100x100(jsonObject.getString("avatar_100x100"));
//				userInfo.setAvatar_150x150(jsonObject.getString("avatar_150x150"));
//				SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
//				Editor editor = preferences.edit();
//				editor.clear();
//				editor.commit();
//				editor.putString("userJsonStr", JsonUtil.getJsonStringByEntity(userInfo));
//				// editor.putInt("hasstore", hasstore);
//				editor.commit();
//			} catch (JSONException e2) {
//				e2.printStackTrace();
//			}
//
//			break;
//		case Task.TASK_SYS_QINIU_TOKEN:
//			String tokenStr = (String) params[3];
//			String key = "user/avatar/" + userId + "/" + System.currentTimeMillis() + ".png";
//			UploadManager uploadManager = new UploadManager();
//			uploadManager.put(LocalCacheData.REPLACE_AVATAR_PATH, key, tokenStr, new UpCompletionHandler() {
//				@Override
//				public void complete(String key, ResponseInfo info, JSONObject response) {
//					RequestParams params = new RequestParams(Task.API_USER_UPDATE_AVATAR + key);
//					params.addHeader("Authorization", mMyApplication.getAccessToken(ActMyData.this));
//					HttpUtil httpUtil = new HttpUtil(ActMyData.this, httpCallback, Task.TASK_USER_UPDATE_AVATAR, true,
//							true);
//					httpUtil.HttpPost(params);
//				}
//			}, null);
//			break;
//		case Task.TASK_USER_EDIT:
//			try {
//				UserInfoModel userInfo = mMyApplication.getUserInfo(this);
//				JSONObject jsonObject = new JSONObject(params[3].toString());
//				userInfo.setNickname(jsonObject.getString("nickname"));
//				userInfo.setSex(jsonObject.getInt("sex"));
//				userInfo.setEmail(jsonObject.getString("email"));
//				SharedPreferences preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
//				Editor editor = preferences.edit();
//				editor.clear();
//				editor.commit();
//				editor.putString("userJsonStr", new Gson().toJson(userInfo));
//				editor.commit();
//				this.finish();
//			} catch (JSONException e1) {
//				e1.printStackTrace();
//			}
//
//			break;
//		case Task.TASK_USER_GET:
//			try {
//				userInfoDetailData = JsonUtil.getEntityByJsonString(params[3].toString(), UserInfoDetailData.class);
//				// iv_mydata_avatar.setImageViewBitmap(bitmap);
//				if (userInfoDetailData.getSex() == 0)
//					tv_mydata_sex.setText(R.string.sex_0);
//				else
//					tv_mydata_sex.setText(R.string.sex_1);
//				String nickname = userInfoDetailData.getNickname();
//				String phone = userInfoDetailData.getPhone();
//				String email = userInfoDetailData.getEmail();
//				String trueName = userInfoDetailData.getTrueName();
//				String avatar = userInfoDetailData.getAvatar_150x150();
//				userId = userInfoDetailData.getUserId();
//				if (!TextUtils.isEmpty(nickname)) {
//					et_mydata_nickname.setText(nickname);
//				} else {
//					et_mydata_nickname.setText(phone);
//				}
//
//				StringBuffer buffer = new StringBuffer();
//				for (int i = 0; i < phone.length(); i++) {
//
//					if (i < 7 && i > 2) {
//						buffer.append('*');
//					} else {
//						buffer.append(phone.charAt(i));
//					}
//				}
//				et_mydata_phone.setText(buffer.toString());
//
//				if (!TextUtils.isEmpty(email)) {
//					et_mydata_email.setText(email);
//				}
//				if (!TextUtils.isEmpty(avatar)) {
////					ImageOptions imageOptions = new ImageOptions.Builder().setCrop(true)
////							.setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.drawable.beij)
////							.setFailureDrawableId(R.drawable.beij).build();
////					x.image().bind(iv_mydata_avatar, avatar, imageOptions);
//					ImageLoader.getInstance().displayImage(avatar,
//							iv_mydata_avatar, MyApplication.options);
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			break;
//		}
//		super.onHandleHttpCallback(taskId, params);
//	}
//
//	@Override
//	protected void initView() {
//		super.initView();
//	}
//
//	/* * 剪切图片 */
//	private void crop(Uri uri) { // 裁剪图片意图
//		Intent intent = new Intent("com.android.camera.action.CROP");
//		intent.setDataAndType(uri, "image/*");
//		intent.putExtra("scale", true);
//		intent.putExtra("crop", "true");// 发送裁剪信号
//		// 裁剪后输出图片的尺寸大小
//		// if (false) {
//		// intent.putExtra("aspectX", 2);
//		// intent.putExtra("aspectY", 1);
//		// intent.putExtra("outputX", Helper.dip2px(this, 100));
//		// intent.putExtra("outputY", Helper.dip2px(this, 50));
//		// } else {
//		// // 裁剪框的比例，1：1
//		// intent.putExtra("aspectX", 1);
//		// intent.putExtra("aspectY", 1);
//		// intent.putExtra("outputX", Helper.dip2px(this, 100));
//		// intent.putExtra("outputY", Helper.dip2px(this, 100));
//		// }
//		intent.putExtra("aspectX", 1);
//		intent.putExtra("aspectY", 1);
//		intent.putExtra("outputX", Helper.dip2px(this, 80));
//		intent.putExtra("outputY", Helper.dip2px(this, 80));
//		intent.putExtra("outputFormat", "JPEG");
//		// 图片格式
//		intent.putExtra("noFaceDetection", true);
//		// 取消人脸识别
//		intent.putExtra("return-data", true);
//		// 是否返回数据
//		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
//		startActivityForResult(intent, Global.PICTURE_CUT);
//	}
//}
