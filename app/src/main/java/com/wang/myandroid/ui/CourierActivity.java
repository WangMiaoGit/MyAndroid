package com.wang.myandroid.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.http.VolleyError;
import com.wang.myandroid.R;
import com.wang.myandroid.adapter.CourierAdapter;
import com.wang.myandroid.entity.CourierData;
import com.wang.myandroid.utils.LogUtil;
import com.wang.myandroid.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 快递查询
 */
public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name, et_num;
    private Button but_get_courier;
    private ListView courier_listView;
    private List<CourierData> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    //初始化view
    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_num = findViewById(R.id.et_num);
        but_get_courier = findViewById(R.id.btn_get_courier);
        but_get_courier.setOnClickListener(this);
        courier_listView = findViewById(R.id.courier_list);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_courier:
                /**
                 *  1.获取输入框的内容
                 *  2.判断是否为空
                 *  3.拿到数据区请求数据
                 *  4.解析返回的JSON
                 *  5.listview适配器
                 *  6.实体类 item
                 *  7.设置数据显示效果
                 */
                String name = et_name.getText().toString().trim();
                String num = et_num.getText().toString().trim();

                String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY
                        + "&com=" + name + "&no=" + num;


                System.out.println(url);
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(num)) {
                   /* RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(CourierActivity.this, t, Toast.LENGTH_SHORT).show();
                            LogUtil.i("JSON:" + t);
                        }

                        @Override
                        public void onFailure(VolleyError error) {
                            super.onFailure(error);
                        }
                    });*/
                    parsingJson(StaticClass.JSON_STRING);
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void parsingJson(String t){
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject json_courier = (JSONObject) jsonArray.get(i);
                CourierData data = new CourierData();
                data.setRemark(json_courier.getString("remark"));
                data.setZone(json_courier.getString("zone"));
                data.setDatetime(json_courier.getString("datetime"));

                mList.add(data);
            }

            //倒叙
            Collections.reverse(mList);
            CourierAdapter courierAdapter = new CourierAdapter(this,mList);
            courier_listView.setAdapter(courierAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
