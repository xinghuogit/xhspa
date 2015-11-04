package com.xh.shopping.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.xh.shopping.R;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.ui.activity.RegistActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SettingHelper.getInstance().setApplicationContext(MainActivity.this);
		SettingHelper.getInstance().setCurrentActivity(MainActivity.this);
		startActivity(new Intent(MainActivity.this, RegistActivity.class));
	}

}
