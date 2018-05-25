package com.ruihuan.fastcommon.storage.http.lisenter;



import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class BaseLisenter<T> implements Callback{

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        onFailure(400, e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response){
        try{
            onResponse(convert(call, response));
        }catch (Exception e){
            e.printStackTrace();
            onFailure(400, e.getMessage());
        }
    }

    protected abstract T convert(Call call, Response response) throws IOException;

    public abstract void onFailure(int statusCode, String errorMessage);

    protected abstract void onResponse(T response);


    public void onProgress(long currentBytes, long totalBytes){}

    public void onProgress(long currentBytes, long totalBytes, float progress){}

}
