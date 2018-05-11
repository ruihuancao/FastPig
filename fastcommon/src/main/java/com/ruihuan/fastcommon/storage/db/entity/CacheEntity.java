package com.ruihuan.fastcommon.storage.db.entity;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Description:
 * Data：2018/4/25-10:38
 * Author: caoruihuan
 */
@Entity
public class CacheEntity {

    @Id
    private long id;

    private String key;

    private String data;

    private long cacheTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }
}
