package com.wang.myandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wang.myandroid.R;
import com.wang.myandroid.entity.WeChatData;
import com.wang.myandroid.utils.PicassoUtil;
import com.wang.myandroid.utils.StringUtil;
import com.wang.myandroid.utils.UtilTools;

import java.util.List;

/**
 * Created by MaxWang on 2018/12/15.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/15 8:30
 * 修改人  ：MaxWang
 * 修改时间：2018/12/15
 * 修改备注：
 */

public class WeChatAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<WeChatData> mList;
    private WeChatData data;

    public WeChatAdapter(Context mContext, List<WeChatData> list) {
        this.mContext = mContext;
        this.mList = list;
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
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.wechat_item,null);
            viewHolder.imageView = convertView.findViewById(R.id.iv_img_wechat_item);
            viewHolder.textView_title = convertView.findViewById(R.id.tv_title_wechat_item);
            viewHolder.textView_source = convertView.findViewById(R.id.tv_source_wechat_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        data = mList.get(position);
        viewHolder.textView_title.setText(data.getTitle());
        viewHolder.textView_source.setText(data.getSource());
        //Picasso加载图片
//        PicassoUtil.loadImgView(data.getImgUrl(),viewHolder.imageView);
//        PicassoUtil.loadImgViewSize(data.getImgUrl(),300,100,viewHolder.imageView);
       if (StringUtil.isEmpty(data.getImgUrl())){

       }else {
           PicassoUtil.loadImgViewSize(data.getImgUrl(),300,100,viewHolder.imageView);
       }
        return convertView;
    }

    class ViewHolder{
        private ImageView imageView;
        private TextView textView_title;
        private TextView textView_source;
    }
}
