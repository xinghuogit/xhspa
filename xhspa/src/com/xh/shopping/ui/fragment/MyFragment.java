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
import android.widget.TextView;

import com.xh.shopping.R;
import com.xh.shopping.model.User;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.ui.MainActivity;
import com.xh.shopping.ui.activity.TestRegistActivity;
import com.xh.shopping.ui.fragment.activity.LoginActivity;
import com.xh.shopping.ui.fragment.activity.MyInfoActivity;
import com.xh.shopping.util.UIHelper;
import com.xh.shopping.view.CircleImageView;

/**
 * @filename 文件名称：MyFragment.java
 * @contents 内容摘要：我的Fragment
 */
public class MyFragment extends Fragment implements OnClickListener {
	private MainActivity activity;
	private View parent;

	private RelativeLayout my_notuser;// 未登录top

	private RelativeLayout my_user;// 登录top

	private CircleImageView my_logo;
	private TextView my_name, my_vip, my_manage;
	private TextView my_attention1, my_attention2, my_browser;

	private RelativeLayout my_order;

	private TextView payment, receipt, evaluation, salesafter;

	private User user;

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
		setData();
	}

	// private CircleImageView my_logo;
	// private TextView my_name, my_vip, my_manage;
	// private TextView my_attention1, my_attention2, my_browser
	// private RelativeLayout my_order;
	//
	// private TextView my_waiting_payment, my_waiting_goods,
	// my_waiting_evaluation, my_salesafter;
	private void findView() {
		my_notuser = (RelativeLayout) parent.findViewById(R.id.my_notuser);

		my_user = (RelativeLayout) parent.findViewById(R.id.my_user);

		my_logo = (CircleImageView) parent.findViewById(R.id.my_logo);

		my_name = (TextView) parent.findViewById(R.id.my_name);
		my_vip = (TextView) parent.findViewById(R.id.my_vip);
		my_manage = (TextView) parent.findViewById(R.id.my_manage);

		my_attention1 = (TextView) parent.findViewById(R.id.my_attention1);
		my_attention2 = (TextView) parent.findViewById(R.id.my_attention2);
		my_browser = (TextView) parent.findViewById(R.id.my_browser);

		my_order = (RelativeLayout) parent.findViewById(R.id.my_order);

		payment = (TextView) parent.findViewById(R.id.payment);
		receipt = (TextView) parent.findViewById(R.id.receipt);
		evaluation = (TextView) parent.findViewById(R.id.evaluation);
		salesafter = (TextView) parent.findViewById(R.id.salesafter);

	}

	private void setData() {
		user = SettingHelper.getInstance().getUserInfo();
		if (user == null) {
			my_user.setVisibility(View.GONE);
			my_notuser.setVisibility(View.VISIBLE);
		} else {
			my_user.setVisibility(View.VISIBLE);
			my_notuser.setVisibility(View.GONE);

			my_name.setText(user.getNickname());
			my_attention1.setText(user.getNickname());

			my_attention1.setText("0\n关注的商品");
			my_attention2.setText("0\n关注的店铺");
			my_browser.setText("0\n浏览的商品");
		}
	}

	private void setListener() {
		my_notuser.setOnClickListener(this);
		my_user.setOnClickListener(this);

		my_attention1.setOnClickListener(this);
		my_attention2.setOnClickListener(this);
		my_browser.setOnClickListener(this);

		my_order.setOnClickListener(this);
		payment.setOnClickListener(this);
		receipt.setOnClickListener(this);
		evaluation.setOnClickListener(this);
		salesafter.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (user == null) {
			Intent intent = new Intent(activity, LoginActivity.class);
			startActivityForResult(intent, 101);
			return;
		}

		switch (v.getId()) {
		// case R.id.my_notuser:
		// break;
		case R.id.my_user:
			Intent intent = new Intent(activity, MyInfoActivity.class);
			startActivityForResult(intent, 101);
			break;
		case R.id.my_attention1:
			break;
		case R.id.my_attention2:
			break;
		case R.id.my_browser:
			break;
		case R.id.my_order:
			UIHelper.showProgressDialog(activity, "测试");
			break;
		case R.id.payment:
			break;
		case R.id.receipt:
			break;
		case R.id.evaluation:
			break;
		case R.id.salesafter:
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// System.out.println("onActivityResult1:" + activity.RESULT_OK);
		if (requestCode != 101) {
			return;
		}

		if (resultCode == activity.RESULT_OK) {
			// System.out.println("onActivityResult:" + activity.RESULT_OK);
			setData();
		}

	}

}
