/*************************************************************************************************
 * 版权所有 (C)2015,  
 * 
 * 文件名称：RegistFragment.java
 * 内容摘要：RegistFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙 1605651971@qq.com
 * 完成日期：2015-11-3 下午10:13:36
 * 修改记录：
 * 修改日期：2015-11-3 下午10:13:36
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xh.shopping.R;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.ui.fragment.activity.RegistActivity;
import com.xh.shopping.util.UIHelper;

/**
 * @FileName 文件名称：RegistFragment.java
 * @Contents 内容摘要：注册Fragment
 */
public class RegistFragment extends Fragment implements OnClickListener {
	private RegistActivity activity;
	private View parent;

	private LinearLayout linearLayout;
	private RelativeLayout relativeLayout;
	private ImageView layout_head_left_iv, layout_head_centre_iv,
			layout_head_right_iv;
	private TextView layout_head_left_tv, layout_head_centre_tv,
			layout_head_right_tv;

	private ImageView regist_indicator1, regist_indicator2, regist_indicator3;
	private String indicator;

	private LinearLayout regist_input_phone;
	private ImageView regist_phone_logo;
	private EditText regist_phone;
	private Button regist_regist;
	private TextView regist_agreement;

	private LinearLayout regist_input_authcode;
	private EditText regist_authcode;
	private Button regist_confirm;

	private LinearLayout regist_nicknamepwd;
	private EditText regist_nickname, regist_pwd, regist_confirmpwd;
	private ImageView regist_pwd_click, regist_confirmpwd_click;
	private Button regist_confirmregist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_regist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (RegistActivity) getActivity();
		parent = getView();
		SettingHelper.getInstance().setCurrentActivity(activity);
		UIHelper.getInstance().setSystemBar();
		findView();
		setListener();
	}

	@SuppressLint("InflateParams")
	private void findView() {
		linearLayout = (LinearLayout) parent.findViewById(R.id.layout_head_l);
		relativeLayout = (RelativeLayout) LayoutInflater.from(activity)
				.inflate(R.layout.layout_head, null, false);
		linearLayout.removeAllViews();
		linearLayout.addView(relativeLayout);

		layout_head_left_iv = (ImageView) parent
				.findViewById(R.id.layout_head_left_iv);
		layout_head_centre_iv = (ImageView) parent
				.findViewById(R.id.layout_head_centre_iv);
		layout_head_centre_iv = (ImageView) parent
				.findViewById(R.id.layout_head_centre_iv);

		layout_head_left_tv = (TextView) parent
				.findViewById(R.id.layout_head_left_tv);
		layout_head_centre_tv = (TextView) parent
				.findViewById(R.id.layout_head_centre_tv);
		layout_head_right_tv = (TextView) parent
				.findViewById(R.id.layout_head_right_tv);

		layout_head_left_iv.setImageResource(R.drawable.back_wihte);
		layout_head_left_iv.setVisibility(View.VISIBLE);
		parent.findViewById(R.id.layout_head_centre_l);

		layout_head_centre_tv.setText("手机注册");
		layout_head_centre_tv.setVisibility(View.VISIBLE);

		// regist_indicator1
		// regist_indicator2
		// regist_indicator3
		// regist_input_phone
		// regist_phone_logo
		// regist_phone
		// regist_regist
		// regist_agreement
		// regist_input_authcode
		// regist_authcode
		// regist_confirm
		// regist_nicknamepwd
		// regist_nickname
		// regist_pwd
		// regist_confirmpwd
		// regist_pwd_click
		// regist_confirmpwd_click
		// regist_confirmregist

		regist_indicator1 = (ImageView) parent
				.findViewById(R.id.regist_indicator1);
		regist_indicator2 = (ImageView) parent
				.findViewById(R.id.regist_indicator2);
		regist_indicator3 = (ImageView) parent
				.findViewById(R.id.regist_indicator3);

		regist_input_phone = (LinearLayout) parent
				.findViewById(R.id.regist_input_phone);
		regist_phone_logo = (ImageView) parent
				.findViewById(R.id.regist_phone_logo);
		regist_phone = (EditText) parent.findViewById(R.id.regist_phone);
		regist_regist = (Button) parent.findViewById(R.id.regist_regist);

		regist_agreement = (TextView) parent
				.findViewById(R.id.regist_agreement);

		regist_input_authcode = (LinearLayout) parent
				.findViewById(R.id.regist_input_authcode);
		regist_authcode = (EditText) parent.findViewById(R.id.regist_authcode);
		regist_confirm = (Button) parent.findViewById(R.id.regist_confirm);

		regist_nicknamepwd = (LinearLayout) parent
				.findViewById(R.id.regist_nicknamepwd);
		regist_nickname = (EditText) parent.findViewById(R.id.regist_nickname);
		regist_pwd = (EditText) parent.findViewById(R.id.regist_pwd);
		regist_confirmpwd = (EditText) parent
				.findViewById(R.id.regist_confirmpwd);
		regist_pwd_click = (ImageView) parent
				.findViewById(R.id.regist_pwd_click);
		regist_confirmpwd_click = (ImageView) parent
				.findViewById(R.id.regist_confirmpwd_click);
		regist_confirmregist = (Button) parent
				.findViewById(R.id.regist_confirmregist);
	}

	private void setListener() {
		regist_phone_logo.setOnClickListener(this);
		regist_regist.setOnClickListener(this);
		regist_agreement.setOnClickListener(this);

		regist_confirm.setOnClickListener(this);

		regist_pwd_click.setOnClickListener(this);
		regist_confirmpwd_click.setOnClickListener(this);
		regist_confirmregist.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_head_centre_l:
			activity.onBackPressed();
			break;
		case R.id.regist_phone_logo:

			break;
		case R.id.regist_regist:
			
			break;
		case R.id.regist_agreement:
			break;
		case R.id.regist_confirm:
			break;
		case R.id.regist_pwd_click:
			break;
		case R.id.regist_confirmpwd_click:
			break;
		case R.id.regist_confirmregist:
			break;
		default:
			break;
		}
	}
}
