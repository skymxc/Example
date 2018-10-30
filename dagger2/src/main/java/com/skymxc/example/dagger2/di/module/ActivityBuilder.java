package com.skymxc.example.dagger2.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.skymxc.example.dagger2.ui.main.MainActivity;
import com.skymxc.example.dagger2.ui.main.MainModule;
import com.skymxc.example.dagger2.ui.second.SecondActivity;
import com.skymxc.example.dagger2.ui.second.SecondFragmentBuilder;
import com.skymxc.example.dagger2.ui.second.SecondModule;
import com.skymxc.example.dagger2.ui.second.fragment.one.OneFragment;
import com.skymxc.example.dagger2.ui.second.fragment.two.TwoFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by mxc on 2018/10/30.
 * description: 映射到自己的 activity
 *
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {SecondModule.class,SecondFragmentBuilder.class})
    abstract SecondActivity bindSecondActivity();

}
