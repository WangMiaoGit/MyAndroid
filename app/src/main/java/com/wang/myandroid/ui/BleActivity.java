package com.wang.myandroid.ui;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.myandroid.R;
import com.wang.myandroid.common.blu.BleUtil;

import java.util.ArrayList;
import java.util.List;

public class BleActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BleActivity";
    Button btn_open, btn_close, btn_search;
    private ListView listView_paired,listView_new;
    BleUtil bleUtil;

    private List<String> list_mac_paired = new ArrayList<>();
    private List<String> list_mac_new = new ArrayList<>();
    ArrayAdapter<String> stringArrayAdapter_paired,stringArrayAdapter_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        int currentapiVersion=android.os.Build.VERSION.SDK_INT;
        System.out.println(currentapiVersion);
        initBle();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        btn_open = findViewById(R.id.btn_open_ble);
        btn_close = findViewById(R.id.btn_close_ble);
        btn_search = findViewById(R.id.btn_search_ble);

        listView_new = findViewById(R.id.list_search_ble);
        listView_paired = findViewById(R.id.listView2);

        //得到已配对数据   bleUtil下的listDevice_pairde赋值
        List<BluetoothDevice> list = bleUtil.pairdeDevices();
        for (BluetoothDevice b : list){
            String  s= b.getAddress()+"------"+b.getName();
            list_mac_paired.add(s);
        }
        stringArrayAdapter_paired = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_mac_paired);
        listView_paired.setAdapter(stringArrayAdapter_paired);
        listView_paired.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = list_mac_paired.get(position);
                Toast.makeText(BleActivity.this, s, Toast.LENGTH_SHORT).show();

                bleUtil.connectLeDevice_paired(position);
            }
        });


        stringArrayAdapter_new = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_mac_new);
        listView_new.setAdapter(stringArrayAdapter_new);
        listView_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = list_mac_new.get(position);
                Toast.makeText(BleActivity.this, s, Toast.LENGTH_SHORT).show();
                bleUtil.connectLeDevice(position);
            }
        });

        listView_new.setEmptyView(findViewById(R.id.tv_empty));


        btn_open.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }

    //init ble
    private void initBle() {
        bleUtil = BleUtil.getInstance();
        bleUtil.setContext(this);
        bleUtil.setBTUtilListener(new BleUtil.BTUtilListener() {
            @Override
            public void onLeScanStart() {
                Log.i(TAG, "start scanner");
            }

            @Override
            public void onLeScanStop() {
                stringArrayAdapter_new.notifyDataSetChanged();
                Log.i(TAG, "stop scanner");
            }

            //scanner devices
            @Override
            public void onLeScanDevices(List<BluetoothDevice> listDevice) {
                Log.i(TAG, listDevice.size() + "");
                list_mac_new.clear();
                if (listDevice.size() > 0) {
                    for (BluetoothDevice b : listDevice) {
                        list_mac_new.add(b.getAddress()+"------"+b.getName());
                    }
                }
            }

            @Override
            public void onConnected(BluetoothDevice mCurDevice) {
                Log.i(TAG, mCurDevice.getAddress()+"------connected");
            }

            @Override
            public void onDisConnected(BluetoothDevice mCurDevice) {
                Log.i(TAG, mCurDevice.getAddress()+"------disconnect");
            }

            @Override
            public void onConnecting(BluetoothDevice mCurDevice) {

                Log.i(TAG, "connecting......");
            }

            @Override
            public void onDisConnecting(BluetoothDevice mCurDevice) {
                Log.e(TAG,"failed");
            }

            @Override
            public void onStrength(int strength) {

            }

            @Override
            public void onModel(int model) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_open_ble:
                //设置上下文 打开蓝牙
                bleUtil.setContext(this);
                break;
            case R.id.btn_close_ble:
                bleUtil.disConnectBle();
                break;
            case R.id.btn_search_ble:
                bleUtil.setContext(this);
                bleUtil.scanLeDevice(true);

            default:
                break;
        }
    }
}
