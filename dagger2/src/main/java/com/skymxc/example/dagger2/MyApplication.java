package com.skymxc.example.dagger2;

import android.app.Activity;
import android.app.Application;

import com.skymxc.example.dagger2.di.component.APPComponent;
import com.skymxc.example.dagger2.di.component.DaggerAPPComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by mxc on 2018/10/30.
 * description:
 *
 * 参考 https://medium.com/@iammert/new-android-injector-with-dagger-2-part-1-8baa60152abe
 */
public class MyApplication extends Application implements HasActivityInjector {

    // 不要使用 AndroidInjector<Activity>；
    @Inject DispatchingAndroidInjector<Activity> androidInjector;
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAPPComponent.builder().build().inject(this);

        DaggerApplication
    }


    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return androidInjector;
    }
}
