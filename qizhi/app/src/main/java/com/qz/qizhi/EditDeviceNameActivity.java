package com.qz.qizhi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qz.qizhi.kevin.app.App;
import com.qz.qizhi.kevin.params.BLEDevice;
import com.qz.qizhi.kevin.service.RFStarBLEService;
import com.qz.qizhi.kevin.view.ToastUntil;

/**
 * Created by xycong on 2016/3/31.
 */
public class EditDeviceNameActivity extends Activity implements View.OnClickListener, BLEDevice.RFStarBLEBroadcastReceiver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_devicename);
        setTitle();
        init();
    }

    EditText etDeviceName;

    private void init() {
        etDeviceName = (EditText) findViewById(R.id.etDeviceName);
        etDeviceName.setHint(getIntent().getStringExtra("deviceName"));
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("修改名称");
        Button btRight = (Button) findViewById(R.id.btRight);
        btRight.setText("确认");
        btRight.setOnClickListener(this);
        btRight.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btRight:
                if (!etDeviceName.getText().toString().equals("")) {
                    //重命名
                    App.showDialog(this);
                    writeData("ff91", etDeviceName.getText().toString().getBytes());
                }
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        //读取设备名称
                        if (App.manager.cubicBLEDevice != null) {
                            App.manager.cubicBLEDevice.readValue("ff90",  "ff91");
                        }
                    }
                }, 1000);
                break;
        }
    }

    private void writeData(String type, byte[] data) {
        if (App.manager.cubicBLEDevice != null) {
            App.manager.cubicBLEDevice.writeValue("ff90", type, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.manager.cubicBLEDevice != null) {
            App.manager.cubicBLEDevice.setBLEBroadcastDelegate(this);
            // 读取系统功能使能开关
//            App.manager.cubicBLEDevice.readValue("ff90",  "ff9A");
        }
    }


    @Override
    public void onReceive(Context context, Intent intent, String macData, String uuid) {
        App.dismiss();
        if (uuid != null) {
            byte[] data = intent.getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
            // Log.d(App.TAG, "fff0 service  recevice " + new String(data));
            if (uuid.contains("ff91")) {
                etDeviceName.setText(new String(data));
                ToastUntil.makeText(EditDeviceNameActivity.this, "重命名成功", Toast.LENGTH_SHORT);
                setResult(Activity.RESULT_OK, new Intent().putExtra("deviceName", etDeviceName.getText().toString()));
                finish();
            }
        } else {
            ToastUntil.makeText(EditDeviceNameActivity.this, "回调uuid为null", Toast.LENGTH_SHORT);
        }

    }
}
