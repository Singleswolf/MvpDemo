package com.zy.mvp.http;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Map;

import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;

public interface RetrofitConfig {

    Gson buildGson();

    Params buildParams();

    String buildBaseUrl();

    OkHttpClient buildClient();

    Scheduler getExecuteScheduler();

    interface Params {

        @NonNull
        Map<String, String> getHeaders();

        @NonNull
        Map<String, String> getUrlParams();

        @NonNull
        Map<String, String> getBodyParams();
    }
}
