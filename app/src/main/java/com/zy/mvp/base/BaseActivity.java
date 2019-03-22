package com.zy.mvp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.zy.mvp.mvp.BasePresenter;
import com.zy.mvp.mvp.IView;

/**
 * @Description: Created by yong on 2019/3/22 11:11.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView {

    private P mPresenter;
    private ProgressDialog mDialog;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(getLayoutId());
        initView();
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
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
        }
        if (!TextUtils.isEmpty(msg)) {
            mDialog.setMessage(msg);
        }
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
        this.mPresenter = null;
    }
}
