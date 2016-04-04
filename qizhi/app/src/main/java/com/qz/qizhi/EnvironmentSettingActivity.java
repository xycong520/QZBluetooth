package com.qz.qizhi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.qz.qizhi.kevin.app.App;
import com.qz.qizhi.kevin.params.BLEDevice;
import com.qz.qizhi.kevin.service.RFStarBLEService;
import com.qz.qizhi.kevin.view.ToastUntil;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * Created by xycong on 2016/3/31.
 */
public class EnvironmentSettingActivity extends Activity implements View.OnClickListener, BLEDevice.RFStarBLEBroadcastReceiver {
    EditText etTemp, etMoisture;
    TextView tvTime, tvDate;
    int temp;
    int moisture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_environment_setting);
        setTitle();
        init();
        getdata();
    }

    private void init() {
        etTemp = (EditText) findViewById(R.id.etTemp);
        etMoisture = (EditText) findViewById(R.id.etMoisture);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDate.setOnClickListener(this);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setOnClickListener(this);
    }

    private void setTitle() {
        findViewById(R.id.btBack).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.tvTitle)).setText("环境设置");
        Button btRight = (Button) findViewById(R.id.btRight);
        btRight.setText("确认");
        btRight.setOnClickListener(this);
        btRight.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btRight:
                save();
                break;
            case R.id.tvDate:
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();
                break;
            case R.id.tvTime:
                Calendar calendar1 = Calendar.getInstance();
                new TimePickerDialog(this, timeSetListener, calendar1.get(Calendar.HOUR_OF_DAY), calendar1.get(Calendar.MINUTE), true).show();
                break;
        }
    }

    private void save() {
        if (TextUtils.isEmpty(tvTime.getText().toString()) || TextUtils.isEmpty(tvDate.getText().toString())) {
            ToastUntil.makeText(this, "请选择日期或时间", Toast.LENGTH_SHORT);
        } else {
            if (App.manager.cubicBLEDevice != null) {
                String datetime = tvDate.getText().toString().replace("年", "").replace("月", "").replace("日", "") +
                        tvTime.getText().toString().replace(":", "");
                App.manager.cubicBLEDevice.writeValue("ffe5", "ffe9", ("HEAD6" + datetime + "   ").getBytes());
                Log.i("QZ", ("HEAD6" + datetime + "   "));
            }
        }
        temp = Integer.valueOf("".equals(etTemp.getText().toString().replace("℃", "").trim()) ? "0" : etTemp.getText().toString().replace("℃", "").trim());
        moisture = Integer.valueOf("".equals(etMoisture.getText().toString().replace("%RH", "").trim()) ? "0" : etMoisture.getText().toString().replace("%RH", "").trim());
        String tempStr = "", moistureStr = "";
        if (temp < 10) {
            tempStr = "0" + temp;
        }else{
            tempStr = String.valueOf(temp);
        }
        if (moisture < 0) {
            moistureStr = "0" + moisture;
        }else{
            moistureStr = String.valueOf(moisture);
        }
        Log.i("QZ", ("HEAD5" + tempStr + "-" + moistureStr + "-" + grainType + "-" + maxWeight + "    "));
        if (App.manager.cubicBLEDevice != null) {
            App.manager.cubicBLEDevice.writeValue("ffe5", "ffe9", ("HEAD5" + tempStr + "-" + moistureStr + "-" + grainType + "-" + maxWeight + "    ").getBytes());
        }

    }

    int grainType = 1;
    int maxWeight = 999;

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hour = "", min = "";
            if (hourOfDay < 10) {
                hour = "0" + hourOfDay;
            }else{
                hour = String.valueOf(hourOfDay);
            }
            if (minute < 10) {
                min = "0" + minute;
            }else{
                min = String.valueOf(minute);
            }
            tvTime.setText(hour + ":" + min);
        }
    };


    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String month = "",day="";
            if (++monthOfYear<10){
                month = "0"+monthOfYear;
            }else{
                month = String.valueOf(monthOfYear);
            }
            if (dayOfMonth<10){
                day = "0"+dayOfMonth;
            }else{
                day = String.valueOf(dayOfMonth);
            }
            tvDate.setText(year + "年" + month + "月" + day + "日");
        }
    };

    public void getdata() {
        if (App.manager.cubicBLEDevice == null){
            ToastUntil.makeText(this,"设备未连接",Toast.LENGTH_SHORT);
            return;
        }
        App.manager.cubicBLEDevice.setBLEBroadcastDelegate(this);
        //发送获取指令
        App.manager.cubicBLEDevice.writeValue("ffe5", "ffe9", "HEAD3".getBytes());
        App.showDialog(this);
        //接收返回数据
        App.manager.cubicBLEDevice.setNotification("ffe0", "ffe4",
                true);

    }

    @Override
    public void onReceive(Context context, Intent intent, String macData, String uuid) {
        String action = intent.getAction();
        App.dismiss();
        if (RFStarBLEService.ACTION_DATA_AVAILABLE.equals(action)) {
            if (uuid.contains("ffe4")) {
                byte[] data = intent
                        .getByteArrayExtra(RFStarBLEService.EXTRA_DATA);
                try {
                    String res = new String(data, "GB2312");
                    ToastUntil.makeText(EnvironmentSettingActivity.this, "返回数据 =" + res, Toast.LENGTH_SHORT);
                    String result[] = res.replace("当前设置:", "").split("\n");
                    for (int i = 0; i < result.length; i++) {
                        if (result[i].contains("温度")) {
                            etTemp.setText(result[i].replace("温度=", ""));
                        } else if (result[i].contains("湿度")) {
                            etMoisture.setText(result[i].replace("湿度=", ""));
                        } else if (result[i].contains("限制")) {
                            try {
                                maxWeight = Integer.valueOf(result[i].replace("限制=", "").replace("g", ""));
                            } catch (NumberFormatException e) {
                                ToastUntil.makeText(EnvironmentSettingActivity.this, "格式化数字出错：maxWeight", Toast.LENGTH_SHORT);
                            }
                        } else if (result[i].contains("存储种类:")) {
                            switch (result[i].replace("存储种类:", "")) {
                                case "小米":
                                    grainType = 1;
                                    break;
                                case "大米":
                                    grainType = 2;
                                    break;
                                case "绿豆":
                                    grainType = 3;
                                    break;
                            }
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ToastUntil.makeText(EnvironmentSettingActivity.this, "uuid=" + uuid, Toast.LENGTH_SHORT);
            }
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED
                .equals(action)) {
        } else {
            ToastUntil.makeTextTop(context, "指令发送完回调 " + macData + " " + uuid, Toast.LENGTH_SHORT);
        }
    }
}
