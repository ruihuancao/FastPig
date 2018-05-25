package com.ruihuan.fastcommon.storage;

import com.blankj.utilcode.util.EncryptUtils;
import com.ruihuan.fastcommon.storage.http.HttpHelper;
import com.ruihuan.fastcommon.storage.http.lisenter.BaseLisenter;

import java.util.HashMap;


/**
 * Description:
 * Data：2018/5/25-10:21
 * Author: Charles
 */
public class StorageRequest {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_HEAD = "HEAD";

    private String method;

    private String url;

    private HashMap<String, String> params;

    private HashMap<String, String> headers;

    private boolean isCache;

    private long cacheExpiredTime;

    private BaseLisenter baseLisenter;

    private StorageRequest(){
        method = METHOD_GET;
        isCache = true;
        cacheExpiredTime = 1000*60*10;
    }

    public static StorageRequest buildResquest(){
        return new StorageRequest();
    }

    public StorageRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    public StorageRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public StorageRequest addParams(String key, String value){
        if(params == null){
            params = new HashMap<>();
        }
        params.put(key, value);
        return this;
    }

    public StorageRequest addParams(String key, int value){
        return addParams(key, String.valueOf(value));
    }

    public StorageRequest addParams(String key, long value){
        return addParams(key, String.valueOf(value));
    }

    public StorageRequest setParams(HashMap<String, String> params) {
        this.params = params;
        return this;
    }

    public StorageRequest addHeaders(String key, String value){
        if(headers == null){
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return this;
    }

    public StorageRequest addHeaders(String key, int value){
        return addHeaders(key, String.valueOf(value));
    }

    public StorageRequest addHeaders(String key, long value){
        return addHeaders(key, String.valueOf(value));
    }

    public StorageRequest setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public StorageRequest setCacheExpiredTime(long cacheExpiredTime) {
        this.cacheExpiredTime = cacheExpiredTime;
        return this;
    }

    public StorageRequest setBaseLisenter(BaseLisenter baseLisenter) {
        this.baseLisenter = baseLisenter;
        return this;
    }

    public StorageRequest setCache(boolean cache) {
        isCache = cache;
        return this;
    }

    public String getCacheKey() {
        return EncryptUtils.encryptMD5ToString(HttpHelper.encodeParameters(url, params));
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getParams() {
        if(params == null){
            params = new HashMap<>();
        }
        return params;
    }

    public HashMap<String, String> getHeaders() {
        if(headers == null){
            headers = new HashMap<>();
        }
        return headers;
    }

    public boolean isCache() {
        return isCache;
    }

    public long getCacheExpiredTime() {
        return cacheExpiredTime;
    }

    public BaseLisenter getBaseLisenter() {
        return baseLisenter;
    }
}
