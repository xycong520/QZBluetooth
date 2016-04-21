package com.qz.qizhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2016/4/21.
 */
public class BindDeviceActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_device_bind);
        init();

    }

    private void init() {
        findViewById(R.id.btNext).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, BindDeviceWaiting.class));
        finish();
    }
}
