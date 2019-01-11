package com.wang.myandroid;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by MaxWang on 2019/1/2.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2019/1/2 15:28
 * 修改人  ：MaxWang
 * 修改时间：2019/1/2
 * 修改备注：
 */

public class TestOkHtp3 {
    @Test
    public  void testGet(){
        //创建OKHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建 Request 对象
        Request request = new Request.Builder()
                .url("http://www.baidu.com")//GET请求
                .build();
        //创建Call对象
        Call call = okHttpClient.newCall(request);
        //执行请求 得到相应
        try {
            Response response = call.execute();
            System.out.println("response:"+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void testPost(){
        //创建OKHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建 RequestBody
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType,"{\"name\":\"maxwang\"}");
        //创建 Request 对象
        Request request = new Request.Builder()
                .url("http://www.baidu.com")//POST请求  请求行
//                .head()//请求头
                .post(requestBody)//请求体
                .build();
        //创建Call对象
        Call call = okHttpClient.newCall(request);
        //执行请求 得到相应
        try {
            Response response = call.execute();
            System.out.println("response:"+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拦截器
     */
    @Test
    public void testInterceptor(){
        //定义拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                long start = System.currentTimeMillis();
                Request request = chain.request();
                Response response = chain.proceed(request);
                long end = System.currentTimeMillis();
                System.out.println("current time:"+(end-start));
                return null;
            }
        };


        //创建OKHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)//添加拦截器
                .build();
        //创建 Request 对象
        Request request = new Request.Builder()
                .url("http://www.baidu.com")//GET请求
                .build();
        //创建Call对象
        Call call = okHttpClient.newCall(request);
        //执行请求 得到相应
        try {
            Response response = call.execute();
            System.out.println("response:"+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缓存测试
     */
    public void testCache(){
        //创建缓存对象
        Cache cache = new Cache(new File("cache.cache"),1024*1024);


        //创建OKHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .build();
        //创建 Request 对象
        Request request = new Request.Builder()
                .url("http://www.baidu.com")//GET请求
                .cacheControl(CacheControl.FORCE_CACHE)//强制使用缓存 或者 网络
                .build();
        //创建Call对象
        Call call = okHttpClient.newCall(request);
        //执行请求 得到相应
        try {
            Response response = call.execute();
            Response response_cache = response.cacheResponse();//缓存中取
            Response response_net = response.networkResponse();//网络中取
            if (response_cache!=null){
                //缓存
            }
            if (response_net!=null){
                //网络
            }

            System.out.println("response:"+response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
