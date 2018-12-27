package com.wang.myandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wang.myandroid.R;

/**
 * Created by MaxWang on 2018/12/15.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/12/15 10:26
 * 修改人  ：MaxWang
 * 修改时间：2018/12/15
 * 修改备注：
 */

public class PicassoUtil {
    //默认加载图片
    public static void loadImgView(String url, ImageView imageView){
        Picasso.get().load(url).into(imageView);
    }

    //指定大小
    public static void loadImgViewSize(String url, int width,int height,
                                       ImageView imageView){
        Picasso.get()
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }

    //加载默认图片
    public static void loadImgDefault(String url,int loadImg,int errorImg ,ImageView imageView){
        Picasso.get()
                .load(url)
                .placeholder(loadImg)
                .error(errorImg)
                .into(imageView);
    }

    //裁剪图片
    public static void loadImgCrop(String url,ImageView imageView){
        Picasso.get()
                .load(url)
                .transform(new CropSquareTransformation())
                .into(imageView);
    }

    //按比例裁剪  矩形
    public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                //回收
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "square()"; }
    }
}
