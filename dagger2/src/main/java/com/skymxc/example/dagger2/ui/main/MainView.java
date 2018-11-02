package com.skymxc.example.dagger2.ui.main;

import com.skymxc.example.dagger2.mvp.IView;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
public interface MainView extends IView<MainPresenter> {

    void loadData(String data);
}
