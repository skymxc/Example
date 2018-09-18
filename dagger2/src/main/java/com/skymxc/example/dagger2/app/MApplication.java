package com.skymxc.example.dagger2.app;

import android.app.Application;

import com.skymxc.example.dagger2.di.component.APPComponent;
import com.skymxc.example.dagger2.di.component.DaggerAPPComponent;
import com.skymxc.example.dagger2.di.module.APPModule;

public class MApplication extends Application {

    private APPComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAPPComponent.builder()
                .aPPModule(new APPModule(this))
                .build();
    }

    public APPComponent getAppComponent() {
        return appComponent;
    }
}
