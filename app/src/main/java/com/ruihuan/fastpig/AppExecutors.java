package com.ruihuan.fastpig;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Description:
 * Data：12/25/2018-3:30 PM
 * Author: ruihuancao@gmail.com
 */
public class AppExecutors {

    private Executor diskIO;
    private Executor networkIO;
    private Executor mainThread;

    public AppExecutors() {
        diskIO = Executors.newSingleThreadExecutor();
        networkIO = Executors.newFixedThreadPool(3);
        mainThread = new MainThreadExecutor();
    }

    public void test(){
        Log.d("test", "AppExecutors test");
    }

    public Executor diskIO(){
        return diskIO;
    }

    public Executor networkIO(){
        return networkIO;
    }


    public Executor mainThread(){
        return mainThread;
    }

    private class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }

        public void execute(Runnable command, long delay) {
            mainThreadHandler.postDelayed(command, delay);
        }
    }
}
