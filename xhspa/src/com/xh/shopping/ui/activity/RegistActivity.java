/*************************************************************************************************
 * 版权所有 (C)2015,  
 * 
 * 文件名称：RegisterActivity.java
 * 内容摘要：RegisterActivity.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-11-3 下午9:35:16
 * 修改记录：
 * 修改日期：2015-11-3 下午9:35:16
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

import com.xh.shopping.ui.activity.fragment.RegistFragment;

/**
 * @FileName 文件名称：RegisterActivity.java
 * @Content 内容摘要：注册界面
 */
public class RegistActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RegistFragment registFragment = new RegistFragment();
//		FragmentManager manager = registFragment.getFragmentManager();
	}

}
