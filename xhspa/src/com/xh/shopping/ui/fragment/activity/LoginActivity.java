/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：LoginActivity.java
 * 内容摘要：LoginActivity.java
 * 当前版本：TODO
 * 作        者：李加蒙1605651971@qq.com
 * 完成日期：2015-11-11 下午5:51:44
 * 修改记录：
 * 修改日期：2015-11-11 下午5:51:44
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.xh.shopping.R;
import com.xh.shopping.ui.fragment.LoginFragment;

/**
 * @filename 文件名称：LoginActivity.java
 * @contents 内容摘要：登录界面Activity
 */
public class LoginActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		if (getSupportFragmentManager().findFragmentById(
				R.id.fragment_container) == null) {
			LoginFragment activity = new LoginFragment();
			FragmentTransaction bt = getSupportFragmentManager()
					.beginTransaction();
			bt.disallowAddToBackStack();
			bt.add(R.id.fragment_container, activity).commit();
		}
	}
}
