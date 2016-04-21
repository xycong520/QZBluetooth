package com.qz.qizhi.fragment;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qz.qizhi.AddDeviceActivity;
import com.qz.qizhi.Bean.BeanDevice;
import com.qz.qizhi.Bean.DeviceFunction;
import com.qz.qizhi.DBUtils;
import com.qz.qizhi.EventActivity;
import com.qz.qizhi.JianceActivity;
import com.qz.qizhi.MyAlertDialog;
import com.qz.qizhi.NewsActivity;
import com.qz.qizhi.R;
import com.qz.qizhi.SettingActivity;
import com.qz.qizhi.adapter.CommonAdapter;
import com.qz.qizhi.adapter.ViewHolder;
import com.qz.qizhi.kevin.app.App;
import com.qz.qizhi.kevin.params.BLEDevice;
import com.qz.qizhi.kevin.service.RFStarBLEService;
import com.qz.qizhi.kevin.view.ToastUntil;
import com.qz.qizhi.widget.MyGridView;

import org.xutils.ex.DbException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xycong on 2016/3/27.
 */
public class FragmentDevice extends Fragment implements View.OnClickListener, BLEDevice.RFStarBLEBroadcastReceiver {
    View mLayout;
    Context mContext;
    ListView mListView;
    List<BeanDevice> deviceList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mLayout == null) {
            mLayout = inflater.inflate(R.layout.fragment_device, null);
            mContext = getActivity();
            init();
        } else {
            ViewGroup parent = (ViewGroup) mLayout.getParent();
            if (parent != null) {
                parent.removeView(mLayout);
            }
        }
        return mLayout;
    }


    private void setTitle() {
        TextView tvTitle = (TextView) mLayout.findViewById(R.id.tvTitle);
        tvTitle.setText("我的设备");
        ImageButton btScan = (ImageButton) mLayout.findViewById(R.id.ibLeft);
        btScan.setVisibility(View.VISIBLE);
        btScan.setOnClickListener(this);
        Button btRight = (Button) mLayout.findViewById(R.id.btRight);
        btRight.setText("获取数据");
        btRight.setVisibility(View.VISIBLE);
        btRight.setOnClickListener(this);
    }

    BaseAdapter adapter;

    private void init() {

        setTitle();
        mListView = (ListView) mLayout.findViewById(R.id.lvDivice);
        deviceList = new ArrayList<BeanDevice>();
//        initDate();
        adapter = new CommonAdapter<BeanDevice>(mContext, deviceList, R.layout.item_device) {
            @Override
            public void convert(ViewHolder holper, BeanDevice item) {
                holper.setText(R.id.tvName, item.getDeviceName());
                holper.setText(R.id.tvStatus, item.getStatus());
                holper.getView(R.id.tvEvent).setOnClickListener(FragmentDevice.this);
                holper.getView(R.id.tvNews).setOnClickListener(FragmentDevice.this);
                holper.getView(R.id.tvShare).setOnClickListener(FragmentDevice.this);
                holper.getView(R.id.tvSetting).setOnClickListener(FragmentDevice.this);
                holper.setTag(R.id.tvEvent, item.getMacAddress());
                holper.setTag(R.id.tvNews, item.getMacAddress());
                holper.setTag(R.id.tvShare, item.getMacAddress());
                holper.setTag(R.id.tvSetting, item.getMacAddress());
//                holper.getConvertView().setTag(R.id.tvName,item.getMacAddress());
                MyGridView gridView = holper.getView(R.id.gridViewDeviceFunction);
                gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                gridView.setAdapter(new CommonAdapter<DeviceFunction>(mContext, item.getFunctions(), R.layout.item_device_function) {
                    @Override
                    public void convert(ViewHolder helper, DeviceFunction item) {
                        TextView tvName = helper.getView(R.id.tvName);
                        helper.setText(R.id.tvName, item.getFunName());

                        if ("余米".equals(item.getFunName())){
                            helper.getView(R.id.tvValue1).setBackgroundResource(R.mipmap.icon_weight3);
                            helper.getView(R.id.tvValue2).setBackgroundResource(R.mipmap.icon_weight3);
                        }else{
                            helper.setText(R.id.tvValue1, item.getFunValue1());
                            helper.setText(R.id.tvValue2, item.getFunValue2());
                        }
                        tvName.setText(item.getFunName());
                        if (item.getFunIcon() == 0) {
                            helper.getView(R.id.ivIcon).setVisibility(View.INVISIBLE);
                        } else {
                            ((ImageView) helper.getView(R.id.ivIcon)).setImageResource(item.getFunIcon());
                        }
                        helper.getConvertView().setTag(R.id.tvEvent, item.getActionType());
                    }

                });
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int actionType = (int) view.getTag(R.id.tvEvent);
                        switch (actionType){
                            case 1://出米：
                                new MyAlertDialog(mContext,R.layout.layout_dialog_outgrain);
                                break;
                            case 2://余米：
                                new MyAlertDialog(mContext,R.layout.layout_dialog_keepingrain);
                                break;
                            case 3://温度：
                                new MyAlertDialog(mContext,R.layout.layout_dialog_temp);
                                break;
                            case 4://湿度：
                                new MyAlertDialog(mContext,R.layout.layout_dialog_wet);
                                break;
                            case 5://复位：
                                new MyAlertDialog(mContext,R.layout.layout_dialog_reset);
                                break;
                            case 6://监测：
                                startActivity(new Intent(mContext, JianceActivity.class));
                                break;
                        }
//                        if (App.manager.cubicBLEDevice != null) {
//                            App.manager.cubicBLEDevice.setBLEBroadcastDelegate(FragmentDevice.this);
//                            if (actionType == 1) {//出米
//                                App.manager.cubicBLEDevice.writeValue("ffe5", "ffe9", "HEAD0".getBytes());
//                            }
//                        } else {
//                            Toast.makeText(mContext, "请先连接设备", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });
            }
        };
        mListView.setAdapter(adapter);
    }

    public void initData() {
        //获取连接后设备的保存数据
        BeanDevice device = new BeanDevice();
        try {
            /*deviceList =*/
            new DBUtils().getDB().findAll(BeanDevice.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        List<DeviceFunction> functions = device.getFunctions();
        device.setDeviceName("米箱");
        device.setStatus("在线");
        device.setType(1);
        DeviceFunction function = device.getFunction();
        function.setFunName("温度");
        function.setFunValue1("16℃");
        function.setFunValue2("24℃");
        function.setActionType(3);
        function.setFunIcon(R.mipmap.icon_temp);
        functions.add(function);
        DeviceFunction function2 = device.getFunction();
        function2.setFunName("余米");
        function2.setFunValue1("20");
        function2.setFunValue2("30");
        function2.setActionType(2);
        function2.setFunIcon(R.mipmap.icon_mi);
        functions.add(function2);
        DeviceFunction function3 = device.getFunction();
        function3.setFunName("复位");
        function3.setFunValue1("复位");
        function3.setFunValue2("杀菌");
        function3.setActionType(5);
        function3.setFunIcon(R.mipmap.icon_reset);
        functions.add(function3);
        DeviceFunction function4 = device.getFunction();
        function4.setFunName("湿度");
        function4.setActionType(4);
        function4.setFunValue1("74%");
        function4.setFunValue2("95%");
        function4.setFunIcon(R.mipmap.icon_wet);
        functions.add(function4);
        DeviceFunction function5 = device.getFunction();
        function5.setFunName("出米");
        function5.setFunValue1("上次出米");
        function5.setFunValue2("500g");
        function5.setFunIcon(R.mipmap.icon_out_grain);
        function5.setActionType(1);
        functions.add(function5);
        DeviceFunction function6 = device.getFunction();
        function6.setFunName("监测");
        function6.setActionType(6);
        function6.setFunValue1("烟雾");
        function6.setFunValue2("瓦斯");
        function6.setFunIcon(R.mipmap.icon_jiance);
        functions.add(function6);
        deviceList.add(device);
        adapter.notifyDataSetChanged();
    }

    /**
     * 打开蓝牙
     */
    public static void openBluetooth(Activity activity) {
        Intent enableBtIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 1);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ibLeft:
                startActivity(new Intent(mContext, AddDeviceActivity.class));

//                if (App.manager.isOpen()) {
//                    intent = new Intent(mContext, SearchActivity.class);
//                    getActivity().startActivityForResult(intent, Activity.RESULT_FIRST_USER);
//                } else {
//                    App.manager.openBluetooth(getActivity());
//                }
                break;
            case R.id.btRight:
                update();
                break;

            case R.id.tvEvent:
                if (App.manager.cubicBLEDevice != null) {
                    intent = new Intent(mContext, EventActivity.class);
                    intent.putExtra("titleName", "谷箱事件");
                    intent.putExtra("mac", App.manager.cubicBLEDevice.deviceMac);
                    startActivity(intent);
                } else {
                    ToastUntil.makeText(mContext, "请先连接", Toast.LENGTH_SHORT);
                }

                break;
            case R.id.tvNews:
                if (App.manager.cubicBLEDevice != null) {
                    intent = new Intent(mContext, NewsActivity.class);
                    intent.putExtra("mac", App.manager.cubicBLEDevice.deviceMac);
                    intent.putExtra("titleName", "谷箱信息");
                    startActivity(intent);
                } else {
                    ToastUntil.makeText(mContext, "请先连接", Toast.LENGTH_SHORT);
                }

                break;
            case R.id.tvSetting:
                if (App.manager.cubicBLEDevice != null) {
                    intent = new Intent(mContext, SettingActivity.class);
                    intent.putExtra("titleName", "谷箱设置");
                    intent.putExtra("mac", App.manager.cubicBLEDevice.deviceMac);
                    startActivity(intent);
                } else {
                    ToastUntil.makeText(mContext, "请先连接", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.tvShare:
//                intent = new Intent(mContext, EventActivity.class);
//                intent.putExtra("titleName","谷物事件");
//                startActivity(intent);
                break;
        }
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
                    Log.i("QZ","返回数据 =" + res);
                    String result[] = res.replace("当前状态:", "").split("\n");
                    for (int i = 0; i < result.length; i++) {
                        for (int j = 0; j < deviceList.size(); j++) {
                            BeanDevice device = deviceList.get(j);
                            for (int k = 0; k < device.getFunctions().size(); k++) {
                                if (result[i].contains(device.getFunctions().get(k).getFunName())) {
                                    device.getFunctions().get(k).setFunValue2(result[i].replace(device.getFunctions().get(k).getFunName() + "=", "").trim());
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ToastUntil.makeText(mContext, "数据刷新完成", Toast.LENGTH_SHORT);
            }
        } else if (RFStarBLEService.ACTION_GATT_SERVICES_DISCOVERED
                .equals(action)) {
        } else {
            ToastUntil.makeTextTop(context, "指令发送完回调 " + macData + " " + uuid, Toast.LENGTH_SHORT);
        }
    }

    public void update() {
        if (App.manager.cubicBLEDevice != null) {
            App.manager.cubicBLEDevice.setBLEBroadcastDelegate(FragmentDevice.this);
            //发送获取指令
            Log.i("QZ","发送指令获取数据head2");
            App.manager.cubicBLEDevice.writeValue("ffe5", "ffe9", "HEAD2".getBytes());
            App.showDialog(getActivity());
            //接收返回数据
            App.manager.cubicBLEDevice.setNotification("ffe0", "ffe4",
                    true);
        } else {
            ToastUntil.makeText(mContext, "请先连接", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (App.manager.cubicBLEDevice != null) {
            App.manager.cubicBLEDevice.setBLEBroadcastDelegate(this);
        }
    }
}
