package com.skymxc.example.dagger2.di.component;

import com.skymxc.example.dagger2.MainActivity;
import com.skymxc.example.dagger2.di.annotation.ActivityScoped;
import com.skymxc.example.dagger2.di.module.MainModule;

import javax.inject.Singleton;

import dagger.Subcomponent;



@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
