/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：T.java
 * 内容摘要：T.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-3-31 下午2:48:24
 * 修改记录：
 * 修改日期：2016-3-31 下午2:48:24
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.serve;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.xh.shopping.setting.SettingHelper;

import android.content.Context;
import android.util.Log;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：ServiceResultCache.java
 * @contents 内容摘要：
 */
public class ServiceResultCache {
	private static final String TAG = ServiceResultCache.class.getName();
	/**
	 * ServiceResultCache对象singleton
	 */
	private static ServiceResultCache singleton = new ServiceResultCache();
	/**
	 * Context对象appContext
	 */
	private static Context appContext;
	/**
	 * 缓存文件名字
	 */
	private static final String CACHE_FILE = "com.xh.shopping";
	/**
	 * 大小 1024*8
	 */
	private static final int BUF_SIZE = 8192;

	/**
	 * 获取ServiceResultCache对象singleton
	 * 
	 * @return
	 */
	public static ServiceResultCache getInstance() {
		return singleton;
	}

	/**
	 * 私有构造方法，成为懒汉模式
	 */
	private ServiceResultCache() {
		loadCacheFileInfo();
	}

	/**
	 * 获取Context对象appContext
	 * 
	 * @return
	 */
	private static Context getContext() {
		if (appContext == null) {
			appContext = SettingHelper.getInstance().getApplicationContext();
		}
		return appContext;
	}

	/**
	 * 获取缓存目录文件名
	 * 
	 * @return
	 */
	private static File getCacheDirectory() {
		File dir = getContext().getCacheDir();
		return dir;
	}

	/**
	 * 构建缓存文件名
	 * 
	 * @param name
	 *            缓存文件名
	 * @return
	 */
	private String buildCacheFileName(String name) {
		String filename = getCacheDirectory() + File.pathSeparator + name;
		return filename;
	}
	
//	private 

	/**
	 * 加载缓存文件信息
	 */
	private void loadCacheFileInfo() {
		// 缓存路径
		String path = getContext().getFilesDir() + File.separator + CACHE_FILE;
		// 打开缓存文件
		File cacheFile = new File(path);
		// 缓存文件存在
		if (cacheFile.exists()) {
			try {
				// 打开一个输入流
				FileInputStream fis = new FileInputStream(cacheFile);
				// 打开一个输出字节流
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				// 512字节流
				byte[] buffer = new byte[512];
				// 读出来
				int read = fis.read(buffer, 0, 512);
				// 当有数据的时候
				while (read > 0) {
					// 写入
					bos.write(buffer, 0, 512);
					// 在读出
					fis.read(buffer, 0, 512);
				}
				// 写入
				bos.flush();
				// 关闭输出流
				bos.close();
				// 关闭输入流
				fis.close();
			} catch (FileNotFoundException e) {
				Log.i(TAG, "没有缓存文件异常");
				e.printStackTrace();
			} catch (IOException e) {
				Log.i(TAG, "读入写入异常");
				e.printStackTrace();
			}
		}
	}

}
