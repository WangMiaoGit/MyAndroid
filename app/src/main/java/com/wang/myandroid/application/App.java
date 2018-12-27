package com.wang.myandroid.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

//import com.tencent.bugly.crashreport.CrashReport;
import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.wang.myandroid.utils.ShareUtil;
import com.wang.myandroid.utils.StaticClass;


/**
 * Created by MaxWang on 2018/11/29.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/11/29 16:07
 * 修改人  ：MaxWang
 * 修改时间：2018/11/29
 * 修改备注：
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        //CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, false);
        ShareUtil.getInstance(this.getApplicationContext(), StaticClass.NAME);

        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=" + StaticClass.VOICE_KEY);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }


}
