package com.wang.myandroid.ui;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.wang.myandroid.R;

public class ShowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //找到动画
            final AnimatedVectorDrawable anim1 = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim);
            final ImageView img1 = findViewById(R.id.img1);
            img1.setImageDrawable(anim1);
            anim1.start();
        }
    }
}
