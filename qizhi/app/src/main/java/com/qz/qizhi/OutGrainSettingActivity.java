package com.qz.qizhi;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.qz.qizhi.adapter.CommonAdapter;
import com.qz.qizhi.adapter.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/8.
 */
public class OutGrainSettingActivity extends Activity implements View.OnClickListener {
    GridView myGridView;
    CheckBox cbNum, cbChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_outgrain_setting);
        setTitle();
        init();
    }

    private void init() {

        myGridView = (GridView) findViewById(R.id.gridlayout);
        List<String> list = new ArrayList<>();
        list.add(" ");
        Calendar calendar = Calendar.getInstance();
        java.text.DateFormat format1 = new java.text.SimpleDateFormat(
                "MM-dd");
        int dayofyear = calendar.get(Calendar.DAY_OF_YEAR);
        String todayDate = format1.format(calendar.getTime());
        list.add("今天\n" + todayDate);
        for (int i = 1; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_YEAR, dayofyear - i);
            String Date = format1.format(calendar.getTime());
            list.add(getWeek(Date) + "\n" + Date);
        }
        list.add("上午\n" + 500);
        for (int i = 0; i < 7; i++) {
            list.add("" + (100 + i));
        }
        list.add("中午\n" + 2100);
        for (int i = 0; i < 7; i++) {
            list.add("" + (300 + i));
        }
        list.add("晚上\n" + 2100);
        for (int i = 0; i < 7; i++) {
            list.add("" + (350 + i));
        }

        myGridView.setAdapter(new CommonAdapter<String>(this, list, R.layout.item_gridlayout) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.tvWeek, item);
            }
        });


        cbNum = (CheckBox) findViewById(R.id.cbNum);
        cbChart = (CheckBox) findViewById(R.id.cbChart);
        cbNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbChart.setChecked(!isChecked);
                if (isChecked) {
                    myGridView.setVisibility(View.VISIBLE);
                    getMultiLineChart("", "").setVisibility(View.GONE);
                }
            }
        });
        cbChart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbNum.setChecked(!isChecked);
                if (isChecked) {
                    myGridView.setVisibility(View.GONE);
                    getMultiLineChart("", "").setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("出米");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    PopupWindow popupWindow;
    boolean isTypeSelect;

    private void showPw(View v, TextView tv, final List<String> grainString) {
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


    public String getWeek(int index) {
        String week = null;
        switch (index) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

    public String getWeek(String date) {
        int month = Integer.parseInt(date.split("-")[0]);
        int day = Integer.parseInt(date.split("-")[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        return getWeek(calendar.get(Calendar.DAY_OF_WEEK));
    }

    private View getMultiLineChart(String label, String description) {
        int[] mColors = new int[]{
                ColorTemplate.COLORFUL_COLORS[0],
                ColorTemplate.VORDIPLOM_COLORS[1],
                ColorTemplate.COLORFUL_COLORS[2]
        };
        String[] time = {
                "早上", "中午", "晚上"
        };
        LineChart lineChart = (LineChart) findViewById(R.id.barchat);
        ArrayList<String> xVals = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_YEAR, dayOfYear - i);
            String sdf = new SimpleDateFormat("MM-dd").format(calendar.getTime());
            xVals.add(sdf);
        }
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        for (int z = 0; z < 3; z++) {

            ArrayList<Entry> values = new ArrayList<Entry>();

            for (int i = 0; i < 7; i++) {
                int val = new Random().nextInt(200);
                values.add(new Entry((float) val, i));
            }

            LineDataSet d = new LineDataSet(values, time[z]);
            d.setLineWidth(2.5f);
            d.setCircleRadius(4f);

            int color = mColors[z % mColors.length];
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }

        LineData data = new LineData(xVals, dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
        return lineChart;
    }
}
