/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：CategoryFragment.java
 * 内容摘要：CategoryFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙1605651971@qq.com
 * 完成日期：2015-11-11 下午1:30:29
 * 修改记录：
 * 修改日期：2015-11-11 下午1:30:29
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment;

import com.xh.shopping.R;
import com.xh.shopping.ui.MainActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @filename 文件名称：CategoryFragment.java
 * @contents 内容摘要：分类Fragment
 */
public class CategoryFragment extends Fragment {
	private MainActivity activity;
	private View parent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_category, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (MainActivity) getActivity();
		parent = getView();

		findView();
	}

	private void findView() {
		System.out.println("CategoryFragment");
	}
}
