package com.skymxc.example.dagger2.mvp;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
public interface IPresenter<V extends IView> {


    /**
     * 分离 view
     */
    void detachView();

    /**
     * 加载一些初始化的数据
     */
    void start();
}
