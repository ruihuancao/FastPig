package com.ruihuan.fastcommon.storage.db;


import android.content.Context;

import com.ruihuan.fastcommon.helper.LogHelper;
import com.ruihuan.fastcommon.storage.db.entity.CacheEntity;
import com.ruihuan.fastcommon.storage.db.entity.CacheEntity_;
import com.ruihuan.fastcommon.storage.db.entity.MyObjectBox;


import java.util.Date;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class DBManager {

    private volatile static DBManager instance;
    private BoxStore boxStore;
    Box<CacheEntity> cacheBox;

    public DBManager init(Context context){
        boxStore = MyObjectBox.builder().androidContext(context).build();
        return instance;
    }

    private DBManager() {
    }

    public static DBManager getInstance() {
        if(instance == null){
            synchronized (DBManager.class){
                if(instance == null){
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

    public synchronized Box<CacheEntity> getCacheBox(){
        if (cacheBox == null){
            cacheBox = boxStore.boxFor(CacheEntity.class);
        }
        return cacheBox;
    }


    public long putCache(String key, String data, long expiredTime){
        Box<CacheEntity> box = getCacheBox();
        CacheEntity cacheEntity;
        if(hasCache(key)){
            // update
            LogHelper.d("更新缓存");
            cacheEntity = getCacheEntity(key);
        }else{
            LogHelper.d("添加缓存");
            cacheEntity = new CacheEntity();
        }
        cacheEntity.setKey(key);
        cacheEntity.setData(data);
        cacheEntity.setCacheTime(System.currentTimeMillis());
        cacheEntity.setExpiredTime(System.currentTimeMillis() + expiredTime);
        return box.put(cacheEntity);
    }


    public String getCacheData(String key){
        Box<CacheEntity> box = getCacheBox();
        Query<CacheEntity> query = box.query()
                .equal(CacheEntity_.key, key)
                .build();
        if(query.count() > 0){
            CacheEntity cacheEntity = query.findFirst();
            return cacheEntity.getData();
        }
        return null;
    }

    public CacheEntity getCacheEntity(String key){
        Box<CacheEntity> box = getCacheBox();
        Query<CacheEntity> query = box.query()
                .equal(CacheEntity_.key, key)
                .build();
        return query.findFirst();
    }

    private boolean hasCache(String key){
        Box<CacheEntity> box = getCacheBox();
        Query<CacheEntity> query = box.query()
                .equal(CacheEntity_.key, key)
                .build();
        return query.count() > 0;
    }


    public boolean checkCache(String key){
        boolean flag = false;
        Box<CacheEntity> box = getCacheBox();
        if(hasCache(key)){
            Query<CacheEntity> query = box.query()
                    .equal(CacheEntity_.key, key)
                    .build();
            CacheEntity cacheEntity = query.findFirst();
            LogHelper.d("缓存有效时间："+ new Date(cacheEntity.getExpiredTime()));
            LogHelper.d("缓存创建时间："+ new Date(cacheEntity.getCacheTime()));
            if(cacheEntity.getExpiredTime() > System.currentTimeMillis()){
                flag = true;
            }
        }
        return flag;
    }

    public Observable<String> getCacheResult(final String key){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                if (checkCache(key)) {
                    String data = getCacheData(key);
                    LogHelper.d("从本地缓存获取数据");
                    emitter.onNext(data);
                }else{
                    emitter.onNext("");
                }
                emitter.onComplete();
            }
        });
    }
}
