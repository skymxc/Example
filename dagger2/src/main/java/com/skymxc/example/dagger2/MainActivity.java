package com.skymxc.example.dagger2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.skymxc.example.dagger2.app.MApplication;
import com.skymxc.example.dagger2.data.DBManager;
import com.skymxc.example.dagger2.di.module.MainModule;
import com.skymxc.example.dagger2.single.MainSingleton;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Inject
    DBManager dbManager;


    @Inject DBManager dbManager1;

    @Inject
    MainSingleton mainSingleton;

    @Inject
    MainSingleton mainSingleton1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_to_second).setOnClickListener(this);
        findViewById(R.id.bt_to_third).setOnClickListener(this);
        ((MApplication)getApplication()).getAppComponent()
                .plus(new MainModule())
                .inject(this);

        Log.e(MainActivity.class.getSimpleName(), "onCreate: appdb-->"+((MApplication)getApplication()).getAppComponent().getDBManager().hashCode());
        //查看 是否和 main的一致，是否是全局范围内单例
        if (dbManager==dbManager1) {
            Log.e(MainActivity.class.getSimpleName(), "onCreate: dbmanager-sintleton->"+dbManager.hashCode());
        }else{
            Log.e(MainActivity.class.getSimpleName(), "onCreate: dbmanager:"+dbManager.hashCode());
            Log.e(MainActivity.class.getSimpleName(), "onCreate: dbmanager1:"+dbManager1.hashCode());
        }
        //主要看 这个 和 main的是否一致，是否是activity范围内单例。
        if (mainSingleton==mainSingleton1){
            Log.e(MainActivity.class.getSimpleName(), "onCreate: main-singleton->"+mainSingleton.hashCode());
        }else{
            Log.e(MainActivity.class.getSimpleName(), "onCreate: main:"+mainSingleton.hashCode());
            Log.e(MainActivity.class.getSimpleName(), "onCreate: main1:"+mainSingleton1.hashCode());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_to_second:
                Intent intent = new Intent(this,SecondActivity.class);

                startActivity(intent);
                break;
            case R.id.bt_to_third:
                intent = new Intent(this,ThirdActivity.class);
                startActivity(intent);
                break;
        }

    }
}
