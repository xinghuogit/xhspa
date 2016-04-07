/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：TextActivity.java
 * 内容摘要：TextActivity.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-11-10 下午3:46:19
 * 修改记录：
 * 修改日期：2015-11-10 下午3:46:19
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui;

import android.app.Activity;
import android.os.Bundle;
import com.xh.shopping.R;
import com.xh.shopping.setting.SettingHelper;

/**
 * @filename 文件名称：TextActivity.java
 * @contents 内容摘要：
 */
public class TextActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SettingHelper.getInstance().setCurrentActivity(this);
//		UIHelper.getInstance().setSystemBar();
		setContentView(R.layout.activity_test);
	}
}
