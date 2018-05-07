package com.ruihuan.fastpig;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import com.ruihuan.common.helper.PremissionHelper;
import com.ruihuan.common.view.base.BaseActivity;
import com.ruihuan.fastpig.data.Api;
import com.ruihuan.fastpig.data.DataManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity {

    private TextView mTextMessage;
    private StringBuilder stringBuilder = new StringBuilder();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    testHttp();
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParams() {
        Bundle bundle = getIntent().getExtras();
    }

    @Override
    protected void initView() {
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        PremissionHelper.requestCamera(new PremissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {

            }
        });
    }

    @Override
    protected void initData() {

    }

    private void testHttp(){
        Api api = DataManager.getInstance().getApi();
        api.testGet();
//        api.testHead();
//        api.testDelete();
//        api.testPatch();
//        api.testPost();
//        api.testPut();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reciverData(DataEvent dataEvent){
        stringBuilder.append(dataEvent.data);
        stringBuilder.append("\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append("\r\n");
        mTextMessage.setText(stringBuilder);
    }

}
