package com.ruihuan.commonpig;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.ruihuan.commonpig.log.LogPig;

/**
 * Description:
 * Data：2018/4/25-15:32
 * Author: caoruihuan
 */
public class CommonInit {

    public static void init(Context context){
        Utils.init(context);
        LogPig.init();
    }
}
