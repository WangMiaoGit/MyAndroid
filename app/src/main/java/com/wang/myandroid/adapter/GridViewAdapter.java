package com.wang.myandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wang.myandroid.R;
import com.wang.myandroid.entity.CourierData;

import java.util.List;

/**
 * Created by MaxWang on 2018/12/13.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/13 12:18
 * 修改人  ：MaxWang
 * 修改时间：2018/12/13
 * 修改备注：
 */

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;
    //布局加载器
    private LayoutInflater inflater;
    private CourierData data;

    public GridViewAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        //第一次加载
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.gridview_item, null);

            viewHolder.tv_gridview = convertView.findViewById(R.id.tv_gridview);
            //设置缓存
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //让内容匹配高度
        AbsListView.LayoutParams lp=new AbsListView.LayoutParams(android.view.ViewGroup
                .LayoutParams.MATCH_PARENT,parent.getHeight()/3);
        convertView.setLayoutParams(lp);
        viewHolder.tv_gridview.setText(mList.get(position));
        return convertView;
    }


    class ViewHolder {
        private TextView tv_gridview;

    }
}
