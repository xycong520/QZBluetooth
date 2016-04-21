package com.qz.qizhi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.qz.qizhi.kevin.app.App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/14.
 */
public class TempDataSettingActivity extends Activity implements View.OnClickListener {
    String title = "";
    EditText etTempMax, etTempMin, etTempBox, etTempkitchen;
    String unit = "℃";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getIntent().getStringExtra("title");
        if ("温度".equals(title)) {
            setContentView(R.layout.layout_temp_setting);
        } else {
            setContentView(R.layout.layout_wet_setting);
            unit = "%";

        }
        setTitle();
        init();
    }

    private void setTitle() {
        ((TextView) findViewById(R.id.tvTitle)).setText(title);
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
    }

    private void init() {
        ((CheckBox) findViewById(R.id.cbKitchen)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEnvironmentSelect = isChecked;
                getMultiLineChart(title, "");
            }
        });
        findViewById(R.id.btAddTempMax).setOnClickListener(this);
        findViewById(R.id.btSubTempMax).setOnClickListener(this);
        findViewById(R.id.btAddTempMin).setOnClickListener(this);
        findViewById(R.id.btSubTempMin).setOnClickListener(this);
        findViewById(R.id.btAddTempBox).setOnClickListener(this);
        findViewById(R.id.btSubTempBox).setOnClickListener(this);
        findViewById(R.id.btAddTempKitchen).setOnClickListener(this);
        findViewById(R.id.btSubTempKitchen).setOnClickListener(this);
        etTempMax = (EditText) findViewById(R.id.etTempMax);
        etTempMin = (EditText) findViewById(R.id.etTempMin);
        etTempBox = (EditText) findViewById(R.id.etTempBox);
        etTempkitchen = (EditText) findViewById(R.id.etTempKitchen);
        getMultiLineChart(title, "");

    }

    boolean isEnvironmentSelect;

    private void getMultiLineChart(String label, String description) {
        int[] mColors = new int[]{
                ColorTemplate.COLORFUL_COLORS[0],
                ColorTemplate.VORDIPLOM_COLORS[1]
        };
        String[] time = {
                "箱内", "室内"
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

            if (z == 1 && !isEnvironmentSelect) {
                break;
            }
            ArrayList<Entry> values = new ArrayList<Entry>();
            for (int i = 0; i < 7; i++) {
                int val = new Random().nextInt(4) + 30;
                if (z == 0) {
                    val = new Random().nextInt(4) + 14;
                }
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

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btAddTempMax:
                App.setValue(etTempMax, true, unit);
                break;
            case R.id.btAddTempMin:
                App.setValue(etTempMin, true, unit);
                break;
            case R.id.btAddTempBox:
                App.setValue(etTempBox, true, unit);
                break;
            case R.id.btAddTempKitchen:
                App.setValue(etTempkitchen, true, unit);
                break;
            case R.id.btSubTempMax:
                App.setValue(etTempMax, false, unit);
                break;
            case R.id.btSubTempMin:
                App.setValue(etTempMin, false, unit);
                break;
            case R.id.btSubTempBox:
                App.setValue(etTempBox, false, unit);
                break;
            case R.id.btSubTempKitchen:
                App.setValue(etTempkitchen, false, unit);
                break;
        }
    }
}
