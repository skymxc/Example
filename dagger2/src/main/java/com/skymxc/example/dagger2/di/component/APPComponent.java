package com.skymxc.example.dagger2.di.component;

import com.skymxc.example.dagger2.MApplication;
import com.skymxc.example.dagger2.di.annotation.APPScoped;
import com.skymxc.example.dagger2.di.module.APIModule;
import com.skymxc.example.dagger2.di.module.APPModule;
import com.skymxc.example.dagger2.di.module.ActivityBuilder;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;

@APPScoped
@Component(modules = { APIModule.class,APPModule.class,
        ActivityBuilder.class,
        AndroidSupportInjectionModule.class})
public interface APPComponent  extends AndroidInjector<MApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        APPComponent.Builder application(MApplication application);

        APPComponent build();
    }


}
