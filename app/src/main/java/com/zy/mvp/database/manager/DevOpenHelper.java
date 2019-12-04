package com.zy.mvp.database.manager;

import android.content.Context;

import com.zy.mvp.model.entity.DaoMaster;
import com.zy.mvp.utils.Logger;

import org.greenrobot.greendao.database.Database;

/**
 * @Description: Created by yong on 2019/9/9 12:25.
 */
public class DevOpenHelper extends DaoMaster.OpenHelper {
    public DevOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Logger.i("DBMigrationHelper", "Upgrading schema from version %d to %d", oldVersion, newVersion);
        //升级过程需要升级的类
        if (oldVersion < newVersion) {
//            MigrationHelper.getInstance().migrate(db, UserDao.class);
        }
//        DaoMaster.dropAllTables(db, true);
//        DaoMaster.createAllTables(db, false);
    }
}
