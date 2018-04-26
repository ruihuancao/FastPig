package com.ruihuan.storagepig.http.lisenter;

/**
 * Description:
 * Data：2018/4/25-11:04
 * Author: caoruihuan
 */
public interface CacheLisenter {

    boolean checkCache(String url);

    void returnCache(String url, BaseLisenter baseLisenter);

    void cache(String url, String response);
}
