package com.skymxc.example.dagger2.ui.second.fragment.one;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@Module
public class OneModule {

    @Named("one")
    @Provides
    public String provideName(){
        return "It is one!";
    }
}
