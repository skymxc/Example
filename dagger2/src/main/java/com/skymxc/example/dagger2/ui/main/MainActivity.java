package com.skymxc.example.dagger2.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.skymxc.example.dagger2.R;
import com.skymxc.example.dagger2.base.BaseActivity;
import com.skymxc.example.dagger2.ui.second.SecondActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView, View.OnClickListener {


    TextView mTVData;

    @Inject
    MainPresenter mPresenter;

    @Override
    protected void findView() {
        mTVData = findViewById(R.id.tv_data);
    }

    @Override
    protected void initListener() {
        findViewById(R.id.bt_to_second).setOnClickListener(this);
        findViewById(R.id.bt_to_third).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (null==savedInstanceState){
            mPresenter.start();
        }
    }

    @Override
    protected int loadLaoyutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_to_second:
                Intent intent = new Intent(this, SecondActivity.class);

                startActivity(intent);
                break;
        }

    }

    @Override
    public void loadData(String data) {
       mTVData.setText(data);
    }

    @Override
    public MainPresenter getPresenter() {
        return mPresenter;
    }
}
