package com.wang.myandroid.ui;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wang.myandroid.MainActivity;
import com.wang.myandroid.R;
import com.wang.myandroid.utils.LogUtil;
import com.wang.myandroid.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    //viewpager中的存放view的list
    private List<View> mList = new ArrayList<>();
    //viewpager中的view
    private View view1, view2, view3;
    //小圆点
    private ImageView point1, point2, point3;
    //跳过的图片
    private ImageView back;
    //自定义Dialog
    private CustomDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();

    }

    //初始化View
    private void initView() {

        back = findViewById(R.id.iv_back);

        back.setOnClickListener(this);

        point1 = findViewById(R.id.point1);
        point2 = findViewById(R.id.point2);
        point3 = findViewById(R.id.point3);
        setPointImg(true, false, false);

        mViewPager = findViewById(R.id.guide_viewPager);

        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);

        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        mViewPager.setAdapter(new GuideAdapter());

        //viewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //PAGER切换
            @Override
            public void onPageSelected(int position) {
                LogUtil.i("position:" + position);
                switch (position) {
                    case 0:
                        setPointImg(true, false, false);
                        back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false, true, false);
                        back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false, false, true);
                        back.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mDialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //无法取消
        mDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //两个点击事件相同
            case R.id.btn_start:
            case R.id.iv_back:
                mDialog.show();
//
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                mDialog.dismiss();
                finish();
                break;

        }
    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mList.get(position));
//            super.destroyItem(container, position, object);
        }
    }

    //设置小圆点的选中效果
    private void setPointImg(boolean isCheckde1, boolean isCheckde2, boolean isCheckde3) {
        if (isCheckde1) {

            point1.setBackgroundResource(R.drawable.point_on);
        } else {

            point1.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheckde2) {

            point2.setBackgroundResource(R.drawable.point_on);
        } else {

            point2.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheckde3) {

            point3.setBackgroundResource(R.drawable.point_on);
        } else {

            point3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
