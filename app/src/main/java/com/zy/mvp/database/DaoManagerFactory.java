package com.zy.mvp.database;

import android.content.Context;

import com.zy.mvp.database.manager.DaoManager;

public class DaoManagerFactory {
    private static final String TAG = DaoManagerFactory.class.getSimpleName();
    private static final String DB_NAME = "MvpDemo.db";
    private static volatile DaoManagerFactory mFactory;
    private DaoManager mDaoManager;
    private UserManager mUserManager;
    private boolean isDebug;

    private DaoManagerFactory() {

    }

    public static DaoManagerFactory getFactory() {
        if (mFactory == null) {
            synchronized (DaoManagerFactory.class) {
                if (mFactory == null) {
                    mFactory = new DaoManagerFactory();
                }
            }
        }
        return mFactory;
    }


    public synchronized void init(Context context) {
        if (mDaoManager == null) {
            mDaoManager = new DaoManager(context, DB_NAME);
        }
        mDaoManager.setDebug(isDebug);
    }

    public UserManager getUserManager() {
        if (mUserManager == null) {
            mUserManager = new UserManager(mDaoManager.getDaoSession().getUserDao());
        }
        return mUserManager;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
        if (mDaoManager != null) {
            mDaoManager.setDebug(debug);
        }
    }

    public void deleteData() {
        if (mUserManager != null) {
            mUserManager.deleteAll();
        }
    }

    public void close() {
        if (mDaoManager != null) {
            mDaoManager.closeDataBase();
        }
    }
}