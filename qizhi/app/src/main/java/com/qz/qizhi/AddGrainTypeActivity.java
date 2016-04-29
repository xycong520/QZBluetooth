package com.qz.qizhi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qz.qizhi.adapter.CommonAdapter;
import com.qz.qizhi.adapter.ViewHolder;
import com.qz.qizhi.kevin.app.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xycong on 2016/4/16.
 */
public class AddGrainTypeActivity extends Activity implements OnClickListener {
    List<List<String>> granins = new ArrayList<>();
    List<String> graninsType = new ArrayList<>();
    int index;
    boolean isTypeSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addgraintype);
        setTitle();
        init();
    }

    private void init() {
        findViewById(R.id.ivCammer).setOnClickListener(this);
        findViewById(R.id.ivFactory).setOnClickListener(this);
        findViewById(R.id.ivType).setOnClickListener(this);
        findViewById(R.id.ivName).setOnClickListener(this);
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
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("增加品名");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCammer:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("autofocus", true); // 自动对焦
                intent.putExtra("fullScreen", false); // 全屏
                intent.putExtra("showActionIcons", false);
                startActivityForResult(intent, -1);
                break;
            case R.id.ivFactory:
                List<String> factorys = new ArrayList<>();
                factorys.add("五常稻花香");
                factorys.add("五常稻花香2");
                factorys.add("五常稻花香3");
                App.showPw(v, (TextView) findViewById(R.id.ivFactory),factorys);
                break;
            case R.id.ivName:
                if (index == -1){
                    Toast.makeText(this, "请先选择种类", Toast.LENGTH_SHORT).show();
                }
                isTypeSelect = false;
                showPw(v, (TextView) findViewById(R.id.tvName), granins.get(index));
                break;
            case R.id.ivType:
                isTypeSelect = true;
                showPw(v, (TextView) findViewById(R.id.tvType), graninsType);
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
