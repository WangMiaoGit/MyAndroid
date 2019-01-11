package com.wang.myandroid.utils;

/**
 * Created by liym on 2018/8/2.
 */

public class EpcRecord {
    private int no;
    private String epc;
    private int count;
    private float rssi;

    public EpcRecord(int no, String epc, float rssi) {
        this.no = no;
        this.epc = epc;
        this.rssi = rssi;
        count = 1;
    }

    public int getNo() {
        return no;
    }

    public String getEpc() {
        return epc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getRssi() {
        return rssi;
    }

    public void setRssi(float rssi) {
        this.rssi = rssi;
    }
}
