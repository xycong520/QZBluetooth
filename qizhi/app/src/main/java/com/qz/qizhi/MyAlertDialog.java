package com.qz.qizhi;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MyAlertDialog implements View.OnClickListener {

    AlertDialog dialog;
    Context mContext;
    View view;
    int layoutID;

    public MyAlertDialog(Context context, int layoutID) {
        this.mContext = context;
        this.layoutID = layoutID;
        view = LayoutInflater.from(mContext).inflate(layoutID, null);
        dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        view.findViewById(R.id.ivGrainbox).setOnClickListener(this);
        view.findViewById(R.id.ivkitchen).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivGrainbox:
                if (layoutID == R.layout.layout_dialog_temp){

                }else if (layoutID == R.layout.layout_dialog_wet){

                }else if (layoutID == R.layout.layout_dialog_outgrain){

                }
                break;
            case R.id.ivkitchen:
                if (layoutID == R.layout.layout_dialog_temp){

                }else if (layoutID == R.layout.layout_dialog_wet){

                }else if (layoutID == R.layout.layout_dialog_outgrain){

                }
                break;
        }
    }
}
