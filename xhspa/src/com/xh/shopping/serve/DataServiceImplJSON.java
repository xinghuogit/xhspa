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

import org.json.JSONObject;

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
		// 获取URL
		String url = getFullURL();
		// JSON对象
		JSONObject jsonObject = null;
		// 返回二进制字节流
		byte[] result = null;
		// 是否把数据缓存到本地
		boolean isCached = false;

		if (isCachingEnabled()) {
			
		}
	}
}
