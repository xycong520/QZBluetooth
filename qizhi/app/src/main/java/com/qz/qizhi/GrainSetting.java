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
import android.widget.Toast;

import com.qz.qizhi.adapter.CommonAdapter;
import com.qz.qizhi.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xycong on 2016/4/7.
 */
public class GrainSetting extends Activity implements View.OnClickListener {

    List<List<String>> granins = new ArrayList<>();
    List<String> graninsType = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grain_setting);
        List<String> g1 = new ArrayList<>();
        g1.add("东北米");
        g1.add("泰国米");
        g1.add("五常稻米");
        granins.add(g1);
        List<String> g2 = new ArrayList<>();
        g2.add("红豆");
        g2.add("绿豆");
        g2.add("黑豆");
        granins.add(g2);
        graninsType.add("大米");
        graninsType.add("豆类");
        setTitle();
        init();
    }

    private void init() {
        findViewById(R.id.ivType).setOnClickListener(this);
        findViewById(R.id.ivName).setOnClickListener(this);
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("谷物设置");
    }

    int index;
    boolean isTypeSelect;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivName:
                if (index == -1){
                    Toast.makeText(this,"请先选择种类",Toast.LENGTH_SHORT).show();
                }
                isTypeSelect = false;
                showPw(view, (TextView) findViewById(R.id.tvName),granins.get(index));
                break;
            case R.id.ivType:
                isTypeSelect = true;
                showPw(view, (TextView) findViewById(R.id.tvType),graninsType);
                break;
        }
    }

    PopupWindow popupWindow;

    private void showPw(View v, TextView tv, List<String> grainString) {
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
                        index = i;
                        ((TextView) findViewById(R.id.tvType)).setText(graninsType.get(i));
                        ((TextView) findViewById(R.id.tvName)).setText(granins.get(index).get(0));
                    } else {
                        ((TextView) findViewById(R.id.tvName)).setText(granins.get(index).get(i));
                    }
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAsDropDown(v, -tv.getWidth(), 0);
        }

    }
}
