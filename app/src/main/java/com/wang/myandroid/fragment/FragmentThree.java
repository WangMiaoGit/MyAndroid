package com.wang.myandroid.fragment;


import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wang.myandroid.R;
import com.wang.myandroid.adapter.GridAdapter;
import com.wang.myandroid.entity.GirlData;
import com.wang.myandroid.utils.HttpUtil;
import com.wang.myandroid.utils.LogUtil;
import com.wang.myandroid.utils.PicassoUtil;
import com.wang.myandroid.view.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment {

    private static final String TAG = "FragmentThree";
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

    private SmartRefreshLayout smartRefreshLayout;

    private  int page = 1;
    private  int ref_page = 1;
    private  int pre_page = 0;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    pre_page = ref_page;
                    Random random = new Random();
                    int i = random.nextInt(10)+1;
                    while (i==pre_page){
                        i = random.nextInt(10)+1;
                    }
                    ref_page = i;
                    break;
                case 1:

                    page++;
                    break;
            }
        }
    };

    /**
     * 1.监听点击事件
     * 2.提示框
     * 3.加载图片
     * 4.PhotoView
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_three, null);


        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {

        smartRefreshLayout = view.findViewById(R.id.refreshLayout);
        mGridView = view.findViewById(R.id.mGridView);
        TextView textView = new TextView(getContext());
        textView.setText("暂无数据");

//        TextView textView = view.findViewById(R.id.tv_empty);
        mGridView.setEmptyView(textView);

        //初始化提示框  点击图片弹出的可缩放 框
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl,
                R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);

        iv_img = dialog.findViewById(R.id.iv_img);

        getPic(ref_page, 1);


        mAdapter = new GridAdapter(getActivity(), mList);
        //设置适配器
        mGridView.setAdapter(mAdapter);

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

        //设置 Header 为 贝塞尔雷达 样式
//        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
//        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败


                getPic(ref_page, 0);
                Log.d(TAG, "刷新");
                System.out.println("刷新到第" + page + "页");
//                mAdapter.notifyDataSetChanged();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                getPic(page, 1);
//                mAdapter.notifyDataSetChanged();
                Log.d(TAG, "加载");
                System.out.println("加载到第" + page + "页");
            }
        });
    }

    private void getPic(int i, int model) {
        if (model == 0) {
            mList.clear();
            mListUrl.clear();
        }
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
        String url2 = "/20/" + i;
        String url = url1 + welfare + url2;
        LogUtil.d("加载的url======" + url);
        //解析
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                LogUtil.i("Girl Json:" + t);
                parsingJson(t, model);
            }

            @Override
            public void onFailure(VolleyError error) {
                LogUtil.e(error.toString());
            }
        });
    }

    //解析Json
    private void parsingJson(String t, int model) {
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
            if (model == 0) {
                myHandler.sendEmptyMessage(0);
            } else {
                myHandler.sendEmptyMessage(1);
            }
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //没有解决301的问题
    private String http(String path) {


        LogUtil.e("1111111111111");
        try {
            URL url = new URL(path.trim());
            //打开连接
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setInstanceFollowRedirects(false);


            LogUtil.e("22222222222222");

            int code = urlConnection.getResponseCode();
            LogUtil.e("3333333333333333");
            System.out.println(code);
            if (200 == code) {
                //得到输入流
                InputStream is = urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while (-1 != (len = is.read(buffer))) {
                    baos.write(buffer, 0, len);
                    baos.flush();
                }
                LogUtil.e("4444444444444444");
                return baos.toString("utf-8");
            } else {
                String location = urlConnection.getHeaderField("Location");
                System.out.println("location" + location + "");
            }
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
        }

        return "no string";
    }

   /* private String jsonString = "{\n" +
            "    \"error\": false,\n" +
            "    \"results\": [\n" +
            "        {\n" +
            "            \"_id\": \"5c12216d9d21223f5a2baea2\",\n" +
            "            \"createdAt\": \"2018-12-13T09:07:57.2Z\",\n" +
            "            \"desc\": \"2018-12-13\",\n" +
            "            \"publishedAt\": \"2018-12-13T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5bfe1a5b9d2122309624cbb7\",\n" +
            "            \"createdAt\": \"2018-11-28T04:32:27.338Z\",\n" +
            "            \"desc\": \"2018-11-28\",\n" +
            "            \"publishedAt\": \"2018-11-28T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5bf22fd69d21223ddba8ca25\",\n" +
            "            \"createdAt\": \"2018-11-19T03:36:54.950Z\",\n" +
            "            \"desc\": \"2018-11-19\",\n" +
            "            \"publishedAt\": \"2018-11-19T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5be14edb9d21223dd50660f8\",\n" +
            "            \"createdAt\": \"2018-11-06T08:20:43.656Z\",\n" +
            "            \"desc\": \"2018-11-06\",\n" +
            "            \"publishedAt\": \"2018-11-06T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqgy1fwyf0wr8hhj30ie0nhq6p.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5bcd71979d21220315c663fc\",\n" +
            "            \"createdAt\": \"2018-10-22T06:43:35.440Z\",\n" +
            "            \"desc\": \"2018-10-22\",\n" +
            "            \"publishedAt\": \"2018-10-22T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5bc434ac9d212279160c4c9e\",\n" +
            "            \"createdAt\": \"2018-10-15T06:33:16.497Z\",\n" +
            "            \"desc\": \"2018-10-15\",\n" +
            "            \"publishedAt\": \"2018-10-15T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqly1fw8wzdua6rj30sg0yc7gp.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5bbb0de09d21226111b86f1c\",\n" +
            "            \"createdAt\": \"2018-10-08T07:57:20.978Z\",\n" +
            "            \"desc\": \"2018-10-08\",\n" +
            "            \"publishedAt\": \"2018-10-08T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqly1fw0vdlg6xcj30j60mzdk7.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5ba206ec9d2122610aba3440\",\n" +
            "            \"createdAt\": \"2018-09-19T08:21:00.295Z\",\n" +
            "            \"desc\": \"2018-09-19\",\n" +
            "            \"publishedAt\": \"2018-09-19T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqly1fvexaq313uj30qo0wldr4.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b9771a29d212206c1b383d0\",\n" +
            "            \"createdAt\": \"2018-09-11T07:41:22.491Z\",\n" +
            "            \"desc\": \"2018-09-11\",\n" +
            "            \"publishedAt\": \"2018-09-11T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqly1fv5n6daacqj30sg10f1dw.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b830bba9d2122031f86ee51\",\n" +
            "            \"createdAt\": \"2018-08-27T04:21:14.703Z\",\n" +
            "            \"desc\": \"2018-08-27\",\n" +
            "            \"publishedAt\": \"2018-08-28T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqly1fuo54a6p0uj30sg0zdqnf.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b7b836c9d212201e982de6e\",\n" +
            "            \"createdAt\": \"2018-08-21T11:13:48.989Z\",\n" +
            "            \"desc\": \"2018-08-21\",\n" +
            "            \"publishedAt\": \"2018-08-21T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqly1fuh5fsvlqcj30sg10onjk.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b74e9409d21222c52ae4cb4\",\n" +
            "            \"createdAt\": \"2018-08-16T11:02:24.289Z\",\n" +
            "            \"desc\": \"2018-08-16\",\n" +
            "            \"publishedAt\": \"2018-08-16T00:00:00.0Z\",\n" +
            "            \"source\": \"api\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ws1.sinaimg.cn/large/0065oQSqly1fubd0blrbuj30ia0qp0yi.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b7102749d2122341d563844\",\n" +
            "            \"createdAt\": \"2018-08-13T12:00:52.458Z\",\n" +
            "            \"desc\": \"2018-08-13\",\n" +
            "            \"publishedAt\": \"2018-08-13T00:00:00.0Z\",\n" +
            "            \"source\": \"api\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqly1fu7xueh1gbj30hs0uwtgb.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b6bad449d21226f45755582\",\n" +
            "            \"createdAt\": \"2018-08-09T10:56:04.962Z\",\n" +
            "            \"desc\": \"2018-08-09\",\n" +
            "            \"publishedAt\": \"2018-08-09T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqgy1fu39hosiwoj30j60qyq96.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b67b7fd9d2122195bdbd806\",\n" +
            "            \"createdAt\": \"2018-08-06T10:52:45.809Z\",\n" +
            "            \"desc\": \"2018-08-06\",\n" +
            "            \"publishedAt\": \"2018-08-06T00:00:00.0Z\",\n" +
            "            \"source\": \"api\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqly1ftzsj15hgvj30sg15hkbw.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b63cd4e9d21225e0d3f58c9\",\n" +
            "            \"createdAt\": \"2018-08-03T11:34:38.672Z\",\n" +
            "            \"desc\": \"2018-08-03\",\n" +
            "            \"publishedAt\": \"2018-08-03T00:00:00.0Z\",\n" +
            "            \"source\": \"api\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqgy1ftwcw4f4a5j30sg10j1g9.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b6151509d21225206860f08\",\n" +
            "            \"createdAt\": \"2018-08-01T14:21:04.556Z\",\n" +
            "            \"desc\": \"2018-08-01\",\n" +
            "            \"publishedAt\": \"2018-08-01T00:00:00.0Z\",\n" +
            "            \"source\": \"api\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqly1ftu6gl83ewj30k80tites.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b60356a9d212247776a2e0e\",\n" +
            "            \"createdAt\": \"2018-07-31T18:09:46.825Z\",\n" +
            "            \"desc\": \"2018-07-31\",\n" +
            "            \"publishedAt\": \"2018-07-31T00:00:00.0Z\",\n" +
            "            \"source\": \"api\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqgy1ftt7g8ntdyj30j60op7dq.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshan\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b5e93499d21220fc64181a9\",\n" +
            "            \"createdAt\": \"2018-07-30T12:25:45.937Z\",\n" +
            "            \"desc\": \"2018-07-30\",\n" +
            "            \"publishedAt\": \"2018-07-30T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqgy1ftrrvwjqikj30go0rtn2i.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b50107f421aa917a31c0565\",\n" +
            "            \"createdAt\": \"2018-07-19T12:15:59.226Z\",\n" +
            "            \"desc\": \"2018-07-19\",\n" +
            "            \"publishedAt\": \"2018-07-19T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqly1ftf1snjrjuj30se10r1kx.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b4eaae4421aa93aa99bee16\",\n" +
            "            \"createdAt\": \"2018-07-18T11:14:55.648Z\",\n" +
            "            \"desc\": \"2018-07-18\",\n" +
            "            \"publishedAt\": \"2018-07-18T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqly1ftdtot8zd3j30ju0pt137.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b481d01421aa90bba87b9ae\",\n" +
            "            \"createdAt\": \"2018-07-13T11:31:13.266Z\",\n" +
            "            \"desc\": \"2018-07-13\",\n" +
            "            \"publishedAt\": \"2018-07-13T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0073sXn7ly1ft82s05kpaj30j50pjq9v.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b456f5d421aa92fc4eebe48\",\n" +
            "            \"createdAt\": \"2018-07-11T10:45:49.246Z\",\n" +
            "            \"desc\": \"2018-07-11\",\n" +
            "            \"publishedAt\": \"2018-07-11T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqly1ft5q7ys128j30sg10gnk5.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b441f06421aa92fccb520a2\",\n" +
            "            \"createdAt\": \"2018-07-10T10:50:46.379Z\",\n" +
            "            \"desc\": \"2018-07-10\",\n" +
            "            \"publishedAt\": \"2018-07-10T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"https://ww1.sinaimg.cn/large/0065oQSqgy1ft4kqrmb9bj30sg10fdzq.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b42d1aa421aa92d1cba2918\",\n" +
            "            \"createdAt\": \"2018-07-09T11:08:26.162Z\",\n" +
            "            \"desc\": \"2018-07-09\",\n" +
            "            \"publishedAt\": \"2018-07-09T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1ft3fna1ef9j30s210skgd.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b3ed2d5421aa91cfe803e35\",\n" +
            "            \"createdAt\": \"2018-07-06T10:24:21.907Z\",\n" +
            "            \"desc\": \"2018-07-06\",\n" +
            "            \"publishedAt\": \"2018-07-06T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fszxi9lmmzj30f00jdadv.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b3d883f421aa906e5b3c6f1\",\n" +
            "            \"createdAt\": \"2018-07-05T10:53:51.361Z\",\n" +
            "            \"desc\": \"2018-07-05\",\n" +
            "            \"publishedAt\": \"2018-07-05T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsysqszneoj30hi0pvqb7.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b3ae394421aa906e7db029b\",\n" +
            "            \"createdAt\": \"2018-07-03T10:46:44.112Z\",\n" +
            "            \"desc\": \"2018-07-03\",\n" +
            "            \"publishedAt\": \"2018-07-03T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fswhaqvnobj30sg14hka0.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b398cf8421aa95570db5491\",\n" +
            "            \"createdAt\": \"2018-07-02T10:24:56.546Z\",\n" +
            "            \"desc\": \"2018-07-02\",\n" +
            "            \"publishedAt\": \"2018-07-02T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsvb1xduvaj30u013175p.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b33ccf2421aa95570db5478\",\n" +
            "            \"createdAt\": \"2018-06-28T01:44:18.488Z\",\n" +
            "            \"desc\": \"2018-06-28\",\n" +
            "            \"publishedAt\": \"2018-06-28T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsq9iq8ttrj30k80q9wi4.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b32807e421aa95570db5471\",\n" +
            "            \"createdAt\": \"2018-06-27T02:05:50.227Z\",\n" +
            "            \"desc\": \"2018-06-27\",\n" +
            "            \"publishedAt\": \"2018-06-27T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsp4iok6o4j30j60optbl.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b31aa33421aa9556d2cc4a7\",\n" +
            "            \"createdAt\": \"2018-06-26T10:51:31.60Z\",\n" +
            "            \"desc\": \"2018-06-26\",\n" +
            "            \"publishedAt\": \"2018-06-26T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsoe3k2gkkj30g50niwla.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b2f8847421aa9556b44c666\",\n" +
            "            \"createdAt\": \"2018-06-24T20:02:15.413Z\",\n" +
            "            \"desc\": \"2018-06-24\",\n" +
            "            \"publishedAt\": \"2018-06-25T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsmis4zbe7j30sg16fq9o.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b0d6ac0421aa97efda86560\",\n" +
            "            \"createdAt\": \"2018-05-29T22:59:12.622Z\",\n" +
            "            \"desc\": \"2018-06-02\",\n" +
            "            \"publishedAt\": \"2018-06-22T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1frslruxdr1j30j60ok79c.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b27c7aa421aa923c0fbfda0\",\n" +
            "            \"createdAt\": \"2018-06-18T22:54:34.199Z\",\n" +
            "            \"desc\": \"2018-06-19\",\n" +
            "            \"publishedAt\": \"2018-06-21T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1k9cb5j30sg0y7q61.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b27c7bf421aa923c0fbfda1\",\n" +
            "            \"createdAt\": \"2018-06-18T22:54:55.614Z\",\n" +
            "            \"desc\": \"2018-06-20\",\n" +
            "            \"publishedAt\": \"2018-06-20T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1ykabxj30k00pracv.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b27c7eb421aa923c43fe485\",\n" +
            "            \"createdAt\": \"2018-06-18T22:55:39.819Z\",\n" +
            "            \"desc\": \"2018-06-22\",\n" +
            "            \"publishedAt\": \"2018-06-19T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsfq2pwt72j30qo0yg78u.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b2269a6421aa92a5f2a35f9\",\n" +
            "            \"createdAt\": \"2018-06-14T21:12:06.463Z\",\n" +
            "            \"desc\": \"2018-06-15\",\n" +
            "            \"publishedAt\": \"2018-06-15T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fsb0lh7vl0j30go0ligni.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b1fec10421aa9793930bf99\",\n" +
            "            \"createdAt\": \"2018-06-12T23:51:44.815Z\",\n" +
            "            \"desc\": \"2018-06-13\",\n" +
            "            \"publishedAt\": \"2018-06-14T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fs8tym1e8ej30j60ouwhz.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b1fec9f421aa9793930bf9a\",\n" +
            "            \"createdAt\": \"2018-06-12T23:54:07.908Z\",\n" +
            "            \"desc\": \"2018-06-14\",\n" +
            "            \"publishedAt\": \"2018-06-13T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fs8u1joq6fj30j60orwin.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b1e8164421aa910a82cf54f\",\n" +
            "            \"createdAt\": \"2018-06-11T22:04:20.9Z\",\n" +
            "            \"desc\": \"2018-06-12\",\n" +
            "            \"publishedAt\": \"2018-06-12T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fs7l8ijitfj30jg0shdkc.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b196deb421aa910ab3d6b3d\",\n" +
            "            \"createdAt\": \"2018-06-08T01:39:55.555Z\",\n" +
            "            \"desc\": \"2018-06-09\",\n" +
            "            \"publishedAt\": \"2018-06-11T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fs35026dloj30j60ov79x.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b196d0b421aa910ab3d6b3c\",\n" +
            "            \"createdAt\": \"2018-06-08T01:36:11.740Z\",\n" +
            "            \"desc\": \"2018-06-08\",\n" +
            "            \"publishedAt\": \"2018-06-08T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fs34w0jx9jj30j60ootcn.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b17fec9421aa9109f56a6bb\",\n" +
            "            \"createdAt\": \"2018-06-06T23:33:29.429Z\",\n" +
            "            \"desc\": \"2018-06-07\",\n" +
            "            \"publishedAt\": \"2018-06-07T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fs1vq7vlsoj30k80q2ae5.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b1026a0421aa9029661ae00\",\n" +
            "            \"createdAt\": \"2018-06-01T00:45:20.83Z\",\n" +
            "            \"desc\": \"2018-06-01\",\n" +
            "            \"publishedAt\": \"2018-06-06T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1frv032vod8j30k80q6gsz.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b15ec20421aa97e45f15aae\",\n" +
            "            \"createdAt\": \"2018-06-05T09:49:20.355Z\",\n" +
            "            \"desc\": \"2018-06-05\",\n" +
            "            \"publishedAt\": \"2018-06-05T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fs02a9b0nvj30sg10vk4z.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b14aaa9421aa93df569c6f1\",\n" +
            "            \"createdAt\": \"2018-06-04T10:57:45.583Z\",\n" +
            "            \"desc\": \"2018-06-04\",\n" +
            "            \"publishedAt\": \"2018-06-04T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1fryyn63fm1j30sg0yagt2.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b1026ba421aa9029895ba44\",\n" +
            "            \"createdAt\": \"2018-06-01T00:45:46.820Z\",\n" +
            "            \"desc\": \"2018-06-02\",\n" +
            "            \"publishedAt\": \"2018-06-01T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1frv03m8ky5j30iz0rltfp.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b0d6946421aa97f0308836b\",\n" +
            "            \"createdAt\": \"2018-05-29T22:52:54.29Z\",\n" +
            "            \"desc\": \"2018-05-31\",\n" +
            "            \"publishedAt\": \"2018-05-31T00:00:00.0Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1frsllc19gfj30k80tfah5.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\": \"5b0d6895421aa97f0308836a\",\n" +
            "            \"createdAt\": \"2018-05-29T22:49:57.62Z\",\n" +
            "            \"desc\": \"2018-05-30\",\n" +
            "            \"publishedAt\": \"2018-05-30T13:22:16.505Z\",\n" +
            "            \"source\": \"web\",\n" +
            "            \"type\": \"福利\",\n" +
            "            \"url\": \"http://ww1.sinaimg.cn/large/0065oQSqly1frslibvijrj30k80q678q.jpg\",\n" +
            "            \"used\": true,\n" +
            "            \"who\": \"lijinshanmx\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    private String httpsGet(String path) {

        LogUtil.e("111111111111111111111");
        try {
            URL url = new URL(path);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            TrustManager[] trustManagers = new TrustManager[]{
                    new X509TrustManager() {
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        }

                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        }

                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null,trustManagers,null);

            connection.setSSLSocketFactory(ctx.getSocketFactory());
            connection.setRequestMethod("GET");

            System.out.println(connection.getResponseCode());
            LogUtil.e("2222222222222222222222");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine())!=null){
                sb.append(line);
                sb.append("\n");
            }
            LogUtil.e("333333333333333333333");
            String response = sb.toString();
            System.out.print(response);
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }









//        LogUtil.e("1111111111111");
//        SSLContext sslcontext = null;
//        try {
//            sslcontext = SSLContext.getInstance("TLS");
//            URL url = new URL(path);
//            sslcontext.init(null, new TrustManager[]{new HttpUtil.MyX509TrustManager()}, new java.security.SecureRandom());
//            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
//                public boolean verify(String s, SSLSession sslsession) {
//                    System.out.println("WARNING: Hostname is not matched for cert.");
//                    return true;
//                }
//            };
//            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//
//
////            urlConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
////            urlConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
//
//            urlConnection.setSSLSocketFactory(sslcontext.getSocketFactory());
//            urlConnection.setRequestMethod("GET");
//
//            LogUtil.e("2222222222222222");
//            int code = urlConnection.getResponseCode();
//            LogUtil.e("3333333333333333");
//            System.out.println(code);
//            if (200 == code) {
//                //得到输入流
//                InputStream is = urlConnection.getInputStream();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int len = 0;
//                while (-1 != (len = is.read(buffer))) {
//                    baos.write(buffer, 0, len);
//                    baos.flush();
//                }
//                LogUtil.e("4444444444444444");
//                return baos.toString("utf-8");
//            }
//
//        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
//            e.printStackTrace();
//        }


        return "";
    }*/
}
