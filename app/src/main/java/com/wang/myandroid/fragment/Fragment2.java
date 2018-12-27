package com.wang.myandroid.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;
import com.wang.myandroid.R;
import com.wang.myandroid.adapter.GridAdapter;
import com.wang.myandroid.adapter.WeChatAdapter;
import com.wang.myandroid.entity.GirlData;
import com.wang.myandroid.entity.WeChatData;
import com.wang.myandroid.ui.WebViewActivity;
import com.wang.myandroid.utils.LogUtil;
import com.wang.myandroid.utils.PicassoUtil;
import com.wang.myandroid.utils.StaticClass;
import com.wang.myandroid.view.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by MaxWang on 2018/12/27.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/27 9:13
 * 修改人  ：MaxWang
 * 修改时间：2018/12/27
 * 修改备注：
 */

public class Fragment2 extends LazyFragment {
    //列表
    private GridView mGridView;
    //数据
    private List<GirlData> mList = new ArrayList<>();
    //适配器
    private GridAdapter mAdapter;
    //提示框
    private CustomDialog dialog;
    //预览图片
    private ImageView iv_img;
    //图片地址的数据
    private List<String> mListUrl = new ArrayList<>();
    //PhotoView 可拖拽图片
    private PhotoViewAttacher mAttacher;


    public Fragment2() {
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_three, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mGridView = view.findViewById(R.id.mGridView);
        TextView textView = view.findViewById(R.id.tv_empty);
        mGridView.setEmptyView(textView);

        //初始化提示框  点击图片弹出的可缩放 框
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl,
                R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);

        iv_img = dialog.findViewById(R.id.iv_img);


        //监听点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解析图片
                PicassoUtil.loadImgView(mListUrl.get(position), iv_img);
                //缩放
                mAttacher = new PhotoViewAttacher(iv_img);
                //刷新
                mAttacher.update();
                dialog.show();
            }
        });
    }

    @Override
    protected void initData() {

        System.out.println("等待产生数据");
        String welfare = null;
        try {
            //Gank升級 需要转码
            welfare = URLEncoder.encode(getString(R.string.text_welfare), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**
         * http://gank.io/api/search/query/listview/category/Android/count/10/page/1
         * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
         * count 最大 50
         *
         * http://gank.io/api/data/福利/5/1
         *
         *
         */
        String url1 = "https://gank.io/api/data/";
        String url2 = "/20/2";
        String url = url1 + welfare + url2;
        LogUtil.d(url);
        //解析
        RxVolley.get(url, new HttpCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(String t) {
                LogUtil.i("Girl Json:" + t);
                parsingJson(t);
            }

            @Override
            public void onFailure(VolleyError error) {
                LogUtil.e(error.toString());
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mListUrl.add(url);

                GirlData data = new GirlData();
                data.setImgUrl(url);
                mList.add(data);
            }
            mAdapter = new GridAdapter(getActivity(), mList);
            //设置适配器
            mGridView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }
}
