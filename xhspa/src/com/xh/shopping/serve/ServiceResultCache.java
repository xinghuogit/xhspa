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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import android.content.Context;
import android.util.Log;
import com.xh.shopping.setting.SettingHelper;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：ServiceResultCache.java
 * @contents 内容摘要：网络数据 Cache
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
	 * @return appContext
	 */
	private Context getContext() {
		if (appContext == null) {
			appContext = SettingHelper.getInstance().getApplicationContext();
		}
		return appContext;
	}

	/**
	 * 获取缓存文件夹
	 * 
	 * @return path 缓存文件夹路径
	 */
	private String getCacheFilePath() {
		String path = getContext().getFilesDir() + File.separator + CACHE_FILE;
		return path;
	}

	/**
	 * 获取缓存目录文件路径
	 * 
	 * @return dir 缓存目录
	 */
	private File getCacheDirectory() {
		File dir = getContext().getCacheDir();
		return dir;
	}

	/**
	 * 构建缓存文件名
	 * 
	 * @param name
	 *            缓存文件名 是cacheKey
	 * @return filename 缓存路径
	 */
	private String buildCacheFileName(String name) {
		String filename = getCacheDirectory() + File.pathSeparator + name;
		return filename;
	}

	/**
	 * URL生成cacheKey
	 * 
	 * @param url
	 *            网址
	 * @return cacheKey 返回缓存名字、钥匙
	 */
	private String generateCacheKey(String url) {
		String cacheKey = String.valueOf(url.hashCode());
		return cacheKey;
	}

	/**
	 * 加载缓存文件信息
	 */
	private void loadCacheFileInfo() {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		// 缓存文件夹路径
		String path = getCacheFilePath();
		// 打开缓存文件夹
		File cacheFile = new File(path);
		// 缓存文件存在
		if (cacheFile.exists()) {
			try {
				// 打开一个输入流
				fis = new FileInputStream(cacheFile);
				// 打开一个输出字节流
				bos = new ByteArrayOutputStream();
				// 512字节流
				byte[] buffer = new byte[512];
				// 读出来
				int read = fis.read(buffer, 0, 512);
				// 当有数据的时候
				while (read > 0) {
					// 写入
					bos.write(buffer, 0, 512);
					// 在读出
					read = fis.read(buffer, 0, 512);
				}
				// 强制写入
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
			} finally {
				try {
					// 如果发生异常，如bos非空
					if (bos != null) {
						// 关闭bos
						bos.close();
					}
					// 如果发生异常，如fis非空
					if (fis != null) {
						// 关闭fis
						fis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 添加缓存数据
	 * 
	 * @param data
	 *            网络数据
	 * @param cacheKey
	 *            缓存名字，也是钥匙
	 */
	public void addCache(byte[] data, String url) {
		FileOutputStream fos = null;
		try {
			// 获取一个文件路径
			String cacheFileName = buildCacheFileName(generateCacheKey(url));
			// 打开一个文件
			File file = new File(cacheFileName);
			// 文件存在
			if (file.exists()) {
				// 则文件删除
				file.delete();
			}
			// 获取一个输出流
			fos = new FileOutputStream(file);
			// 把网络数据写入到缓存中
			fos.write(data);
			//
			fos.flush();
			// 关闭输出流
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 如果发生异常，如fos非空
				if (fos != null) {
					// 关闭fos
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检查缓存是否可用
	 * 
	 * @param cacheKey
	 *            缓存名字，也是钥匙
	 * @param cacheExpirationInMillis
	 *            缓存过期时间
	 * @return
	 */
	public boolean checkCache(String url, long cacheExpirationInMillis) {
		// 获取一个文件路径
		String cacheFileName = buildCacheFileName(generateCacheKey(url));
		// 打开一个文件
		File file = new File(cacheFileName);
		// 文件存在
		if (file.exists()) {
			// 当过期时间小等于0（肯定是错的）;当前时间减去文件最后一次修改的时间 如果小于等于过期时间返回true
			if ((cacheExpirationInMillis <= 0)
					|| ((new Date().getTime() - file.lastModified()) <= cacheExpirationInMillis)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取缓存数据
	 * 
	 * @param cacheKey
	 *            缓存名字，也是钥匙
	 * @param cacheExpirationMillis
	 *            缓存过期时间
	 * @return
	 */
	public byte[] getCache(String url, long cacheExpirationMillis) {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			if (checkCache(url, cacheExpirationMillis)) {
				// 获取一个文件路径
				String cacheFileName = buildCacheFileName(generateCacheKey(url));
				// 打开一个文件
				File file = new File(cacheFileName);
				// 打开一个输入流
				fis = new FileInputStream(file);
				// 打开一个输出字节流
				bos = new ByteArrayOutputStream();
				// 以大小 1024*8 写入读出
				byte[] buffer = new byte[BUF_SIZE];
				// 读出数据
				int amtRead = fis.read(buffer, 0, BUF_SIZE);
				// 数据非空
				while (amtRead > 0) {
					// 写入数据
					bos.write(buffer, 0, BUF_SIZE);
					// 在读出
					amtRead = fis.read(buffer, 0, BUF_SIZE);
				}
				// 强制bais写入
				bos.flush();
				// 关闭bais
				bos.close();
				// 关闭fis
				fis.close();
				return bos.toByteArray();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 如果发生异常，如bos非空
				if (bos != null) {
					// 关闭bos
					bos.close();
				}
				// 如果发生异常，如fis非空
				if (fis != null) {
					// 关闭fis
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 使一个URL的缓存失效
	 * 
	 * @param url
	 *            网址
	 */
	public void invalidateCache(String url) {
		try {
			// 获取当前文件路径
			String cacheFileName = buildCacheFileName(generateCacheKey(url));
			// 打开文件
			File file = new File(cacheFileName);
			// 如果文件在
			if (file.exists()) {
				// 则删除
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清空缓存
	 */
	public void resetCache() {
		// 获取缓存目录文件路径
		File cacheDirectory = getCacheDirectory();
		// 获取所有缓存文件
		File[] files = cacheDirectory.listFiles();
		for (File file : files) {
			// 如果存在
			if (file.exists()) {
				// 则删除文件
				file.delete();
			}
		}
		// 缓存文件夹路径
		String path = getCacheFilePath();
		// 打开缓存文件夹
		File file = new File(path);
		// 如果存在
		if (file.exists()) {
			// 删除文件夹
			file.delete();
		}
	}
}
