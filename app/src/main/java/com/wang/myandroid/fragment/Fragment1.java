package com.wang.myandroid.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.wang.myandroid.R;
import com.wang.myandroid.adapter.WeChatAdapter;
import com.wang.myandroid.entity.WeChatData;
import com.wang.myandroid.ui.WebViewActivity;
import com.wang.myandroid.utils.LogUtil;
import com.wang.myandroid.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

public class Fragment1 extends LazyFragment {


    private ListView mListView;
    private TextView mTextview;
    private List<WeChatData> mList = new ArrayList<>();
    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    public Fragment1() {
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_two, container, false);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mListView = view.findViewById(R.id.mListView_fragment_two);
        mTextview = view.findViewById(R.id.text_tip);

        mListView.setEmptyView(mTextview);

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title",mListTitle.get(position));
                intent.putExtra("url",mListUrl.get(position));
                startActivity(intent);
//                Bundle bundle = new Bundle();
//                bundle.putString("key","value");
//                intent.putExtras(bundle)
            }
        });

    }

    @Override
    protected void initData() {
        //解析接口
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY+"&&ps=10";
        LogUtil.d(url);
        //做分页的时候传入page数和当前显示的条目数   做url传递
//        String url = "http://v.juhe.cn.weixin/query?key=" + StaticClass.WECHAT_KEY+"&&ps=100";//100条数据 接口设定
        RxVolley.get(url, new HttpCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(String t) {
//                Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
                LogUtil.d(t);
                parsingJson(t);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void parsingJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                WeChatData data = new WeChatData();
                String title = json.getString("title");
                String url = json.getString("url");
                data.setTitle(title);
                data.setSource(json.getString("source"));
                data.setImgUrl(json.getString("firstImg"));

                mList.add(data);

                mListTitle.add(title);
                mListUrl.add(url);
            }


            WeChatAdapter weChatAdapter = new WeChatAdapter(getContext(), mList);
            mListView.setAdapter(weChatAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }
}
