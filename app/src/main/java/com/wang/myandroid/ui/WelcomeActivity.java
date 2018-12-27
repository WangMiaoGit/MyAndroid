package com.wang.myandroid.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wang.myandroid.MainActivity;
import com.wang.myandroid.R;
import com.wang.myandroid.utils.ShareUtil;
import com.wang.myandroid.utils.StaticClass;
import com.wang.myandroid.utils.UtilTools;

/**
 * 首页
 *      展现个人公司
 *      广告
 *      初始化数据
 *      自定义字体
 */
public class WelcomeActivity extends AppCompatActivity {

    /**
     * 1,延时2000ms
     * 2.判断是否第一次运行
     * 3.自定义字体
     * 4.全屏
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是否第一次运行
                    if (isFirst()){
                        startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                    }else {
                        startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                    }
                    WelcomeActivity.this.finish();
                    break;
            }
        }
    };


    private TextView mTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
    }

    private void initView() {
        //延时两秒
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,1000);
        mTextview = this.findViewById(R.id.tv_welcome);
        //设置字体
        UtilTools.setFont(this,mTextview);
    }

    //程序是否第一次运行
    private boolean isFirst() {
        Boolean isFirst = ShareUtil.getBoolean(StaticClass.SHARE_IS_FIRST, true);
        if (isFirst){
            //第一次运行了，设置SharedPreferences值为false
            ShareUtil.putBoolean(StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 禁止返回键
     */
    @Override
    public void onBackPressed() {

    }
}
