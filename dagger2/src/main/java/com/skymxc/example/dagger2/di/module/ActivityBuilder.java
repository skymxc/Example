package com.skymxc.example.dagger2.di.module;

import com.skymxc.example.dagger2.di.annotation.ActivityScoped;
import com.skymxc.example.dagger2.ui.main.MainActivity;
import com.skymxc.example.dagger2.ui.main.MainActivityModule;
import com.skymxc.example.dagger2.ui.second.SecondActivity;
import com.skymxc.example.dagger2.ui.second.SecondActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
@Module
public abstract class ActivityBuilder {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();


    @ContributesAndroidInjector(modules = SecondActivityModule.class)
    abstract SecondActivity bindSecondActivity();
}
