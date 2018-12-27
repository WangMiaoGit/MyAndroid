package com.wang.myandroid.utils;

import android.util.Log;

/**
 * Created by MaxWang on 2018/12/5.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/5 11:48
 * 修改人  ：MaxWang
 * 修改时间：2018/12/5
 * 修改备注：
 */

public class LogUtil {
    //开关
    public static final boolean BEBUG = true;
    //TAG
    public static final String TAG = "MyAndroid";
    //等级
    public static void d(String text){
        if (BEBUG){
            Log.d(TAG,text);
        }
    }
    public static void i(String text){
        if (BEBUG){
            Log.i(TAG,text);
        }
    }
    public static void w(String text){
        if (BEBUG){
            Log.w(TAG,text);
        }
    }
    public static void e(String text){
        if (BEBUG){
            Log.e(TAG,text);
        }
    }
}
