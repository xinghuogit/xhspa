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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.xh.shopping.util.StringUtil;

import android.os.Handler;
import android.os.Message;

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
	private Handler handler;

	public JSONDataService(String url, Object postData, Handler handler) {
		this.url = url;
		this.postData = postData;
		this.handler = handler;
	}

	@Override
	public void run() {
		getData();
	}

	private void getData() {
		if (url == null) {
			return;
		}
		URL httpUrl = null;

		HttpURLConnection connection = null;
		try {
			System.out.println("url:" + url);
			httpUrl = new URL(url);
			connection = (HttpURLConnection) httpUrl.openConnection();
			connection.setConnectTimeout(5000);
			connection.setDoInput(true);
			connection.setDoInput(true);
			if (postData != null) {
				connection.setRequestMethod("POST");
				String content = getContent();
				if (!StringUtil.isEmpty(content)) {
					System.out.println("POST请求" + content);
					OutputStream os = connection.getOutputStream();
					os.write(content.getBytes());

				} else {
					System.out.println("POST请求错误，参数为空");
				}
			} else {
				connection.setRequestMethod("GET");
				System.out.println("GET请求");
			}

			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String len;
			while ((len = reader.readLine()) != null) {
				buffer.append(len);
				System.out.println("buffer:" + buffer.toString());
				Message msg = new Message();
				msg.obj = buffer.toString();
				handler.sendMessage(msg);
			}
		} catch (MalformedURLException e) {
			if (connection != null) {
				connection.disconnect();
			}
			System.out.println("MalformedURLException");
			e.printStackTrace();
		} catch (IOException e) {
			if (connection != null) {
				connection.disconnect();
			}
			System.out.println("IOException");
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
