package com.skymxc.example.dagger2.ui.second;

import com.skymxc.example.dagger2.data.ApiService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@Module
public class SecondModule {

    @Provides
    public SecondView provideSecondView(SecondActivity activity){
        return activity;
    }

    @Provides
    public SecondPresenter provideSecondPresenter(SecondView view, ApiService apiService){
        return new SecondPresenter(apiService,view);
    }
}
