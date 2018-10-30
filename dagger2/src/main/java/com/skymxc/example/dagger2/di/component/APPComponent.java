package com.skymxc.example.dagger2.di.component;

import android.app.Application;

import com.skymxc.example.dagger2.MyApplication;
import com.skymxc.example.dagger2.di.annotation.APPScoped;
import com.skymxc.example.dagger2.di.module.APIModule;
import com.skymxc.example.dagger2.di.module.APPModule;
import com.skymxc.example.dagger2.di.module.ActivityBuilder;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjectionModule;

/**
 * Component 是一张图表，我们构建它，它将通过它的模块提供注入的实例。
 * Component.Builder 我们可能会需要绑定一些实例在 Component 上。例如这次 我绑定了 Application 的实例（通过 @BindsInstance）。
 * AndroidInjectionModule dagger.Android 中定义的类 提供 四大组件和 Fragment 的 实例。
 * APIModule 自己定义的 API相关的实例
 * APPModule 自己定义的 全局相关的实例
 * ActivityBuilder 自己定义的 映射 Activity 的模块，所有的 ACtivity 都在这里映射
 */

@APPScoped
@Component(modules = {APIModule.class,
        APPModule.class,
        AndroidInjectionModule.class,
        ActivityBuilder.class})
public interface APPComponent {

    void inject(MyApplication app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        APPComponent build();
    }
}
