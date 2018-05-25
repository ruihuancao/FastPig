package com.ruihuan.fastpig.data;

import com.ruihuan.fastcommon.event.EventBusManager;
import com.ruihuan.fastcommon.helper.LogHelper;
import com.ruihuan.fastcommon.storage.StorageApi;
import com.ruihuan.fastcommon.storage.StorageRequest;
import com.ruihuan.fastcommon.storage.http.lisenter.GsonLisenter;
import com.ruihuan.fastcommon.storage.http.lisenter.StringLisenter;
import com.ruihuan.fastpig.DataEvent;


import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Description:
 * Data：2018/4/25-14:14
 * Author: caoruihuan
 */
public class ApiImpl extends StorageApi implements Api {


    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            LogHelper.d("onSubscribe");
        }

        @Override
        public void onNext(String s) {
            LogHelper.d(s);
            EventBusManager.post(new DataEvent(s));
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            LogHelper.d("onComplete");
        }
    };

    public ApiImpl() {
        super();
        final HashMap<String, String> commonparams = new HashMap<>();
        commonparams.put("commonparams", "commonparams");

        final HashMap<String, String> commonheaders = new HashMap<>();
        commonheaders.put("commonheaders", "commonheaders");

        httpManager.setCommonParams(commonparams);
        httpManager.setCommonHeaders(commonheaders);
    }

    @Override
    public void testGet() {
        final String url = "http://httpbin.org/get";
        final HashMap<String, String> params = new HashMap<>();
        params.put("test1", "get1");
        params.put("test2", "get2");

        final HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "get3");
        headers.put("header2", "get4");

//        httpManager.rxget(url, params, headers)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);

        StorageRequest storageRequest = StorageRequest.buildResquest()
                .setUrl(url)
                .addParams("test1", 1)
                .addHeaders("header1", "header1")
                .setMethod(StorageRequest.METHOD_GET);
        rxReqeust(storageRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void testHead() {
        String url = "http://httpbin.org/head";
        HashMap<String, String> params = new HashMap<>();
        params.put("test1", "head1");
        params.put("test2", "head2");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "head3");
        headers.put("header2", "head4");

        httpManager.rxhead(url, params, headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void testDelete() {
        String url = "http://httpbin.org/delete";
        HashMap<String, String> params = new HashMap<>();
        params.put("test1", "delete1");
        params.put("test2", "delete2");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "delete3");
        headers.put("header2", "delete4");

        httpManager.rxdelete(url, params, headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void testPost() {
        String url = "http://httpbin.org/post";
        HashMap<String, String> params = new HashMap<>();
        params.put("test1", "post1");
        params.put("test2", "post2");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "post3");
        headers.put("header2", "post4");

        httpManager.rxpost(url, params, headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void testPut() {
        String url = "http://httpbin.org/put";
        HashMap<String, String> params = new HashMap<>();
        params.put("test1", "put1");
        params.put("test2", "put2");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "put3");
        headers.put("header2", "put4");

        httpManager.rxput(url, params, headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
