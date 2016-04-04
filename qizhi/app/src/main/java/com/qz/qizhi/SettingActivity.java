package com.qz.qizhi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xycong on 2016/3/28.
 */
public class SettingActivity extends Activity implements View.OnClickListener {
    Context mContext;
    String macAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        mContext = this;

        setTitle();
        init();
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        String title = getIntent().getStringExtra("titleName");
        if (TextUtils.isEmpty(title)) {
            title = "谷箱设置";
        }
        ((TextView) findViewById(R.id.tvTitle)).setText(title);
    }

    private void init() {
        macAddress = getIntent().getStringExtra("mac");
        findViewById(R.id.tvDeviceInfo).setOnClickListener(this);
        findViewById(R.id.tvEnvironmentInfo).setOnClickListener(this);
        findViewById(R.id.tvGrainInfo).setOnClickListener(this);
        findViewById(R.id.tvOutGrain).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.tvDeviceInfo:
                i = new Intent(mContext, DeviceSettingActivity.class);
                i.putExtra("mac", macAddress);
                startActivity(i);
                break;
            case R.id.tvEnvironmentInfo:
                i = new Intent(mContext, EnvironmentSettingActivity.class);
                startActivity(i);
                break;
            case R.id.tvGrainInfo:
                //谷物设置
                break;
            case R.id.tvOutGrain:
                //出谷设置
                break;
        }
    }
}
