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

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;

import com.xh.shopping.R;
import com.xh.shopping.constant.Constant;
import com.xh.shopping.model.User;
import com.xh.shopping.serve.JSONDataService;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.ui.activity.TestRegistActivity;
import com.xh.shopping.util.NetworkUtil;
import com.xh.shopping.util.ToastUtil;
import com.xh.shopping.util.UIHelper;

/**
 * @filename 文件名称：LoginFragment.java
 * @contents 内容摘要：
 */
public class LoginFragment extends Fragment implements OnClickListener {
	private Activity activity;
	private View parent;

	private EditText login_account;
	private EditText login_pwd;

	private Button login_login;

	private TextView login_regist, login_lostpwd;

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
		activity = getActivity();
		parent = getView();

		findView();
		setListener();
	}

	private void findView() {
		login_account = (EditText) parent.findViewById(R.id.login_account);
		login_pwd = (EditText) parent.findViewById(R.id.login_pwd);

		login_login = (Button) parent.findViewById(R.id.login_login);

		login_regist = (TextView) parent.findViewById(R.id.login_regist);
		login_lostpwd = (TextView) parent.findViewById(R.id.login_lostpwd);
	}

	private void setListener() {
		login_login.setOnClickListener(this);
		login_regist.setOnClickListener(this);
		login_lostpwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_login:
			startLoginService();
			break;
		case R.id.login_regist:
			Intent intent = new Intent(activity, TestRegistActivity.class);
			startActivityForResult(intent, 102);
			break;
		default:
			break;
		}
	}

	protected void startLoginService() {
		if (UIHelper.isEdittextHasData(login_account)) {
			ToastUtil.makeToast(activity, "账号不可为空");
			return;
		}
		if (UIHelper.isEdittextHasData(login_pwd)) {
			ToastUtil.makeToast(activity, "密码不可为空");
			return;
		}
		if (!NetworkUtil.isNetworkAvailable()) {
			ToastUtil.makeToast(activity, R.string.not_network);
			return;
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", login_account.getText().toString().trim());
		map.put("password", login_pwd.getText().toString().trim());
		UIHelper.showProgressDialog(activity, R.string.login_centre);
		JSONDataService service = new JSONDataService(
				Constant.getService(Constant.API_LOGIN), map, handlerlog, false);
		service.start();
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode!=102) {
			return;
		}
		if (resultCode==activity.RESULT_OK) {
			activity.setResult(activity.RESULT_OK);
			activity.finish();
		}
	
	}

	private Handler handlerlog = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int arg = msg.arg1;

			switch (arg) {
			case 0:
				System.out.println("服务器连接失败");
				UIHelper.dismissProgressDialog();
				break;
			case 1:
				JSONObject json = (JSONObject) msg.obj;
				User user = new User();
				user.parseJSON(json);
				if (SettingHelper.getInstance().getUserInfo() == null) {
					System.out.println("登录异常，请重新登录");
					ToastUtil.makeToast(activity, "登录异常，请重新登录");
					return;
				}
				System.out.println("登录成功");
				ToastUtil.makeToast(activity, "登录成功");
				System.out
						.println((SettingHelper.getInstance().getUserInfo() == null)
								+ "");
				UIHelper.dismissProgressDialog();
				activity.setResult(activity.RESULT_OK);
				activity.finish();
				break;
			case 2:
				String msgRet = (String) msg.obj;
				System.out.println("返回失败：" + msgRet);
				ToastUtil.makeToast(activity, msgRet);
				UIHelper.dismissProgressDialog();
				break;
			default:
				break;
			}

		};
	};
}
