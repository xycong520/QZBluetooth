package com.qz.qizhi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qz.qizhi.adapter.CommonAdapter;
import com.qz.qizhi.adapter.ViewHolder;

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
                showPw(v, (TextView) findViewById(R.id.tvType), ls);
                break;
            case R.id.ivWeight:
                List<String> ls2 = new ArrayList<>();
                ls2.add("5kg");
                ls2.add("4kg");
                ls2.add("3kg");
                ls2.add("2kg");
                showPw(v, (TextView) findViewById(R.id.tvType),ls2);
                break;
            case R.id.ivBox:
                List<String> ls3 = new ArrayList<>();
                ls3.add("①舱");
                ls3.add("②舱");
                showPw(v, (TextView) findViewById(R.id.tvType),ls3);
                break;
        }
    }

    PopupWindow popupWindow;

    private void showPw(View v, final TextView tv, final List<String> grainString) {
        View view = getLayoutInflater().inflate(R.layout.layout_lv, null);
        if (popupWindow == null) {
            popupWindow = new PopupWindow(view, v.getWidth() + tv.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    popupWindow = null;
                }
            });
            ListView listView = (ListView) view.findViewById(R.id.lv);
            listView.setAdapter(new CommonAdapter<String>(this, grainString, R.layout.item_grainsetting) {
                @Override
                public void convert(ViewHolder helper, String item) {
                    helper.setText(R.id.tvName, item);
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    tv.setText(grainString.get(i));
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAsDropDown(v, -tv.getWidth(), 0);
        }

    }
}
