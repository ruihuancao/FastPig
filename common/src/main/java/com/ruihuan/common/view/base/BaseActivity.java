package com.ruihuan.common.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ruihuan.common.event.EventBusManager;
import com.ruihuan.common.helper.LogHelper;


/**
 * Description:
 * Data：2018/5/7-10:31
 * Author: caoruihuan
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.d(TAG, getClass().getSimpleName() + "-------------onCreate");
        initParams();
        setContentView(getLayoutId());
        EventBusManager.register(this);
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogHelper.d(TAG, getClass().getSimpleName() + "-------------onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogHelper.d(TAG, getClass().getSimpleName() + "-------------onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogHelper.d(TAG, getClass().getSimpleName() + "-------------onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogHelper.d(TAG, getClass().getSimpleName() + "-------------onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogHelper.d(TAG, getClass().getSimpleName() + "-------------onStop");
        EventBusManager.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogHelper.d(TAG, getClass().getSimpleName() + "-------------onDestroy");
    }

    protected abstract int getLayoutId();

    protected abstract void initParams();

    protected abstract void initView();

    protected abstract void initData();

}
