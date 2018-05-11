package com.ruihuan.fastpig;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.ruihuan.fastcommon.helper.PremissionHelper;
import com.ruihuan.fastcommon.view.base.BaseActivity;
import com.ruihuan.fastpig.data.Api;
import com.ruihuan.fastpig.data.DataManager;
import com.ruihuan.fastupdate.UpdateAppBean;
import com.ruihuan.fastupdate.UpdateAppManager;
import com.ruihuan.fastupdate.UpdateCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.sharesdk.onekeyshare.OnekeyShare;

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

        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

    private void update(){
        String updateUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt";
        new UpdateAppManager
                .Builder()
                .setActivity(this)
                .setUpdateUrl(updateUrl)
                .setHttpManager(new UpdateHttpImpl())
                .build()
                .update();
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
