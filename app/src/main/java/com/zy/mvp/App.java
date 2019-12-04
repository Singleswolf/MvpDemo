package com.zy.mvp;

import android.app.Application;

import com.zy.mvp.database.DaoManagerFactory;

/**
 * @Description: Created by yong on 2019/3/22 12:15.
 */
public class App extends Application {
    private static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        DaoManagerFactory.getFactory().init(this);
        DaoManagerFactory.getFactory().setDebug(true);
    }

    public static Application getApp() {
        return app;
    }
}
