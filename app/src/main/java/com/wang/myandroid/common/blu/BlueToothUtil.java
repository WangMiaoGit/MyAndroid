package com.wang.myandroid.common.blu;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by MaxWang on 2019/1/8.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2019/1/8 14:05
 * 修改人  ：MaxWang
 * 修改时间：2019/1/8
 * 修改备注：
 */

public class BlueToothUtil {


    public static boolean isBle(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            System.out.println("支持BLE");
            return true;
        } else {
            System.out.println("不支持BLE");
            return false;
        }
    }

    public static void openBle(Activity context) {
        //通过系统服务获取蓝牙管理者
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        //获取蓝牙适配器
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            System.out.println("蓝牙没有开启");
            //请求开启蓝牙
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            context.startActivityForResult(enableBtIntent, 1);
        }

    }

}
