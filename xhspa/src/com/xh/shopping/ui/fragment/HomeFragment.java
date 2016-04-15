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

import java.util.ArrayList;
import java.util.List;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.xh.shopping.R;
import com.xh.shopping.adapter.HomeAdapter;
import com.xh.shopping.model.Product;
import com.xh.shopping.ui.BaseFragment;
import com.xh.shopping.view.RefreshLayout1;
import com.xh.shopping.view.RefreshLayout1.OnLoadListener;

/**
 * @filename 文件名称：HomeFragment.java
 * @contents 内容摘要：首页界面Fragment
 */
public class HomeFragment extends BaseFragment implements OnClickListener,
		OnRefreshListener, OnLoadListener {

	private RefreshLayout1 swipe_container;
	private ListView listview;
	private boolean isServiceRunning = false;
	private int page = 1;

	private HomeAdapter adapter = null;
	private List<Product> products = new ArrayList<Product>();

	@Override
	protected int getLayoutView() {
		return R.layout.fragment_home;
	}

	@Override
	protected void findView() {
		swipe_container = findView(R.id.swipe_container);
		listview = findView(R.id.listview);
	}

	@Override
	protected void setData() {
		swipe_container.setColorSchemeResources(R.color.holo_light_blue,
				R.color.holo_light_green, R.color.holo_light_orange,
				R.color.holo_light_red);
		layout_head_centre_tv.setText("首页");
		layout_head_left_tv.setText("扫一扫");
		layout_head_right_tv.setText("消息");
		adapter = new HomeAdapter();
	}

	@Override
	protected void setListener() {
		findView(R.id.layout_head_left_r).setOnClickListener(this);
		findView(R.id.layout_head_right_r).setOnClickListener(this);
		swipe_container.setOnRefreshListener(this);
		swipe_container.setOnLoadListener(this);
		startRefresh();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_head_left_r:
			break;
		case R.id.layout_head_right_r:
			break;
		default:
			break;
		}
	}

	public void startRefresh() {
		swipe_container.postDelayed(new Runnable() {
			@Override
			public void run() {
				swipe_container.setRefreshing(true);
			}
		}, 1);
		onRefresh();
	}

	@Override
	public void onRefresh() {
		if (isServiceRunning) {
			return;
		}
		products.clear();
		adapter.notifyDataSetChanged();
		page = 1;
		startService();
	}

	@Override
	public void onLoad() {
		if (isServiceRunning) {
			return;
		}
		++page;
		startService();
	}

	private void startService() {

	}

}
