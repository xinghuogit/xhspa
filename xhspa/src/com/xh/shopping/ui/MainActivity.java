package com.xh.shopping.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.xh.shopping.R;
import com.xh.shopping.setting.SettingHelper;
import com.xh.shopping.ui.fragment.activity.RegistActivity;
import com.xh.shopping.util.UIHelper;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SettingHelper.getInstance().setApplicationContext(MainActivity.this);
		SettingHelper.getInstance().setCurrentActivity(MainActivity.this);
		UIHelper.getInstance().setSystemBar();
		findViewById(R.id.start).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,
						RegistActivity.class));
			}
		});
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, TextActivity.class));
			}
		});

	}

}
