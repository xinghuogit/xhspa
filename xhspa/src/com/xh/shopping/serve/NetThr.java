//package com.pintx.services;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.List;
//import java.util.Map;
//import java.util.zip.GZIPInputStream;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.pintx.global.LocalCacheData;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//public class NetThr extends Thread {
//
//	private int svrType;
//
//	private String message = "";
//	private String svrurladdr = "";
//	private ServiceCaller RespFun = null;
//	private int trytime = 1;
//	private String mresp = "";
//
//	public String getReturnedString() {
//		return mresp;
//	}
//
//	public NetThr(String svrurl, String message, int svrType, ServiceCaller fun) {
//		this.message = message;
//		this.svrType = svrType;
//		if (message == null)
//			message = "";
//		svrurladdr = svrurl;
//		RespFun = fun;
//	}
//
//	public NetThr(String svrurl, String message, int svrType, int trytimes,
//			ServiceCaller fun) {
//		this.message = message;
//		this.svrType = svrType;
//		if (message == null)
//			message = "";
//		svrurladdr = svrurl;
//		RespFun = fun;
//		trytime = trytimes;
//	}
//
////	static String sessionid = null;
//
//	public void MainFun() {
//
//		String svraddr = "";
//		svraddr = svrurladdr.replace(" ", "%20");
//		Log.e("Jashon", "server url is :" + svraddr);
//		
//		try {
//
//			URL svr = new URL(svraddr);
//			String respstr = "";
//			if (trytime <= 0)
//				trytime = 1;
//			boolean netok = false;
//			if (message == null)
//				message = "";
//			for (int j = 0; j < trytime; j++) {
//				try {
//					HttpURLConnection httpreq = (HttpURLConnection) svr
//							.openConnection();
//					httpreq.setConnectTimeout(60000);
//					httpreq.setReadTimeout(60000);
//
//					if (message.length() >0)
//						httpreq.setRequestMethod("POST");
//					else
//						httpreq.setRequestMethod("GET");
//					httpreq.setDoInput(true);
//					if (message.length() > 0)
//						httpreq.setDoOutput(true);
//
//					// httpreq.setRequestProperty("Connection","Keep-Alive");
//					httpreq.setRequestProperty("Charset", "UTF-8");
//					if (message.length() > 0)
//						httpreq.setRequestProperty("Content-length", ""
//								+ message.getBytes("UTF-8").length);// +(message.length()+2));
//					
//					if (message.length() > 0) {
//						// httpreq.setRequestProperty("Content-Type",
//						// "application/x-www-form-urlencoded");
//						
//						httpreq.setRequestProperty("Content-Type",
//								"application/json");
//					}
////					if(!TextUtils.isEmpty(LocalCacheData.ACCESS_TOKEN)){
////						httpreq.setRequestProperty("access_token",
////								LocalCacheData.ACCESS_TOKEN);
////					}else{
////					httpreq.setRequestProperty("access_token",
////							"efd74916a1fb4fdabcba6c6feb6ba9ea");
//					
////					}
//					if (svrType==ServiceCaller.API_ACCOUNT_EDIT_PROFILE||
//						svrType==ServiceCaller.API_ACCOUNT_SIGN||
//						svrType==ServiceCaller.API_ACCOUNT_GET||
//						svrType==ServiceCaller.API_ACCOUNT_GET_OTHER||
//						svrType==ServiceCaller.API_ACCOUNT_FOLLOW||
//						svrType==ServiceCaller.API_ACCOUNT_FOLLOW_CANCEL||
//						svrType==ServiceCaller.API_ACCOUNT_CHANGE_BG||
//						svrType==ServiceCaller.API_ACCOUNT_IDENTIFICATION||
//						svrType==ServiceCaller.API_SHOW_CREATE||
//						svrType==ServiceCaller.API_SHOW_CREATESTORE||
//						svrType==ServiceCaller.API_SHOW_EDIT||
//						svrType==ServiceCaller.API_TOPIC_CREATE||
//						svrType==ServiceCaller.API_TOPIC_CREATE_ANWER||
//						svrType==ServiceCaller.API_ATTACHMENT_UPLOAD||
//						svrType==ServiceCaller.API_ATTACHMENT_GET_ATTACHMENTS||
//						svrType==ServiceCaller.API_ATTACHMENT_GETTEMP_ATTACHMENTMENTS||
//						svrType==ServiceCaller.API_ATTACHMENT_DETELE||
//						svrType==ServiceCaller.API_ATTACHMENT_DETELETEMP||
//						svrType==ServiceCaller.API_C0MMON_CREATE_VISIT||
//						svrType==ServiceCaller.API_C0MMON_CREATE_ATTITUDE||
//						svrType==ServiceCaller.API_C0MMON_CREATE_COMMENT||
//						svrType==ServiceCaller.API_QUESTION_CREATE||
//						svrType==ServiceCaller.API_SHOW_CREATESTORE||
//						svrType==ServiceCaller.API_SHOW_MYSTORE||
//						svrType==ServiceCaller.API_QUESTION_CREATE_ANSWER
//						) {
//						Log.e("svrType", svrType+"");
//						if(!TextUtils.isEmpty(LocalCacheData.ACCESS_TOKEN)){
//							httpreq.setRequestProperty("access_token",
//									LocalCacheData.ACCESS_TOKEN);
//						}
//					}else{
//						httpreq.setRequestProperty("access_token",
//								"efd74916a1fb4fdabcba6c6feb6ba9ea");
//					}
//					httpreq.connect();
//					if (message.length() > 0) {
//						OutputStream os = httpreq.getOutputStream();
//						DataOutputStream dos = new DataOutputStream(os);
//						dos.write(message.getBytes("UTF-8"));
//						dos.close();
//					}
//					int respcode = httpreq.getResponseCode();
//					String encoding = httpreq
//							.getHeaderField("Content-Encoding");
//					boolean gzipped = encoding != null
//							&& encoding.toLowerCase().contains("gzip");
//
////					if (svrType == ServiceCaller.API_PUBLIC_COOKIE) {
////						Map<String, List<String>> kk = httpreq
////								.getHeaderFields();
////						List<String> cook = kk.get("Set-Cookie");
////						if (cook != null) {
////							for (String onecook : cook) {
////								if (onecook.contains("JSESSIONID")) {
////									// sessionid=onecook.split(";")[0].split("=")[1];
////									sessionid = onecook.split(";")[0];
////								}
////							}
////						}
////					}
//					Log.e("Jashon", "encoding:" + encoding
//							+ ", boolean encoding:" + gzipped);
//					Log.e("Jashon", "jsonStr: " + message);
//
//					boolean returnstr = false;
//					if (HttpURLConnection.HTTP_OK == respcode) {//
//						InputStream is = null;
//						if (gzipped)
//							is = new GZIPInputStream(httpreq.getInputStream());
//						else
//							is = httpreq.getInputStream();// new
//															// GZIPInputStream(httpreq.getInputStream());
//						Log.v("Jashon",
//								"Content-length:" + httpreq.getContentLength());
//
//						if (RespFun == null
//								|| !RespFun.SaveContent(is,
//										httpreq.getContentLength())) {
//							StringBuffer sb = new StringBuffer();
//							String ReadLine;
//							BufferedReader responseReader = new BufferedReader(
//									new InputStreamReader(is));
//							// 逐行读出
//							while ((ReadLine = responseReader.readLine()) != null) {
//								sb.append(ReadLine).append("\n");
//							}
//							respstr = sb.toString();
//							returnstr = true;
//						}
//					} else {
//						if (RespFun != null) {
//							InputStream is = null;
//							is = httpreq.getErrorStream();// new
//
//							StringBuffer sb = new StringBuffer();
//							String ReadLine;
//							BufferedReader responseReader = new BufferedReader(
//									new InputStreamReader(is));
//							// 逐行读出
//							while ((ReadLine = responseReader.readLine()) != null) {
//								sb.append(ReadLine).append("\n");
//							}
//							respstr = sb.toString();
//							returnstr = true;
//							Log.v("Jashon", "Got server response error!"
//									+ respstr + ", code:" + respcode);
//						}
//					}
//					if (respstr.length() > 0) {
//						Log.v("Jashon", "Server return :" + respstr);
//						mresp = respstr;
//					}
//					if (httpreq != null)
//						httpreq.disconnect();
//
//					if (RespFun != null && returnstr) {
//                       
//						JSONObject json;
//						try {
//							json = new JSONObject(respstr);
//							// String message, Integer errorCode, String
//							// details,Boolean success, String pagerInfo,
//							// PagerInfo pagerInfos
//							RespFun.HandleRespStr(json, svrType);
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//					netok = true;
//					break;
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e1) {
//						e1.printStackTrace();
//					}
//					respstr = "Exception message:" + e.getMessage();
//				}
//			}
//			if (RespFun != null && !netok) {
//				RespFun.HandleErrorState("Sending request to server failed by network error. Please check and try again."
//						+ respstr);// getString(R.string.neterror));
//			}
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//	}
//	@Override
//	public void run() {
//		if (RespFun != null)
//			MainFun();
//	}
//}
