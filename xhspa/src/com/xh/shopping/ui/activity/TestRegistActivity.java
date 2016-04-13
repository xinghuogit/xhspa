/*************************************************************************************************
 * 版权所有 (C)2015,  
 * 
 * 文件名称：RegistActivity1.java
 * 内容摘要：RegistActivity1.java
 * 当前版本：TODO
 * 作        者：李加蒙
 * 完成日期：2015-11-3 下午10:13:15
 * 修改记录：
 * 修改日期：2015-11-3 下午10:13:15
 * 版   本 号：
 * 修   改 人：
 * 修改内容：
 ************************************************************************************************/
package com.xh.shopping.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xh.shopping.R;
import com.xh.shopping.adapter.GridImagesAdapter;
import com.xh.shopping.constant.Constant;
import com.xh.shopping.model.User;
import com.xh.shopping.serve.DataService;
import com.xh.shopping.serve.DataServiceDelegate;
import com.xh.shopping.serve.JSONDataServiceImpl1;
import com.xh.shopping.serve.extend.TestUploadService;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.ui.UploadPhotoActivity1;
import com.xh.shopping.util.BitmapUtil;
import com.xh.shopping.util.FilePartUtil;
import com.xh.shopping.util.NetworkUtil;
import com.xh.shopping.util.PartUtil;
import com.xh.shopping.util.StringPartUtil;
import com.xh.shopping.util.ToastUtil;
import com.xh.shopping.util.UIHelper;
import com.xh.shopping.view.GridViewForScrollView;

/**
 * @FileName 文件名称：RegistActivity1.java
 * @Contents 内容摘要： 注册Activity
 */
@SuppressLint("HandlerLeak")
public class TestRegistActivity extends Activity implements DataServiceDelegate {
	private EditText user, psw1, psw2;

	private Button button;

	private List<String> images = new ArrayList<String>();

	private GridViewForScrollView addimg;

	private GridImagesAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		findView();
	}

	private void findView() {
		addimg = (GridViewForScrollView) findViewById(R.id.addimg);
		adapter = new GridImagesAdapter(this);
		addimg.setAdapter(adapter);

		adapter.setData(BitmapUtil.tempSelectBitmap);
		adapter.notifyDataSetChanged();

		addimg.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				UIHelper.closeInput(TestRegistActivity.this);
				if (position == BitmapUtil.tempSelectBitmap.size()) {
					// showPopwindow();
					UIHelper.showImage(TestRegistActivity.this, true, false,
							true, false);
				} else {
					// Intent intent = new Intent(TestRegistActivity.this,
					// GalleryActivity.class);
					// intent.putExtra("showAlbum", false);
					// intent.putExtra("position", "1");
					// intent.putExtra("ID", arg2);
					// startActivity(intent);
				}
			}
		});

		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<PartUtil> list = new ArrayList<PartUtil>();
				list.add(new StringPartUtil("user_id", "7"));
				list.add(new StringPartUtil("amount", "666.66"));
				list.add(new StringPartUtil("financeclass_id", "26"));
				list.add(new StringPartUtil("content", "你的刚刚好"));
				list.add(new StringPartUtil("paytime", "2016-03-30 15:43"));
				list.add(new StringPartUtil("project", "测试1"));
				for (int i = 0; i < BitmapUtil.tempSelectBitmap.size(); i++) {
					list.add(new FilePartUtil("image" + i,
							BitmapUtil.tempSelectBitmap.get(i)));
				}

				TestUploadService service = new TestUploadService();
				service.setAuth(true);
				service.setCachingEnabled(false);
				service.setDataService(TestRegistActivity.this);
				service.setList(list);
				service.start();
			}
		});

		user = (EditText) findViewById(R.id.user);
		psw1 = (EditText) findViewById(R.id.psw1);
		psw2 = (EditText) findViewById(R.id.psw2);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (UIHelper.isEdittextHasData(user)) {
					ToastUtil.makeToast(TestRegistActivity.this, "账号不可为空");
					return;
				}
				if (UIHelper.isEdittextHasData(psw1)) {
					ToastUtil.makeToast(TestRegistActivity.this, "密码不可为空");
					return;
				}
				if (UIHelper.isEdittextHasData(psw2)) {
					ToastUtil.makeToast(TestRegistActivity.this, "确认密码不可为空");
					return;
				}
				if (!psw1.getText().toString().trim()
						.equals(psw2.getText().toString().trim())) {
					ToastUtil.makeToast(TestRegistActivity.this, "密码和确认密码不同");
					return;
				}

				if (!NetworkUtil.isNetworkAvailable()) {
					ToastUtil.makeToast(TestRegistActivity.this,
							R.string.not_network);
					return;
				}

				Map<String, String> map = new HashMap<String, String>();
				map.put("username", user.getText().toString().trim());
				map.put("password", psw1.getText().toString().trim());
				map.put("password2", psw2.getText().toString().trim());
				map.put("phone", user.getText().toString().trim());

				UIHelper.showProgressDialog(TestRegistActivity.this,
						R.string.regist_centre, false);
				JSONDataServiceImpl1 service = new JSONDataServiceImpl1(
						Constant.getService(Constant.API_REGISTER), map,
						handlerregist, false);
				service.start();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		adapter.setData(BitmapUtil.tempSelectBitmap);
		if (BitmapUtil.tempSelectBitmap.size() < BitmapUtil.num
				&& resultCode == RESULT_OK) {
			for (int i = 0; i < UploadPhotoActivity1.imagePathList.size(); i++) {
				BitmapUtil.tempSelectBitmap
						.add(UploadPhotoActivity1.imagePathList.get(i));
				runOnUiThread(new Runnable() {
					public void run() {
						adapter.setData(BitmapUtil.tempSelectBitmap);
						adapter.notifyDataSetChanged();
					}
				});
			}
			UploadPhotoActivity1.imagePathList.clear();
		}

	}

	protected void setLogin() {
		if (UIHelper.isEdittextHasData(user)) {
			ToastUtil.makeToast(TestRegistActivity.this, "账号不可为空");
			return;
		}
		if (UIHelper.isEdittextHasData(psw1)) {
			ToastUtil.makeToast(TestRegistActivity.this, "密码不可为空");
			return;
		}
		if (!NetworkUtil.isNetworkAvailable()) {
			ToastUtil.makeToast(TestRegistActivity.this, R.string.not_network);
			return;
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", user.getText().toString().trim());
		map.put("password", psw1.getText().toString().trim());
		JSONDataServiceImpl1 service = new JSONDataServiceImpl1(
				Constant.getService(Constant.API_LOGIN), map, handlerlog, false);
		service.start();
	}

	private Handler handlerregist = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int arg = msg.arg1;

			switch (arg) {
			case 0:
				System.out.println("服务器连接失败");
				UIHelper.dismissProgressDialog();
				break;
			case 1:
				ToastUtil.makeToast(TestRegistActivity.this,
						R.string.regist_success);
				System.out.println("注册成功,登录中...");
				setLogin();
				// UIHelper.dismissProgressDialog();
				break;
			case 2:
				String msgRet = (String) msg.obj;
				System.out.println("返回失败：" + msgRet);
				ToastUtil.makeToast(TestRegistActivity.this, msgRet);
				UIHelper.dismissProgressDialog();
				break;
			default:
				break;
			}

		};
	};

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
				System.out.println("登录成功");
				ToastUtil.makeToast(TestRegistActivity.this, "登录成功");
				System.out
						.println((SettingHelper.getInstance().getUserInfo() == null)
								+ "");
				UIHelper.dismissProgressDialog();
				setResult(RESULT_OK);
				TestRegistActivity.this.finish();
				break;
			case 2:
				String msgRet = (String) msg.obj;
				System.out.println("返回失败：" + msgRet);
				ToastUtil.makeToast(TestRegistActivity.this, msgRet);
				UIHelper.dismissProgressDialog();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onServiceSuccess(DataService service, Object object) {

	}

	@Override
	public void onServiceFailure(DataService service, String ret) {

	}

}
