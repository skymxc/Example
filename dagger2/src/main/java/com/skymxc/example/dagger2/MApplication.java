package com.skymxc.example.dagger2;

import android.app.Application;

import com.skymxc.example.dagger2.di.component.APPComponent;
import com.skymxc.example.dagger2.di.component.DaggerAPPComponent;
import com.skymxc.example.dagger2.di.module.APPModule;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class MApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

       return DaggerAPPComponent.builder().application(this).build();
    }
}
