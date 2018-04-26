package com.ruihuan.storagepig;


import android.content.Context;

import com.ruihuan.commonpig.CommonInit;
import com.ruihuan.commonpig.log.LogPig;
import com.ruihuan.storagepig.config.AppConfig;
import com.ruihuan.storagepig.db.DBManager;
import com.ruihuan.storagepig.http.HttpManager;

/**
 * Description:
 * Data：2018/4/25-10:59
 * Author: caoruihuan
 */
public class StorageInit {

    public static void init(Context context){
        CommonInit.init(context);
        AppConfig.getInstance().init(context);
        HttpManager.getInstance().init(context);
        DBManager.getInstance().init(context);
        LogPig.d("storage init");
    }
}
