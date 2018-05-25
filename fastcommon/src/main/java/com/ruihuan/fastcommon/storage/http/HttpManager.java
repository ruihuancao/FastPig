package com.ruihuan.fastcommon.storage.http;

import android.content.Context;


import com.ruihuan.fastcommon.storage.http.body.ProgressRequestBody;
import com.ruihuan.fastcommon.storage.http.lisenter.BaseLisenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class HttpManager {

    private static final int DEFAULT_CONNECT_TIMEOUT = 20;
    private static final int DEFAULT_READ_TIMEOUT = 20;

    private volatile static HttpManager instance;

    private OkHttpClient okHttpClient;
    private HashMap<String, String> commonParams;
    private HashMap<String, String> commonHeaders;

    private HttpManager() {
    }

    /**
     * init
     * @param context context
     */
    public HttpManager init(Context context){
        // log
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return instance;
    }

    /**
     * 返回单例
     * @return HttpManager
     */
    public static HttpManager getInstance() {
        if(instance == null){
            synchronized (HttpManager.class){
                if(instance == null){
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public void setCommonHeaders(HashMap<String, String> commonHeaders) {
        this.commonHeaders = commonHeaders;
    }

    public void setCommonParams(HashMap<String, String> commonParams) {
        this.commonParams = commonParams;
    }

    public HashMap<String, String> commonParams(Map<String, String> params){
        HashMap<String, String> resultParams = new HashMap<>();
        if(params != null){
            resultParams.putAll(params);
        }
        if(commonParams != null){
            resultParams.putAll(commonParams);
        }
        return resultParams;
    }

    public HashMap<String, String> commonHeaders(Map<String, String> headers){
        HashMap<String, String> resultHeaders = new HashMap<>();
        if(headers != null){
            resultHeaders.putAll(headers);
        }
        if(commonHeaders != null){
            resultHeaders.putAll(commonHeaders);
        }
        return resultHeaders;
    }

    /**
     * rxget request
     * @param url url
     * @param lisenter lisenter
     */
    public void get(String url, BaseLisenter lisenter){
        get(url, null, lisenter);
    }

    /**
     * rxget request with params
     * @param url url
     * @param params params
     * @param lisenter lisenter
     */
    public void get(String url, Map<String, String> params, BaseLisenter lisenter){
        get(url, params, null, lisenter);
    }

    /**
     * rxget request with params and header
     * @param url url
     * @param params params
     * @param headers headers
     * @param lisenter lisenter
     */
    public void get(String url, Map<String, String> params,
                    Map<String, String> headers, BaseLisenter lisenter) {
       try{
           Map<String, String> resultParams = commonParams(params);
           Map<String, String> resultHeaders = commonHeaders(headers);
           String resultUrl = HttpHelper.encodeParameters(url, resultParams);
           Request.Builder builder = new Request.Builder();
           builder.url(resultUrl).get();
           if (headers != null){
               for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                   builder.addHeader(entry.getKey(), entry.getValue());
               }
           }
           okHttpClient.newCall(builder.build()).enqueue(lisenter);
       }catch (Exception e){
           lisenter.onFailure(400, e.getMessage());
       }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> rxget(final String url, final Map<String, String> params,
                                    final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    Map<String, String> resultParams = commonParams(params);
                    Map<String, String> resultHeaders = commonHeaders(headers);

                    String resultUrl = HttpHelper.encodeParameters(url, resultParams);
                    Request.Builder builder = new Request.Builder();
                    builder.url(resultUrl).get();
                    if(resultHeaders != null){
                        for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                            builder.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    Response response = okHttpClient.newCall(builder.build()).execute();
                    emitter.onNext(response.body().string());
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * head request
     * @param url url
     * @param lisenter lisenter
     */
    public void head(String url, BaseLisenter lisenter){
        head(url, null, lisenter);
    }

    /**
     * head request with params
     * @param url url
     * @param params params
     * @param lisenter lisenter
     */
    public void head(String url, Map<String, String> params, BaseLisenter lisenter){
        head(url, params, null, lisenter);
    }

    /**
     * head request with params add headers
     * @param url url
     * @param params params
     * @param headers headers
     * @param lisenter lisenter
     */
    public void head(String url, Map<String, String> params,
                     Map<String, String> headers, BaseLisenter lisenter) {
        try {
            Map<String, String> resultParams = commonParams(params);
            Map<String, String> resultHeaders = commonHeaders(headers);
            String resultUrl = HttpHelper.encodeParameters(url, resultParams);
            Request.Builder builder = new Request.Builder();
            builder.url(resultUrl).head();
            if(resultHeaders!= null){
                for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        } catch (Exception e) {
            lisenter.onFailure(400, e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> rxhead(final String url, final Map<String, String> params,
                     final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    Map<String, String> resultParams = commonParams(params);
                    Map<String, String> resultHeaders = commonHeaders(headers);
                    String resultUrl = HttpHelper.encodeParameters(url, resultParams);
                    Request.Builder builder = new Request.Builder();
                    builder.url(resultUrl).head();
                    if(resultHeaders != null){
                        for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                            builder.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    Response response = okHttpClient.newCall(builder.build()).execute();
                    emitter.onNext(response.headers().toString());
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     *
     * @param url
     * @param lisenter
     */
    public void delete(String url, BaseLisenter lisenter) {
        delete(url, null, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param lisenter
     */
    public void delete(String url, Map<String, String> params, BaseLisenter lisenter) {
        delete(url, params, null, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @param lisenter
     */
    public void delete(String url, Map<String, String> params,
                       Map<String, String> headers, BaseLisenter lisenter) {
        try{
            Map<String, String> resultParams = commonParams(params);
            Map<String, String> resultHeaders = commonHeaders(headers);

            Request.Builder builder = new Request.Builder();
            FormBody.Builder paramsBuilder = new FormBody.Builder();
            if(resultParams != null){
                for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                    paramsBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            builder.url(url).delete(paramsBuilder.build());
            if(resultHeaders != null){
                for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        }catch (Exception e){
            lisenter.onFailure(400, e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> rxdelete(final String url, final Map<String, String> params,
                       final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    Map<String, String> resultParams = commonParams(params);
                    Map<String, String> resultHeaders = commonHeaders(headers);

                    Request.Builder builder = new Request.Builder();
                    FormBody.Builder paramsBuilder = new FormBody.Builder();
                    if(resultParams != null){
                        for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                            paramsBuilder.add(entry.getKey(), entry.getValue());
                        }
                    }
                    builder.url(url).delete(paramsBuilder.build());
                    if(resultHeaders != null){
                        for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                            builder.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    Response response = okHttpClient.newCall(builder.build()).execute();
                    emitter.onNext(response.body().string());
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     *
     * @param url
     * @param lisenter
     */
    public void post(String url, BaseLisenter lisenter) {
        post(url, null, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param lisenter
     */
    public void post(String url, Map<String, String> params, BaseLisenter lisenter) {
        post(url, params, null, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @param lisenter
     */
    public void post(String url, Map<String, String> params,
                     Map<String, String> headers, BaseLisenter lisenter) {
        try{
            Map<String, String> resultParams = commonParams(params);
            Map<String, String> resultHeaders = commonHeaders(headers);

            Request.Builder builder = new Request.Builder();
            FormBody.Builder paramsBuilder = new FormBody.Builder();
            if(resultParams != null){
                for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                    paramsBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            builder.url(url).post(paramsBuilder.build());
            // 添加header
            if(resultHeaders != null){
                for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        }catch (Exception e){
            lisenter.onFailure(400, e.getMessage());
        }
    }


    public void post(String url, Map<String, String> params,
                       Map<String, String> headers, Map<String, File> files,
                       BaseLisenter lisenter){
        try{
            Map<String, String> resultParams = commonParams(params);
            Map<String, String> resultHeaders = commonHeaders(headers);
            Request.Builder builder = new Request.Builder();
            MultipartBody.Builder paramsBuilder = new MultipartBody.Builder();
            //设置类型
            paramsBuilder.setType(MultipartBody.FORM);
            //追加参数
            if(resultParams != null){
                for (Map.Entry<String, String> entry : resultParams.entrySet()){
                    paramsBuilder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
            // 添加file
            if(files != null){
                for (Map.Entry<String, File> entry : files.entrySet()){
                    paramsBuilder.addFormDataPart(entry.getKey(), entry.getValue().getName(), RequestBody.create(null, entry.getValue()));
                }
            }
            builder.url(url).post(new ProgressRequestBody(paramsBuilder.build(), lisenter));
            // 添加header
            if(resultHeaders != null){
                for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        }catch (Exception e){
            lisenter.onFailure(400, e.getMessage());
        }
    }


    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> rxpost(final String url, final Map<String, String> params,
                     final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    Map<String, String> resultParams = commonParams(params);
                    Map<String, String> resultHeaders = commonHeaders(headers);
                    Request.Builder builder = new Request.Builder();
                    FormBody.Builder paramsBuilder = new FormBody.Builder();
                    if(resultParams != null){
                        for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                            paramsBuilder.add(entry.getKey(), entry.getValue());
                        }
                    }
                    builder.url(url).post(paramsBuilder.build());
                    // 添加header
                    if(resultHeaders != null){
                        for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                            builder.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    Response response = okHttpClient.newCall(builder.build()).execute();
                    emitter.onNext(response.body().string());
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     *
     * @param url
     * @param lisenter
     */
    public void put(String url, BaseLisenter lisenter) {
        put(url, null, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param lisenter
     */
    public void put(String url, Map<String, String> params, BaseLisenter lisenter) {
        put(url, params, null, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @param lisenter
     */
    public void put(String url, Map<String, String> params,
                     Map<String, String> headers, BaseLisenter lisenter) {
        try{
            Map<String, String> resultParams = commonParams(params);
            Map<String, String> resultHeaders = commonHeaders(headers);
            Request.Builder builder = new Request.Builder();
            FormBody.Builder paramsBuilder = new FormBody.Builder();
            if(resultParams != null){
                for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                    paramsBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            builder.url(url).put(paramsBuilder.build());
            // 添加header
            if(resultHeaders != null){
                for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        }catch (Exception e){
            lisenter.onFailure(400, e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> rxput(final String url, final Map<String, String> params,
                    final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    Map<String, String> resultParams = commonParams(params);
                    Map<String, String> resultHeaders = commonHeaders(headers);

                    Request.Builder builder = new Request.Builder();
                    FormBody.Builder paramsBuilder = new FormBody.Builder();
                    if(resultParams != null){
                        for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                            paramsBuilder.add(entry.getKey(), entry.getValue());
                        }
                    }
                    builder.url(url).put(paramsBuilder.build());
                    // 添加header
                    if(resultHeaders != null){
                        for (Map.Entry<String, String> entry : resultHeaders.entrySet()){
                            builder.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    Response response = okHttpClient.newCall(builder.build()).execute();
                    emitter.onNext(response.body().string());
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        });
    }
}
