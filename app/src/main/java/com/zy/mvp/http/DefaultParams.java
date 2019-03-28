package com.zy.mvp.http;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: add common params
 * Created by yong on 2019/3/26 15:15.
 */
public class DefaultParams implements RetrofitConfig.Params {
    @NonNull
    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> map = new HashMap<>();
        return map;
    }

    @NonNull
    @Override
    public Map<String, String> getUrlParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "00d91e8e0cca2b76f515926a36db68f5");
        return map;
    }

    @NonNull
    @Override
    public Map<String, String> getBodyParams() {
        HashMap<String, String> map = new HashMap<>();
        return map;
    }
}
