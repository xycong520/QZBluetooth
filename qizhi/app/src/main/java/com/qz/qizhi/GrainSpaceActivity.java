package com.qz.qizhi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qz.qizhi.kevin.app.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class GrainSpaceActivity extends Activity implements View.OnClickListener {
    TextView etSC, etSC2;//时长
    String unit = "个月";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grainspace);
        setTitle();
        init();
    }

    private void init() {
        findViewById(R.id.tvOpenInfo).setOnClickListener(this);
        findViewById(R.id.ivYM).setOnClickListener(this);
        findViewById(R.id.ivYM2).setOnClickListener(this);
        findViewById(R.id.btSubSC).setOnClickListener(this);
        findViewById(R.id.btSubSC2).setOnClickListener(this);
        findViewById(R.id.btAddSC2).setOnClickListener(this);
        findViewById(R.id.btAddSC).setOnClickListener(this);
        etSC = (TextView) findViewById(R.id.etSC);
        etSC2 = (TextView) findViewById(R.id.etSC2);
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("余米");

    }


    boolean isOpen;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvOpenInfo:
                isOpen = !isOpen;
                if (isOpen) {
                    findViewById(R.id.tvInfo).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.tvInfo).setVisibility(View.GONE);
                }
                break;
            case R.id.btAddSC:
                App.setTextValue(etSC, true, unit);
                break;
            case R.id.btAddSC2:
                App.setTextValue(etSC2, true, unit);
                break;
            case R.id.btSubSC2:
                App.setTextValue(etSC2, false, unit);
                break;
            case R.id.btSubSC:
                App.setTextValue(etSC, false, unit);
                break;
            case R.id.ivYM:
                List<String> ls2 = new ArrayList<>();
                ls2.add("①档, 约2斤");
                ls2.add("②档, 约4斤");
                ls2.add("③档, 约6斤");
                ls2.add("④档, 约8斤");
                ls2.add("⑤档, 约10斤");
                App.showPw(view, (TextView) findViewById(R.id.etYM), ls2);
                break;
            case R.id.ivYM2:
                ls2 = new ArrayList<>();
                ls2.add("①档, 约2斤");
                ls2.add("②档, 约4斤");
                ls2.add("③档, 约6斤");
                ls2.add("④档, 约8斤");
                ls2.add("⑤档, 约10斤");
                App.showPw(view, (TextView) findViewById(R.id.etYM2), ls2);
                break;
        }
    }


}
