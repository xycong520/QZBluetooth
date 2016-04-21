package com.qz.qizhi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.qz.qizhi.kevin.app.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MyAlertDialog implements View.OnClickListener {

    AlertDialog dialog;
    Context mContext;
    View view;
    int layoutID;
    EditText etNum;
    int curCheck;
    List<String> selectPeopleList;

    public MyAlertDialog(Context context, int layoutID) {
        this.mContext = context;
        this.layoutID = layoutID;
        view = LayoutInflater.from(mContext).inflate(layoutID, null);
        dialog = new AlertDialog.Builder(mContext).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        if (layoutID != R.layout.layout_dialog_outgrain) {
            view.findViewById(R.id.ivRight).setOnClickListener(this);
            view.findViewById(R.id.ivLeft).setOnClickListener(this);
        } else if (layoutID == R.layout.layout_dialog_outgrain) {
            etNum = (EditText) view.findViewById(R.id.etNum);
            view.findViewById(R.id.btSure).setOnClickListener(this);
            view.findViewById(R.id.tvSetting).setOnClickListener(this);
            ((RadioGroup) view.findViewById(R.id.rgOutGrain)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    curCheck = checkedId;
                    if (checkedId == R.id.rbPeople) {
                        view.findViewById(R.id.layoutPeople).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.layoutNum).setVisibility(View.GONE);
                    } else {
                        view.findViewById(R.id.layoutPeople).setVisibility(View.GONE);
                        view.findViewById(R.id.layoutNum).setVisibility(View.VISIBLE);
                    }
                    switch (checkedId) {
                        case R.id.rbWeight:
                            etNum.setText(200 + "克");
                            break;
                        case R.id.rbBowl:
                            etNum.setText(4 + "碗");
                            break;
                        case R.id.rbCal:
                            etNum.setText(1200 + "Cal");
                            break;
                        case R.id.rbPeople:
                            LinearLayout layoutView = ((LinearLayout) view.findViewById(R.id.layoutPeople));
                            String peopleString[] = {
                                    "爸爸", "妈妈", "儿子"
                            };
                            layoutView.removeAllViews();
                            selectPeopleList = new ArrayList<String>();
                            for (int i = 0; i < 3; i++) {
                                CheckBox checkBox = new CheckBox(mContext);
                                checkBox.setButtonDrawable(R.drawable.icon_checkbox);
                                checkBox.setText(peopleString[i]);
                                checkBox.setTag(peopleString[i]);
                                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        if (isChecked) {
                                            selectPeopleList.add(String.valueOf(buttonView.getTag()));
                                        } else {
                                            selectPeopleList.remove(String.valueOf(buttonView.getTag()));
                                        }
                                    }
                                });
                                layoutView.addView(checkBox);
                            }
                            break;
                    }
                }
            });

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                if (layoutID == R.layout.layout_dialog_temp) {
                    mContext.startActivity(new Intent(mContext, TempDataSettingActivity.class).putExtra("title", "温度"));
                } else if (layoutID == R.layout.layout_dialog_wet) {
                    mContext.startActivity(new Intent(mContext, TempDataSettingActivity.class).putExtra("title", "湿度"));
                } else if (layoutID == R.layout.layout_dialog_keepingrain) {//存米
                    mContext.startActivity(new Intent(mContext, GrainSpaceActivity.class));
                } else if (layoutID == R.layout.layout_dialog_reset) {
                    mContext.startActivity(new Intent(mContext, ResetActivity.class));
                }
                break;
            case R.id.ivRight:
                if (layoutID == R.layout.layout_dialog_temp) {

                } else if (layoutID == R.layout.layout_dialog_wet) {

                } else if (layoutID == R.layout.layout_dialog_keepingrain) {//存米
                    mContext.startActivity(new Intent(mContext, SaveGrainActivity.class));
                } else if (layoutID == R.layout.layout_dialog_reset) {
                    mContext.startActivity(new Intent(mContext, ShajunActivity.class));
                }
                break;
            case R.id.btSure:
                if (curCheck == R.id.rbPeople) {
                    String selectName = "";
                    for (int i = 0; i < selectPeopleList.size(); i++) {
                        selectName += selectPeopleList.get(i) + " ";
                    }
//                    ToastUntil.makeText(mContext, selectName, Toast.LENGTH_SHORT);
                } else {
//                    ToastUntil.makeText(mContext, etNum.getText().toString(), Toast.LENGTH_SHORT);
                }
                if (App.manager.cubicBLEDevice != null) {
//                    App.manager.cubicBLEDevice.setBLEBroadcastDelegate(FragmentDevice.this);
                    App.manager.cubicBLEDevice.writeValue("ffe5", "ffe9", "HEAD0".getBytes());
                } else {
                    Toast.makeText(mContext, "请先连接设备", Toast.LENGTH_SHORT).show();
                }
        break;
        case R.id.tvSetting:
        mContext.startActivity(new Intent(mContext, OutGrainSettingActivity.class));
        break;
    }
}
}
