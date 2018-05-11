package com.ruihuan.fastcommon.storage.http.lisenter;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


public abstract class StringLisenter extends BaseLisenter<String> {

    @Override
    public String convert(Call call, Response response) throws IOException{
        return response.body().string();
    }
}
