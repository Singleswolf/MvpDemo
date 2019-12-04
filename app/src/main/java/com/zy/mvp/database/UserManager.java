package com.zy.mvp.database;

import com.zy.mvp.database.manager.BaseManager;
import com.zy.mvp.model.entity.User;

import org.greenrobot.greendao.AbstractDao;

/**
 * @Description: Created by yong on 2019/12/4 14:56.
 */
public class UserManager extends BaseManager<User, Long> {
    public UserManager(AbstractDao<User, Long> dao) {
        super(dao);
    }
}
