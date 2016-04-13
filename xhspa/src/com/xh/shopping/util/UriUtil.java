/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：UriUtil.java
 * 内容摘要：UriUtil.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-13 上午11:51:34
 * 修改记录：
 * 修改日期：2016-4-13 上午11:51:34
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：UriUtil.java
 * @contents 内容摘要：
 */
public class UriUtil {
	public static String getAbsoluteImagePath(Uri uri, Context context) {

		String[] proj = { MediaStore.Images.Media.DATA };

		@SuppressWarnings("deprecation")
		Cursor actualimagecursor = ((Activity) context).managedQuery(uri, proj,
				null, null, null);

		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		actualimagecursor.moveToFirst();

		String img_path = actualimagecursor
				.getString(actual_image_column_index);

		return img_path;

	}

}
