/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：FileUtil.java
 * 内容摘要：FileUtil.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-13 上午10:50:15
 * 修改记录：
 * 修改日期：2016-4-13 上午10:50:15
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.util;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：FileUtil.java
 * @contents 内容摘要：
 */
public class FileUtil {
	private static final String BASE_PATH = "/xhshop";

	/**
	 * 创建一个文件 在sd卡中
	 */
	public static File getBasePath() {
		File basePath = new File(Environment.getExternalStorageDirectory(),
				BASE_PATH);

		if (!basePath.exists()) {
			if (!basePath.mkdirs()) {
				try {
					throw new IOException(String.format(
							"%s cannot be created!", basePath.toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (!basePath.isDirectory()) {
			try {
				throw new IOException(String.format("%s is not a directory!",
						basePath.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return basePath;
	}

	/**
	 * 创建一个文件 在data中
	 */
	public static File getPhoneBasePath() {
		File basePath = new File(Environment.getDataDirectory(), BASE_PATH);

		if (!basePath.exists()) {
			if (!basePath.mkdirs()) {
				try {
					throw new IOException(String.format(
							"%s cannot be created!", basePath.toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (!basePath.isDirectory()) {
			try {
				throw new IOException(String.format("%s is not a directory!",
						basePath.toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return basePath;
	}

	/**
	 * 删除文件
	 */
	public static boolean removeFile(String filePath) {
		File file = new File(filePath);
		return file.delete();
	}

	public static File updateDir = null;
	public static File updateFile = null;
	/*********** 保存升级APK的目录 ***********/
	public static final String XHSHOP = "XHSHOPdateApplication";

	public static boolean isCreateFileSucess;

	/**
	 * 方法描述：createFile方法
	 * 
	 * @param String
	 *            app_name
	 * @return
	 * @see FileUtil
	 */
	public static void createFile(String app_name) {

		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			isCreateFileSucess = true;

			updateDir = new File(Environment.getExternalStorageDirectory()
					+ "/" + XHSHOP + "/");
			updateFile = new File(updateDir + "/" + app_name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					isCreateFileSucess = false;
					e.printStackTrace();
				}
			}

		} else {
			isCreateFileSucess = false;
		}
	}

}
