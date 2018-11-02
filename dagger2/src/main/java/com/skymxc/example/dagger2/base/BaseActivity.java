package com.skymxc.example.dagger2.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.skymxc.example.dagger2.mvp.IView;

import dagger.android.AndroidInjection;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView<P> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(loadLaoyutId());
        findView();
        initView();
        initListener();
        initData(savedInstanceState);

    }

    protected abstract @LayoutRes
    int loadLaoyutId();

    protected void initData(Bundle savedInstanceState) {
    }

    protected void initListener() {
    }

    protected void initView() {
    }

    protected void findView() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRegister();
    }

    protected void initRegister() {
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegister();
    }

    protected void unRegister() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().detachView();
        }
    }

    @Override
    public void showLoading(String msg, boolean cancelable) {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public P getPresenter() {
        return null;
    }
}
