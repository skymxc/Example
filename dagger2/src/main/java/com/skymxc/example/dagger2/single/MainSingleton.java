package com.skymxc.example.dagger2.single;


import com.skymxc.example.dagger2.di.annotation.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
public class MainSingleton {


    @Inject
    public MainSingleton(){}
}
