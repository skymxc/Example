package com.skymxc.example.dagger2.di.component;

import com.skymxc.example.dagger2.MainActivity;

import dagger.Subcomponent;

@Subcomponent
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
