package com.ruihuan.fastupdate;

import org.json.JSONObject;

public class UpdateCallback {

    protected UpdateAppBean parseJson(String json) {
        UpdateAppBean updateAppBean = new UpdateAppBean();
        try {
            JSONObject jsonObject = new JSONObject(json);
            updateAppBean.setUpdate(jsonObject.optString("update"))
                    //存放json，方便自定义解析
                    .setOriginRes(json)
                    .setNewVersion(jsonObject.optString("new_version"))
                    .setApkFileUrl(jsonObject.optString("apk_file_url"))
                    .setTargetSize(jsonObject.optString("target_size"))
                    .setUpdateLog(jsonObject.optString("update_log"))
                    .setConstraint(jsonObject.optBoolean("constraint"))
                    .setNewMd5(jsonObject.optString("new_md5"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateAppBean;
    }

    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
        updateAppManager.showDialogFragment();
    }

    /**
     * 网路请求之后
     */
    protected void onAfter() {
    }

    protected void noNewApp(String error) {
    }

    /**
     * 网络请求之前
     */
    protected void onBefore() {
    }

}
