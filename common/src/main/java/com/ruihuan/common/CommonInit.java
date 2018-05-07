package com.ruihuan.common;

import android.content.Context;

import com.ruihuan.common.helper.LogHelper;
import com.ruihuan.common.helper.UtilHelper;
import com.ruihuan.common.storage.StorageInit;

/**
 * Description:
 * Data：2018/4/25-15:32
 * Author: caoruihuan
 */
public class CommonInit {

    public static void init(Context context){
        UtilHelper.init(context);
        LogHelper.init();
        StorageInit.init(context);
    }
}
