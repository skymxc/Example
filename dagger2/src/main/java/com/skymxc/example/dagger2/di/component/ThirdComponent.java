package com.skymxc.example.dagger2.di.component;

import com.skymxc.example.dagger2.ThirdActivity;
import com.skymxc.example.dagger2.data.DBManager;
import com.skymxc.example.dagger2.di.annotation.APPScoped;
import com.skymxc.example.dagger2.di.module.ThirdModule;

import dagger.Component;

@Component(modules = ThirdModule.class)
public interface ThirdComponent {

    void inject(ThirdActivity activity);


}
