package com.ruihuan.fastpig.data;


import com.ruihuan.fastcommon.storage.http.lisenter.GsonLisenter;
import com.ruihuan.fastcommon.storage.http.lisenter.StringLisenter;

/**
 * Description:
 * Data：2018/4/25-13:53
 * Author: caoruihuan
 */
public interface Api {

    void getPoetry(int id);

    void getPoetry(int id, StringLisenter stringLisenter);

    void getPoetry(int id, GsonLisenter<TestBean> gsonLisenter);

    void getTestPoetry(int id, GsonLisenter<BaseBean<PoetryDataBean>> gsonLisenter);


    void testGet();

    void testHead();

    void testDelete();

    void testPost();

    void testPut();

    void testPatch();
}
