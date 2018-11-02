package com.skymxc.example.dagger2.ui.second;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.skymxc.example.dagger2.MApplication;
import com.skymxc.example.dagger2.R;
import com.skymxc.example.dagger2.base.BaseActivity;
import com.skymxc.example.dagger2.mvp.IPresenter;

import javax.inject.Inject;

public class SecondActivity extends BaseActivity {


    TextView mTVData;

    @Inject
    String data;

    @Override
    protected int loadLaoyutId() {
        return R.layout.activity_second;
    }


    @Override
    protected void findView() {
        super.findView();
        mTVData = findViewById(R.id.tv_data);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if (null==savedInstanceState){
            mTVData.setText(data);
        }
    }
}
