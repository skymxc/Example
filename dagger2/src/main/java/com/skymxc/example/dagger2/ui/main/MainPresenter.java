package com.skymxc.example.dagger2.ui.main;

import com.skymxc.example.dagger2.base.BasePresenter;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
public class MainPresenter extends BasePresenter<MainView> {

    private MainData data;

    public MainPresenter(MainView view, MainData data) {
        this.data = data;
        this.view = view;
    }

    @Override
    public void start() {
        super.start();
        view.loadData(data.loadData());
    }
}
