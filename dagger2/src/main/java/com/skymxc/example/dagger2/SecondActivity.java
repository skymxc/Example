package com.skymxc.example.dagger2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.skymxc.example.dagger2.app.MApplication;
import com.skymxc.example.dagger2.data.DBManager;
import com.skymxc.example.dagger2.di.module.SecondModule;
import com.skymxc.example.dagger2.single.MainSingleton;

import javax.inject.Inject;

public class SecondActivity extends AppCompatActivity {

    @Inject
    DBManager dbManager;
    
    @Inject
    DBManager dbManager1;
    
    @Inject
    MainSingleton mainSingleton;
    @Inject MainSingleton mainSingleton1;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        ((MApplication)getApplication()).getAppComponent()
                .plus(new SecondModule())
                .inject(this);

        if (dbManager==dbManager1) {
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: dbmanager-singleton->"+dbManager.hashCode());
        }else{
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: dbmanager:"+dbManager.hashCode());
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: dbmanager1:"+dbManager1.hashCode());
        }

        if (mainSingleton==mainSingleton1){
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: main-singleton>"+mainSingleton.hashCode());
        }else{
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: main:"+mainSingleton.hashCode());
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: main1:"+mainSingleton1.hashCode());
        }


    }
}
