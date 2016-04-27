/*************************************************************************************************
 * 版权所有 (C)2016
 * 
 * 文件名称：RefreshLayout1.java
 * 内容摘要：RefreshLayout1.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2016-4-15 下午3:32:15
 * 修改记录：
 * 修改日期：2016-4-15 下午3:32:15
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.view;

import com.xh.shopping.R;
import com.xh.shopping.view.RefreshLayout.OnLoadListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author 创建作者LI：李加蒙
 * @filename 文件名称：RefreshLayout1.java
 * @contents 内容摘要：
 */
@SuppressLint("InflateParams")
public class RefreshLayout1 extends SwipeRefreshLayout implements
		OnScrollListener {
	/**
	 * 滑动到最下面时的上拉操作
	 */
	private int mTouchSlop;

	/**
	 * 按下时的y坐标
	 */
	private int mYDown;
	/**
	 * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
	 */
	private int mLastY;
	/**
	 * 是否在加载中 ( 上拉加载更多 )
	 */
	private boolean isLoading = false;

	/**
	 * 上拉监听器, 到了最底部的上拉加载操作
	 */
	private OnLoadListener mOnLoadListener;

	/**
	 * ListView的实例
	 */
	private ListView refreshView;

	/**
	 * RefreshView的上拉加载时footer
	 */
	private View refreshViewFooter;

	/**
	 * 刷新完成后显示的文字
	 */
	private TextView mTextView;
	/**
	 * 刷新中显示的进度条
	 */
	private ProgressBar mProgressBar;
	/**
	 * 是否还有更多
	 */
	private boolean moreData = true;

	/**
	 * @param loadListener
	 */
	public void setOnLoadListener(OnLoadListener loadListener) {
		mOnLoadListener = loadListener;
	}

	/**
	 * 加载更多的监听器
	 */
	public static interface OnLoadListener {
		public void onLoad();
	}

	public RefreshLayout1(Context context) {
		super(context);
	}

	public RefreshLayout1(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		try {
			refreshViewFooter = LayoutInflater.from(context).inflate(
					R.layout.refresh_footer, null, false);
			mTextView = (TextView) refreshViewFooter
					.findViewById(R.id.pull_to_refresh_loadmore_text);
			mProgressBar = (ProgressBar) refreshViewFooter
					.findViewById(R.id.pull_to_refresh_load_progress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RefreshLayout1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// 初始化ListView对象
		if (refreshView == null) {
			getRefreshView();
		}
	}

	private void getRefreshView() {
		int childs = getChildCount();
		if (childs > 0) {
			try {
				View childView = getChildAt(0);
				if (childView instanceof ListView) {
					refreshView = (ListView) childView;
					// 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
					refreshView.setOnScrollListener(this);
					Log.d(VIEW_LOG_TAG, "### 找到listview");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Pass the touch screen motion event down to the target view, or this view
	 * if it is the target.
	 * 
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			mYDown = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			// 移动
			mLastY = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_UP:
			// 抬起
			if (canLoad() && mLastY != 0) {
				loadData();
			}
			break;
		default:
			break;
		}
		try {
			return super.dispatchTouchEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 是否可以加载更多, 条件是到了最底部, ListView或者GridView不在加载中, 且为上拉操作.
	 * 
	 * @return
	 */
	private boolean canLoad() {
		return isBottom() && !isLoading && isPullUp() && moreData;
	}

	/**
	 * 判断是否到了最底部
	 */
	private boolean isBottom() {
		if (refreshView instanceof ListView) {
			if (refreshView != null && refreshView.getAdapter() != null) {
				return refreshView.getLastVisiblePosition() == (refreshView
						.getAdapter().getCount() - 1);
			}
		}
		return false;
	}

	/**
	 * 是否是上拉操作
	 * 
	 * @return
	 */
	private boolean isPullUp() {
		return (mYDown - mLastY) >= mTouchSlop;
	}

	/**
	 * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
	 */
	private void loadData() {
		if (mOnLoadListener != null) {
			// 设置状态
			setLoading(true);
			//
			mOnLoadListener.onLoad();
		}
	}

	/**
	 * @param loading
	 */
	public void setLoading(boolean loading) {
		try {
			isLoading = loading;
			if (isLoading) {
				refreshView.addFooterView(refreshViewFooter);
			} else {
				refreshView.removeFooterView(refreshViewFooter);
				mYDown = 0;
				mLastY = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	/**
	 * firstVisibleItem 表示在当前屏幕显示的第一个listItem在整个listView里面的位置（下标从0开始）
	 * visibleItemCount 表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数 totalItemCount
	 * 表示ListView的ListItem总数
	 * listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem
	 * (最后ListItem要完全显示出来才算)在整个ListView的位置（下标从0开始）
	 */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// 滚动时到了最底部也可以加载更多
		if (canLoad() && mLastY != 0 && firstVisibleItem != 0
				&& totalItemCount != 1) {
			loadData();
		}
		if (refreshView.getFooterViewsCount() > 1) {
			try {
				refreshView.removeFooterView(refreshView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 数据库还有数据，可以加载
	 */
	public void setMoreData() {
		this.moreData = true;
		if (mProgressBar != null) {
			mProgressBar.setVisibility(View.GONE);
		}
		if (mTextView != null) {
			mTextView.setText("加载更多");
		}
		if (refreshView == null) {
			getRefreshView();
		}
		SystemRefreshView();
		try {
			if (refreshView.getFooterViewsCount() == 0) {
				refreshView.addFooterView(refreshViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 正在获取数据中
	 */
	public void setLoadMoreData() {
		this.moreData = true;
		this.isLoading = false;
		if (mProgressBar != null) {
			mProgressBar.setVisibility(View.VISIBLE);
		}
		if (mTextView != null) {
			mTextView.setText("加载更多");
		}
		if (refreshView == null) {
			getRefreshView();
		}
		SystemRefreshView();
		try {
			if (refreshView.getFooterViewsCount() == 0) {
				refreshView.addFooterView(refreshViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			refreshView.addFooterView(refreshViewFooter, null, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数据库有数据，但是只有当前页和之前页的
	 */
	public void setNoMoreDataFooterView() {
		setNoMoreDataFooterView("暂无更多");
	}

	/**
	 * 数据库没有数据
	 */
	public void setNoDataFooterView() {
		setNoDataFooterView("暂无数据");
	}

	/**
	 * 数据库有数据，但是只有当前页和之前页的
	 */
	public void setNoMoreDataFooterView(String content) {
		if (refreshView == null) {
			getRefreshView();
		}
		setNoMoreData();
		mTextView.setText(content);
	}

	/**
	 * 数据库没有数据
	 */
	private void setNoDataFooterView(String content) {
		if (refreshView == null) {
			getRefreshView();
		}
		setNoMoreData();
		mTextView.setText(content);
	}

	/**
	 * 本次刷新或者加载没有获取到数据
	 */
	private void setNoMoreData() {
		this.moreData = false;
		this.isLoading = false;
		if (refreshView == null) {
			getRefreshView();
		}
		SystemRefreshView();
		try {
			if (refreshView.getFooterViewsCount() == 0) {
				refreshView.addFooterView(refreshViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setRefreshing(false);
		mProgressBar.setVisibility(View.GONE);
		mYDown = 0;
		mLastY = 0;
	}

	/**
	 * 刷新的时候加载失败
	 */
	public void setRefreshFail() {
		isLoading = false;
		this.moreData = true;
		if (refreshView == null) {
			getRefreshView();
		}
		SystemRefreshView();
		try {
			if (refreshView.getFooterViewsCount() == 0) {
				refreshView.addFooterView(refreshViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setRefreshing(false);
		mProgressBar.setVisibility(View.GONE);
		mTextView.setText("加载失败,下拉刷新");
	}

	/**
	 * 上拉的的时候加载数据失败
	 */
	public void setLoadingFail() {
		isLoading = false;
		this.moreData = true;
		if (refreshView == null) {
			getRefreshView();
		}
		SystemRefreshView();
		try {
			if (refreshView.getFooterViewsCount() == 0) {
				refreshView.addFooterView(refreshViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mProgressBar.setVisibility(View.GONE);
		mTextView.setText("加载失败,重新加载");
	}

	private void SystemRefreshView() {
		System.out.println("refreshView:" + refreshView == null);
	}
}
