package com.skymxc.example.dagger2.di.component;

import com.skymxc.example.dagger2.SecondActivity;
import com.skymxc.example.dagger2.di.annotation.ActivityScoped;
import com.skymxc.example.dagger2.di.module.SecondModule;

import dagger.Subcomponent;

@ActivityScoped
@Subcomponent(modules = SecondModule.class)
public interface SecondComponent {
    void inject(SecondActivity activity);
}
