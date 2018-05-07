package com.ruihuan.common.storage.http.lisenter;



import com.ruihuan.common.storage.http.contants.RequestContants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class BaseLisenter<T> implements Callback{

    @Override
    public void onFailure(Call call, IOException e) {
        onFailure(RequestContants.REQUEST_ERROR_CODE, e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response){
        try{
            onResponse(convert(call, response));
        }catch (Exception e){
            onFailure(RequestContants.REQUEST_ERROR_CODE, e.getMessage());
        }
    }

    public abstract T convert(Call call, Response response) throws IOException;

    public abstract void onFailure(int statusCode, String errorMessage);

    public abstract void onResponse(T response);

    public void onProgress(long currentBytes, long totalBytes){}
}
