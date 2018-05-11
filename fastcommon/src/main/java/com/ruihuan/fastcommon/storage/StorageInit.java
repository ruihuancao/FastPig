package com.ruihuan.fastcommon.storage;


import android.content.Context;

import com.ruihuan.fastcommon.CommonInit;
import com.ruihuan.fastcommon.helper.LogHelper;
import com.ruihuan.fastcommon.storage.config.AppConfig;
import com.ruihuan.fastcommon.storage.db.DBManager;
import com.ruihuan.fastcommon.storage.http.HttpManager;

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
