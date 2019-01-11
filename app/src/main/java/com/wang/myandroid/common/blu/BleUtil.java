package com.wang.myandroid.common.blu;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.baidu.platform.comapi.map.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by MaxWang on 2019/1/8.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2019/1/8 14:15
 * 修改人  ：MaxWang
 * 修改时间：2019/1/8
 * 修改备注：
 */

public class BleUtil {
    private static final String TAG = "BleUtil";
    private static final long SCAN_PERIOD = 10000;

    static final String CHARACTER_UUID1 = "0000fff2-0000-1000-8000-00805f9b34fb";//APP发送命令
    static String CHARACTER_UUID2 = "0000fff1-0000-1000-8000-00805f9b34fb";//BLE用于回复命令
    private static String DESCRIPTOR_UUID = "00002902-0000-1000-8000-00805f9b34fb";//BLE设备特性的UUID

    public static byte[] workModel = {0x02, 0x01};

    private Context mContext;
    private static BleUtil mInstance;

    //作为中央来使用和处理数据； 蓝牙连接重连断开连接等操作的类
    private BluetoothGatt mGatt;

    //蓝牙操作类 获取mBtAdapter
    private BluetoothManager manager;
    private BTUtilListener mListener;
    //蓝牙设备
    private BluetoothDevice mCurDevice;
    //蓝牙的打开关闭等基本操作
    private BluetoothAdapter mBtAdapter;
    //蓝牙设备 的列表  新搜索的
    private List<BluetoothDevice> listDevice;
    //蓝牙设备 的列表  已经匹配过的------------------------------
    private List<BluetoothDevice> listDevice_pairde;
    private List<BluetoothGattService> serviceList;//服务
    private List<BluetoothGattCharacteristic> characterList;//特征

    private BluetoothGattService service;
    //数据通信操作类，读写等操作
    private BluetoothGattCharacteristic character1;
    private BluetoothGattCharacteristic character2;

    //5.0以后搜索蓝牙
    BluetoothLeScanner bluetoothLeScanner;
    //单例获取工具类
    public static synchronized BleUtil getInstance() {
        if (mInstance == null) {
            mInstance = new BleUtil();
        }
        return mInstance;
    }


    //设置上下文  并初始化开启蓝牙
    public void setContext(Context context) {
        mContext = context;
        init();
    }

    //初始化
    public void init() {
        listDevice = new ArrayList<>();
        //判断是否支持BLE
        if (!mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            showToast("BLE不支持此设备!");
            ((Activity) mContext).finish();
        }
        //获取蓝牙操作类
        manager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        //注：这里通过getSystemService获取BluetoothManager，
        //再通过BluetoothManager获取BluetoothAdapter。BluetoothManager在Android4.3以上支持(API level 18)。
        if (manager != null) {
            //获取操作蓝牙开关的  BluetoothAdapter
            mBtAdapter = manager.getAdapter();

        }

        if (mBtAdapter != null) {
            mBtAdapter.enable();
            System.out.println("准备打开蓝牙");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mContext.startActivity(enableBtIntent);
        }
       /* if (mBtAdapter == null || !mBtAdapter.isEnabled()) {
//            mBtAdapter.enable();
            System.out.println("准备打开蓝牙");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mContext.startActivity(enableBtIntent);
        }*/

        //5.0以后搜索蓝牙
         bluetoothLeScanner = mBtAdapter.getBluetoothLeScanner();
    }

    //扫描设备的回调
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi,
                             byte[] scanRecord) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!listDevice.contains(device)) {
                        //不重复添加
                        listDevice.add(device);
                        mListener.onLeScanDevices(listDevice);
                        Log.e(TAG, "device:" + device.toString());
                    }
                }
            });
        }
    };

    //扫描设备
    public void scanLeDevice(final boolean enable) {
        if (enable) {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopScan();
                    Log.e(TAG, "run: stop");
                }
            }, SCAN_PERIOD);
            startScan();
            Log.e(TAG, "start");
        } else {
            stopScan();
            Log.e(TAG, "stop");
        }
    }

    //开始扫描BLE设备
    private void startScan() {
        //android5.0  之下的
        mBtAdapter.startLeScan(mLeScanCallback);
        mListener.onLeScanStart();
    }

    //停止扫描BLE设备
    private void stopScan() {
        mBtAdapter.stopLeScan(mLeScanCallback);
        mListener.onLeScanStop();
    }

    //返回中央的状态和周边提供的数据
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                            int newState) {
            Log.e(TAG, "onConnectionStateChange");

            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.e(TAG, "STATE_CONNECTED");
                    mListener.onConnected(mCurDevice);
                    gatt.discoverServices(); //搜索连接设备所支持的service
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    System.out.println("连接失败。。。。。。。。。。。。。");
                    Log.e(TAG,"status====="+status);
                    /*if (status==133){
                        mGatt.close();
                        mGatt = mCurDevice.connectGatt(mContext, false, mGattCallback);
                    }*/
                    mListener.onDisConnected(mCurDevice);
                    disConnGatt();//关闭连接
                    Log.e(TAG, "STATE_DISCONNECTED");
                    break;
                case BluetoothProfile.STATE_CONNECTING:
                    System.out.println("正在连接。。。。。。。。。。。。。");
                    mListener.onConnecting(mCurDevice);
                    Log.e(TAG, "STATE_CONNECTING");
                    break;
                case BluetoothProfile.STATE_DISCONNECTING:
                    System.out.println("丢失连接中。。。。。。。。。。。。。");
                    mListener.onDisConnecting(mCurDevice);
                    Log.e(TAG, "STATE_DISCONNECTING");
                    break;
            }
            super.onConnectionStateChange(gatt, status, newState);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.d(TAG, "onServicesDiscovered");
            if (status == BluetoothGatt.GATT_SUCCESS) {
                serviceList = gatt.getServices();
                for (int i = 0; i < serviceList.size(); i++) {
                    BluetoothGattService theService = serviceList.get(i);

                    Log.e(TAG, "ServiceName:" + theService.getUuid());
                    characterList = theService.getCharacteristics();
                    for (int j = 0; j < characterList.size(); j++) {
                        String uuid = characterList.get(j).getUuid().toString();
                        Log.e(TAG, "---CharacterName:" + uuid);
                        if (uuid.equals(CHARACTER_UUID1)) {
                            character1 = characterList.get(j);
                        } else if (uuid.equals(CHARACTER_UUID2)) {
                            character2 = characterList.get(j);
                            setNotification();
                        }
                    }
                }
            }
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.e(TAG, "onCharacteristicRead");
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.e(TAG, "onCharacteristicWrite");
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            Log.e(TAG, "onCharacteristicChanged");
//            这里是可以监听到设备自身或者手机改变设备的一些数据修改h通知
            receiveData(characteristic);
            super.onCharacteristicChanged(gatt, characteristic);
        }
    };

    //获取设备指定的特征中的特性,其中对其进行监听, setCharacteristicNotification与上面的回调onCharacteristicChanged进行一一搭配
    private void setNotification() {
        mGatt.setCharacteristicNotification(character2, true);
        BluetoothGattDescriptor descriptor = character2.getDescriptor(UUID.fromString(DESCRIPTOR_UUID));
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        mGatt.writeDescriptor(descriptor);
    }

    //接收数据,对其进行处理
    private void receiveData(BluetoothGattCharacteristic ch) {
        byte[] bytes = ch.getValue();
        int cmd = bytes[0];
        int agree = bytes[1];
        switch (cmd) {
            case 1:
                mListener.onStrength(agree);
                Log.e(TAG, "手机通知BLE设备强度:" + agree);
                break;
            case 2:
                mListener.onModel(agree);
                Log.e(TAG, "工作模式:" + agree);
                break;
            case 3:
                mListener.onStrength(agree);
                Log.e(TAG, "设备自身通知改变强度:" + agree);
                break;
        }
    }

    //连接设备  listDevice  是新的搜查到的
    public void connectLeDevice(int devicePos) {
        mBtAdapter.stopLeScan(mLeScanCallback);
        mCurDevice = listDevice.get(devicePos);
        checkConnGatt();
    }

    //连接设备  listDevice_pairde  已经配对的
    public void connectLeDevice_paired(int devicePos) {
//        mBtAdapter.stopLeScan(mLeScanCallback);
        mCurDevice = listDevice_pairde.get(devicePos);

        System.out.println("连接设备"+ mCurDevice.getName());
        checkConnGatt();
    }

    //发送进入工作模式请求
    public void sendWorkModel() {
        if (character1 != null) {
            character1.setValue(workModel);
            mGatt.writeCharacteristic(character1);
        }
    }

    //发送强度
    public void sendStrength(int strength) {
        byte[] strengthModel = {0x01, (byte) strength};
        if (character1 != null) {
            character1.setValue(strengthModel);
            mGatt.writeCharacteristic(character1);
        }
    }

    //检查设备是否连接了
    private void checkConnGatt() {
        if (mGatt == null) {
            //connect device
            Log.e(TAG,mCurDevice.getName());
            System.out.println("----------------------------------");
            mGatt = mCurDevice.connectGatt(mContext, false, mGattCallback);
            mListener.onConnecting(mCurDevice);
        } else {
            //出现133错误
            System.out.println("++++++++++++++++++++++++++++++++++");
//            mGatt.close();
//            mGatt = mCurDevice.connectGatt(mContext, false, mGattCallback);

            mGatt.connect();
            mGatt.discoverServices();
        }
    }

    //  断开设备连接
    private void disConnGatt() {
        if (mGatt != null) {
            mGatt.disconnect();
            mGatt.close();
            mGatt = null;
            listDevice = new ArrayList<>();
            mListener.onLeScanDevices(listDevice);
        }
    }

    private void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void setBTUtilListener(BTUtilListener listener) {
        mListener = listener;
    }

    //蓝牙工具类回调监听
    public interface BTUtilListener {
        void onLeScanStart(); // 扫描开始

        void onLeScanStop();  // 扫描停止

        void onLeScanDevices(List<BluetoothDevice> listDevice); //扫描得到的设备

        void onConnected(BluetoothDevice mCurDevice); //设备的连接

        void onDisConnected(BluetoothDevice mCurDevice); //设备断开连接

        void onConnecting(BluetoothDevice mCurDevice); //设备连接中

        void onDisConnecting(BluetoothDevice mCurDevice); //设备连接失败

        void onStrength(int strength); //给设备设置强度

        void onModel(int model); //设备模式
    }

    //打开蓝牙
    public void connectBle() {
        mBtAdapter.enable();
    }

    //关闭蓝牙
    public void disConnectBle() {
        mBtAdapter.disable();
    }


    public List<BluetoothDevice> pairdeDevices() {
        Set<BluetoothDevice> bondedDevices = mBtAdapter.getBondedDevices();
        if (bondedDevices.size() > 0) {
            listDevice_pairde = new ArrayList<>(bondedDevices);
            return listDevice_pairde;
        }
        return new ArrayList<>();
    }




    private ScanCallback leCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    BluetoothDevice device = result.getDevice();
                    if (!listDevice.contains(device)) {
                        //不重复添加
                        listDevice.add(device);
                        mListener.onLeScanDevices(listDevice);
                        Log.e(TAG, "device:" + device.toString());
                    }
                }
            });
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);

        }
    };


    //开始扫描BLE设备
    private void startScan_5() {

        //android5.0  以后的
//        BluetoothLeScanner bluetoothLeScanner = mBtAdapter.getBluetoothLeScanner();
        bluetoothLeScanner.startScan(leCallback);
        mListener.onLeScanStart();
    }

    //停止扫描BLE设备
    private void stopScan_5() {
       bluetoothLeScanner.stopScan(leCallback);
        mListener.onLeScanStop();
    }
}
