package com.skymxc.example.dagger2.data;

import android.util.Log;

import com.skymxc.example.dagger2.di.annotation.APPScoped;

import javax.inject.Inject;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@APPScoped
public class ApiService {

    @Inject
    public ApiService(){

    }
    public void loadData(String source){
        Log.e(ApiService.class.getSimpleName()+"","loadData->"+source);
    }
}
