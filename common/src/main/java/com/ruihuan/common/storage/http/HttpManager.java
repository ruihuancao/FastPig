package com.ruihuan.common.storage.http;

import android.content.Context;


import com.ruihuan.common.storage.http.body.ProgressRequestBody;
import com.ruihuan.common.storage.http.contants.RequestContants;
import com.ruihuan.common.storage.http.lisenter.BaseLisenter;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import okhttp3.logging.HttpLoggingInterceptor;


public class HttpManager {

    private static final int DEFAULT_CONNECT_TIMEOUT = 20;
    private static final int DEFAULT_READ_TIMEOUT = 20;
    private volatile static HttpManager instance;

    private OkHttpClient okHttpClient;
    private boolean isLog = true;
    private String host;

    private HashMap<String, String> commonParmas;
    private HashMap<String, String> commonHeaders;

    private HttpManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        okHttpClient = builder.build();
    }

    /**
     * init
     * @param context context
     */
    public HttpManager init(Context context){
        // log
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(isLog ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
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

    /**
     * get request
     * @param url url
     * @param lisenter lisenter
     */
    public void get(String url, BaseLisenter lisenter){
        get(url, null, lisenter);
    }

    /**
     * get request with params
     * @param url url
     * @param params params
     * @param lisenter lisenter
     */
    public void get(String url, Map<String, String> params, BaseLisenter lisenter){
        get(url, params, null, lisenter);
    }

    /**
     * get request with params and header
     * @param url url
     * @param params params
     * @param headers headers
     * @param lisenter lisenter
     */
    public void get(String url, Map<String, String> params,
                    Map<String, String> headers, BaseLisenter lisenter) {
       try{
           HashMap<String, String> resultParams  = commonParams(params);
           HashMap<String, String> resultsHeaders = commonHeaders(headers);
           String resultUrl = encodeParameters(getUrl(url), resultParams);
           Request.Builder builder = new Request.Builder();
           builder.url(resultUrl).get();
           for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
               builder.addHeader(entry.getKey(), entry.getValue());
           }
           okHttpClient.newCall(builder.build()).enqueue(lisenter);
       }catch (Exception e){
           lisenter.onFailure(RequestContants.REQUEST_ERROR_CODE, e.getMessage());
       }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> get(final String url, final Map<String, String> params,
                          final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    HashMap<String, String> resultParams  = commonParams(params);
                    HashMap<String, String> resultsHeaders = commonHeaders(headers);
                    String resultUrl = encodeParameters(getUrl(url), resultParams);
                    Request.Builder builder = new Request.Builder();
                    builder.url(resultUrl).get();
                    for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                        builder.addHeader(entry.getKey(), entry.getValue());
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
            HashMap<String, String> resultParams  = commonParams(params);
            HashMap<String, String> resultsHeaders = commonHeaders(headers);
            String resultUrl = encodeParameters(getUrl(url), resultParams);
            Request.Builder builder = new Request.Builder();
            builder.url(resultUrl).head();
            for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        } catch (Exception e) {
            lisenter.onFailure(RequestContants.REQUEST_ERROR_CODE, e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> head(final String url, final Map<String, String> params,
                     final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                    HashMap<String, String> resultParams  = commonParams(params);
                    HashMap<String, String> resultsHeaders = commonHeaders(headers);
                    String resultUrl = encodeParameters(getUrl(url), resultParams);
                    Request.Builder builder = new Request.Builder();
                    builder.url(resultUrl).head();
                    for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                        builder.addHeader(entry.getKey(), entry.getValue());
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
            HashMap<String, String> resultParams  = commonParams(params);
            HashMap<String, String> resultsHeaders = commonHeaders(headers);
            String resultUrl = getUrl(url);
            Request.Builder builder = new Request.Builder();
            FormBody.Builder paramsBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                paramsBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.url(resultUrl).delete(paramsBuilder.build());
            // 添加header
            for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        }catch (Exception e){
            lisenter.onFailure(RequestContants.REQUEST_ERROR_CODE, e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> delete(final String url, final Map<String, String> params,
                       final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    HashMap<String, String> resultParams  = commonParams(params);
                    HashMap<String, String> resultsHeaders = commonHeaders(headers);
                    String resultUrl = getUrl(url);
                    Request.Builder builder = new Request.Builder();
                    FormBody.Builder paramsBuilder = new FormBody.Builder();
                    for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                        paramsBuilder.add(entry.getKey(), entry.getValue());
                    }
                    builder.url(resultUrl).delete(paramsBuilder.build());
                    // 添加header
                    for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                        builder.addHeader(entry.getKey(), entry.getValue());
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
            HashMap<String, String> resultParams  = commonParams(params);
            HashMap<String, String> resultsHeaders = commonHeaders(headers);
            String resultUrl = getUrl(url);

            Request.Builder builder = new Request.Builder();
            FormBody.Builder paramsBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                paramsBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.url(resultUrl).post(paramsBuilder.build());
            // 添加header
            for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        }catch (Exception e){
            lisenter.onFailure(RequestContants.REQUEST_ERROR_CODE, e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> post(final String url, final Map<String, String> params,
                     final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    HashMap<String, String> resultParams  = commonParams(params);
                    HashMap<String, String> resultsHeaders = commonHeaders(headers);
                    String resultUrl = getUrl(url);

                    Request.Builder builder = new Request.Builder();
                    FormBody.Builder paramsBuilder = new FormBody.Builder();
                    for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                        paramsBuilder.add(entry.getKey(), entry.getValue());
                    }
                    builder.url(resultUrl).post(paramsBuilder.build());
                    // 添加header
                    for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                        builder.addHeader(entry.getKey(), entry.getValue());
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
            HashMap<String, String> resultParams  = commonParams(params);
            HashMap<String, String> resultsHeaders = commonHeaders(headers);
            String resultUrl = getUrl(url);

            Request.Builder builder = new Request.Builder();
            FormBody.Builder paramsBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                paramsBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.url(resultUrl).put(paramsBuilder.build());
            // 添加header
            for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        }catch (Exception e){
            lisenter.onFailure(RequestContants.REQUEST_ERROR_CODE, e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> put(final String url, final Map<String, String> params,
                    final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    HashMap<String, String> resultParams  = commonParams(params);
                    HashMap<String, String> resultsHeaders = commonHeaders(headers);
                    String resultUrl = getUrl(url);

                    Request.Builder builder = new Request.Builder();
                    FormBody.Builder paramsBuilder = new FormBody.Builder();
                    for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                        paramsBuilder.add(entry.getKey(), entry.getValue());
                    }
                    builder.url(resultUrl).put(paramsBuilder.build());
                    // 添加header
                    for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                        builder.addHeader(entry.getKey(), entry.getValue());
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
    public void patch(String url, BaseLisenter lisenter) {
        patch(url, null, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param lisenter
     */
    public void patch(String url, Map<String, String> params, BaseLisenter lisenter) {
        post(url, params, null, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @param lisenter
     */
    public void patch(String url, Map<String, String> params,
                      Map<String, String> headers, BaseLisenter lisenter) {
        try{
            HashMap<String, String> resultParams  = commonParams(params);
            HashMap<String, String> resultsHeaders = commonHeaders(headers);
            String resultUrl = getUrl(url);

            Request.Builder builder = new Request.Builder();
            FormBody.Builder paramsBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                paramsBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.url(resultUrl).patch(paramsBuilder.build());
            // 添加header
            for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        }catch (Exception e){
            lisenter.onFailure(RequestContants.REQUEST_ERROR_CODE, e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public Observable<String> patch(final String url, final Map<String, String> params,
                      final Map<String, String> headers) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    HashMap<String, String> resultParams  = commonParams(params);
                    HashMap<String, String> resultsHeaders = commonHeaders(headers);
                    String resultUrl = getUrl(url);

                    Request.Builder builder = new Request.Builder();
                    FormBody.Builder paramsBuilder = new FormBody.Builder();
                    for (Map.Entry<String, String> entry : resultParams.entrySet()) {
                        paramsBuilder.add(entry.getKey(), entry.getValue());
                    }
                    builder.url(resultUrl).patch(paramsBuilder.build());
                    // 添加header
                    for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                        builder.addHeader(entry.getKey(), entry.getValue());
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
     * @param files
     * @param lisenter
     */
    public void upload(String url, Map<String, File> files,
                       BaseLisenter lisenter){
        upload(url, null, files, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param files
     * @param lisenter
     */
    public void upload(String url, Map<String, String> params, Map<String, File> files,
                       BaseLisenter lisenter){
        upload(url, params, null, files, lisenter);
    }

    /**
     *
     * @param url
     * @param params
     * @param headers
     * @param files
     * @param lisenter
     */
    public void upload(String url, Map<String, String> params,
                       Map<String, String> headers, Map<String, File> files,
                       BaseLisenter lisenter){
        try{
            HashMap<String, String> resultParams  = commonParams(params);
            HashMap<String, String> resultsHeaders = commonHeaders(headers);
            String resultUrl = getUrl(url);
            Request.Builder builder = new Request.Builder();
            MultipartBody.Builder paramsBuilder = new MultipartBody.Builder();
            //设置类型
            paramsBuilder.setType(MultipartBody.FORM);
            //追加参数
            for (Map.Entry<String, String> entry : resultParams.entrySet()){
                paramsBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            // 添加file
            for (Map.Entry<String, File> entry : files.entrySet()){
                paramsBuilder.addFormDataPart(entry.getKey(), entry.getValue().getName(), RequestBody.create(null, entry.getValue()));
            }
            builder.url(resultUrl).post(new ProgressRequestBody(paramsBuilder.build(), lisenter));
            // 添加header
            for (Map.Entry<String, String> entry : resultsHeaders.entrySet()){
                builder.addHeader(entry.getKey(), entry.getValue());
            }
            okHttpClient.newCall(builder.build()).enqueue(lisenter);
        }catch (Exception e){
            lisenter.onFailure(RequestContants.REQUEST_ERROR_CODE, e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @return
     */
    private String getUrl(String url){
        //host+url
        if(url.startsWith(RequestContants.REQUEST_START_HTTP)){
            return url;
        }
        return String.format("%s%s", host, url);
    }

    /**
     * add common headers
     * @param headers headers
     * @return map
     */
    private HashMap<String, String> commonHeaders(Map<String, String> headers){
        HashMap<String, String> resultHeaders = new HashMap<>();
        if(commonHeaders != null){
            resultHeaders.putAll(commonHeaders);
        }
        if(headers != null){
            resultHeaders.putAll(headers);
        }
        return resultHeaders;
    }

    /**
     * add common params
     * @param params params
     * @return map
     */
    private HashMap<String, String> commonParams(Map<String, String> params){
        HashMap<String, String> resultParmas = new HashMap<>();
        if(commonParmas != null){
            resultParmas.putAll(commonParmas);
        }
        if(params != null){
            resultParmas.putAll(params);
        }
        return resultParmas;
    }

    /**
     * logging
     * @param isLog islog
     */
    public void isLog(boolean isLog){
        if(this.isLog == isLog){
            return;
        }
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(isLog ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        okHttpClient = okHttpClient.newBuilder().addInterceptor(logging).build();
    }

    /**
     * set host
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 设置公共参数
     * @param commonParmas
     */
    public void setCommonParmas(HashMap<String, String> commonParmas) {
        this.commonParmas = commonParmas;
    }

    /**
     * 设置公共信息
     * @param commonHeaders
     */
    public void setCommonHeaders(HashMap<String, String> commonHeaders) {
        this.commonHeaders = commonHeaders;
    }

    /**
     * url 参数编码
     * @param url url
     * @param params params
     * @return String
     * @throws Exception
     */
    private String encodeParameters(String url, Map<String, String> params) throws Exception {
        if (params == null || params.size() <= 0) {
            return url;
        }
        StringBuilder encodedParams = new StringBuilder(url);
        try {
            if (!url.contains(RequestContants.REQUEST_QUESTION_HTTP)) {
                encodedParams.append(RequestContants.REQUEST_QUESTION_HTTP);
            } else {
                encodedParams.append(RequestContants.REQUEST_ADD_HTTP);
            }

            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), RequestContants.REQUEST_ENCODING_HTTP));
                encodedParams.append(RequestContants.REQUEST_EQ_HTTP);
                encodedParams.append(URLEncoder.encode(entry.getValue(), RequestContants.REQUEST_ENCODING_HTTP));
                encodedParams.append(RequestContants.REQUEST_ADD_HTTP);
            }
        } catch (UnsupportedEncodingException uee) {
            throw new UnsupportedEncodingException("Encoding not supported: utf-8");
        }
        return encodedParams.toString().endsWith(RequestContants.REQUEST_ADD_HTTP) ?
                encodedParams.substring(0, encodedParams.length() - 1) : encodedParams.toString();
    }
}
