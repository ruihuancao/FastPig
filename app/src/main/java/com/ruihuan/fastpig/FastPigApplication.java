package com.ruihuan.fastpig;

import android.app.Application;

import com.ruihuan.common.CommonInit;
import com.ruihuan.common.helper.LogHelper;
import com.ruihuan.common.storage.StorageInit;

/**
 * Description:
 * Data：2018/4/25-14:22
 * Author: caoruihuan
 */
public class FastPigApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonInit.init(getApplicationContext());
        LogHelper.setShowLog(true);
    }
}
