package com.skymxc.example.dagger2.ui.second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.skymxc.example.dagger2.R;
import com.skymxc.example.dagger2.ui.second.fragment.one.OneFragment;
import com.skymxc.example.dagger2.ui.second.fragment.two.TwoFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;

public class SecondActivity extends DaggerAppCompatActivity implements SecondView,
        RadioGroup.OnCheckedChangeListener,
        HasSupportFragmentInjector {

    public static void start(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }

    @Inject
    SecondPresenter presenter;

    @Inject
    OneFragment oneFragment;

    @Inject
    TwoFragment twoFragment;

    RadioGroup mRGMenu;
    FrameLayout mFLContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mRGMenu = findViewById(R.id.rg_menu);
        mFLContainer = findViewById(R.id.fl_container);

        mRGMenu.setOnCheckedChangeListener(this);
        presenter.loadData();

        if (null == savedInstanceState) {
           mRGMenu.check(R.id.rb_one);
        }
    }

    @Override
    public void loaded() {
        Log.e(SecondActivity.class.getSimpleName() + "", "loaded->");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int checkedRadioButtonId = group.getCheckedRadioButtonId();

        Log.e(SecondActivity.class.getSimpleName() + "", "onCheckedChanged->" + String.valueOf(checkedId == checkedRadioButtonId));
        switch (checkedRadioButtonId) {
            case R.id.rb_one:
                applyFragment(oneFragment);
                break;
            case R.id.rb_two:
                applyFragment(twoFragment);
                break;
        }
    }

    private void applyFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit();
    }
}
