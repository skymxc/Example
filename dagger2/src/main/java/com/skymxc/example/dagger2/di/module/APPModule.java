package com.skymxc.example.dagger2.di.module;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.skymxc.example.dagger2.di.annotation.APPScoped;
import com.skymxc.example.dagger2.ui.main.MainComponent;
import com.skymxc.example.dagger2.ui.second.SecondComponent;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.android.DispatchingAndroidInjector;

/**
 * subcomponents 注册了 main 和 second
 */
@Module(subcomponents = {MainComponent.class,SecondComponent.class})
public class APPModule {



    @Provides
    @APPScoped
    Context provideContext(Application application){
        return application;
    }


}
