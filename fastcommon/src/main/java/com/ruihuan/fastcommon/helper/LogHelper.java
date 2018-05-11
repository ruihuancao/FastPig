package com.ruihuan.fastcommon.helper;

import android.support.annotation.Nullable;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class LogHelper {

    public static final String TAG = "LogHelper";

    private static boolean showLog = false;

    public static void init(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return showLog;
            }
        });
    }

    public static void setShowLog(boolean showLog) {
        LogHelper.showLog = showLog;
    }

    public static void d(String message){
        Logger.d(message);
    }

    public static void e(String message){
        Logger.e(message);
    }

    public static void w(String message){
        Logger.w(message);
    }

    public static void i(String message){
        Logger.i(message);
    }

    public static void v(String message){
        Logger.v(message);
    }

    public static void json(String message){
        Logger.json(message);
    }

    public static void xml(String xml){
        Logger.xml(xml);
    }

    public static void d(String tag, String message){
        if(showLog){
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message){
        if(showLog){
            Log.e(tag, message);
        }
    }
}
