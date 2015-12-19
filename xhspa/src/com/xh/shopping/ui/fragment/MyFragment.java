/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：MyFragment.java
 * 内容摘要：MyFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙1605651971@qq.com
 * 完成日期：2015-11-11 下午1:34:24
 * 修改记录：
 * 修改日期：2015-11-11 下午1:34:24
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.xh.shopping.R;
import com.xh.shopping.ui.MainActivity;
import com.xh.shopping.ui.activity.TestRegistActivity;

/**
 * @filename 文件名称：MyFragment.java
 * @contents 内容摘要：我的Fragment
 */
public class MyFragment extends Fragment implements OnClickListener {
	private MainActivity activity;
	private View parent;

	private RelativeLayout my_notuser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_my, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (MainActivity) getActivity();
		parent = getView();

		findView();
		setListener();
	}

	private void findView() {
		// System.out.println("MyFragment");
		my_notuser = (RelativeLayout) parent.findViewById(R.id.my_notuser);

	}

	private void setListener() {
		my_notuser.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_notuser:
			Intent intent = new Intent(activity, TestRegistActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
