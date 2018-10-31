package com.skymxc.example.dagger2.ui.main;

import com.skymxc.example.dagger2.data.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * 提供 MainActivity 需要的 依赖
 *
 *
 * ，所以这里就能使用了
 */
@Module
public class MainModule {

    @Provides
    public MainView provideMainView(MainActivity activity){
        return activity;
    }

    @Provides
    public MainPresenter provideMainPresenter(MainView view, ApiService apiService){
        return new MainPresenter(view,apiService);
    }

}
