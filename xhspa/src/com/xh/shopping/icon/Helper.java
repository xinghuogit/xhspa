package com.xh.shopping.icon;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @author chuck
 * @category 全局工具类，存放各种算法，或者各种小方法来.
 */
public class Helper {
	
	//byte字节转换成16进制的字符串MD5Utils.hexString  
    public static  String StringToMD5(String info){  
    	 MessageDigest md5=null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return info;
		}  
         byte[] srcBytes = info.getBytes();  
         //使用srcBytes更新摘要  
         md5.update(srcBytes);  
         //完成哈希计算，得到result  
         byte[] bytes = md5.digest();  
        StringBuffer hexValue = new StringBuffer();  
  
        for (int i = 0; i < bytes.length; i++) {  
            int val = ((int) bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString().toUpperCase();  
    } 
       
   
	
	
	
	
	
	
	
	
	/**************************** 判断是否是正确的电话号码 *************************/
	/**
	 * @param mobiles
	 *            电话号码字符串
	 * @return ture为电话号码 反之不是
	 */
	public static boolean isMobileNO(String mobiles){
//		^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$
		Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[5678]|18[0-9]|14[57])[0-9]{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	/**
	  * 验证邮箱
	  * @param email
	  * @return
	  */
	 public static boolean isEmail(String email){
	  boolean flag = false;
	  try{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	   }catch(Exception e){
	    flag = false;
	   }
	  return flag;
	 }
	/**
	 * 按钮点击效果
	 * @param view 点击的控件对象
	 * @param type 点击的类型   
	 */
	public static void clickEffect(final View view ,Integer type){
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					view.getBackground().setAlpha(100);
					break;
				case MotionEvent.ACTION_UP:
					view.getBackground().setAlpha(255);
					break;
				case MotionEvent.ACTION_CANCEL:
					view.getBackground().setAlpha(255);
					break;
				}
				return true;
			}
		});
	}
	/**
	 * 获取strings.xml 中id对应的字符串
	 * 
	 * @param context
	 *            传入Context对象
	 * @param id
	 *            strings.xml对应的id
	 * @return String 对应的String
	 */
	public static String getResourceStr(Context context, int id) {
		return context.getResources().getString(id);
	}
	
	/************************ DP PX 转换 **************************************/
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 *            Context对象
	 * @param dpValue
	 *            dp值
	 * @return int px值
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 *            Context对象
	 * @param pxValue
	 *            px值
	 * @return int dp值
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	  * 将px值转换为sp值，保证文字大小不变
	  * 
	  * @param pxValue
	  * @param fontScale（DisplayMetrics类中属性scaledDensity）
	  * @return
	  */
	 public static int px2sp(float pxValue, float fontScale) {
	  return (int) (pxValue / fontScale + 0.5f);
	 }

	 /**
	  * 将sp值转换为px值，保证文字大小不变
	  * 
	  * @param spValue
	  * @param fontScale（DisplayMetrics类中属性scaledDensity）
	  * @return
	  */
	 public static int sp2px(float spValue, float fontScale) {
	  return (int) (spValue * fontScale + 0.5f);
	 }
	/*********************** MD5 ***********************************/
	/***
	 * MD5加码 生成32位md5码
	 * 
	 * @param inStr
	 * @return String
	 */
//	public static String string2MD5(String inStr) {
//		MessageDigest md5 = null;
//		try {
//			md5 = MessageDigest.getInstance("MD5");
//		} catch (Exception e) {
//			System.out.println(e.toString());
//			e.printStackTrace();
//			return "";
//		}
//		char[] charArray = inStr.toCharArray();
//		byte[] byteArray = new byte[charArray.length];
//
//		for (int i = 0; i < charArray.length; i++)
//			byteArray[i] = (byte) charArray[i];
//		byte[] md5Bytes = md5.digest(byteArray);
//		StringBuffer hexValue = new StringBuffer();
//		for (int i = 0; i < md5Bytes.length; i++) {
//			int val = ((int) md5Bytes[i]) & 0xff;
//			if (val < 16)
//				hexValue.append("0");
//			hexValue.append(Integer.toHexString(val));
//		}
//		return hexValue.toString();
////		return inStr;
//	}

	/**
	 * 加密解密算法 执行一次加密，两次解密
	 * 
	 * @param inStr
	 * @return String
	 */
	public static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;
	}


	/*********************** showToast ***********************************/
	/**
	 * 显示Tosat
	 * 
	 * @param activity
	 * @param showText
	 */
	public static void showToast(Activity activity, String showText) {
		Toast.makeText(activity, showText, 0).show();
	}

	/*********************** set View param ***********************************/
	/**
	 * 重新设置View的宽高属性
	 * 
	 * @param view
	 *            需要设置的View对象
	 * @param width
	 *            传入null 即为不设置此项
	 * @param height
	 *            传入null 即为不设置此项
	 */
	public static void setViewParams(View view, Integer width, Integer height) {
		android.view.ViewGroup.LayoutParams params = view.getLayoutParams();
		
			if (height != null)
				params.height = height;
			if (width != null)
				params.width = width;
		view.setLayoutParams(params);
	}


	/**
	 * Get Screen Width and Height
	 * @return int[]
	 */
	public static int[] getScreen(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return new int[] { dm.widthPixels, dm.heightPixels};
	}
	/**
	 * Get Screen Width and Height
	 * @return int[]
	 */
	public static float getScaledDensity(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.scaledDensity;
	}
	/***------------ Location------------------------**/
//	public static LatLng convertGPStoBaiDu(LatLng sourceLatLng){
//		// 将GPS设备采集的原始GPS坐标转换成百度坐标  
//		CoordinateConverter converter  = new CoordinateConverter();  
//		converter.from(CoordType.GPS);  
//		// sourceLatLng待转换坐标  
//		converter.coord(sourceLatLng);  
//		LatLng desLatLng = converter.convert();
//		return desLatLng;
//	}
	
	
//	static double latitude = 0.0;
//	static double longitude = 0.0;
	/***------------ Location------------------------**/
	/**
	 * use at Activity onActivityResult().
	 * 
	 * @param activity
	 * @param iv
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
//	public static String activityResult(Activity activity, ImageView iv,
//			int requestCode, int resultCode, Intent data) {
//		String str = null;
//
//		ContentResolver resolver = activity.getContentResolver();
//		switch (requestCode) {
//
//		case Global.TAKE_PICTURE:
//			Bitmap bitmap2 = BitmapFactory.decodeFile(Environment
//					.getExternalStorageDirectory() + "/image.jpg");
//			if (bitmap2 == null) {
//				break;
//			}
//			Bitmap newBitmap = ImageController.getImageController().createBitmapBySize(bitmap2,
//					(int) ((float) bitmap2.getWidth() / (float) 2),
//					(int) ((float) bitmap2.getHeight() / (float) 2));
//
//			bitmap2.recycle();
//			iv.setImageBitmap(newBitmap);
//			str = Environment.getExternalStorageDirectory() + "/image.jpg";
//			break;
//		case Global.CHOOSE_PICTURE:
//			if(data==null||data.getData()==null){
//				break;
//			} 
//			Uri originalUri = data.getData();
//		
//			try {
//				// 使用ContentProvider通过URI拷取原始图片
//				Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,
//						originalUri);
//				if (photo != null) {
//					Bitmap smallBitmap = ImageController.getImageController().createBitmapBySize(
//							photo, (int) ((float) photo.getWidth() / 2f),
//							(int) ((float) photo.getHeight() / 2f));
//					photo.recycle();
//					iv.setImageBitmap(smallBitmap);
////					String[] proj = { MediaStore.Images.Media.DATA };
////					
////					@SuppressWarnings("deprecation")
////					Cursor cursor = activity.managedQuery(originalUri, proj,
////							null, null, null);
////					int column_index = cursor
////							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
////					 cursor.moveToFirst();
////					 str = cursor.getString(column_index);
////					  String[] filePathColumn = { MediaStore.Images.Media.DATA };
////				        Cursor cursor = activity.getContentResolver().query(originalUri,
////				                filePathColumn, null, null, null);
////				        cursor.moveToFirst();
////				        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
////				        str = cursor.getString(columnIndex);
//				        str =uri2filePath(originalUri, activity);
//				}
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			break;
//		}
//		return str;
//	}
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<About
	// Img-------------------------------------------------=----------
	/**
	 * 格式化Double 保留两位小数
	 * @param dou
	 * @return string
	 */
	public static String formatDouble( double dou){
		DecimalFormat decimalFormat = new DecimalFormat("00.00");
	  return decimalFormat.format(dou);
	}
    /**
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity){
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight){
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = activity.getResources().getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
    /**
     * 设置View各方向的间距，
     * LayoutType:
     * 0--LinearLayout,
     * 1----RelativeLayout.
     * @param activity
     * @param view
     * @param layoutType
     * @param top
     * @param bottom
     * @param left
     * @param right
     */
    public static void setViewMargins(Activity activity,View view ,int layoutType,int top,int bottom,int left,int right){
    	if(layoutType==0){
    	LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) view.getLayoutParams();		
    	lp.setMargins(left, top, right, bottom); 
		view.setLayoutParams(lp); 
    	}else if(layoutType==1){
    		RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) view.getLayoutParams();		
    		lp.setMargins(left, top, right, bottom); 
    		view.setLayoutParams(lp); 
    	}
    
    }
    /**
     * 将URI转换成filePath
     * @param uri
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("NewApi")
	private static String uri2filePath(Uri uri, Activity activity) {
    	String path = "";

    	if (DocumentsContract.isDocumentUri(activity, uri)) {
    	String wholeID = DocumentsContract.getDocumentId(uri);

    	String id = wholeID.split(":")[1];

    	String[] column = { MediaStore.Images.Media.DATA };

    	String sel = MediaStore.Images.Media._ID + "=?";

    	Cursor cursor = activity.getContentResolver().query(

    	MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,

    	new String[] { id }, null);

    	int columnIndex = cursor.getColumnIndex(column[0]);

    	if (cursor.moveToFirst()) {
    	path = cursor.getString(columnIndex);
    	}
    	cursor.close();
    	} else {
    	String[] projection = { MediaStore.Images.Media.DATA };
    	Cursor cursor = activity.getContentResolver().query(uri,
    	projection, null, null, null);
    	int column_index = cursor
    	.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    	cursor.moveToFirst();
    	path = cursor.getString(column_index);
    	}
    	return path;
    	} 
    
     /**
      * 
      * 开启一个倒计时 定时器
     * @param handler
     */
    public static void timeAlter(final Handler handler){
    	 new Thread(){
				int i=90;//倒计时
				@Override
				public void run() {
					while(i>0){
						try {
							Message msg = handler.obtainMessage();
							msg.what=Global.FLAG_TIMER;
							msg.arg1=i;
							msg.sendToTarget();
							i--;
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Message msg = handler.obtainMessage();
					msg.what=Global.FLAG_TIMER;
					msg.arg1=0;
					msg.sendToTarget();
					super.run();
				}
			}.start();
    	 
     }
    
// 	public static void getPicturePicker(Activity activity) {
//		PopWindow popWindow = new PopWindow(activity);
//		popWindow.setBtnText(R.id.popwindow_btn_one, "相机");
//		popWindow.setBtnText(R.id.popwindow_btn_two, "相册");
//		popWindow.setBtnText(R.id.popwindow_btn_three, "取消");
//		
//		class PopItemOnClick implements OnClickListener {
//			Activity activity;
//			PopWindow popWindow;
//			
//			public PopItemOnClick(Activity activity, PopWindow popWindow) {
//				this.activity = activity;
//				this.popWindow = popWindow;
//			}
//			@Override
//			public void onClick(View v) {
//				switch (v.getId()) {
//				case R.id.popwindow_btn_one:
//					openCamera(activity, tempFile);
//					popWindow.showDisPopWindow();
//					break;
//				case R.id.popwindow_btn_two:
//					openGallery(activity);
//					popWindow.showDisPopWindow();
//					break;
//				case R.id.popwindow_btn_three:
//					popWindow.showDisPopWindow();
//					break;
//				}
//			}
//		}
//		PopItemOnClick popItemOnClick = new PopItemOnClick(activity, popWindow);
//		popWindow.popView.findViewById(R.id.popwindow_btn_one)
//		.setOnClickListener(popItemOnClick);
//		popWindow.popView.findViewById(R.id.popwindow_btn_two)
//		.setOnClickListener(popItemOnClick);
//		popWindow.popView.findViewById(R.id.popwindow_btn_three)
//		.setOnClickListener(popItemOnClick);
//		popWindow.popView.findViewById(R.id.popwindow_btn_two).setVisibility(
//				View.VISIBLE);
//		popWindow.showDisPopWindow();
//	}
	/*
	 * 判断sdcard是否被挂载
	 */
	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

}
