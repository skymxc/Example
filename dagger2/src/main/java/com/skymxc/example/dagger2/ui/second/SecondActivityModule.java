package com.skymxc.example.dagger2.ui.second;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mxc on 2018/11/2.
 * description:
 */
@Module
public class SecondActivityModule {

    @Provides
    public static String provideData(){
        return "this is second data.";
    }
}
