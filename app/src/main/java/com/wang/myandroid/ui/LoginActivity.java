package com.wang.myandroid.ui;

/*
 *  项目名：  SmartButler 
 *  包名：    com.imooc.smartbutler.ui
 *  文件名:   LoginActivity
 *  创建者:   LGL
 *  创建时间:  2016/11/1 1:42
 *  描述：    登录
 */
/**
 * 视频资源要添加res文件夹下创建raw文件夹
 * 需要在onRestart()方法里重新加载视频，防止退出返回时视频黑屏
 * 我这样写简单粗暴而已，当然，也可优雅的以自己看播放控件的VideoView处理方法，去处理资源释放和播放显示的问题。
 * 记得修改布局控件<com.daqie.videobackground.CustomVideoView...引用的包名，不然会报错哦
 * android:screenOrientation="portrait" 习惯性的把横竖屏切换也设置一下
 * android:theme="@style/Theme.AppCompat.Light.NoActionBar" ActionBar也可以设置成不显示的状态，可以根据自己喜好和项目需求
 */
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.myandroid.MainActivity;
import com.wang.myandroid.R;
import com.wang.myandroid.utils.ShareUtil;
import com.wang.myandroid.view.CustomDialog;
import com.wang.myandroid.view.CustomVideoView;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_registered;
    private EditText et_name;
    private EditText et_password;
    private Button btnLogin;
    private CheckBox keep_password;

    private TextView tv_forget;
    private CustomDialog dialog;

    private CustomVideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //加载数据
        initVideo();
    }

    private void initVideo() {
        //加载视频资源控件
//        videoView = (CustomVideoView) findViewById(R.id.videoview);
        //设置播放加载路径
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background_gif));
        //播放
        videoView.start();
        //循环播放
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }

    private void initView() {

        btn_registered =  findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);
        et_name =  findViewById(R.id.et_name);
        et_password =  findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        keep_password = findViewById(R.id.keep_password);
        tv_forget = findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        boolean isKeep = ShareUtil.getBoolean( "keeppass", false);
        keep_password.setChecked(isKeep);

        //登录的等待框
        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        dialog.setCancelable(false);

        if(isKeep){
            String name = ShareUtil.getString( "name", "");
            String password = ShareUtil.getString( "password", "");
            et_name.setText(name);
            et_password.setText(password);
        }

        //加载视频资源控件
        videoView = findViewById(R.id.videoview);
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        initVideo();
        super.onRestart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btnLogin:
/*                //1.获取输入框的值
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    dialog.show();
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if (e == null) {
                                //判断邮箱是否验证
                                if (user.getEmailVerified()) {
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }*/
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
        }
    }


    @Override
    protected void onStop() {
        videoView.stopPlayback();
        super.onStop();
    }

    //假设我现在输入用户名和密码，但是我不点击登录，而是直接退出了
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存状态
        ShareUtil.putBoolean( "keeppass", keep_password.isChecked());

        //是否记住密码
        if (keep_password.isChecked()) {
            //记住用户名和密码
            ShareUtil.putString( "name", et_name.getText().toString().trim());
            ShareUtil.putString( "password", et_password.getText().toString().trim());
        } else {
            ShareUtil.deleteOne( "name");
            ShareUtil.deleteOne( "password");
        }
    }
}
