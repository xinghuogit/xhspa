/*************************************************************************************************
 * 版权所有 (C)2015,  
 * 
 * 文件名称：RegistActivity1.java
 * 内容摘要：RegistActivity1.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-11-3 下午10:13:15
 * 修改记录：
 * 修改日期：2015-11-3 下午10:13:15
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.xh.shopping.R;
import com.xh.shopping.ui.fragment.RegistFragment;

/**
 @filename文件名称：RegistActivity1.java
 @content
 */
/**
 * @FileName 文件名称：RegistActivity1.java
 * @Contents 内容摘要： 注册Activity
 */
public class RegistActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		if (getSupportFragmentManager().findFragmentById(
				R.id.fragment_container) == null) {
			RegistFragment fragment = new RegistFragment();
			fragment.setArguments(getIntent().getExtras());
			FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
			trans.disallowAddToBackStack();
			trans.add(R.id.fragment_container, fragment).commit();
		}
	}

}
