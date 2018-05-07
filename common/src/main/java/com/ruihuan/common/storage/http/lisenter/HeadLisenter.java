package com.ruihuan.common.storage.http.lisenter;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


public abstract class HeadLisenter extends BaseLisenter<String> {

    @Override
    public String convert(Call call, Response response) throws IOException{
        return response.headers().toString();
    }

}
