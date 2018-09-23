package com.skymxc.example.dagger2.di.component;

import com.skymxc.example.dagger2.data.DBManager;
import com.skymxc.example.dagger2.di.annotation.APPScoped;
import com.skymxc.example.dagger2.di.module.APIModule;
import com.skymxc.example.dagger2.di.module.APPModule;
import com.skymxc.example.dagger2.di.module.MainModule;
import com.skymxc.example.dagger2.di.module.SecondModule;

import javax.inject.Singleton;

import dagger.Component;

@APPScoped
@Component(modules = { APIModule.class,APPModule.class})
public interface APPComponent {

    MainComponent plus(MainModule module);

    SecondComponent plus(SecondModule module);

    DBManager getDBManager();
}
