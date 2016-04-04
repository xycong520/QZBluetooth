package com.qz.qizhi.kevin.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qz.qizhi.kevin.app.App;
import com.qz.qizhi.kevin.app.Res;
import com.qz.qizhi.kevin.params.BLEDevice;
import com.qz.qizhi.kevin.params.MemberItem;
import com.qz.qizhi.kevin.view.SendDataView;

/**
 * 发送数据的界面
 * 
 * @author kevin
 * 
 */
public class SendDataActivity extends BaseActivity implements
		View.OnClickListener, BLEDevice.RFStarBLEBroadcastReceiver {
	private SendDataView sendView = null;

	private Button resetBtn = null, clearBtn = null, sendBtn = null;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(Res.layout.activity_senddata);
		this.initView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (app.manager.cubicBLEDevice != null) {
			app.manager.cubicBLEDevice.setBLEBroadcastDelegate(this);
		}
	}

	@SuppressWarnings("static-access")
	private void initView() {
		// TODO Auto-generated method stub
		Intent intent = this.getIntent();
		MemberItem member = (MemberItem) intent.getSerializableExtra(App.TAG);
		this.initNavigation(member.name);
		sendView = (SendDataView) this.findViewById(Res.id.sendDataView);
		sendView.setEdit("Hello");
		resetBtn = (Button) this.findViewById(Res.id.resetBtn);
		clearBtn = (Button) this.findViewById(Res.id.clearBtn);
		sendBtn = (Button) this.findViewById(Res.id.sendBtn);
		resetBtn.setOnClickListener(this);
		clearBtn.setOnClickListener(this);
		sendBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == resetBtn) {
			sendView.reset();
		} else if (v == clearBtn) {
			sendView.clear();
		} else if (v == sendBtn) {

			if (app.manager.cubicBLEDevice != null)
				app.manager.cubicBLEDevice.writeValue("ffe5", "ffe9", sendView
						.getEdit().getBytes());
			sendView.setCountTimes();
		}
	}

	@Override
	public void onReceive(Context context, Intent intent, String macData,
			String uuid) {
		// TODO Auto-generated method stub
		this.connectedOrDis(intent.getAction());
	}
}
