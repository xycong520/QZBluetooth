package com.qz.qizhi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/4/15.
 */
public class GrainSpaceActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grainspace);
        setTitle();
        init();
    }

    private void init() {
        findViewById(R.id.tvOpenInfo).setOnClickListener(this);
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.tvTitle)).setText("余米");

    }


    boolean isOpen;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvOpenInfo:
                isOpen =!isOpen;
                if (isOpen){
                    findViewById(R.id.tvInfo).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.tvInfo).setVisibility(View.GONE);
                }
                break;
        }
    }
}
