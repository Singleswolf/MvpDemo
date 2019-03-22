package com.zy.mvp;

import android.app.Application;

/**
 * @Description: Created by yong on 2019/3/22 12:15.
 */
public class App extends Application {
    private static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Application getApp() {
        return app;
    }
}
