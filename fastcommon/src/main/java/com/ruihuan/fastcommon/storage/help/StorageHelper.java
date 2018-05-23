package com.ruihuan.fastcommon.storage.help;

import com.blankj.utilcode.util.EncryptUtils;
import com.ruihuan.fastcommon.helper.LogHelper;
import com.ruihuan.fastcommon.storage.http.contants.RequestContants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Description:
 * Data：2018/5/22-16:26
 * Author: Charles
 */
public class StorageHelper {


    public static String encodeParameters(String url, Map<String, String> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }
        StringBuilder encodedParams = new StringBuilder(url);
        try {
            if (!url.contains(RequestContants.REQUEST_QUESTION_HTTP)) {
                encodedParams.append(RequestContants.REQUEST_QUESTION_HTTP);
            } else {
                encodedParams.append(RequestContants.REQUEST_ADD_HTTP);
            }

            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), RequestContants.REQUEST_ENCODING_HTTP));
                encodedParams.append(RequestContants.REQUEST_EQ_HTTP);
                encodedParams.append(URLEncoder.encode(entry.getValue(), RequestContants.REQUEST_ENCODING_HTTP));
                encodedParams.append(RequestContants.REQUEST_ADD_HTTP);
            }
        } catch (UnsupportedEncodingException uee) {
            LogHelper.d("Encoding not supported: utf-8:"+url);
        }
        return encodedParams.toString().endsWith(RequestContants.REQUEST_ADD_HTTP) ?
                encodedParams.substring(0, encodedParams.length() - 1) : encodedParams.toString();
    }

    public static String geCacheKey(String url, Map<String, String> params){
        return EncryptUtils.encryptMD5ToString(encodeParameters(url, params));
    }
}
