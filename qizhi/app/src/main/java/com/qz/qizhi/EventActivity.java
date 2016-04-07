package com.qz.qizhi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qz.qizhi.Bean.BeanEvent;
import com.qz.qizhi.adapter.CommonAdapter;
import com.qz.qizhi.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xycong on 2016/3/29.
 */
public class EventActivity extends Activity {
    ListView mListView;
    Context mContext;
    List<BeanEvent> eventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_event);
        mContext = this;
        setTitle();
        init();

    }

    public void setTitle() {
        ((TextView)findViewById(R.id.tvTitle)).setText(getIntent().getStringExtra("titleName"));
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
    }
   private void  init(){
        mListView = (ListView) findViewById(R.id.lvEvent);
       eventList = new ArrayList<>();
       BeanEvent event = new BeanEvent();
       event.setDate("今天");
       SparseArray eventArray = new SparseArray();
       eventArray.put(1, "19:11:20");
       eventArray.put(2, "出米200g\n剩余19斤");
       event.getEvents().add(eventArray);
       event.getEvents().add(eventArray);
       eventList.add(event);
       eventList.add(event);
       mListView.setAdapter(new CommonAdapter<BeanEvent>(mContext,eventList,R.layout.item_event) {
           @Override
           public void convert(ViewHolder helper, final BeanEvent item) {
                helper.setText(R.id.tvDate,item.getDate());
                final  LinearLayout layout = helper.getView(R.id.layout_time_event);
               for (int i=0;i<item.getEvents().size();i++){
                   View onEvent = LayoutInflater.from(mContext).inflate(R.layout.item_gridlayout,null);
                   TextView textView = (TextView) onEvent.findViewById(R.id.tvWeek);
                   final TextView textView1 = (TextView) onEvent.findViewById(R.id.tvDate);
                   textView.setText(String.valueOf(item.getEvents().get(i).get(1)));
                   textView1.setText(String.valueOf(item.getEvents().get(i).get(2)));
                   textView1.setVisibility(View.GONE);
//                   textView.setTag(i);
                   textView.setTag(R.id.tvDate, false);

                   textView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

//                           int index = (int) v.getTag();
//
                           boolean isCheck = (boolean) v.getTag(R.id.tvDate);
                           v.setTag(R.id.tvDate, isCheck = !isCheck);
                           if (isCheck) {
                               textView1.setVisibility(View.VISIBLE);

//                               TextView textView = new TextView(mContext);
//                               textView.setText(String.valueOf(item.getEvents().get(index).get(2)));
//                               layout.addView(textView,index+1);
                           } else {
                               textView1.setVisibility(View.GONE);
//                               layout.removeViewAt(index+1);
                           }

                       }
                   });
                   layout.addView(onEvent);
               }
                helper.getView(R.id.tvDate).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       item.setIsCheck(!item.isCheck());
                       if (item.isCheck()){
                           layout.setVisibility(View.VISIBLE);
                           ((TextView)v).setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.mipmap.icon_shrink);
                       }else{
                           layout.setVisibility(View.GONE);
                           ((TextView)v).setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.mipmap.icon_open);
                       }
                   }
               });
           }
       });
    }
}
