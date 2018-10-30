package com.skymxc.example.dagger2.ui.second;

import android.support.v4.app.Fragment;

import com.skymxc.example.dagger2.ui.second.fragment.one.OneFragment;
import com.skymxc.example.dagger2.ui.second.fragment.one.OneModule;
import com.skymxc.example.dagger2.ui.second.fragment.two.TwoFragment;
import com.skymxc.example.dagger2.ui.second.fragment.two.TwoModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@Module()
public abstract class SecondFragmentBuilder {


    @ContributesAndroidInjector(modules = OneModule.class)
    abstract OneFragment bindOneFragment();

    @ContributesAndroidInjector(modules = TwoModule.class)
    abstract TwoFragment bindTwoFragment();
}
