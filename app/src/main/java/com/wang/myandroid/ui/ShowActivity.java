package com.wang.myandroid.ui;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.wang.myandroid.R;

public class ShowActivity extends BaseActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        imageView = findViewById(R.id.image_anim);

        showShakeAnimation(imageView, 200);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //找到动画
//            final AnimatedVectorDrawable anim1 = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim);
//            final ImageView img1 = findViewById(R.id.img1);
//            img1.setImageDrawable(anim1);
//            anim1.start();
//        }
    }

    private void showShakeAnimation(final View view, final int y) {
        if (y <= 0) {
            return;
        }
        TranslateAnimation anim = new TranslateAnimation(0,
                0, 0, -y);
        anim.setInterpolator(new CycleInterpolator(1f));
        anim.setDuration(1000);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showShakeAnimation(view, y /2);//循环跳动
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(anim);
    }

}
