/*************************************************************************************************
 * 版权所有 (C)2015,  
 * 
 * 文件名称：SettingHelper.java
 * 内容摘要：SettingHelper.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-11-4 下午7:56:54
 * 修改记录：
 * 修改日期：2015-11-4 下午7:56:54
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import com.xh.shopping.model.User;

import android.app.Activity;
import android.content.Context;

/**
 @filename文件名称：SettingHelper.java
 @content
 */
/**
 * @FileName 文件名称：SettingHelper.java
 * @Contents 内容摘要：设置
 */
public class SettingHelper {
	private Context ApplicationContext;
	private Activity currentActivity;

	private User mUserInfo;
	private static final String USER_INFO_FILE = "com.xh.shopping.userinfo.dat";

	public Context getApplicationContext() {
		return ApplicationContext;
	}

	public void setApplicationContext(Context applicationContext) {
		ApplicationContext = applicationContext;
	}

	public Activity getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}

	private static SettingHelper mSettingHelper;

	private SettingHelper() {
	}

	public static synchronized SettingHelper getInstance() {
		if (mSettingHelper == null) {
			mSettingHelper = new SettingHelper();
		}
		return mSettingHelper;
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @return
	 */
	public User getUserInfo() {
		if (mUserInfo == null) {
			try {
				File file = getApplicationContext().getFileStreamPath(
						USER_INFO_FILE);
				if (file.exists()) {
					FileInputStream fis = getApplicationContext()
							.openFileInput(USER_INFO_FILE);
					ObjectInputStream ois = new ObjectInputStream(fis);
					Object object = ois.readObject();
					if (object != null && object instanceof User) {
						mUserInfo = (User) object;
					}
					ois.close();
					fis.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		return mUserInfo;
	}

	/**
	 * 设置当前用户信息
	 * 
	 * @param mUserInfo
	 */
	public void setUserInfo(User mUserInfo) {
		this.mUserInfo = mUserInfo;
		if (mUserInfo == null) {
			File file = getInstance().getApplicationContext()
					.getFileStreamPath(USER_INFO_FILE);
			if (file.exists()) {
				file.delete();
			}
		} else {
			try {
				FileOutputStream fos = getApplicationContext().openFileOutput(
						USER_INFO_FILE, Context.MODE_PRIVATE);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(mUserInfo);
				oos.flush();
				oos.close();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
