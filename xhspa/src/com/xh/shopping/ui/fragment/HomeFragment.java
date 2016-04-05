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

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.xh.shopping.R;
import com.xh.shopping.constant.Constant;
import com.xh.shopping.model.User;
import com.xh.shopping.serve.JSONDataServiceImpl1;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.ui.MainActivity;
import com.xh.shopping.ui.activity.ChangePasswordActivity;
import com.xh.shopping.ui.activity.TestRegistActivity;
import com.xh.shopping.util.ToastUtil;
import com.xh.shopping.util.UIHelper;

/**
 * @filename 文件名称：HomeFragment.java
 * @contents 内容摘要：首页界面Fragment
 */
public class HomeFragment extends Fragment {
	private MainActivity activity;
	private View parent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = (MainActivity) getActivity();
		parent = getView();

		findView();
	}

	private void findView() {
		parent.findViewById(R.id.home_regist).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// startActivity(new Intent(activity,
						// MyInfoActivity.class));
						startActivity(new Intent(activity,
								TestRegistActivity.class));
					}
				});
		parent.findViewById(R.id.test).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// startActivity(new Intent(activity,
						// MyInfoActivity.class));
						JSONDataServiceImpl1 service = new JSONDataServiceImpl1(
"http://www.people.com.cn/", null,
								handlerchange, true);
						service.start();
					}
				});
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
					ToastUtil.makeToast(activity, "返回数据异常，请重新登录");
					return;
				}
				System.out.println("修改密码成功");
				ToastUtil.makeToast(activity, "修改密码成功");
				System.out
						.println((SettingHelper.getInstance().getUserInfo() == null)
								+ "");
				UIHelper.dismissProgressDialog();
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
