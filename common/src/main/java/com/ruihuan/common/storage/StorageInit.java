package com.ruihuan.common.storage;


import android.content.Context;

import com.ruihuan.common.CommonInit;
import com.ruihuan.common.helper.LogHelper;
import com.ruihuan.common.storage.config.AppConfig;
import com.ruihuan.common.storage.db.DBManager;
import com.ruihuan.common.storage.http.HttpManager;

/**
 * Description:
 * Data：2018/4/25-10:59
 * Author: caoruihuan
 */
public class StorageInit {

    public static void init(Context context){
        AppConfig.getInstance().init(context);
        HttpManager.getInstance().init(context);
        DBManager.getInstance().init(context);
        LogHelper.d("storage init");
    }
}
