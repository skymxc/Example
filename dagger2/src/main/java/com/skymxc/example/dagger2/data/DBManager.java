package com.skymxc.example.dagger2.data;

import com.skymxc.example.dagger2.di.annotation.APPScoped;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 由 inject 注入全局单例
 */
@Singleton
public class DBManager {

    @Inject DBHelper helper;

    @Inject
    public DBManager(){}
}
