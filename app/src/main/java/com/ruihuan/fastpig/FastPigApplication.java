package com.ruihuan.fastpig;

import android.app.Application;

import com.mob.MobSDK;
import com.ruihuan.fastcommon.CommonInit;
import com.ruihuan.fastcommon.helper.LogHelper;

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
        MobSDK.init(this);
    }
}
