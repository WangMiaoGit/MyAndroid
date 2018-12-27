package com.wang.myandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wang.myandroid.R;
import com.wang.myandroid.entity.ChatListData;

import java.util.List;

/**
 * Created by MaxWang on 2018/12/13.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/13 15:52
 * 修改人  ：MaxWang
 * 修改时间：2018/12/13
 * 修改备注：
 */

public class CharListAdapter extends BaseAdapter {

    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;
    private Context mContext;
    private LayoutInflater mInflater;
    private ChatListData data;
    private List<ChatListData> mList;

    public CharListAdapter(Context context, List<ChatListData> list) {
        this.mContext = context;
        this.mList = list;
        //获取系统服务
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        //获取当前显示的type根据type区分数据的加载
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    convertView = mInflater.inflate(R.layout.left_item, null);
                    viewHolderLeftText.tv_left_text = convertView.findViewById(R.id.tv_left_text);
                    convertView.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    convertView = mInflater.inflate(R.layout.right_item, null);
                    viewHolderRightText.tv_right_text = convertView.findViewById(R.id.tv_right_text);
                    convertView.setTag(viewHolderRightText);
                    break;
            }
        } else {

            switch (type) {
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) convertView.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) convertView.getTag();
                    break;
            }
        }
        ChatListData data = mList.get(position);
        switch (type) {
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(data.getText());
                break;
        }

        return convertView;
    }

    //根据数据源的position显示返回的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所有的Layout数量
    @Override
    public int getViewTypeCount() {
        return 3;
    }

    //左边的文本
    class ViewHolderLeftText {
        private TextView tv_left_text;
    }

    //右边的文本
    class ViewHolderRightText {
        private TextView tv_right_text;
    }
}
