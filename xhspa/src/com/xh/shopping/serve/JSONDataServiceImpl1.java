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
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Handler;
import android.os.Message;
import com.xh.shopping.R;
import com.xh.shopping.model.User;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.util.StringUtil;
import com.xh.shopping.util.ToastUtil;
import com.xh.shopping.util.UIHelper;

/**
 * @filename 文件名称：Constant.java
 * @contents 内容摘要：网络获取服务
 * 
 * @msg arg1
 * @0 返回为空 服务器未连接
 * @1 返回非空 提交获取成功 0001
 * @2 返回非空 提交获取失败 0002
 */
public class JSONDataServiceImpl1 extends Thread {


	// public static synchronized JSONDataService getInstance() {
	// if (serviceInstance == null) {
	// serviceInstance = new JSONDataService();
	// }
	// return serviceInstance;
	// }

	private String url;
	private Object postData;
	private Handler handler;
	private boolean isAuth;

	public JSONDataServiceImpl1(String url, Object postData, Handler handler,
			boolean isAuth) {
		this.url = url;
		this.postData = postData;
		this.handler = handler;
		this.isAuth = isAuth;
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
			addHeader(connection);
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
			}
			System.out.println("buffer:" + buffer.toString());
			String result = buffer.toString();
			Message msg = new Message();
			JSONObject json = null;
			String ret = null;

			if (StringUtil.isEmpty(result)) {
				msg.arg1 = 0;
			} else {
				json = getJSON(result);
			}

			if (json == null) {
				msg.arg1 = 0;
			} else {
				ret = json.optString("ret");
			}

			if (StringUtil.isEmpty(ret)) {
				msg.arg1 = 0;
			} else if (ret.equals("0001")) {
				msg.arg1 = 1;
				msg.obj = json;
			} else {
				msg.arg1 = 2;
				String msgRet = json.optString("msg");
				msg.obj = msgRet;
			}
			handler.sendMessage(msg);
		} catch (MalformedURLException e) {
			if (connection != null) {
				connection.disconnect();
			}
			UIHelper.dismissProgressDialog();
			System.out.println("MalformedURLException");
			e.printStackTrace();
		} catch (IOException e) {
			
			if (connection != null) {
				connection.disconnect();
			}
			UIHelper.dismissProgressDialog();
//			ToastUtil.makeToast(SettingHelper.getInstance()
//					.getCurrentActivity(), "连接服务器失败，请检查网络");
//			System.out.println("IOException");
			e.printStackTrace();
		}

	}

	private void addHeader(HttpURLConnection connection) {
		if (isAuth) {
			User userInfo = SettingHelper.getInstance().getUserInfo();
			if (userInfo == null) {
				ToastUtil.makeToast(SettingHelper.getInstance()
						.getApplicationContext(), R.string.logininfo_error);
				return;
			} else {
				String userpsw = userInfo.getUsername() + ":"
						+ userInfo.getPassword();
				System.out.println("userpsw:" + userpsw);
				connection.setRequestProperty("Authorization", userpsw);
			}
		}
	}

	/**
	 * 如果是返回失败的话 要用这段
	 * 
	 * @param result
	 * @return
	 */
	private JSONObject getJSON(String result) {
		try {
			JSONObject json = new JSONObject(result);
			return json;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
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
