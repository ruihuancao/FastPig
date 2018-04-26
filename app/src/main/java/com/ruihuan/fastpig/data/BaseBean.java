package com.ruihuan.fastpig.data;

import com.google.gson.annotations.SerializedName;

/**
 * Description:
 * Data：2018/4/25-13:53
 * Author: caoruihuan
 */
public class BaseBean<T> {


    @SerializedName("status")
    private int code;

    @SerializedName("data")
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
