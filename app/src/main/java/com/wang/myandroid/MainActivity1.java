package com.wang.myandroid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

public class MainActivity1 extends AppCompatActivity {

    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        sendNormalNotification();

        sendProgressNotification();
    }

    //获取系统服务
    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
    //兼容android 8.0已经之前版本
    public Notification.Builder getNotificationBuilder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id",
                    "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            channel.canBypassDnd();//可否绕过请勿打扰模式
            channel.enableLights(true);//闪光
            channel.setLockscreenVisibility(RECEIVER_VISIBLE_TO_INSTANT_APPS);//锁屏显示
            channel.setLightColor(Color.RED);//指定闪光时的灯光颜色
            channel.canShowBadge();//消息角标
            channel.enableVibration(true);//是否震动
            channel.getAudioAttributes();//获取系统通知响铃配置
            channel.getGroup();//获取通知渠道组
            channel.setBypassDnd(true);//可绕过
            channel.setVibrationPattern(new long[]{100, 100, 200});//震动模式

            getManager().createNotificationChannel(channel);
        }

//        Notification notification = new Notification.Builder(this)
//                .setAutoCancel(true)
//                .setChannelId("channel_id")//绑定了channel的channel_id
//                .setContentTitle("new Info")
//                .setContentText("You have an information!")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .build();

        return new Notification.Builder(this)
                .setAutoCancel(true)
                .setChannelId("channel_id")//绑定了channel的channel_id
                .setContentTitle("new Info")
                .setContentText("You have an information!")
                .setSmallIcon(R.mipmap.ic_launcher);
    }

    //普通通知
    private void sendNormalNotification() {
        Notification.Builder builder = getNotificationBuilder();

        getManager().notify(1, builder.build());
    }
    //进度条通知
    private void sendProgressNotification() {
        Notification.Builder builder = getNotificationBuilder();
        builder.setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);//第一次带声音
        getManager().notify(2, builder.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(1000);
                        builder.setDefaults(Notification.FLAG_ONLY_ALERT_ONCE);
                        builder.setProgress(100, i,false);

                        //带下载进度的notifiction
                        getManager().notify(2,builder.build());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
    //自定义通知
    private void sendCustomNotification(){
        Notification.Builder builder = getNotificationBuilder();
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.layout_custom_notification);

        remoteViews.setTextViewText(R.id.textView,"custom_title");
        remoteViews.setTextViewText(R.id.textView2,"custom_content");
        //即将要发生的意图 可以被取消和更新
        Intent intent = new Intent(this,NotificationIntentActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                -1,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.button,pendingIntent);

//        remoteViews.setOnClickFillInIntent(R.id.button,intent);//带列表的
        builder.setCustomContentView(remoteViews);

        getManager().notify(3,builder.build());
        getManager().cancel(3);//通知的管理--删除
    }
}
