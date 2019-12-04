package com.zy.mvp.database.manager;

import android.content.Context;

import com.zy.mvp.model.entity.DaoMaster;
import com.zy.mvp.model.entity.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;


public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private Context mContext;
    private String dbName;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DevOpenHelper mDevOpenHelper;

    public DaoManager(Context context, String dbname) {
        this.mContext = context;
        this.dbName = dbname;
    }

    /**
     * 判断是否存在数据库，如果没有则创建数据库
     *
     * @return DaoMaster
     */
    private DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            mDevOpenHelper = new DevOpenHelper(mContext, dbName);
            mDaoMaster = new DaoMaster(mDevOpenHelper.getWritableDb());
        }
        return mDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询的操作，仅仅是一个接口
     *
     * @return DaoSession
     */
    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    /**
     * 打开输出日志的操作,默认是关闭的
     */
    public void setDebug(boolean debug) {
        QueryBuilder.LOG_SQL = debug;
        QueryBuilder.LOG_VALUES = debug;
    }

    /**
     * 关闭所有的操作,数据库开启的时候，使用完毕了必须要关闭
     */
    public void closeDataBase() {
        closeHelper();
        closeDaoSession();
    }

    private void closeHelper() {
        if (mDevOpenHelper != null) {
            mDevOpenHelper.close();
            mDevOpenHelper = null;
        }
    }

    private void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }
}