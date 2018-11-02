package com.skymxc.example.dagger2.ui.main;

import com.skymxc.example.dagger2.data.RemoteManager;

import javax.inject.Inject;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
public class MainData {

    protected RemoteManager manager;
    @Inject
    public MainData(RemoteManager manager){
        this.manager = manager;
    }
    public String loadData(){
        return manager.loadData("main");
    }
}
