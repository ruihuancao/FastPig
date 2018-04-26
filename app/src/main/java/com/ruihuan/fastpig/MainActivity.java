package com.ruihuan.fastpig;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruihuan.fastpig.data.Api;
import com.ruihuan.fastpig.data.BaseBean;
import com.ruihuan.fastpig.data.DataManager;
import com.ruihuan.fastpig.data.PoetryDataBean;
import com.ruihuan.fastpig.data.TestBean;
import com.ruihuan.commonpig.log.LogPig;
import com.ruihuan.storagepig.http.lisenter.GsonLisenter;
import com.ruihuan.thirdpig.eventbus.EventBusManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBusManager.register(this);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onDestroy() {
        EventBusManager.unregister(this);
        super.onDestroy();
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
