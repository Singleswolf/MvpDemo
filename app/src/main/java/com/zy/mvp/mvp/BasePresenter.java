package com.zy.mvp.mvp;

import java.lang.ref.WeakReference;

/**
 * @Description: Created by yong on 2019/3/22 10:56.
 */
public class BasePresenter<V extends IView> implements IPresenter {

    private WeakReference<V> mViewRef;

    public BasePresenter(V view) {
        attachView(view);
    }

    private void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    public V getView() {
        if (!isViewAttach()) {
            throw new IllegalStateException(this.getClass().getCanonicalName() + " is not attach view");
        }
        return mViewRef.get();
    }

    @Override
    public boolean isViewAttach() {
        return mViewRef != null && getView() != null;
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
