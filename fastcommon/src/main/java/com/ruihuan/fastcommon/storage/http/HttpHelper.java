package com.ruihuan.fastcommon.storage.http;

import com.ruihuan.fastcommon.helper.LogHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Description:
 * Data：2018/5/25-12:05
 * Author: Charles
 */
public class HttpHelper {

    public static final String REQUEST_QUESTION_HTTP = "?";
    public static final String REQUEST_ENCODING_HTTP = "utf-8";
    public static final String REQUEST_ADD_HTTP = "&";
    public static final String REQUEST_EQ_HTTP = "=";


    public static String encodeParameters(String url, Map<String, String> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }
        StringBuilder encodedParams = new StringBuilder(url);
        try {
            if (!url.contains(REQUEST_QUESTION_HTTP)) {
                encodedParams.append(REQUEST_QUESTION_HTTP);
            } else {
                encodedParams.append(REQUEST_ADD_HTTP);
            }

            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), REQUEST_ENCODING_HTTP));
                encodedParams.append(REQUEST_EQ_HTTP);
                encodedParams.append(URLEncoder.encode(entry.getValue(), REQUEST_ENCODING_HTTP));
                encodedParams.append(REQUEST_ADD_HTTP);
            }
        } catch (UnsupportedEncodingException uee) {
            LogHelper.d("Encoding not supported: utf-8:"+url);
        }
        return encodedParams.toString().endsWith(REQUEST_ADD_HTTP) ?
                encodedParams.substring(0, encodedParams.length() - 1) : encodedParams.toString();
    }
}
