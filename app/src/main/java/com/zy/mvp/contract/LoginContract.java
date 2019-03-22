package com.zy.mvp.contract;

import com.zy.mvp.base.HttpResponseListener;
import com.zy.mvp.mvp.IPresenter;
import com.zy.mvp.mvp.IView;
import com.zy.mvp.model.entity.User;

/**
 * @Description: Created by yong on 2019/3/22 11:31.
 */
public interface LoginContract {

    interface LoginView extends IView {
        void loginSuccess();
        void loginFailed(String error);
    }

    interface LoginPresenter extends IPresenter {
        void login(String user, String pwd);
    }

    interface LoginModel {
        void requestLogin(String user, String pwd, HttpResponseListener<User> callback);
    }

}
