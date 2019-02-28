package com.wang.myandroid;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


//import com.tencent.bugly.crashreport.CrashReport;
import com.wang.myandroid.fragment.Fragment0ne;
import com.wang.myandroid.fragment.Fragment1;
import com.wang.myandroid.fragment.Fragment2;
import com.wang.myandroid.fragment.FragmentFive;
import com.wang.myandroid.fragment.FragmentFour;
import com.wang.myandroid.fragment.FragmentThree;
import com.wang.myandroid.fragment.FragmentTwo;
import com.wang.myandroid.ui.BaseActivity;
import com.wang.myandroid.ui.SettingActivity;
import com.wang.myandroid.utils.LogUtil;
import com.wang.myandroid.utils.ShareUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitles;
    private List<Fragment> mFragments;
    private FloatingActionButton mFloatingActionButton;

    private WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        LogUtil.d(width+"\n"+height);
        //去掉阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    //初始化数据
    private void initData() {
        mTitles = new ArrayList<>();
        mTitles.add(getResources().getString(R.string.title_one));
        mTitles.add(getResources().getString(R.string.title_two));
        mTitles.add(getResources().getString(R.string.title_three));
        mTitles.add(getResources().getString(R.string.title_four));
        mTitles.add(getResources().getString(R.string.title_five));

        mFragments = new ArrayList<>();
        mFragments.add(new Fragment0ne());
        mFragments.add(new Fragment1());
        mFragments.add(new FragmentThree());
        mFragments.add(new FragmentFour());
        mFragments.add(new FragmentFive());
    }

    //初始化控件
    private void initView() {
        mTabLayout = this.findViewById(R.id.mTableLayout);
        mViewPager = this.findViewById(R.id.mViewPager);
        mFloatingActionButton = this.findViewById(R.id.fab_setting);

        //mFloatingActionButton点击事件
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
        //默认是第一页不显示
        mFloatingActionButton.setVisibility(View.GONE);
        //预加载
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
            //设置标题

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
        //ViewPager的滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.d(position+"");
                if (position==0){
                    mFloatingActionButton.setVisibility(View.GONE);
                }else {
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


}
