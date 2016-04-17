package com.qz.qizhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by xycong on 2016/4/16.
 */
public class SaveGrainActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_savegrain);
        setTitle();
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.tvTitle)).setText("存米");
        Button btAdd = (Button) findViewById(R.id.btRight);
        btAdd.setText("添加");
        btAdd.setVisibility(View.VISIBLE);
        btAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btRight:
                startActivity(new Intent(this,AddGrainTypeActivity.class));
                break;
        }
    }
}
