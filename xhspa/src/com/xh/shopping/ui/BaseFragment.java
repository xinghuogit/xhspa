/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：BaseFragment.java
 * 内容摘要：BaseFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-15 下午4:38:52
 * 修改记录：
 * 修改日期：2016-4-15 下午4:38:52
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui;

import com.xh.shopping.R;
import com.xh.shopping.R.layout;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：BaseFragment.java
 * @contents 内容摘要：
 */
public abstract class BaseFragment extends Fragment {
	protected static final String TAG = BaseFragment.class.getName();

	protected Activity activity;
	protected View parent;

	protected RelativeLayout layout_head_left_r, layout_head_centre_l,
			layout_head_right_r;
	protected ImageView layout_head_left_iv, layout_head_centre_iv,
			layout_head_right_iv;
	protected TextView layout_head_left_tv, layout_head_centre_tv,
			layout_head_right_tv;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(getLayoutView(), container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		parent = getView();
		findView();
		findViewHead();
		setData();
		setListener();
	}

	/**
	 * 获取导航栏
	 */
	private void findViewHead() {
		try {
			layout_head_left_r = findView(R.id.layout_head_left_r);
			layout_head_centre_l = findView(R.id.layout_head_centre_l);
			layout_head_right_r = findView(R.id.layout_head_right_r);

			layout_head_left_iv = findView(R.id.layout_head_left_iv);
			layout_head_centre_iv = findView(R.id.layout_head_centre_iv);
			layout_head_right_iv = findView(R.id.layout_head_right_iv);

			layout_head_left_tv = findView(R.id.layout_head_left_tv);
			layout_head_centre_tv = findView(R.id.layout_head_centre_tv);
			layout_head_right_tv = findView(R.id.layout_head_right_tv);
		} catch (Exception e) {
			Log.i(TAG, "当前Fragment没有导航栏");
			e.printStackTrace();
		}
	}

	/**
	 * @return Layout布局
	 */
	protected abstract int getLayoutView();

	/**
	 * 寻找View
	 */
	protected abstract void findView();

	/**
	 * 设置参数
	 */
	protected abstract void setData();

	/**
	 * 设置监听器
	 */
	protected abstract void setListener();

	/**
	 * 选择id
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T extends View> T findView(int id) {
		return (T) parent.findViewById(id);
	}
}
