package com.ruihuan.thirdpig.imageloader;

import com.ruihuan.thirdpig.imageloader.glide.GlideImageLoader;

/**
 * Description:
 * Data：2018/4/26-10:22
 * Author: caoruihuan
 */
public class ImageLoadManager {

    public static ImageLoadManager instance;

    private ImageLoader imageLoader;

    private ImageLoadManager(){
        imageLoader = new GlideImageLoader();
    }

    public static ImageLoadManager getInstance(){
        if(instance == null){
            instance = new ImageLoadManager();
        }
        return instance;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
