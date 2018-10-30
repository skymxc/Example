package com.skymxc.example.dagger2.ui.main;

import com.skymxc.example.dagger2.data.ApiService;

import javax.inject.Inject;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
public class MainPresenter {

    protected MainView view;
    protected ApiService apiService;
    @Inject
    public MainPresenter(MainView view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }
    public void loadMain(){
        apiService.loadData("main");
        view.loaded();
    }
}
