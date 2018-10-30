package com.skymxc.example.dagger2.ui.second;

import com.skymxc.example.dagger2.data.ApiService;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
public class SecondPresenter {

    protected ApiService apiService;
    protected SecondView view;

    public SecondPresenter(ApiService apiService, SecondView view) {
        this.apiService = apiService;
        this.view = view;
    }

    public void loadData(){
        apiService.loadData("second");
        view.loaded();
    }
}
