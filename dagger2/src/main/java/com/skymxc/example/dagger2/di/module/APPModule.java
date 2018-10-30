package com.skymxc.example.dagger2.di.module;


import android.app.Application;
import android.content.Context;

import com.skymxc.example.dagger2.di.annotation.APPScoped;
import com.skymxc.example.dagger2.ui.main.MainComponent;
import com.skymxc.example.dagger2.ui.second.SecondComponent;
import com.skymxc.example.dagger2.ui.second.fragment.one.OneComponent;
import com.skymxc.example.dagger2.ui.second.fragment.two.TwoComponent;
import com.skymxc.example.dagger2.ui.second.fragment.two.TwoFragment;

import dagger.Binds;
import dagger.Module;

/**
 * subcomponents 注册了 main 和 second
 */
@Module(subcomponents = {MainComponent.class, SecondComponent.class})
public abstract class APPModule {


    @Binds //@Binds 注解委托代理的 抽象方法；必须是抽象方法；必须有一个参数，参数必须能转换为 返回类型 不然无法生成代码就会报错。
    @APPScoped
    abstract Context provideContext(Application application);


}
