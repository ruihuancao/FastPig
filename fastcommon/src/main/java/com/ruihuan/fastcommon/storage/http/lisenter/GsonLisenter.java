package com.ruihuan.fastcommon.storage.http.lisenter;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;


public abstract class GsonLisenter<T> extends BaseLisenter<T> {

    public abstract Type getType();

    @Override
    public T convert(Call call, Response response) throws IOException{
        return new Gson().fromJson(response.body().string(), getType());
    }
}
