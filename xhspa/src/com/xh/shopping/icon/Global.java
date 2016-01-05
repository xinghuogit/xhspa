package com.xh.shopping.icon;

import android.os.Environment;

/**
 * 
 * 存放一些不变的全局使用的常量
 * 
 * @author yang 2015年11月19日11:49:05
 *
 */
public class Global {

	/** AccessToken Time*/
//	public static final long ACCESS_TOKEN_TIME= 30000;
	public static final long ACCESS_TOKEN_TIME= 6*24*3600*1000;
	/** 拍照 */
	public static final int PICTURE_TAKE = 100;
	/** 相册 */
	public static final int PICTURE_CHOOSE = 101;
	/** 图片剪切 */
	public static final int PICTURE_CUT = 102;

	/** 获取验证码之类的 倒计时提示标记 */
	public static final int FLAG_TIMER = 103;
	/** 头像图片缓存的路径 */
	public static final String AVATAR_CACHE_PATH = Environment.getExternalStorageDirectory() + "/pintx2.0/";
	public static final String REPLACE_AVATAR_NAME = "replace_avatar.png";
	/** 美食分类 */
	public static final String CATEGORYS_KEY = "categorys";
	/** 美食搜索 */
	public static final String FOODS_SEARCH_KEY = "search";
	/** 历史搜索 */
	public static final String HISTORY_SEARCH = "historySearch";
	
	public static final int  IMG_MAX=9;
}
