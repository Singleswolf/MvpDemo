package com.zy.mvp.http;

import com.zy.mvp.utils.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * @Description: Created by yong on 2019/3/27 20:45.
 */
public abstract class RxCallback<T> extends DisposableObserver<BaseResponse<T>> {

    @Override
    public void onNext(BaseResponse<T> tBaseResponse) {
        if (tBaseResponse.isSuccess()) {
            onSuccess(tBaseResponse.data);
        } else {
            onFailed(tBaseResponse.code, tBaseResponse.msg);
        }
    }

    protected abstract void onSuccess(T data);

    protected abstract void onFailed(int code, String msg);

    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof SocketTimeoutException) {//请求超时
            } else if (e instanceof ConnectException) {//网络连接超时
                onFailed(-1, "网络连接超时");
            } else if (e instanceof SSLHandshakeException) {//安全证书异常
                onFailed(-1, "安全证书异常");
            } else if (e instanceof HttpException) {//请求的地址不存在
                int code = ((HttpException) e).code();
                if (code == 504) {
                    onFailed(-1, "网络异常，请检查您的网络状态");
                } else if (code == 404) {
                    onFailed(-1, "请求的地址不存在");
                } else {
                    onFailed(-1, "请求失败");
                }
            } else if (e instanceof UnknownHostException) {//域名解析失败
                onFailed(-1, "域名解析失败");
            } else {
                onFailed(-1, "error:" + e.getMessage());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            Logger.e("OnSuccessAndFaultSub", "error:", e);
            onFailed(-1, "finally error:" + e.getMessage());
        }
    }

    @Override
    public void onComplete() {
    }
}
