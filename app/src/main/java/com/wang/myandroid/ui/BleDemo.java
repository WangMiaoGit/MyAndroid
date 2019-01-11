package com.wang.myandroid.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.wang.myandroid.R;
import com.wang.myandroid.utils.ByteUtils;
import com.xminnov.ble.BU01_Factory;
import com.xminnov.ble.BU01_Reader;
import com.xminnov.ble.BU01_Service;
import com.xminnov.ble.impl.EpcReply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BleDemo extends AppCompatActivity {
    protected BU01_Reader reader;
    private BU01_Service service;
    private Adapter adapter;
    private List<BU01_Reader> readers = new ArrayList<>();
    private Handler handler = new Handler();
    private Runnable stopScanRunnable = this::stopScan;
    private Runnable notifyRunnable = new Runnable() {
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
            handler.postDelayed(this, 500);
        }
    };



    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_demo);
        ButterKnife.bind(this);

        try {
            //创建  service
            service = BU01_Factory.bu01(this, reader -> adapter.addReader(reader));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        RecyclerView list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter((adapter = new Adapter()));
        list.getItemAnimator().setAddDuration(0);
        list.getItemAnimator().setChangeDuration(0);
        list.getItemAnimator().setMoveDuration(0);
        list.getItemAnimator().setRemoveDuration(0);

        adapter.setOnItemClickListener(reader -> {
//            ((BleApplication) getApplication()).setReader(reader);
//            startActivity(new Intent(this, BleMainActivity.class));
            this.reader=reader;

            if (reader != null){
                //连接
                reader.connect(this, new BU01_Reader.Callback() {
                    @Override
                    public void onConnect() {


                        System.out.println("connect");
                    }

                    @Override
                    public void onDisconnect() {



                        if (!isFinishing()) {
                            Toast.makeText(BleDemo.this, "aaaaaa", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        swipe.setColorSchemeResources(R.color.colorAccent);
        swipe.setOnRefreshListener(this::scanForBleReader);
        swipe.setRefreshing(true);

        scanForBleReader();


    }

    private void scanForBleReader() {
        adapter.clearReaders();
        service.scanForBU01BleReader();
        handler.postDelayed(stopScanRunnable, 10000);
        handler.postDelayed(notifyRunnable, 500);
    }

    private void stopScan() {
        service.stopScan();
        handler.removeCallbacks(stopScanRunnable);
        handler.removeCallbacks(notifyRunnable);
        swipe.setRefreshing(false);
    }

    //自定义适配器
    static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private List<BU01_Reader> readers = new ArrayList<>();
        private OnItemClickListener listener;

        void addReader(BU01_Reader reader) {
            if (readers.contains(reader)) {
                readers.set(readers.indexOf(reader), reader);
            } else {
                readers.add(reader);
            }
            Collections.sort(readers, (o1, o2) -> o2.getRssi() - o1.getRssi());
        }

        void clearReaders() {
            readers.clear();
            notifyDataSetChanged();
        }

        void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            BU01_Reader reader = readers.get(position);
            holder.name.setText(reader.getName());
            holder.mac.setText(reader.getAddress());
            holder.rssi.setText(reader.getRssi() + "dB");
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(readers.get(holder.getAdapterPosition()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return readers.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.name)
            TextView name;
            @BindView(R.id.mac)
            TextView mac;
            @BindView(R.id.rssi)
            TextView rssi;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }

        interface OnItemClickListener {
            void onItemClick(BU01_Reader reader);
        }

    }


    protected void disconnect() {
        if (reader != null){
            reader.disconnect();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnect();
    }


    void onTagScan(){

        try {
//            final byte[] password = getPassword(etAccPwd.getText().toString());
//            dialog = ProgressDialog.show(BleDemoActivity.this, "", getResources().getString(R.string.text_dialog_tamper), true);
//            updateControls(false);
            reader.singleInventory((status, list) -> {
                if (status == 0){
                    if (list.size() == 0) {
//                        showToast(getResources().getString(R.string.toast_err_no_tag));
//                        dialog.dismiss();
//                        updateControls(true);
                        return;
                    }
                    EpcReply epcReply = list.get(0);
                    Toast.makeText(this, ByteUtils.epcBytes2Hex(epcReply.getEpc()), Toast.LENGTH_SHORT).show();


//                    tvEpc.setText(ByteUtils.epcBytes2Hex(epcReply.getEpc()));
                    short address = 32;
                    short length = 1;
                    reader.readTag((status1, data) -> {
                                if (status1 == 0){
                                    if (data[0] == (byte)0x00 && data[1] == (byte)0x40) {
//                                        showTamper();
                                    }else if((data[0] == (byte)0x40 || data[0] == (byte)0x80 || data[0] == (byte)0xC0)
                                            && data[1] == (byte)0x40){
//                                        showNoTamper();
                                    }else{
//                                        showToast(getResources().getString(R.string.toast_err_tamper_nonsupport));
                                    }
                                } else {
//                                    showToast(getResources().getString(R.string.text_tv_status_read_failed, status1));
                                }
//                                dialog.dismiss();
//                                updateControls(true);
                            },
                            getPassword("00000000"),
                            epcReply.getEpc(),
                            BU01_Reader.BANK_EPC,
                            address,
                            length);
                } else {
//                    showToast(getResources().getString(R.string.text_tv_status_single_inventory_failed, status));
//                    dialog.dismiss();
//                    updateControls(true);
                }
            });
        }catch (NumberFormatException e){
//            showToast(e.getMessage());
        }
    }

    private byte[] getPassword(String pwd){

        String s = "00000000";
        s = s.substring(0, s.length() - pwd.length()) + pwd;
        return ByteUtils.str2hex(s);
    }

    public void scanner(View view){
        onTagScan();
    }
}
