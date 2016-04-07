package com.qz.qizhi;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.qz.qizhi.Bean.BeanNew;
import com.qz.qizhi.adapter.CommonAdapter;
import com.qz.qizhi.adapter.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by xycong on 2016/3/29.
 */
public class NewsActivity extends Activity {

    ListView mListView;
    CommonAdapter mAdapter;
    List<BeanNew> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new);
        setTitle();
        init();
//        setContentView(R.layout.layout_out_grain);
       /* GridLayout gridLayout = (GridLayout) findViewById(R.id.gridlayout);
        for(int i=0;i<16;i++){
            View view = LayoutInflater.from(this).inflate(R.layout.item_gridlayout,null);
            TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
            // 行设置，第一个为参数为第几行，默认可不设置，第二个参数为跨行数，没有则表示不跨行
            GridLayout.Spec spaceRow = GridLayout.spec(i/8,i%8);
            // 列设置，第一个为参数为第几列，默认可不设置，第二个参数为跨列数，没有则表示不跨行
            GridLayout.Spec spaceCol = GridLayout.spec(i%8);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(spaceRow,spaceCol);
            tvDate.setText("" + i);
            view.setLayoutParams(params);
            gridLayout.addView(view,params);
        }*/
    }

    public void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("设备信息");
    }

    private void init() {
        news = new ArrayList<BeanNew>();

        BeanNew beanNew1 = new BeanNew(1, "存谷");
        BeanNew beanNew2 = new BeanNew(2, "出谷");
        BeanNew beanNew3 = new BeanNew(3, "温度");
        BeanNew beanNew4 = new BeanNew(4, "湿度");
        BeanNew beanNew5 = new BeanNew(5, "能耗");
        beanNew1.setView(getBarChartView(beanNew1.getTypeName(), "g"));
        news.add(beanNew1);
        beanNew2.setView(getMultiLineChart(beanNew2.getTypeName(), "g"));
        news.add(beanNew2);
        beanNew3.setView(getBarChartView(beanNew3.getTypeName(), "g"));
        news.add(beanNew3);
        beanNew4.setView(getBarChartView(beanNew4.getTypeName(), "g"));
        news.add(beanNew4);
        beanNew5.setView(getBarChartView(beanNew5.getTypeName(), "g"));
        news.add(beanNew5);

        mListView = (ListView) findViewById(R.id.lvNew);

        mListView.setAdapter(mAdapter = new CommonAdapter<BeanNew>(this, news, R.layout.item_event) {
            @Override
            public void convert(ViewHolder helper, BeanNew item) {
                TextView tvDate = helper.getView(R.id.tvDate);
                tvDate.setText(item.getTypeName());
                LinearLayout layout = helper.getView(R.id.layout_time_event);
                layout.removeAllViews();
                if (item.isShow() && item.getView() != null) {
                    layout.addView(item.getView());
                    tvDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.mipmap.icon_shrink);
                } else {
                    tvDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.mipmap.icon_open);
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                news.get(i).setIsShow(!news.get(i).isShow());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private View getMultiLineChart(String label, String description) {
        int[] mColors = new int[] {
                ColorTemplate.COLORFUL_COLORS[0],
                ColorTemplate.VORDIPLOM_COLORS[1],
                ColorTemplate.COLORFUL_COLORS[2]
        };
        String[] time ={
                "早上","中午","晚上"
        };
        View view = LayoutInflater.from(this).inflate(R.layout.layout_cubicline_shidu_, null);
        LineChart lineChart = (LineChart) view.findViewById(R.id.barchat);
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
        return  view;
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
                view = LayoutInflater.from(this).inflate(R.layout.layout_barchart_grain_in_, null);
                BarChart barChart = (BarChart) view.findViewById(R.id.barchat);
                barChart.setData(data);
                barChart.setDescription(description);
        }


        return view;
    }
}
