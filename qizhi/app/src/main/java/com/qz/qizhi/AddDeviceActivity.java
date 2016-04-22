package com.qz.qizhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2016/4/21.
 */
public class AddDeviceActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_device_add);
        init();
    }

    private void init() {
        findViewById(R.id.ivGrainBox).setOnClickListener(this);
        findViewById(R.id.ivYinshuiji).setOnClickListener(this);
        findViewById(R.id.btCancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivGrainBox:
                startActivity(new Intent(this, BindDeviceActivity.class));
                finish();
                break;
            case R.id.ivYinshuiji:
                startActivity(new Intent(this, BindDeviceActivity.class));
                finish();
                break;
            case R.id.btCancel:
                finish();
                break;
        }
    }
}
