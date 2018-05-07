package com.ruihuan.commonpig;

import android.content.Context;

import com.ruihuan.commonpig.log.LogHelper;
import com.ruihuan.commonpig.utils.UtilHelper;

/**
 * Description:
 * Data：2018/4/25-15:32
 * Author: caoruihuan
 */
public class CommonInit {

    public static void init(Context context){
        UtilHelper.init(context);
        LogHelper.init();
    }
}
