package com.skymxc.example.dagger2.ui.main;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by mxc on 2018/10/30.
 * description: 连接 module 的桥梁
 * 这里有一个重大的改变 --不用再 定义 inject 和 build 方法了；
 * 在 父类 AndroidInjector 中已经定义好了；
 * AndroidInjector 是 dagger.Android 框架中已经预定义好的类。
 */
@Subcomponent(modules = MainModule.class)
public interface MainComponent  extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{

    }
}
