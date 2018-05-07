package com.ruihuan.fastpig;

import android.app.Application;

import com.ruihuan.commonpig.log.LogHelper;
import com.ruihuan.storagepig.StorageInit;

/**
 * Description:
 * Data：2018/4/25-14:22
 * Author: caoruihuan
 */
public class FastPigApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StorageInit.init(getApplicationContext());
        LogHelper.setShowLog(true);
    }
}
