package com.wang.myandroid.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.wang.myandroid.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * Created by MaxWang on 2018/12/22.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/22 19:27
 * 修改人  ：MaxWang
 * 修改时间：2018/12/22
 * 修改备注：
 */

/**
 * 二维码
 *
 */
public class QrCodeActivity extends BaseActivity{
    //我的二维码
    private ImageView iv_qr_code;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initView();
    }

    private void initView() {
        iv_qr_code = findViewById(R.id.iv_qr_code);
        //屏幕的宽
        int width = getResources().getDisplayMetrics().widthPixels;

        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是智能管家", width / 2, width / 2,
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_qr_code.setImageBitmap(qrCodeBitmap);
    }
}
