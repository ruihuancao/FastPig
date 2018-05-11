package com.ruihuan.fastpig.data;

import com.ruihuan.fastcommon.event.EventBusManager;
import com.ruihuan.fastcommon.helper.LogHelper;
import com.ruihuan.fastcommon.storage.db.DBManager;
import com.ruihuan.fastcommon.storage.db.entity.CacheEntity;
import com.ruihuan.fastcommon.storage.http.HttpManager;
import com.ruihuan.fastcommon.storage.http.lisenter.GsonLisenter;
import com.ruihuan.fastcommon.storage.http.lisenter.StringLisenter;
import com.ruihuan.fastpig.DataEvent;


import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:
 * Data：2018/4/25-14:14
 * Author: caoruihuan
 */
public class ApiImpl implements Api {

    private HttpManager http;

    private DBManager db;

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
        this.http = HttpManager.getInstance();
        this.db = DBManager.getInstance();
    }

    @Override
    public void getPoetry(int id) {
        String url = "http://192.168.1.186:5000/api/v1.0/poetry/1";
        http.get(url, new StringLisenter() {
            @Override
            public void onFailure(int statusCode, String errorMessage) {
                LogHelper.e(errorMessage);
            }

            @Override
            public void onResponse(String response) {
                LogHelper.d(response);
            }
        });
    }

    @Override
    public void getPoetry(int id, StringLisenter stringLisenter) {
        String url = "http://192.168.1.186:5000/api/v1.0/poetry/1";
        http.get(url, stringLisenter);
    }

    @Override
    public void getPoetry(int id, GsonLisenter<TestBean> gsonLisenter) {
        String url = "http://192.168.1.186:5000/api/v1.0/poetry/1";
        http.get(url, gsonLisenter);
    }

    @Override
    public void getTestPoetry(int id, GsonLisenter<BaseBean<PoetryDataBean>> gsonLisenter) {
        String url = "http://192.168.1.186:5000/api/v1.0/poetry/1";
        http.get(url, gsonLisenter);
    }

    @Override
    public void testGet() {
        final String url = "http://httpbin.org/get";
        HashMap<String, String> params = new HashMap<>();
        params.put("test1", "get1");
        params.put("test2", "get2");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "get3");
        headers.put("header2", "get4");

        if(db.hasCache(url)){
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    String data = db.getCacheData(url);
                    LogHelper.d("缓存结果");
                    emitter.onNext(data);
                    emitter.onComplete();
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }else{
            http.get(url, params, headers)
                    .doOnNext(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            LogHelper.d("网络结果缓存");
                            CacheEntity cacheEntity = new CacheEntity();
                            cacheEntity.setCacheTime(System.currentTimeMillis());
                            cacheEntity.setData(s);
                            cacheEntity.setKey(url);
                            db.addOrUpdateCache(cacheEntity);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
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

        http.head(url, params, headers)
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

        http.delete(url, params, headers)
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

        http.post(url, params, headers)
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

        http.put(url, params, headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void testPatch() {
        String url = "http://httpbin.org/patch";
        HashMap<String, String> params = new HashMap<>();
        params.put("test1", "patch1");
        params.put("test2", "patch2");

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header1", "patch3");
        headers.put("header2", "patch4");

        http.patch(url, params, headers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
