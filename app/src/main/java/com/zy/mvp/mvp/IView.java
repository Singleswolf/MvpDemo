package com.zy.mvp.mvp;

/**
 * @Description: Created by yong on 2019/3/22 10:50.
 */
public interface IView {
    void showLoading(String msg);

    void hideLoading();

    void showToast(String msg);
}
