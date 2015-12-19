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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @filename 文件名称：Constant.java
 * @contents 内容摘要：
 */
public class JSONDataService extends Thread {

	private static JSONDataService serviceInstance = null;

	// public static synchronized JSONDataService getInstance() {
	// if (serviceInstance == null) {
	// serviceInstance = new JSONDataService();
	// }
	// return serviceInstance;
	// }

	private String url;
	private Object postData;

	public JSONDataService(String url, Object postData) {
		this.url = url;
		this.postData = postData;
	}

	@Override
	public void run() {
		getData();
	}

	private void getData() {
		if (url == null) {
			return;
		}

		try {
			URL httpUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) httpUrl
					.openConnection();
			connection.setConnectTimeout(5000);
			if (postData != null) {
				connection.setRequestMethod("POST");
				String content = getContent();
				if (content != null && !content.equals("")) {
					
				} else {
					connection.setRequestMethod("GET");
				}
			} else {
				connection.setRequestMethod("GET");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 如果是post请求 设置字段
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getContent() {
		if (postData instanceof Map) {
			Map<String, Object> data = (Map<String, Object>) postData;
			if (data == null) {
				return null;
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("?");

			for (String key : data.keySet()) {
				String value = String.valueOf(data.get(key));
				buffer.append(key + "=" + value + "&");
			}
			String content = buffer.toString()
					.subSequence(0, buffer.toString().length() - 1).toString();

			return content;
		}
		return null;
	}
}
