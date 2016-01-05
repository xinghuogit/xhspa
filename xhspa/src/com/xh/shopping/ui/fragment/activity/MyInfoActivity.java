/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：MyInfoActivity.java
 * 内容摘要：MyInfoActivity.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-22 下午12:56:53
 * 修改记录：
 * 修改日期：2015-12-22 下午12:56:53
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.xh.shopping.R;
import com.xh.shopping.ui.fragment.MyInfoFragment;

/**
 * @filename 文件名称：MyInfoActivity.java
 * @contents 内容摘要：
 */
@SuppressLint("Recycle")
public class MyInfoActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		if (getSupportFragmentManager().findFragmentById(
				R.id.fragment_container) == null) {
			FragmentTransaction bt = getSupportFragmentManager()
					.beginTransaction();
			MyInfoFragment fragment = new MyInfoFragment();
			bt.disallowAddToBackStack();
			bt.add(R.id.fragment_container, fragment).commit();
		}
	}
}
