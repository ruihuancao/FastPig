package com.ruihuan.thirdpig.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Description:
 * Data：2018/4/26-10:06
 * Author: caoruihuan
 */
public interface ImageLoader {

    void loadUrlImage(Context context, ImageView imageView, String url);

    void loadUrlImage(Context context, ImageView imageView, String url, int placeholder);

    void loadCricleImage(Context context, ImageView imageView, String url);

    void loadCricleImage(Context context, ImageView imageView, String url, int placeholder);

    void clearImage(Context context, ImageView imageView);

    void loadGifImage(Context context, ImageView imageView , String url);

    void loadGifImage(Context context, ImageView imageView , int res);

    void loadGifImage(Context context, ImageView imageView , int res, int placeholder);

}
