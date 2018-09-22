package com.skymxc.example.dagger2.di.module;


import android.app.Application;
import android.content.Context;

import com.skymxc.example.dagger2.data.RemoteManager;
import com.skymxc.example.dagger2.di.annotation.APPScoped;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class APPModule {

    private Application context;


    public APPModule(Application context){
        this.context = context;
    }


    @Provides
    public Context provideContext(){
        return context;
    }

    @APPScoped
    @Provides
    public RemoteManager provideRemoteManager(){
        return new RemoteManager();
    }

}
