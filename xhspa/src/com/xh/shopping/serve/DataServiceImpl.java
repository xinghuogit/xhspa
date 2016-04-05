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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import android.util.Log;
import com.xh.shopping.R;
import com.xh.shopping.constant.Constant;
import com.xh.shopping.model.User;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.util.DeviceUtil;
import com.xh.shopping.util.StringUtil;
import com.xh.shopping.util.ToastUtil;

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

	private HttpURLConnection connection = null;

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
	 * 获取DataServiceDelegate对象dataServiceDelegate是否为空
	 * 
	 * @return
	 */
	public boolean hasDelegate() {
		return hasDelegate();
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
			// 设置使用APP
			connection.setRequestProperty("Host", "xhspa.com");
			// 设置语言
			connection.setRequestProperty("Accept-Language",
					"zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
			// 设置连接
			connection.setRequestProperty("Connection", "Keep-Alive");
			// 设置平台信息
			connection.setRequestProperty(HTTP.USER_AGENT, DeviceUtil
					.getInstance().getUserAgent());
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
					// 开启服务
					doStartService();
					return null;
				}
			};
			// 提交开启Service
			executorTask = getExecutorService().submit(callable);
		}
	}

	/**
	 * 取消Service
	 */
	@Override
	public void cancll() {
		// 当前取消Service 是否取消为true
		isCancelled = true;
		// 开启线程
		new Thread() {
			@Override
			public void run() {
				// 执行人工作非空
				if (executorTask != null) {
					// executorTask撤销并且滞空
					executorTask.cancel(true);
					executorTask = null;
				}
				// 释放connection
				releaseConnection();
			}
		}.start();
	}

	/**
	 * 
	 * @return 获取ExecutorService的对象 一个线程池
	 */
	protected ExecutorService getExecutorService() {
		return DataServiceFactory.getInstance().getExecutorService();
	}

	/**
	 * 滞空connection 释放connection
	 */
	private void releaseConnection() {
		if (connection != null) {
			connection = null;
		}
	}

	/**
	 * 设置请求方式，发送请求，获取返回网络字节流数据
	 * 
	 * @param url
	 *            网址
	 * @param data
	 *            是否post请求
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected byte[] sendServiceRequest(String url, Object data) {
		if (StringUtil.isEmpty(url)) {
			Log.i(TAG, "url为null");
			return null;
		}
		try {
			if (url.indexOf("?") > 0) {// 把url后面用?连接的数据转化成为UTF-8格式
				// 从?之后一位截取到最后
				String param = url
						.substring(url.indexOf("?") + 1, url.length());
				// 从&分离成为数组,键队值的数量
				String[] arrays = param.split("&");
				// 首先拼接?
				StringBuilder stringBuilder = new StringBuilder("?");
				for (String string : arrays) {
					// 从=分离成为数组,一键一值
					String[] keyValuePair = string.split("=");
					// 拼接数组的第一个（拼接键）
					stringBuilder.append(keyValuePair[0] + "=");
					// 如果数组位数大于1（有值）则拼接值
					if (stringBuilder.length() > 1) {
						// 把数组第二个数（值）转化成为UTF-8
						if (keyValuePair.length > 1) {
							stringBuilder.append(URLEncoder.encode(
									keyValuePair[1], Constant.UTF_8));
						}
					}
					// 当下一个之前要连接&（不是第一个）
					stringBuilder.append("&");
				}
			}
			// data非空 post请求
			if (data != null) {
				connection = getHttpConnection(Constant.POST, url);
				if (data instanceof List) {
				} else if (data instanceof Map) {
					// 解析Map类型data数据，并且写入到connection
					submitMap((Map<String, Object>) data, connection);
				}
				addHeader(connection);
			} else {// get请求
				connection = getHttpConnection(Constant.GET, url);
				addHeader(connection);
			}

			// 返回状态码
			int statusCode = connection.getResponseCode();
			// 返回信息
			String message = connection.getResponseMessage();
			StringBuffer buffer = new StringBuffer();
			// connection获取输入流
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String len;
			while ((len = reader.readLine()) != null) {
				buffer.append(len);
			}
			// 返回的转换成字符串
			String response = buffer.toString();
			// 释放connection
			releaseConnection();
			// DataServiceDelegate对象非空；statusCode为200；返回数据非空
			if (hasDelegate() && (statusCode == 200)
					&& (response.getBytes() != null)) {
				// 请求返回字节数据
				return response.getBytes();
			}
			Log.i(TAG, "error：" + message);
			return null;
		} catch (UnsupportedEncodingException e) {
			Log.i(TAG, "数据转化成为UTF-8字符串异常");
			e.printStackTrace();
		} catch (IOException e) {
			Log.i(TAG, "Map数据（post）写入connection异常");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析Map类型data数据，并且写入到connection
	 * 
	 * @param data
	 *            post数据
	 * @param connection
	 *            网络连接
	 * @throws IOException
	 *             写入connection异常
	 */
	private void submitMap(Map<String, Object> data,
			HttpURLConnection connection) throws IOException {
		StringBuffer buffer = new StringBuffer();
		// 迭代出data的数据
		for (String key : data.keySet()) {
			String value = String.valueOf(data.get(key));
			// 用键队值的方式拼接成字符串
			buffer.append(key + "=" + value + "&");
		}
		// 拼接好字符串去掉最后一个&
		String content = buffer.toString()
				.subSequence(0, buffer.toString().length() - 1).toString();
		// connection获取一个输出流
		OutputStream os = connection.getOutputStream();
		// 字符串写入到输出流中
		os.write(content.getBytes());
	}

	/**
	 * 连接是否需要验证
	 * 
	 * @param connection
	 */
	private void addHeader(HttpURLConnection connection) {
		if (isAuth()) {
			User userInfo = SettingHelper.getInstance().getUserInfo();
			if (userInfo == null) {
				ToastUtil.makeToast(SettingHelper.getInstance()
						.getApplicationContext(), R.string.logininfo_error);
				return;
			} else {
				String userpsw = userInfo.getUsername() + ":"
						+ userInfo.getPassword();
				Log.i(TAG, "userpsw:" + userpsw);
				connection.setRequestProperty("Authorization", userpsw);
			}
		}
	}

	/**
	 * 判断返回的JSON是否正常
	 * 
	 * @param jsonObject
	 * @return false：JSON异常 true：JSON正常
	 */
	protected boolean isJSONObject(JSONObject jsonObject) {
		if (jsonObject == null) {
			propagateServiceFailure("服务器未响应");
			return false;
		} else {
			// 获取返回的code
			String returnCode = jsonObject.optString("ret");
			// returnCode为空，但非0000；表示有返回，但数据异常
			if (returnCode == null || !returnCode.equals(Constant.CODE_NORMAL)) {
				propagateServiceFailure(jsonObject.optString("msg"));
				return false;
			} else {
				// returnCode非空，并且为0000；表示有返回，并且数据正常
				return true;
			}
		}
	}

	/**
	 * 表示有返回，并且数据正常
	 * 
	 * @param object
	 *            JSON数据
	 */
	protected void propagateServiceSuccess(Object object) {
		Log.i(TAG, "propagateServiceSuccess");
		// 非取消的Service并且是有DataServiceDelegate对象
		if (!isCancelled && hasDelegate()) {
			getDataServiceDelegate().onServiceSuccess(this, object);
		}
	}

	/**
	 * 有或者无返回，有返回时数据异常，无返回时服务器未响应
	 * 
	 * @param ret
	 *            服务器未响应或者返回未响应的原因
	 */
	protected void propagateServiceFailure(String ret) {
		Log.i(TAG, "propagateServiceFailure");
		// 非取消的Service并且是有DataServiceDelegate对象
		if (!isCancelled && hasDelegate()) {
			getDataServiceDelegate().onServiceFailure(this, ret);
		}
	}

	// class UnsignedTrustManager implements X509TrustManager {
	// public void checkClientTrusted(X509Certificate[] chain, String authType)
	// throws CertificateException {
	// }
	//
	// public void checkServerTrusted(X509Certificate[] chain, String authType)
	// throws CertificateException {
	// }
	//
	// public X509Certificate[] getAcceptedIssuers() {
	// return null;
	// }
	// }
	//
	// class UnsignedSSLSocketFactory extends SSLSocketFactory {
	// SSLContext sslContext = SSLContext.getInstance("TLS");
	//
	// public UnsignedSSLSocketFactory(KeyStore truststore)
	// throws NoSuchAlgorithmException, KeyManagementException,
	// KeyStoreException, UnrecoverableKeyException {
	// super(truststore);
	//
	// sslContext.init(null,
	// new TrustManager[] { new UnsignedTrustManager() }, null);
	// }
	//
	// @Override
	// public Socket createSocket(Socket socket, String host, int port,
	// boolean autoClose) throws IOException, UnknownHostException {
	// Socket s = sslContext.getSocketFactory().createSocket(socket, host,
	// port, autoClose);
	// s.setSoTimeout(SettingHelper.getInstance().getSocketTimeout());
	// return s;
	// }
	//
	// @Override
	// public Socket createSocket() throws IOException {
	// Socket s = sslContext.getSocketFactory().createSocket();
	// s.setSoTimeout(SettingHelper.getInstance().getSocketTimeout());
	// return s;
	// }
	// }

}
