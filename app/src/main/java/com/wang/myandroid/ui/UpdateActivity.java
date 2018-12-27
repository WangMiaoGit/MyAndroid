package com.wang.myandroid.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;
import com.wang.myandroid.R;
import com.wang.myandroid.utils.LogUtil;
import com.wang.myandroid.utils.StringUtil;

import java.io.File;


/**
 * 更新
 */
public class UpdateActivity extends BaseActivity {

    //正在下载
    public static final int HANDLER_LOOING = 10001;
    //下载完成
    public static final int HANDLER_OK = 10002;
    //下载失败
    public static final int HANDLER_NO = 10003;
    private TextView tv_size;
    private NumberProgressBar numberProgressBar;
    private String url;
    private String path;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_LOOING:
                    Bundle bundle = msg.getData();
                    long transferredBytes = bundle.getLong("transferredBytes");
                    long totalSize = bundle.getLong("totalSize");
                    //实时更新进度
                    tv_size.setText(transferredBytes + " / " + totalSize);
                    //给numberProgressBar进度条设置进度

                    numberProgressBar.setProgress((int) (((float) transferredBytes / (float) totalSize) * 100));

                    break;
                case HANDLER_OK:
                    tv_size.setText("下载成功");
                    //启动应用安装
                    startInstallApk();
                    break;
                case HANDLER_NO:
                    tv_size.setText("下载成功");
                    break;

            }
        }
    };

    //安装应用
    private void startInstallApk() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initView();
    }

    private void initView() {
        tv_size = findViewById(R.id.tv_size);
        numberProgressBar = findViewById(R.id.number_progress_bar);
        numberProgressBar.setMax(100);

        //存放的地址
        path = FileUtils.getSDCardPath() + "/" + System.currentTimeMillis() + ".apk";

        url = getIntent().getStringExtra("url");
        if (StringUtil.isEmpty(url)) {
            //下载
            RxVolley.download(path, url, new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    LogUtil.i("transferredBytes:" + transferredBytes + " totalSize:" + totalSize);
                    Message message = new Message();
                    message.what = HANDLER_LOOING;
                    Bundle b = new Bundle();
                    b.putLong("transferredBytes", transferredBytes);
                    b.putLong("totalSize", totalSize);
                    message.setData(b);

                    mHandler.sendMessage(message);
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    LogUtil.i("成功");
                    mHandler.sendEmptyMessage(HANDLER_OK);
                }

                @Override
                public void onFailure(VolleyError error) {
                    LogUtil.e("失败");
                    mHandler.sendEmptyMessage(HANDLER_NO);
                }
            });
        }
    }
}


