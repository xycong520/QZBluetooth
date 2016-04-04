package com.qz.qizhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qz.qizhi.Bean.BeanDevice;

import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by xycong on 2016/3/30.
 */
public class DeviceSettingActivity extends Activity {
    String macAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_device_setting);
        setTitle();
        init();
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("设备信息");
    }

    String deviceName = "";
    private void init() {
        macAddress = getIntent().getStringExtra("mac");
        SqlInfo sqlInfo = new SqlInfo();
        sqlInfo.setSql("select * from Device where macAddress = '" + macAddress + "' ");
        try {
            List<DbModel> dbModelAll = new DBUtils().getDB().findDbModelAll(sqlInfo);
            if (dbModelAll != null && dbModelAll.size()>0) {
                BeanDevice device = new BeanDevice();
                device.setDeviceName(deviceName = dbModelAll.get(0).getString("deviceName"));
                ((TextView)findViewById(R.id.tvDeviceName)).setText(device.getDeviceName());
                findViewById(R.id.layoutDeviceName).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DeviceSettingActivity.this,EditDeviceNameActivity.class);
//                        Intent intent = new Intent(DeviceSettingActivity.this,ModuleParameterActivity.class);
                        intent.putExtra("deviceName",deviceName);
                        startActivityForResult(intent,RESULT_FIRST_USER);
                    }
                });
            }
        } catch (DbException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            String newName = data.getStringExtra("deviceName");
            try {
                new DBUtils().getDB().execQuery("update Device set deviceName = " + newName+" where macAddress = '"+macAddress+"'");
            } catch (DbException e) {
                e.printStackTrace();
            }
            init();
        }
    }
}
