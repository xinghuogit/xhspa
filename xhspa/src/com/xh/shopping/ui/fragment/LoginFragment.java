/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：LoginFragment.java
 * 内容摘要：LoginFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙1605651971@qq.com
 * 完成日期：2015-11-11 下午5:53:52
 * 修改记录：
 * 修改日期：2015-11-11 下午5:53:52
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment;

import com.xh.shopping.R;
import com.xh.shopping.ui.fragment.activity.LoginActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @filename 文件名称：LoginFragment.java
 * @contents 内容摘要：
 */
public class LoginFragment extends Fragment {
	private LoginActivity activity;
	private View parent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (LoginActivity) getActivity();
		parent = getView();

		findView();
	}

	private void findView() {

	}

}
