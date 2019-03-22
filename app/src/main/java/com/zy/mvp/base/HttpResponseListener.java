package com.zy.mvp.base;

/**
 * @Description: Created by yong on 2019/3/22 11:38.
 */
public interface HttpResponseListener<T> {

    void onSuccess(Object tag, T t);

    void onFailed(Object tag, String error);
}
