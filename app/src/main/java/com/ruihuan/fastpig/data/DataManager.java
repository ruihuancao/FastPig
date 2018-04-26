package com.ruihuan.fastpig.data;


/**
 * Description:
 * Data：2018/4/25-13:53
 * Author: caoruihuan
 */
public class DataManager {

    private static DataManager instance;

    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    private Api api;

    private DataManager() {
        this.api = new ApiImpl();
    }

    public Api getApi() {
        return api;
    }
}
