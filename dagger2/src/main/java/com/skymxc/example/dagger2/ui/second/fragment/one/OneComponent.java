package com.skymxc.example.dagger2.ui.second.fragment.one;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@Subcomponent(modules = OneModule.class)
public interface OneComponent extends AndroidInjector<OneFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<OneFragment> {

    }
}
