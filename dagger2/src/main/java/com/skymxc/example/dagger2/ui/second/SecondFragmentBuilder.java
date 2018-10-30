package com.skymxc.example.dagger2.ui.second;

import android.support.v4.app.Fragment;

import com.skymxc.example.dagger2.ui.second.fragment.one.OneComponent;
import com.skymxc.example.dagger2.ui.second.fragment.one.OneFragment;
import com.skymxc.example.dagger2.ui.second.fragment.two.TwoComponent;
import com.skymxc.example.dagger2.ui.second.fragment.two.TwoFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
@Module(subcomponents = {OneComponent.class,TwoComponent.class})
public abstract class SecondFragmentBuilder {
    @Binds
    @IntoMap
    @FragmentKey(OneFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindOneFragment(OneComponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(TwoFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindTwoFragment(TwoComponent.Builder builder);
}
