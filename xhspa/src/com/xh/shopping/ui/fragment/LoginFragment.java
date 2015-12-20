/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：LoginFragment.java
 * 内容摘要：LoginFragment.java
 * 当前版本：TODO
 * 作        者：李加蒙1605651971@qq.com
 * 完成日期：2015-11-11 下午5:53:52
 * 修改记录：
 * 修改日期：2015-11-11 下午5:53:52
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xh.shopping.R;
import com.xh.shopping.constant.Constant;
import com.xh.shopping.serve.JSONDataService;
import com.xh.shopping.ui.fragment.activity.LoginActivity;
import com.xh.shopping.util.ToastUtil;
import com.xh.shopping.util.UIHelper;

/**
 * @filename 文件名称：LoginFragment.java
 * @contents 内容摘要：
 */
public class LoginFragment extends Fragment {
	private LoginActivity activity;
	private View parent;

	private EditText login_account;
	private EditText login_pwd;

	private Button login_login;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			ToastUtil.makeToast(activity, json);
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (LoginActivity) getActivity();
		parent = getView();

		findView();
	}

	private void findView() {
		login_account = (EditText) parent.findViewById(R.id.login_account);
		login_pwd = (EditText) parent.findViewById(R.id.login_pwd);

		login_login = (Button) parent.findViewById(R.id.login_login);
		login_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startLoginService();
			}
		});
	}

	protected void startLoginService() {
		if (UIHelper.isEdittextHasData(login_account)) {
			ToastUtil.makeToast(activity, R.string.account_notnull);
			return;
		}

		if (UIHelper.isEdittextHasData(login_pwd)) {
			ToastUtil.makeToast(activity, R.string.account_notnull);
			return;
		}

		Map<String, String> map = new HashMap<>();
		map.put("username", login_account.getText().toString().trim());
		map.put("password", login_pwd.getText().toString().trim());

		JSONDataService service = new JSONDataService(
				Constant.getService(Constant.API_LOGIN), map, handler);
		service.start();
	}
}
