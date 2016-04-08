package com.qz.qizhi;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qz.qizhi.adapter.CommonAdapter;
import com.qz.qizhi.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class OutGrainSettingActivity extends Activity implements View.OnClickListener {
    List<String> graninsType = new ArrayList<>();
    List<String> graninsUnit = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_out_grain_setting);
        graninsUnit.add("重量(克)");
        graninsUnit.add("容量(饭碗)");
        graninsUnit.add("热量(大卡)");
        graninsType.add("福临门  籼优1号");
        graninsType.add("金龙鱼");
        graninsType.add("稻花香");
        setTitle();
        init();
    }

    private void init() {
        findViewById(R.id.ivUnit).setOnClickListener(this);
        findViewById(R.id.ivType).setOnClickListener(this);
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("出谷设置");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivType:
                isTypeSelect= true;
                showPw(view, (TextView) findViewById(R.id.tvType),graninsType);
                break;
            case R.id.ivUnit:
                isTypeSelect = false;
                showPw(view, (TextView) findViewById(R.id.tvUnit),graninsUnit);
                break;
        }
    }

    PopupWindow popupWindow;
boolean isTypeSelect;
    private void showPw(View v, TextView tv, final List<String> grainString) {
        View view = getLayoutInflater().inflate(R.layout.layout_lv, null);
        if (popupWindow==null){
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
            listView.setAdapter(new CommonAdapter<String>(this, grainString,R.layout.item_grainsetting) {
                @Override
                public void convert(ViewHolder helper, String item) {
                    helper.setText(R.id.tvName,item);
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (isTypeSelect) {
                        ((TextView) findViewById(R.id.tvType)).setText(grainString.get(i));
                    } else {
                        ((TextView) findViewById(R.id.tvUnit)).setText(grainString.get(i));
                    }
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAsDropDown(v, -tv.getWidth(), 0);
        }

    }
}
