package com.skymxc.example.dagger2.mvp;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
public interface IView<P extends IPresenter> {

    void showLoading(String msg, boolean cancelable);

    void showLoading(String msg);

    void showError(String msg);

    void showError(Throwable throwable);

    P getPresenter();


}
