package com.skymxc.example.dagger2;

import com.skymxc.example.dagger2.di.component.DaggerAPPComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by mxc on 2018/10/30.
 * description:
 * <p>
 * 参考 https://medium.com/@iammert/new-android-injector-with-dagger-2-part-1-8baa60152abe
 */
public class MyApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAPPComponent.builder().build();
    }


}
