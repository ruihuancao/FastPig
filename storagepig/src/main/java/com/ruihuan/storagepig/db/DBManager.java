package com.ruihuan.storagepig.db;


import android.content.Context;

import com.ruihuan.storagepig.db.entity.CacheEntity;
import com.ruihuan.storagepig.db.entity.CacheEntity_;
import com.ruihuan.storagepig.db.entity.MyObjectBox;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

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


    public synchronized Box<CacheEntity> getCacheBox(){
        if (cacheBox == null){
            cacheBox = boxStore.boxFor(CacheEntity.class);
        }
        return cacheBox;
    }

    public long addOrUpdateCache(CacheEntity cacheEntity){
        Box<CacheEntity> box = getCacheBox();
        long id = box.put(cacheEntity);
        return id;
    }

    public void deleteCache(CacheEntity cacheEntity){
        Box<CacheEntity> box = getCacheBox();
        box.remove(cacheEntity);
    }

    public void deleteCache(long id){
        Box<CacheEntity> box = getCacheBox();
        box.remove(id);
    }

    public CacheEntity getCacheEntity(long id){
        Box<CacheEntity> box = getCacheBox();
        return box.get(id);
    }

    public String getCacheData(String url){
        Box<CacheEntity> box = getCacheBox();
        Query<CacheEntity> query = box.query()
                .equal(CacheEntity_.key, url)
                .build();
        if(query.count() > 0){
            CacheEntity cacheEntity = query.findUnique();
            return cacheEntity.getData();
        }
        return null;
    }

    public boolean hasCache(String url){
        boolean flag = false;
        Box<CacheEntity> box = getCacheBox();
        Query<CacheEntity> query = box.query()
                .equal(CacheEntity_.key, url)
                .build();
        if(query.count() > 0){
            CacheEntity cacheEntity = query.findFirst();
            if(System.currentTimeMillis() - cacheEntity.getCacheTime() < (1000*60*1)){
                flag = true;
            }
        }
        return flag;
    }
}
