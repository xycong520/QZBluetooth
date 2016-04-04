package com.qz.qizhi.kevin.main;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qz.qizhi.kevin.app.App;
import com.qz.qizhi.kevin.app.Res;
import com.qz.qizhi.kevin.params.BLEDevice;
import com.qz.qizhi.kevin.params.MemberItem;
import com.qz.qizhi.kevin.service.RFStarBLEService;

/**
 * 
 * @author Kevin.wu E-mail:wh19575782@163.com
 * 
 *         0x180F 2A19提供电量的百分比
 * 
 * 
 */
public class BatteryActivity extends BaseActivity implements
		BLEDevice.RFStarBLEBroadcastReceiver {
	private TextView batteryTxt = null;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(Res.layout.activity_battery);
		this.initView();
	}

	@SuppressWarnings("static-access")
	private void initView() {
		// TODO Auto-generated method stub
		Intent intent = this.getIntent();
		MemberItem member = (MemberItem) intent.getSerializableExtra(App.TAG);
		this.initNavigation(member.name);

		batteryTxt = (TextView) this.findViewById(Res.id.batteryTxt);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (app.manager.cubicBLEDevice != null) {
			app.manager.cubicBLEDevice.setBLEBroadcastDelegate(this);
			app.manager.cubicBLEDevice.readValue("180f", "2a19");
			app.manager.cubicBLEDevice.setNotification("180f", "2a19", true);
		}
	}

	/**
	 * 获取电量
	 * 
	 * @param view
	 */
	public void getBattry(View view) {
		if (app.manager.cubicBLEDevice != null) {
			app.manager.cubicBLEDevice.readValue("180f", "2a19");
		}
	}

	@Override
	public void onReceive(Context context, Intent intent, String macData,
			String uuid) {
		// TODO Auto-generated method stub
		Log.d(App.TAG, "有数据返回");
		// TODO Auto-generated method stub
		String action = intent.getAction();
		this.connectedOrDis(intent.getAction());
		if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(action)) {
			Log.d(App.TAG, "111111111 连接完成");
			this.setTitle(macData + "已连接");

		} else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
			Log.d(App.TAG, "111111111 连接断开");
			this.setTitle(macData + "已断开");

		} else if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(action)) {
			if (uuid.contains("2a19")) {
				byte[] data = intent
						.getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
				batteryTxt.setText((int) data[0] + "%");
			}
		} else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED
				.equals(action)) {
		}
	}
}
