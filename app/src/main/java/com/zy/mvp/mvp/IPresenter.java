package com.zy.mvp.mvp;

/**
 * @Description: Created by yong on 2019/3/22 10:53.
 */
public interface IPresenter {
    /**
     * 判断 presenter 是否与 view 建立联系，防止出现内存泄露状况
     *
     * @return {@code true}: 联系已建立<br>{@code false}: 联系已断开
     */
    boolean isViewAttach();

    /**
     * 断开 presenter 与 view 直接的联系
     */
    void detachView();
}
