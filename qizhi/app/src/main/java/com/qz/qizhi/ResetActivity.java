package com.qz.qizhi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.qz.qizhi.kevin.app.App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/18.
 */
public class ResetActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_reset);
        setTitle();
        init();

    }

    private void init() {
        getBarChartView("", "");
        findViewById(R.id.btAddCC).setOnClickListener(this);
        findViewById(R.id.btSubCC).setOnClickListener(this);
        findViewById(R.id.btAddKG).setOnClickListener(this);
        findViewById(R.id.btSubKG).setOnClickListener(this);
    }

    private void setTitle() {
        ((TextView) findViewById(R.id.tvTitle)).setText("复位");
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
    }

    private View getBarChartView(String label, String description) {
        List<String> dateString = new ArrayList<>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<Entry> lineVals = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_YEAR, dayOfYear - i);
            String sdf = new SimpleDateFormat("MM-dd").format(calendar.getTime());
            dateString.add(sdf);
            int value = 0;
            if (label.equals("温度")) {
                value = new Random().nextInt(10) + 28;
            } else if (label.equals("湿度")) {
                value = new Random().nextInt(99) + 0;
            } else {
                value = new Random().nextInt(200);
            }
            lineVals.add(new Entry(value, i));
            yVals1.add(new BarEntry(value, i));
        }

        View view = null;


        switch (label) {
            case "出谷":
                break;
            case "温度":
            case "能耗":
            case "湿度":
                view = LayoutInflater.from(this).inflate(R.layout.layout_cubicline_shidu_, null);
                LineChart lineChart = (LineChart) view.findViewById(R.id.barchat);
                LineDataSet set1 = new LineDataSet(lineVals, label);
                set1.setDrawCubic(true);
                set1.setCubicIntensity(0.2f);
                set1.setDrawFilled(true);
                set1.setDrawCircles(false);
                set1.setLineWidth(1.8f);
                set1.setCircleRadius(4f);
                set1.setCircleColor(Color.WHITE);
                set1.setHighLightColor(Color.rgb(244, 117, 117));
                if ("湿度".equals(label)) {
                    set1.setColor(Color.BLUE);
                    set1.setFillColor(Color.BLUE);
                } else if ("温度".equals(label)) {
                    set1.setColor(Color.RED);
                    set1.setFillColor(Color.RED);
                }
                set1.setFillAlpha(100);
                set1.setDrawHorizontalHighlightIndicator(false);
                set1.setFillFormatter(new FillFormatter() {
                    @Override
                    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                        return -10;
                    }
                });
                LineData lineData = new LineData(dateString, set1);
                lineData.setValueTextSize(9f);
                lineData.setDrawValues(false);
                // set data
                lineChart.setData(lineData);
//                lineChart.setBackgroundColor(Color.rgb(104, 241, 175));
                break;
            default:
                BarDataSet set2 = new BarDataSet(yVals1, label);
                set2.setBarSpacePercent(35f);
                set2.setColor(0xff33eeaa);
                dataSets.add(set2);
                BarData data = new BarData(dateString, dataSets);
                data.setValueTextSize(10f);
//                view = LayoutInflater.from(this).inflate(R.layout.layout_barchart_grain_in_, null);
                BarChart barChart = (BarChart) findViewById(R.id.barchat);
                barChart.setData(data);
                barChart.setDescription(description);
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSubKG:
                App.setValue((EditText) findViewById(R.id.etKG),false,"分钟");
                break;
            case R.id.btAddKG:
                App.setValue((EditText) findViewById(R.id.etKG),true,"分钟");
                break;
            case R.id.btSubCC:
                App.setValue((EditText) findViewById(R.id.etCC),false,"分钟");
                break;
            case R.id.btAddCC:
                App.setValue((EditText) findViewById(R.id.etCC),true,"分钟");
                break;
        }
    }
}
