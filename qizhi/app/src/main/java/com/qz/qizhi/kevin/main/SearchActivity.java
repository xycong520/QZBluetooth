package com.qz.qizhi.kevin.main;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.qz.qizhi.Bean.BeanDevice;
import com.qz.qizhi.DBUtils;
import com.qz.qizhi.kevin.adapter.BAdapter;
import com.qz.qizhi.kevin.app.App;
import com.qz.qizhi.kevin.app.AppManager;
import com.qz.qizhi.kevin.app.Res;
import com.qz.qizhi.kevin.params.BLEDevice;
import com.qz.qizhi.kevin.params.CubicBLEDevice;
import com.qz.qizhi.kevin.service.RFStarBLEService;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;

/**
 * 添加和修改member
 * 
 * @author kevin
 * 
 */
public class SearchActivity extends BaseActivity implements
		OnItemClickListener, AppManager.RFStarManageListener, BLEDevice.RFStarBLEBroadcastReceiver {
	private ListView list = null;
	private BAdapter bleAdapter = null;
	private ArrayList<BluetoothDevice> arraySource = null;

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(Res.layout.activity_search);
		this.initView();

		if (app.manager.cubicBLEDevice != null) {
			app.manager.cubicBLEDevice.disconnectedDevice();
			app.manager.cubicBLEDevice = null;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		app.manager.startScanBluetoothDevice();
		app.manager.isEdnabled(this);


	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		arraySource.clear();
		bleAdapter.notifyDataSetChanged();
		app.manager.stopScanBluetoothDevice();
	}

	@SuppressWarnings("static-access")
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(Res.anim.activity_in_left,
				Res.anim.activity_out_bottom);
	}

	@SuppressWarnings("static-access")
	protected void initView() {
		initNavigation("搜索");
		list = (ListView) this.findViewById(Res.id.list);

		list.setOnItemClickListener(this);

		app.manager.setRFstarBLEManagerListener(this);
		arraySource = new ArrayList<BluetoothDevice>();
		bleAdapter = new BAdapter(this, arraySource);

		list.setAdapter(bleAdapter);

	}

	@Override
	protected void initNavigation(String title) {
		// TODO Auto-generated method stub
		super.initNavigation(title);
		navigateView.setRightHideBtn(false);
		navigateView.rightBtn.setText("刷新");
		navigateView.rightBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				app.manager.startScanBluetoothDevice();
			}
		});
		navigateView.setEnable(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// User chose not to enable Bluetooth.
		super.onActivityResult(requestCode, resultCode, data);
		app.manager.onRequestResult(requestCode, resultCode, data);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		app.manager.bluetoothDevice = arraySource.get(arg2);
		app.manager.cubicBLEDevice = new CubicBLEDevice(
				this.getApplicationContext(), app.manager.bluetoothDevice);
		app.manager.cubicBLEDevice.setBLEBroadcastDelegate(SearchActivity.this);
		// this.finish();
	}

	/**
	 * 扫描到的蓝牙设备
	 */
	@Override
	public void RFstarBLEManageListener(BluetoothDevice device, int rssi,
			byte[] scanRecord) {
		// TODO Auto-generated method stub
		Log.d(App.TAG, "scanrecord : " + device.getAddress());// device.getName());
		arraySource.add(device);
		bleAdapter.notifyDataSetChanged();
	}

	@Override
	public void RFstarBLEManageStartScan() {
		// TODO Auto-generated method stub
		// showMessage("开始扫描设备");
		this.arraySource.clear();
	}

	@Override
	public void RFstarBLEManageStopScan() {
		// TODO Auto-generated method stub
		// showMessage("扫描设备结束");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}


	@Override
	public void onReceive(Context context, Intent intent, String macData,
			String uuid) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		this.connectedOrDis(action);
		if (RFStarBLEService.ACTION_GATT_CONNECTED.equals(action)) {
			Log.d(App.TAG, "111111111 连接完成 " + App.manager.cubicBLEDevice.deviceMac);
			BeanDevice device = new BeanDevice();
			device.setDeviceName(String.valueOf(App.manager.cubicBLEDevice.deviceName));
			device.setMacAddress(String.valueOf(App.manager.cubicBLEDevice.deviceMac));
			try {
				DbManager db = new DBUtils().getDB();
				db.delete(BeanDevice.class,WhereBuilder.b("macAddress","=",device.getMacAddress()));
				new DBUtils().getDB().save(device);
			} catch (DbException e) {
				e.printStackTrace();
			}
			setResult(RESULT_OK);
		} else if (RFStarBLEService.ACTION_GATT_DISCONNECTED.equals(action)) {
			Log.d(App.TAG, "111111111 连接断开");
		} else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED
				.equals(action)) {
			this.finish();
		}
	}
}
