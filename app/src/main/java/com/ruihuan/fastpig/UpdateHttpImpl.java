package com.ruihuan.fastpig;

import android.support.annotation.NonNull;

import com.ruihuan.fastcommon.helper.LogHelper;
import com.ruihuan.fastcommon.storage.http.HttpManager;
import com.ruihuan.fastcommon.storage.http.lisenter.FileLisenter;
import com.ruihuan.fastcommon.storage.http.lisenter.StringLisenter;
import com.ruihuan.fastupdate.IUpdateHttp;

import java.io.File;
import java.util.Map;

/**
 * Description:
 * Data：2018/5/11-11:59
 * Author: caoruihuan
 */
public class UpdateHttpImpl implements IUpdateHttp{

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        HttpManager.getInstance().get(url, params, new StringLisenter() {
            @Override
            public void onFailure(int statusCode, String errorMessage) {
                callBack.onError(errorMessage);
            }

            @Override
            public void onResponse(String response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        HttpManager.getInstance().post(url, params, new StringLisenter() {
            @Override
            public void onFailure(int statusCode, String errorMessage) {
                callBack.onError(errorMessage);
            }

            @Override
            public void onResponse(String response) {
                callBack.onResponse(response);
            }
        });
    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull final FileCallback callback) {
        callback.onBefore();
        HttpManager.getInstance().get(url, new FileLisenter(path+fileName) {
            @Override
            public void onFailure(int statusCode, String errorMessage) {
                LogHelper.d("下载出错");
                callback.onError(errorMessage);
            }

            @Override
            public void onResponse(File response) {
                LogHelper.d("下载完成");
                callback.onResponse(response);
            }

            @Override
            public void onProgress(long currentBytes, long totalBytes, float progress) {
                super.onProgress(currentBytes, totalBytes, progress);
                LogHelper.d("正在下载："+"current:"+currentBytes + "total:"+totalBytes +" progress:"+progress);
                callback.onProgress(progress, totalBytes);
            }
        });
    }
}
