package com.skymxc.example.dagger2.di.component;

import com.skymxc.example.dagger2.di.annotation.APPScoped;
import com.skymxc.example.dagger2.di.module.APIModule;
import com.skymxc.example.dagger2.di.module.APPModule;

import javax.inject.Singleton;

import dagger.Component;

@APPScoped
@Component(modules = {APPModule.class, APIModule.class})
public interface APPComponent {

    MainComponent plus();

}
