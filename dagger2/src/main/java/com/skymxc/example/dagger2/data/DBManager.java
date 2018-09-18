package com.skymxc.example.dagger2.data;

import com.skymxc.example.dagger2.di.annotation.APPScoped;

import javax.inject.Inject;

@APPScoped
public class DBManager {

    @Inject DBHelper helper;

    @Inject
    public DBManager(){}
}
