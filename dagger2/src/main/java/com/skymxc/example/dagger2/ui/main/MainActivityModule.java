package com.skymxc.example.dagger2.ui.main;

import com.skymxc.example.dagger2.di.annotation.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
@Module
public abstract class MainActivityModule {

    @ActivityScoped
    @Provides
    public static MainPresenter providePresenter(MainView view,MainData mainData){
        return new MainPresenter(view,mainData);
    }

    @Binds
    abstract MainView provideMainView(MainActivity activity);

}
