/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：DataServiceImpl.java
 * 内容摘要：DataServiceImpl.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-19 下午6:10:01
 * 修改记录：
 * 修改日期：2015-12-19 下午6:10:01
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.serve;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import com.xh.shopping.constant.Constant;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：DataService.java
 * @contents 内容摘要：数据Service接口
 */
abstract class DataServiceImpl implements DataService {
	/**
	 * 当前ClassName
	 */
	public static final String TAG = DataServiceImpl.class.getName();

	/**
	 * 数据Service委派 当前类
	 */
	private DataServiceDelegate dataServiceDelegate = null;
	/**
	 * 是否需要用户验证
	 */
	private boolean isAuth = false;

	/**
	 * 是否可以取消Service
	 */
	private boolean isCancelled = false;

	/**
	 * 执行工作
	 */
	private Future<?> executorTask;

	/**
	 * 获取 数据Service委派 当前Activity
	 */
	public DataServiceDelegate getDataServiceDelegate() {
		return dataServiceDelegate;
	}

	/**
	 * 设置 数据Service委派 当前Activity
	 */
	public void setDataServiceDelegate(DataServiceDelegate dataServiceDelegate) {
		this.dataServiceDelegate = dataServiceDelegate;
	}

	/**
	 * 获取是否需要用户验证
	 */
	public boolean isAuth() {
		return isAuth;
	}

	/**
	 * 设置是否需要用户验证
	 */
	public void setAuth(boolean isAuth) {
		this.isAuth = isAuth;
	}

	/**
	 * 获取是否取消Service
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * 设置是否取消Service
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	/**
	 * 拼接全部网址
	 * 
	 * @param url
	 * @return
	 */
	protected String constructFullURL(String url) {
		return Constant.START_SERVICE + url;
	}

	/**
	 * 开启服务
	 */
	abstract protected void doStartService();

	/**
	 * 获取一个网络连接 并且设置
	 * 
	 * @param type
	 *            请求类型
	 * @param url
	 *            请求地址
	 * @return
	 */
	private static HttpURLConnection getHttpConnection(String type, String url) {
		URL httpUrl;
		try {
			httpUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) httpUrl
					.openConnection();
			connection.setRequestMethod(type);
			connection.setConnectTimeout(Constant.TIMEOUT_CONNECTION);
			connection.setDoInput(true);
			connection.setDoInput(true);
			return connection;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 开启Service
	 */
	@Override
	public void start() {
		// 当前开启Service 是否取消为false
		isCancelled = false;
		// 当执行者为空或者已经完成 可以开启一个新的工作线程
		if (executorTask == null || executorTask.isDone()) {
			Callable<Void> callable = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					return null;
				}
			};
//			executorTask 
		}
	}

	/**
	 * 取消Service
	 */
	@Override
	public void cancll() {
		// 当前取消Service 是否取消为true
		isCancelled = true;
	}
}
