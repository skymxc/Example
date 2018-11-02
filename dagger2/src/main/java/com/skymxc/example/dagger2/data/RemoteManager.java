package com.skymxc.example.dagger2.data;

import com.skymxc.example.dagger2.di.annotation.APPScoped;

import javax.inject.Inject;

/**
 *  模拟远程数据操作
 */
public class RemoteManager {

    public String loadData(String source){
        return source+" load complete.";
    }

}
