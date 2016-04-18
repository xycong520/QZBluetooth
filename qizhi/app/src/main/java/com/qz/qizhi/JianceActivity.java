package com.qz.qizhi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/18.
 */
public class JianceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_jiance);
        setTitle();
        init();
    }

    boolean isLineChart;
    CheckBox cbChart, cbNum;

    private void init() {
        final LineChart lc = (LineChart) getBarChartView("曲线", "");
        final BarChart bc = (BarChart) getBarChartView("", "");
        cbNum = (CheckBox) findViewById(R.id.cbNum);
        cbChart = (CheckBox) findViewById(R.id.cbChart);
        cbNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbChart.setChecked(!isChecked);
                if (isChecked) {
                    lc.setVisibility(View.GONE);
                    bc.setVisibility(View.VISIBLE);
                }
            }
        });
        cbChart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cbNum.setChecked(!isChecked);
                if (isChecked) {
                    bc.setVisibility(View.GONE);
                    lc.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("检测");
    }

    private BarLineChartBase getBarChartView(String label, String description) {
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
            case "曲线":
//                view = LayoutInflater.from(this).inflate(R.layout.layout_cubicline_shidu_, null);
                LineChart lineChart = (LineChart) findViewById(R.id.linechat);
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
                return lineChart;
//                lineChart.setBackgroundColor(Color.rgb(104, 241, 175));
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
                return barChart;
        }
    }
}
