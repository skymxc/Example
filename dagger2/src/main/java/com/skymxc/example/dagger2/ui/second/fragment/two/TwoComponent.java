package com.skymxc.example.dagger2.ui.second.fragment.two;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@Subcomponent(modules = TwoModule.class)
public interface TwoComponent extends AndroidInjector<TwoFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<TwoFragment> {

    }
}
