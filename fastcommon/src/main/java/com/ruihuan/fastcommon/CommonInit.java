package com.ruihuan.fastcommon;

import android.content.Context;
import android.os.storage.StorageManager;

import com.ruihuan.fastcommon.helper.LogHelper;
import com.ruihuan.fastcommon.helper.UtilHelper;
import com.ruihuan.fastcommon.storage.StorageApi;

/**
 * Description:
 * Data：2018/4/25-15:32
 * Author: caoruihuan
 */
public class CommonInit {

    public static void init(Context context){
        UtilHelper.init(context);
        LogHelper.init();
        StorageApi.init(context);
    }
}
