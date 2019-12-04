package com.zy.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zy.mvp.mvp.BasePresenter;
import com.zy.mvp.mvp.IView;

/**
 * @Description: Created by yong on 2019/3/22 15:03.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView {
    private P mPresenter;
    protected View mContentView;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContentView = inflater.inflate(getLayoutId(), container, false);
        initView();
        return mContentView;
    }

    protected void initView() {
    }

    protected abstract int getLayoutId();

    public P getPresenter() {
        if (mPresenter == null) {
            mPresenter = onBindPresenter();
        }
        return mPresenter;
    }

    protected abstract P onBindPresenter();

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        getPresenter().detachView();
        this.mPresenter = null;
        this.mContext = null;
    }
}
