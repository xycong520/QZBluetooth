package com.qz.qizhi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xycong on 2016/4/16.
 */
public class AddGrainTypeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addgraintype);
        setTitle();
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.tvTitle)).setText("增加品名");
    }
}
