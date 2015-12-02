package com.xh.shopping.ui;

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
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xh.shopping.R;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.ui.fragment.CartFragment;
import com.xh.shopping.ui.fragment.CategoryFragment;
import com.xh.shopping.ui.fragment.HomeFragment;
import com.xh.shopping.ui.fragment.MyFragment;
import com.xh.shopping.util.DeviceUtil;
import com.xh.shopping.util.UIHelper;

/**
 * @filename 文件名称：MainActivity.java
 * @contents 内容摘要：首页界面MainActivity
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	private LinearLayout linearLayout;
	private RelativeLayout relativeLayout;
	private ImageView layout_head_left_iv, layout_head_centre_iv,
			layout_head_right_iv;
	private TextView layout_head_left_tv, layout_head_centre_tv,
			layout_head_right_tv;

	private TextView buttom_tv_home, buttom_tv_category, buttom_tv_cart,
			buttom_tv_my;
	private View currentView;

	private HomeFragment homeFragment = new HomeFragment();
	private CategoryFragment categoryFragment = new CategoryFragment();
	private CartFragment cartFragment = new CartFragment();
	private MyFragment myFragment = new MyFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 设置ApplicationContext
		SettingHelper.getInstance().setApplicationContext(MainActivity.this);
		// 设置当前Activity
		SettingHelper.getInstance().setCurrentActivity(MainActivity.this);
		// 设置状态栏为自定义颜色
		// UIHelper.getInstance().setSystemBar();
		DeviceUtil.getInstance().getPhoneInfo();
		findView();
		setListener();
	}

	@SuppressLint("InflateParams")
	private void findView() {
		linearLayout = (LinearLayout) findViewById(R.id.layout_head_l);
		relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.layout_head, null, false);
		linearLayout.removeAllViews();
		linearLayout.addView(relativeLayout);

		layout_head_left_iv = (ImageView) findViewById(R.id.layout_head_left_iv);
		layout_head_centre_iv = (ImageView) findViewById(R.id.layout_head_centre_iv);
		layout_head_centre_iv = (ImageView) findViewById(R.id.layout_head_centre_iv);

		layout_head_left_tv = (TextView) findViewById(R.id.layout_head_left_tv);
		layout_head_centre_tv = (TextView) findViewById(R.id.layout_head_centre_tv);
		layout_head_right_tv = (TextView) findViewById(R.id.layout_head_right_tv);

		layout_head_left_tv.setText("扫一扫");
		layout_head_left_tv.setVisibility(View.VISIBLE);
		layout_head_right_tv.setText("消息");
		layout_head_right_tv.setVisibility(View.VISIBLE);

		// buttom_tv_home
		// buttom_tv_category
		// buttom_tv_cart
		// buttom_tv_my

		buttom_tv_home = (TextView) findViewById(R.id.buttom_tv_home);
		buttom_tv_category = (TextView) findViewById(R.id.buttom_tv_category);
		buttom_tv_cart = (TextView) findViewById(R.id.buttom_tv_cart);
		buttom_tv_my = (TextView) findViewById(R.id.buttom_tv_my);
	}

	private void setListener() {
		buttom_tv_home.setOnClickListener(this);
		buttom_tv_category.setOnClickListener(this);
		buttom_tv_cart.setOnClickListener(this);
		buttom_tv_my.setOnClickListener(this);

		buttom_tv_home.performClick();
	}

	@SuppressLint("Recycle")
	@Override
	public void onClick(View v) {

		if (currentView != null && currentView.equals(v)) {
			return;
		}

		// 创建Fragment管理者
		FragmentManager manager = getSupportFragmentManager();
		// 获取Fragment提交事物
		FragmentTransaction transaction = manager.beginTransaction();

		switch (v.getId()) {
		case R.id.buttom_tv_home:
			setBottom(v);
			transaction.replace(R.id.frame_layout, homeFragment);
			transaction.commit();
			layout_head_centre_tv.setText(R.string.home);
			layout_head_centre_tv.setVisibility(View.VISIBLE);
			break;
		case R.id.buttom_tv_category:
			setBottom(v);
			transaction.replace(R.id.frame_layout, categoryFragment);
			transaction.commit();
			layout_head_centre_tv.setText(R.string.category);
			layout_head_centre_tv.setVisibility(View.VISIBLE);
			break;
		case R.id.buttom_tv_cart:
			setBottom(v);
			transaction.replace(R.id.frame_layout, cartFragment);
			transaction.commit();
			layout_head_centre_tv.setText(R.string.cart);
			layout_head_centre_tv.setVisibility(View.VISIBLE);
			break;
		case R.id.buttom_tv_my:
			setBottom(v);
			transaction.replace(R.id.frame_layout, myFragment);
			transaction.commit();
			layout_head_centre_tv.setText(R.string.my);
			layout_head_centre_tv.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @param v
	 *            设置图标字体状态、改变颜色
	 */
	private void setBottom(View v) {
		// 如果和上次点击的View不同并且不为空、设置上次的选中状态为false；
		if (currentView != null && !currentView.equals(v)) {
			currentView.setSelected(false);
		}
		currentView = v;
		currentView.setSelected(true);
	}

}
