/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：MyInfoFragment.java
 * 内容摘要：MyInfoFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-22 下午12:58:18
 * 修改记录：
 * 修改日期：2015-12-22 下午12:58:18
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment;

import android.app.Activity;
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
import com.xh.shopping.ui.activity.ChangePasswordActivity;
import com.xh.shopping.util.DialogUtil;
import com.xh.shopping.view.CircleImageView;

/**
 * @filename 文件名称：MyInfoFragment.java
 * @contents 内容摘要：
 */
public class MyInfoFragment extends Fragment implements OnClickListener {
	private Activity activity;
	private View parent;

	private RelativeLayout info_logo_r;
	private CircleImageView info_logo_iv;

	private RelativeLayout info_nname_r;
	private TextView info_nname_tv;
	private RelativeLayout info_cpwd_r;
	private TextView info_phone_tv, info_name_tv, create_time_tv;
	private TextView info_exit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_myinfo, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		parent = getView();

		findView();
		setListener();
	}

	// info_logo_r;
	// info_logo_iv;
	// info_nickname_r;
	// info_nickname_tv;
	// info_cpwd_r;
	// info_phone_tv
	// info_name_tv
	// create_time_tv
	// info_exit;

	private void findView() {

		info_logo_r = (RelativeLayout) parent.findViewById(R.id.info_logo_r);
		info_logo_iv = (CircleImageView) parent.findViewById(R.id.info_logo_iv);

		info_nname_r = (RelativeLayout) parent
				.findViewById(R.id.info_nickname_r);
		info_nname_tv = (TextView) parent.findViewById(R.id.info_nickname_tv);

		info_cpwd_r = (RelativeLayout) parent.findViewById(R.id.info_cpwd_r);

		info_phone_tv = (TextView) parent.findViewById(R.id.info_phone_tv);
		info_name_tv = (TextView) parent.findViewById(R.id.info_name_tv);
		create_time_tv = (TextView) parent.findViewById(R.id.create_time_tv);

		info_exit = (TextView) parent.findViewById(R.id.info_exit);
	}

	private void setListener() {
		info_logo_r.setOnClickListener(this);
		info_nname_r.setOnClickListener(this);
		info_cpwd_r.setOnClickListener(this);
		info_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.info_logo_r:

			break;
		case R.id.info_nickname_r:

			break;
		case R.id.info_cpwd_r:
			Intent intent = new Intent(activity, ChangePasswordActivity.class);
			startActivity(intent);
			break;
		case R.id.info_exit:
			DialogUtil.exitDialog(activity);
			break;
		default:
			break;
		}
	}

}
