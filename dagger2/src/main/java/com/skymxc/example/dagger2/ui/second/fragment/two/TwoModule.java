package com.skymxc.example.dagger2.ui.second.fragment.two;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@Module
public class TwoModule {

    @Named("two")
    @Provides
    public String provideName(){
        return "It is two !";
    }
}
