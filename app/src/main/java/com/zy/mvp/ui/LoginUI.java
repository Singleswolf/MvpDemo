package com.zy.mvp.ui;

import android.view.View;
import android.widget.EditText;

import com.zy.mvp.R;
import com.zy.mvp.base.BaseActivity;
import com.zy.mvp.contract.LoginContract;
import com.zy.mvp.model.entity.User;
import com.zy.mvp.presenter.LoginPresenter;

/**
 * @Description: Created by yong on 2019/3/22 11:33.
 */
public class LoginUI extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {

    private EditText mUserName;
    private EditText mPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mUserName = (EditText) findViewById(R.id.user);
        mPassword = (EditText) findViewById(R.id.pwd);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading("登录中");
                getPresenter().login(mUserName.getText().toString().trim(), mPassword.getText().toString().trim());
            }
        });
    }

    @Override
    protected LoginPresenter onBindPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginSuccess(User user) {
        hideLoading();
        showToast("登录成功");
    }

    @Override
    public void loginFailed(String error) {
        hideLoading();
        showToast(error);
    }
}
