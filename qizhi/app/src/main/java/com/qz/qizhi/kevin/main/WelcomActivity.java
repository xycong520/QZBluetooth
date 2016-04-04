package com.qz.qizhi.kevin.main;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.qz.qizhi.kevin.app.Res;

/**
 * 欢迎界面
 * 
 * @author kevin
 * 
 */
public class WelcomActivity extends BaseActivity {
	Handler handler = new Handler();
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(Res.layout.welcom_activity);
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomActivity.this,
						MainActivity.class);
				startActivity(intent);
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						finish();
					}
				}, 500);
			}
		}, 2000);
	}
}
