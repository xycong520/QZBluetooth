package com.qz.qizhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2016/4/21.
 */
public class BindDeviceWaiting extends Activity {

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bind_waiting);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                sendBroadcast(new Intent().setAction("addGrain"));
                finish();
            }
        };
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.sendMessage(new Message());
            }
        }, 5000);
    }

}
