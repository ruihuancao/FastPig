package com.ruihuan.fastupdate;



import com.ruihuan.fastupdate.service.DownloadService;
import com.ruihuan.fastupdate.utils.AppUpdateUtils;

import java.io.File;


public class SilenceUpdateCallback extends UpdateCallback {
    @Override
    protected final void hasNewApp(final UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
        //添加信息
        UpdateAppBean updateAppBean = updateAppManager.fillUpdateAppData();
        //设置不显示通知栏下载进度
        updateAppBean.dismissNotificationProgress(true);

        if (AppUpdateUtils.appIsDownloaded(updateApp)) {
            showDialog(updateApp, updateAppManager, AppUpdateUtils.getAppFile(updateApp));
        } else {
            //假如是onlyWifi,则进行判断网络环境
            if (updateApp.isOnlyWifi() && !AppUpdateUtils.isWifi(updateAppManager.getContext())) {
                //要求是wifi下，且当前不是wifi环境
                return;
            }
            updateAppManager.download(new DownloadService.DownloadCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onProgress(float progress, long totalSize) {

                }

                @Override
                public void setMax(long totalSize) {

                }

                @Override
                public boolean onFinish(File file) {
                    showDialog(updateApp, updateAppManager, file);
                    return false;
                }


                @Override
                public void onError(String msg) {

                }

                @Override
                public boolean onInstallAppAndAppOnForeground(File file) {
                    return false;
                }
            });
        }
    }

    protected void showDialog(UpdateAppBean updateApp, UpdateAppManager updateAppManager, File appFile) {
        updateAppManager.showDialogFragment();
    }
}
