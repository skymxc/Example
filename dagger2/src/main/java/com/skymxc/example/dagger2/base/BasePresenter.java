package com.skymxc.example.dagger2.base;

import com.skymxc.example.dagger2.mvp.IPresenter;
import com.skymxc.example.dagger2.mvp.IView;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {
    protected V view;



    @Override
    public void detachView() {
        // TODO: 2018/11/2 处理一些尚未完成的任务 & 切断与 view的联系
    }

    @Override
    public void start() {
        // TODO: 2018/11/2 开始处理一些初始化的数据
    }
}
