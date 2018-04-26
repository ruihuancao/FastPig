package com.ruihuan.thirdpig.imageloader.glide;

import android.content.Context;
import android.widget.ImageView;

import com.ruihuan.thirdpig.imageloader.ImageLoader;

/**
 * Description:
 * Data：2018/4/26-10:09
 * Author: caoruihuan
 */
public class GlideImageLoader implements ImageLoader {

    @Override
    public void loadUrlImage(Context context, ImageView imageView, String url) {
        GlideApp.with(context)
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadUrlImage(Context context, ImageView imageView, String url, int placeholder) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .into(imageView);
    }

    @Override
    public void loadCricleImage(Context context, ImageView imageView, String url) {
        GlideApp.with(context)
                .load(url)
                .circleCrop()
                .into(imageView);
    }

    @Override
    public void loadCricleImage(Context context, ImageView imageView, String url, int placeholder) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .circleCrop()
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, ImageView imageView, String url) {
        GlideApp.with(context)
                .asGif()
                .load(url)
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, ImageView imageView, int res) {
        GlideApp.with(context)
                .asGif()
                .load(res)
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, ImageView imageView, int res, int placeholder) {
        GlideApp.with(context)
                .asGif()
                .load(res)
                .placeholder(placeholder)
                .into(imageView);
    }

    @Override
    public void clearImage(Context context, ImageView imageView) {
        GlideApp.with(context).clear(imageView);
    }
}
