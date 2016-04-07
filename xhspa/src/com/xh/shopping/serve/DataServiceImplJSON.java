/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：Constant.java
 * 内容摘要：Constant.java
 * 当前版本：TODO
 * 作        者：李加蒙1605651971@qq.com
 * 完成日期：2015-12-17 下午2:29:15
 * 修改记录：
 * 修改日期：2015-12-17 下午2:29:15
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.serve;

import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import com.xh.shopping.constant.Constant;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：DataServiceImplJSON.java
 * @contents 内容摘要：网络服务并且获取JSON
 */
public abstract class DataServiceImplJSON extends DataServiceImpl {
	/**
	 * 是否使用缓存,默认不使用
	 */
	private boolean cachingEnabled = false;

	/**
	 * 获取是否使用缓存
	 */
	public boolean isCachingEnabled() {
		return cachingEnabled;
	}

	/**
	 * 设置是否使用缓存
	 */
	public void setCachingEnabled(boolean cachingEnabled) {
		this.cachingEnabled = cachingEnabled;
	}

	/**
	 * 获取完整的URL
	 * 
	 * @return
	 */
	protected abstract String getFullURL();

	/**
	 * 获取参数（post请求）
	 * 
	 * @return
	 */
	protected abstract Object getParams();

	/**
	 * 
	 * @param jsonObject
	 * @return 请求成功后,返回的JSON数据
	 */
	protected abstract Object buildObjectModel(JSONObject jsonObject);

	/**
	 * 开启服务
	 */
	@Override
	protected void doStartService() {
		try {
			// 获取URL
			String url = getFullURL();
			// JSON对象
			JSONObject jsonObject = null;
			// 返回二进制字节流
			byte[] result = null;
			// 是否把数据缓存到本地
			boolean isCached = false;
			// 使用缓存
			if (isCachingEnabled()) {
				// 获取缓存数据
				result = ServiceResultCache.getInstance().getCache(url,
						Constant.TIMEOUT_CONNECTION);
			}
			// 如果不是用缓存或者缓存数据为空
			if (result == null) {
				// 联网获取
				result = sendServiceRequest(url, getParams());
			} else {
				// 设置为把数据添加到缓存
				isCached = true;
			}
			// 网络数据字符串
			String strResult = null;
			if (result != null) {
				// 把二进制字节流转为字符串
				strResult = new String(result, Constant.UTF_8);
			}
			if (Constant.SHOW) {
				System.out.println("url:\n" + url + "strResult:\n" + strResult);
			}
			// 转换为JSON
			jsonObject = new JSONObject(strResult);
			// 判断JSON是否正常
			if (isJSONObject(jsonObject)) {
				// 下发到当前使用网络服务的类 以便操作数据
				propagateServiceSuccess(buildObjectModel(jsonObject));
				if (isCached) {
					// 添加到缓存
					ServiceResultCache.getInstance().addCache(result, url);
				}
			}
		} catch (UnsupportedEncodingException e) {
			Log.i(TAG, "编码转换异常");
			e.printStackTrace();
		} catch (JSONException e) {
			Log.i(TAG, "字符串转换成JSON异常");
			e.printStackTrace();
		}
	}
}
