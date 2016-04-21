package com.qz.qizhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qz.qizhi.kevin.app.App;
import com.qz.qizhi.widget.DateSelectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xycong on 2016/4/16.
 */
public class SaveGrainActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_savegrain);
        setTitle();
        init();
    }

    private void init() {
        findViewById(R.id.ivType).setOnClickListener(this);
        findViewById(R.id.ivWeight).setOnClickListener(this);
        findViewById(R.id.ivBox).setOnClickListener(this);
        findViewById(R.id.tvDate).setOnClickListener(this);
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("存米");
        Button btAdd = (Button) findViewById(R.id.btRight);
        btAdd.setText("添加");
        btAdd.setVisibility(View.VISIBLE);
        btAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btRight:
                startActivity(new Intent(this, AddGrainTypeActivity.class));
                break;
            case R.id.ivType:
                List<String> ls = new ArrayList<>();
                ls.add("福临门 籼优1号");
                ls.add("金龙鱼");
                ls.add("青橄榄 月芽米");
                ls.add("稻花香");
                App.showPw(v, (TextView) findViewById(R.id.tvType), ls);
                break;
            case R.id.ivWeight:
                List<String> ls2 = new ArrayList<>();
                ls2.add("5kg");
                ls2.add("4kg");
                ls2.add("3kg");
                ls2.add("2kg");
                App.showPw(v, (TextView) findViewById(R.id.tvType), ls2);
                break;
            case R.id.ivBox:
                List<String> ls3 = new ArrayList<>();
                ls3.add("①舱");
                ls3.add("②舱");
                App.showPw(v, (TextView) findViewById(R.id.tvType), ls3);
                break;
            case R.id.tvDate:
                new DateSelectView(this, (TextView) findViewById(R.id.tvDate),v).showDatePw();
                break;
            case R.id.tvSaveTime:
                new DateSelectView(this, (TextView) findViewById(R.id.tvSaveTime),v).showDatePw();
                break;
        }
    }
}
