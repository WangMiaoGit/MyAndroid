package com.wang.myandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wang.myandroid.application.App;

/**
 * Created by MaxWang on 2018/12/5.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/5 12:01
 * 修改人  ：MaxWang
 * 修改时间：2018/12/5
 * 修改备注：
 */

public class ShareUtil {


    private static ShareUtil util;
    private static SharedPreferences sp;


    private ShareUtil(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 初始化SharedPreferencesUtil,只需要初始化一次，建议在Application中初始化
     *
     * @param context 上下文对象
     * @param name    SharedPreferences Name
     */
    public static void getInstance(Context context, String name) {
        if (util == null) {
            util = new ShareUtil(context, name);
        }
    }


    //存入String类型 键 值
    public static void putString(String key,String value) {
        sp.edit().putString(key,value).commit();
    }
    //获取String类型  默认值
    public static String getString (String key,String defValue){
        return sp.getString(key,defValue);
    }
    //设置int类型
    public static void putInt(String key,int value) {

        sp.edit().putInt(key,value).commit();
    }
    public static int getInt (String key,int defValue){
        return sp.getInt(key,defValue);

    }
    //设置boolean类型
    public static void putBoolean(String key,boolean value) {
        sp.edit().putBoolean(key,value).commit();
    }
    public static boolean getBoolean (String key,boolean defValue){
        return sp.getBoolean(key,defValue);
    }
    //删除单个
    public static void deleteOne(String key){
        sp.edit().remove(key).commit();
    }
    //删除全部
    public static void deleteAll(String key){
        sp.edit().clear().apply();
    }
}
