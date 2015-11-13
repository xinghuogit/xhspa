/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：HomeFragment.java
 * 内容摘要：HomeFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙1605651971@qq.com
 * 完成日期：2015-11-11 下午1:15:53
 * 修改记录：
 * 修改日期：2015-11-11 下午1:15:53
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

import com.xh.shopping.R;
import com.xh.shopping.ui.MainActivity;
import com.xh.shopping.ui.fragment.activity.RegistActivity;

/**
 * @filename 文件名称：HomeFragment.java
 * @contents 内容摘要：首页界面Fragment
 */
public class HomeFragment extends Fragment {
	private MainActivity activity;
	private View parent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (MainActivity) getActivity();
		parent = getView();

		findView();
	}

	private void findView() {
		parent.findViewById(R.id.home_regist).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(activity, RegistActivity.class));
					}
				});
	}
}
