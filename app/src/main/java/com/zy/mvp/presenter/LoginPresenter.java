package com.zy.mvp.presenter;

import com.zy.mvp.mvp.BasePresenter;
import com.zy.mvp.base.HttpResponseListener;
import com.zy.mvp.contract.LoginContract;
import com.zy.mvp.model.LoginModel;
import com.zy.mvp.model.entity.User;

/**
 * @Description: Created by yong on 2019/3/22 11:33.
 */
public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter, HttpResponseListener<User> {

    private LoginModel mLoginModel;

    public LoginPresenter(LoginContract.LoginView view) {
        super(view);
        mLoginModel = new LoginModel();
    }

    @Override
    public void login(String user, String pwd) {
        mLoginModel.requestLogin(user, pwd, this);
    }

    @Override
    public void onSuccess(Object tag, User user) {
        getView().loginSuccess(user);
    }

    @Override
    public void onFailed(Object tag, String error) {
        getView().loginFailed(error);
    }
}
