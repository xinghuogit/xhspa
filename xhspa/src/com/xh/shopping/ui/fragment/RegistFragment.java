/*************************************************************************************************
 * 版权所有 (C)2015,  
 * 
 * 文件名称：RegistFragment.java
 * 内容摘要：RegistFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-11-3 下午10:13:36
 * 修改记录：
 * 修改日期：2015-11-3 下午10:13:36
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment;

import com.xh.shopping.R;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.ui.fragment.activity.RegistActivity;
import com.xh.shopping.util.UIHelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 @filename文件名称：RegistFragment.java
 @content
 */
/**
 * @FileName 文件名称：RegistFragment.java
 * @Contents 内容摘要：注册Fragment
 */
public class RegistFragment extends Fragment {
	private RegistActivity activity;
	private View parent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_regist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (RegistActivity) getActivity();
		parent = getView();
		SettingHelper.getInstance().setCurrentActivity(activity);
		UIHelper.getInstance().setSystemBar();
	}

}
