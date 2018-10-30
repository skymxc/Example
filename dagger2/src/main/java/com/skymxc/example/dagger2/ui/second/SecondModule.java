package com.skymxc.example.dagger2.ui.second;

import android.support.v4.app.Fragment;

import com.skymxc.example.dagger2.data.ApiService;
import com.skymxc.example.dagger2.ui.second.fragment.one.OneFragment;
import com.skymxc.example.dagger2.ui.second.fragment.two.TwoFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@Module(/*subcomponents = {OneComponent.class,TwoComponent.class}*/)
public  class SecondModule {

    @Provides
    public SecondView provideSecondView(SecondActivity activity) {
        return activity;
    }

    @Provides
    public SecondPresenter provideSecondPresenter(SecondView view, ApiService apiService) {
        return new SecondPresenter(apiService, view);
    }

    @Provides
    public OneFragment provideOneFragment(){
        return OneFragment.instance();
    }

    @Provides
    public TwoFragment provideTwoFragment(){
        return TwoFragment.instance();
    }


}
