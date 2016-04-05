/*************************************************************************************************
 * 版权所有 (C)2015
 * 
 * 文件名称：ChangePasswordActivity.java
 * 内容摘要：ChangePasswordActivity.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-12-22 下午1:52:02
 * 修改记录：
 * 修改日期：2015-12-22 下午1:52:02
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xh.shopping.R;
import com.xh.shopping.constant.Constant;
import com.xh.shopping.model.User;
import com.xh.shopping.serve.JSONDataServiceImpl;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.util.NetworkUtil;
import com.xh.shopping.util.ToastUtil;
import com.xh.shopping.util.UIHelper;

/**
 * @filename 文件名称：ChangePasswordActivity.java
 * @contents 内容摘要：
 */
@SuppressLint("HandlerLeak")
public class ChangePasswordActivity extends Activity {
	EditText editText, editText1, editText2;
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepwd);

		editText = findView(R.id.editText1);
		editText1 = findView(R.id.editText2);
		editText2 = findView(R.id.editText3);

		button = findView(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startCPsd();
			}
		});
	}

	private void startCPsd() {

		if (UIHelper.isEdittextHasData(editText)) {
			ToastUtil.makeToast(ChangePasswordActivity.this, "原密码不可为空");
			return;
		}

		if (UIHelper.isEdittextHasData(editText1)) {
			ToastUtil.makeToast(ChangePasswordActivity.this, "新密码不可为空");
			return;
		}

		if (UIHelper.isEdittextHasData(editText2)) {
			ToastUtil.makeToast(ChangePasswordActivity.this, "确认密码不可为空");
			return;
		}

		if (!editText1.getText().toString().trim()
				.equals(editText2.getText().toString().trim())) {
			ToastUtil.makeToast(ChangePasswordActivity.this, "新密码和确认密码不同");
			return;
		}

		if (editText.getText().toString().trim()
				.equals(editText1.getText().toString().trim())) {
			ToastUtil.makeToast(ChangePasswordActivity.this, "原密码和新密码相同，不需要修改");
			return;
		}

		if (!NetworkUtil.isNetworkAvailable()) {
			ToastUtil.makeToast(ChangePasswordActivity.this,
					R.string.not_network);
			return;
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("password", editText.getText().toString().trim());
		map.put("password1", editText1.getText().toString().trim());
		map.put("password2", editText2.getText().toString().trim());

		JSONDataServiceImpl service = new JSONDataServiceImpl(
				Constant.getService(Constant.API_CHANGEPASSWORD), map,
				handlerchange, true);
		service.start();
	}

	private Handler handlerchange = new Handler() {
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
					System.out.println("返回数据异常，请重新登录");
					ToastUtil.makeToast(ChangePasswordActivity.this,
							"返回数据异常，请重新登录");
					return;
				}
				System.out.println("修改密码成功");
				ToastUtil.makeToast(ChangePasswordActivity.this, "修改密码成功");
				System.out
						.println((SettingHelper.getInstance().getUserInfo() == null)
								+ "");
				UIHelper.dismissProgressDialog();
				break;
			case 2:
				String msgRet = (String) msg.obj;
				System.out.println("返回失败：" + msgRet);
				ToastUtil.makeToast(ChangePasswordActivity.this, msgRet);
				UIHelper.dismissProgressDialog();
				break;
			default:
				break;
			}

		};
	};

	@SuppressWarnings("unchecked")
	private <T extends View> T findView(int id) {
		return (T) findViewById(id);
	}

}
