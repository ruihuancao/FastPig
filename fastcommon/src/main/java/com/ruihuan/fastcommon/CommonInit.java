package com.ruihuan.fastcommon;

import android.content.Context;

import com.ruihuan.fastcommon.helper.LogHelper;
import com.ruihuan.fastcommon.helper.UtilHelper;
import com.ruihuan.fastcommon.storage.StorageInit;

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
