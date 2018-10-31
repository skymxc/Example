package com.skymxc.example.dagger2.ui.second.fragment.one;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;

/**
 * Created by mxc on 2018/10/30.
 * description:
 */
public class OneFragment extends DaggerFragment {

    public static OneFragment instance(){
        Bundle bundle =new Bundle();
        OneFragment fragment =new OneFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Named("one")
    @Inject
    String name;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        textView.setText(name);
        return textView;
    }
}
