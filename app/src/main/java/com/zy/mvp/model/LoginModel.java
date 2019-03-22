package com.zy.mvp.model;

import android.os.Handler;

import com.zy.mvp.base.HttpResponseListener;
import com.zy.mvp.contract.LoginContract;
import com.zy.mvp.model.entity.User;

/**
 * @Description: Created by yong on 2019/3/22 11:55.
 */
public class LoginModel implements LoginContract.LoginModel {
    @Override
    public void requestLogin(final String user, final String pwd, final HttpResponseListener<User> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!"admin".equals(user)){
                    callback.onFailed(null, "账号不对");
                } else if (!"123".equals(pwd)){
                    callback.onFailed(null, "密码不对");
                } else {
                    callback.onSuccess(null, null);
                }
            }
        }, 2000);
    }
}
