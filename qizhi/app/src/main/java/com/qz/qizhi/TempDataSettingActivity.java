package com.qz.qizhi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/14.
 */
public class TempDataSettingActivity extends Activity{
    String title = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        if ("温度".equals(title)){
            setContentView(R.layout.layout_temp_setting);
        }else{
            setContentView(R.layout.layout_wet_setting);
        }
        setTitle();
        init();
    }

    private void setTitle(){
        ((TextView)findViewById(R.id.tvTitle)).setText(title);
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
    }

    private void init() {
        ((CheckBox)findViewById(R.id.cbKitchen)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEnvironmentSelect = isChecked;
                getMultiLineChart(title,"");
            }
        });
        getMultiLineChart(title, "");
    }

    boolean isEnvironmentSelect;
    private void getMultiLineChart(String label, String description) {
        int[] mColors = new int[] {
                ColorTemplate.COLORFUL_COLORS[0],
                ColorTemplate.VORDIPLOM_COLORS[1]
        };
        String[] time ={
                "箱内","室内"
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
        for (int z = 0; z < 2; z++) {

            if (z==1 && !isEnvironmentSelect){
                break;
            }
            ArrayList<Entry> values = new ArrayList<Entry>();
            for (int i = 0; i < 7; i++) {
                int val = new Random().nextInt(4)+30;
                if (z==0){
                    val = new Random().nextInt(4)+14;
                }
                values.add(new Entry((float) val, i));
            }

            LineDataSet d = new LineDataSet(values,time[z] );
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

    }
}
