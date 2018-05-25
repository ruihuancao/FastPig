package com.ruihuan.fastcommon.storage.config;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Description:
 * Data：2018/4/25-15:21
 * Author: caoruihuan
 */
public class ConfigManager {

    private static final String PREF_DEFAULT_NAME = "pref_common";
    private volatile static ConfigManager instance;
    private SharedPreferences mPref;

    public ConfigManager init(Context context){
        mPref = context.getSharedPreferences(PREF_DEFAULT_NAME, Context.MODE_PRIVATE);
        return instance;
    }

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        if(instance == null){
            synchronized (ConfigManager.class){
                if(instance == null){
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    public void setPrefName(Context context, String name){
        mPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void setStringValue(String key, String value){
        mPref.edit().putString(key, value).apply();
    }

    public String getStringValue(String key, String defaultValue){
        return mPref.getString(key, defaultValue);
    }

    public String getStringValue(String key){
        return mPref.getString(key, "");
    }

    public void setBooleanValue(String key, boolean value){
        mPref.edit().putBoolean(key, value).apply();
    }

    public boolean getBooleanValue(String key, boolean defaultValue){
        return mPref.getBoolean(key, defaultValue);
    }

    public boolean getBooleanValue(String key){
        return mPref.getBoolean(key, false);
    }

    public void setLongValue(String key, long value){
        mPref.edit().putLong(key, value).apply();
    }

    public long getLongValue(String key){
        return mPref.getLong(key, 0);
    }
    public long getLongValue(String key, long defaultValue){
        return mPref.getLong(key, defaultValue);
    }

    public void setIntValue(String key, int value){
        mPref.edit().putInt(key, value).apply();
    }

    public int getIntValue(String key){
        return mPref.getInt(key, 0);
    }
    public int getIntValue(String key, int defaultValue){
        return mPref.getInt(key, defaultValue);
    }

    public void setFloatValue(String key, float value){
        mPref.edit().putFloat(key, value).apply();
    }

    public float getFloatValue(String key){
        return mPref.getFloat(key, 0f);
    }

    public float getFloatValue(String key, float defaultValue){
        return mPref.getFloat(key, defaultValue);
    }
}
