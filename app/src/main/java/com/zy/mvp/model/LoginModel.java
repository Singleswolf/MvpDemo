package com.zy.mvp.model;

import android.annotation.SuppressLint;

import com.zy.mvp.base.HttpResponseListener;
import com.zy.mvp.contract.LoginContract;
import com.zy.mvp.http.BaseResponse;
import com.zy.mvp.http.DefaultRetrofitConfig;
import com.zy.mvp.http.RetrofitFactory;
import com.zy.mvp.http.RxCallback;
import com.zy.mvp.http.utils.DelayRetryWhenError;
import com.zy.mvp.http.utils.RxSchedulers;
import com.zy.mvp.model.api.LoginService;
import com.zy.mvp.model.entity.User;
import com.zy.mvp.utils.Logger;

import io.reactivex.Observable;

/**
 * @Description: Created by yong on 2019/3/22 11:55.
 */
public class LoginModel implements LoginContract.LoginModel {
    @SuppressLint("CheckResult")
    @Override
    public void requestLogin(final String user, final String pwd, final HttpResponseListener<User> callback) {
        LoginService service = RetrofitFactory.newBuilder(new DefaultRetrofitConfig(RxSchedulers.NETWORKING)).build().create(LoginService.class);
        Observable<BaseResponse<User>> observable = service.login(user, pwd);
        observable.compose(RxSchedulers.<BaseResponse<User>>compose())
                .retryWhen(new DelayRetryWhenError())
                .subscribe(new RxCallback<User>() {
                    @Override
                    protected void onFailed(int code, String msg) {
                        Logger.e("LoginModel", "code %d, msg %s", code, msg);
                        callback.onFailed(null, msg);
                    }

                    @Override
                    protected void onSuccess(User data) {
                        Logger.i("LoginModel", "name = %s", data.getName());
                        callback.onSuccess(null, data);
                    }
                });
    }
}
