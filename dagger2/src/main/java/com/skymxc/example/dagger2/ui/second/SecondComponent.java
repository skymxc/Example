package com.skymxc.example.dagger2.ui.second;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@Subcomponent(modules = {SecondModule.class,SecondFragmentBuilder.class})
public interface SecondComponent extends AndroidInjector<SecondActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SecondActivity> {

    }
}
