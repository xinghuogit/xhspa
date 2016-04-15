package com.xh.shopping.view;

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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xh.shopping.R;

/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能.
 * 
 */
public class RefreshLayout extends SwipeRefreshLayout implements
		OnScrollListener {

	/**
	 * 滑动到最下面时的上拉操作
	 */

	private int mTouchSlop;
	/**
	 * listview实例
	 */
	private ListView mListView;
	// private OnRefreshListener mRefreshListener;
	/**
	 * 上拉监听器, 到了最底部的上拉加载操作
	 */
	private OnLoadListener mOnLoadListener;

	/**
	 * ListView的加载中footer
	 */
	private View mListViewFooter;

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

	private TextView mTextView;
	private ProgressBar mProgressBar;
	private boolean moreData = true;

	/**
	 * @param context
	 */
	public RefreshLayout(Context context) {
		this(context, null);
	}

	@SuppressLint("InflateParams")
	public RefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		try {
			mListViewFooter = LayoutInflater.from(context).inflate(
					R.layout.refresh_footer, null, false);
			mTextView = (TextView) mListViewFooter
					.findViewById(R.id.pull_to_refresh_loadmore_text);
			mProgressBar = (ProgressBar) mListViewFooter
					.findViewById(R.id.pull_to_refresh_load_progress);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// 初始化ListView对象
		if (mListView == null) {
			getListView();
		}
	}

	public View getListViewFooter() {
		return mListViewFooter;
	}

	/**
	 * 获取ListView对象
	 */
	private void getListView() {
		int childs = getChildCount();
		if (childs > 0) {
			try {
				View childView = getChildAt(0);
				if (childView instanceof ListView) {
					mListView = (ListView) childView;
					// 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
					mListView.setOnScrollListener(this);
					Log.d(VIEW_LOG_TAG, "### 找到listview");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
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
	 * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
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
		if (mListView != null && mListView.getAdapter() != null) {
			return mListView.getLastVisiblePosition() == (mListView
					.getAdapter().getCount() - 1);
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
				mListView.addFooterView(mListViewFooter);
			} else {
				mListView.removeFooterView(mListViewFooter);
				mYDown = 0;
				mLastY = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param loadListener
	 */
	public void setOnLoadListener(OnLoadListener loadListener) {
		mOnLoadListener = loadListener;
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
		if (mListView.getFooterViewsCount() > 1) {
			try {
				mListView.removeFooterView(mListViewFooter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 加载更多的监听器
	 * 
	 * @author mrsimple
	 */
	public static interface OnLoadListener {
		public void onLoad();
	}

	public void setNoMoreDataFooterView() {
		setNoMoreDataFooterView("暂无更多");
	}

	public void setNoDataFooterView() {
		setNoDataFooterView("暂无数据");
	}

	public void setNoMoreDataFooterView(String content) {
		if (mListView == null) {
			getListView();
		}
		setNoMoreData();
		mTextView.setText(content);
	}

	public void setNoDataFooterView(String content) {
		if (mListView == null) {
			getListView();
		}
		setNoMoreData();
		mTextView.setText(content);
	}

	private void setNoMoreData() {
		this.moreData = false;
		this.isLoading = false;
		if (mListView == null) {
			getListView();
		}
		System.out.println("mListView" + mListView == null);
		try {
			if (mListView.getFooterViewsCount() == 0) {
				mListView.addFooterView(mListViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setRefreshing(false);
		mProgressBar.setVisibility(View.GONE);
		mYDown = 0;
		mLastY = 0;
	}

	public void setMoreData() {
		this.moreData = true;
		this.isLoading = false;
		if (mProgressBar != null) {
			mProgressBar.setVisibility(View.VISIBLE);
		}
		if (mTextView != null) {
			mTextView.setText("加载更多");
		}
		if (mListView == null) {
			getListView();
		}
		try {
			if (mListView.getFooterViewsCount() == 0) {
				mListView.addFooterView(mListViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			mListView.addFooterView(mListViewFooter, null, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setLoadingFail() {
		isLoading = false;
		this.moreData = true;
		if (mListView == null) {
			getListView();
		}
		try {
			if (mListView.getFooterViewsCount() == 0) {
				mListView.addFooterView(mListViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setRefreshing(false);
		mProgressBar.setVisibility(View.GONE);
		mTextView.setText("加载失败,下拉刷新");
	}

	public void setLoadingSucsess() {
		try {
			if (mListView != null && mListView.getFooterViewsCount() == 0) {
				mProgressBar.setVisibility(View.VISIBLE);
				mTextView.setText("加载更多");
				mListView.addFooterView(mListViewFooter, null, false);
			} else {
				mProgressBar.setVisibility(View.VISIBLE);
				mTextView.setText("加载更多");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setRefreshing(false);
		isLoading = false;
		moreData = true;
		// try {
		// mListView.removeFooterView(mListViewFooter);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		mYDown = 0;
		mLastY = 0;
	}

	public void setStartLoading() {
		this.moreData = true;
		this.isLoading = true;
		try {
			if (mListView.getFooterViewsCount() == 0) {
				mListView.addFooterView(mListViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setRefreshing(false);
		mProgressBar.setVisibility(View.VISIBLE);
		mTextView.setText("加载更多");
	}

	public void initFooter() {
		isLoading = false;
		this.moreData = true;
		if (mListView == null) {
			getListView();
		}
		try {
			if (mListView.getFooterViewsCount() == 0) {
				mListView.addFooterView(mListViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mProgressBar.setVisibility(View.VISIBLE);
		mTextView.setText("加载更多");
		try {
			mListView.removeFooterView(mListViewFooter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMoreDataPG() {
		this.moreData = true;
		if (mProgressBar != null) {
			mProgressBar.setVisibility(View.GONE);
		}
		if (mTextView != null) {
			mTextView.setText("加载更多");
		}
		if (mListView == null) {
			getListView();
		}
		try {
			if (mListView.getFooterViewsCount() == 0) {
				mListView.addFooterView(mListViewFooter, null, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}