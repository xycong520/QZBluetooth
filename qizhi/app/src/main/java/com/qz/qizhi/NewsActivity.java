package com.qz.qizhi;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Created by xycong on 2016/3/29.
 */
public class NewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_out_grain);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridlayout);
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
        }
    }
}
