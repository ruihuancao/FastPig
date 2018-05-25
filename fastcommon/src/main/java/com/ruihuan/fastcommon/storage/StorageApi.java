package com.ruihuan.fastcommon.storage;

import android.content.Context;
import android.text.TextUtils;

import com.ruihuan.fastcommon.helper.LogHelper;
import com.ruihuan.fastcommon.storage.config.ConfigManager;
import com.ruihuan.fastcommon.storage.db.DBManager;
import com.ruihuan.fastcommon.storage.http.HttpManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description:
 * Data：2018/5/25-10:07
 * Author: Charles
 */
public class StorageApi {

    protected HttpManager httpManager;
    protected DBManager dbManager;
    protected ConfigManager configManager;

    public static void init(Context context){
        HttpManager.getInstance().init(context);
        DBManager.getInstance().init(context);
        ConfigManager.getInstance().init(context);
    }

    public StorageApi() {
        httpManager = HttpManager.getInstance();
        dbManager = DBManager.getInstance();
        configManager = ConfigManager.getInstance();
    }

    public Observable<String> rxReqeust(final StorageRequest storageRequest){
        if(StorageRequest.METHOD_GET.equalsIgnoreCase(storageRequest.getMethod())){
            if(storageRequest.isCache()){
                return dbManager.getCacheResult(storageRequest.getCacheKey())
                        .flatMap(new Function<String, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(String s){
                                if(TextUtils.isEmpty(s)){
                                    return httpManager.rxget(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders())
                                            .doOnNext(new Consumer<String>() {
                                                @Override
                                                public void accept(String s){
                                                    LogHelper.d("网络");
                                                    dbManager.putCache(storageRequest.getCacheKey(), s, storageRequest.getCacheExpiredTime());
                                                }
                                            });
                                }else{
                                    LogHelper.d("缓存");
                                    return Observable.just(s);
                                }
                            }
                        });
            }else{
                return httpManager.rxget(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders());
            }
        }else if(StorageRequest.METHOD_POST.equalsIgnoreCase(storageRequest.getMethod())){
            return httpManager.rxpost(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders());
        }else if(StorageRequest.METHOD_HEAD.equalsIgnoreCase(storageRequest.getMethod())){
            return httpManager.rxhead(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders());
        }else if(StorageRequest.METHOD_DELETE.equalsIgnoreCase(storageRequest.getMethod())){
            return httpManager.rxdelete(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders());
        }else if(StorageRequest.METHOD_PUT.equalsIgnoreCase(storageRequest.getMethod())){
            return httpManager.rxput(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders());
        }else{
            return Observable.just("");
        }
    }


    public void reqeust(StorageRequest storageRequest){
        if(StorageRequest.METHOD_GET.equalsIgnoreCase(storageRequest.getMethod())){
            httpManager.get(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders(), storageRequest.getBaseLisenter());
        }else if(StorageRequest.METHOD_POST.equalsIgnoreCase(storageRequest.getMethod())){
            httpManager.post(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders(), storageRequest.getBaseLisenter());
        }else if(StorageRequest.METHOD_HEAD.equalsIgnoreCase(storageRequest.getMethod())){
            httpManager.head(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders(), storageRequest.getBaseLisenter());
        }else if(StorageRequest.METHOD_DELETE.equalsIgnoreCase(storageRequest.getMethod())){
            httpManager.delete(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders(), storageRequest.getBaseLisenter());
        }else if(StorageRequest.METHOD_PUT.equalsIgnoreCase(storageRequest.getMethod())){
            httpManager.put(storageRequest.getUrl(), storageRequest.getParams(), storageRequest.getHeaders(), storageRequest.getBaseLisenter());
        }else{
            LogHelper.d("method not support");
        }
    }
}
