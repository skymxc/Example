package com.skymxc.example.dagger2.di.module;


import android.app.Application;
import android.content.Context;

import com.skymxc.example.dagger2.data.RemoteManager;
import com.skymxc.example.dagger2.di.annotation.APPScoped;

import javax.inject.Inject;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public  abstract  class APPModule {



    @Binds
    abstract Context provideContext(Application application);

    @APPScoped
    @Provides
    public static RemoteManager provideRemoteManager(){
        return new RemoteManager();
    }

}
