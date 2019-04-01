package com.zy.mvp.http;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.zy.mvp.App;
import com.zy.mvp.http.interceptor.ConfigParamsInterceptor;
import com.zy.mvp.http.interceptor.HttpLoggingInterceptor;
import com.zy.mvp.http.interceptor.TimeoutInterceptor;
import com.zy.mvp.http.utils.Gsons;
import com.zy.mvp.http.utils.HttpsUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;

/**
 * @Description: Created by yong on 2019/3/26 14:56.
 */
public class DefaultRetrofitConfig implements RetrofitConfig {
    private static final int DEFAULT_TIME_OUT_SECOND = 15;
    private static final int DEFAULT_UPLOAD_TIME_OUT_SECOND = 60;
    private Scheduler mScheduler;
    private OkHttpClient mOkHttpClient;
    private OkHttpClient mUploadOkHttpClient;
    private boolean isUpload = false;
    private boolean logging = true;
    private boolean isHttps = true;
    private boolean isCookie = true;

    public DefaultRetrofitConfig(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    @Override
    public Gson buildGson() {
        return Gsons.GSON;
    }

    @Override
    public Params buildParams() {
        return new DefaultParams();
    }

    @Override
    public String buildBaseUrl() {
        return "https://www.apiopen.top/";
    }

    @Override
    public OkHttpClient buildClient() {
        if (isUpload) {
            if (mUploadOkHttpClient == null) {
                mUploadOkHttpClient = getHttpClient(DEFAULT_UPLOAD_TIME_OUT_SECOND);
            }
            return mUploadOkHttpClient;
        }
        if (mOkHttpClient == null) {
            mOkHttpClient = getHttpClient(DEFAULT_TIME_OUT_SECOND);
        }
        return mOkHttpClient;
    }

    @Override
    public Scheduler getExecuteScheduler() {
        return mScheduler;
    }


    private OkHttpClient getHttpClient(int timeOut) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(timeOut, TimeUnit.SECONDS);
        builder.readTimeout(timeOut, TimeUnit.SECONDS);
        builder.writeTimeout(timeOut, TimeUnit.SECONDS);

        if (logging) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        if (isHttps) {
            HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();//https
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
            builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
        }
        if (isCookie) {
            ClearableCookieJar cookieJar =
                    new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getApp()));
            builder.cookieJar(cookieJar);
        }
        builder.addInterceptor(new TimeoutInterceptor());
        builder.addInterceptor(new ConfigParamsInterceptor(buildParams()));
        return builder.build();
    }

}
