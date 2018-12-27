package com.wang.myandroid.entity;

/**
 * Created by MaxWang on 2018/12/15.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/15 8:31
 * 修改人  ：MaxWang
 * 修改时间：2018/12/15
 * 修改备注：
 */

public class WeChatData {

    //标题
    private String title;
    //出处
    private String source;
    //图片的url
    private String imgUrl;
    //新闻地址
    private String newsUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
