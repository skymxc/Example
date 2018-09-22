package com.skymxc.example.dagger2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.skymxc.example.dagger2.app.MApplication;
import com.skymxc.example.dagger2.data.DBManager;
import com.skymxc.example.dagger2.di.component.DaggerAPPComponent;
import com.skymxc.example.dagger2.di.module.APPModule;
import com.skymxc.example.dagger2.di.module.MainModule;
import com.skymxc.example.dagger2.single.MainSingleton;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Inject
    DBManager dbManager;


    @Inject DBManager dbManager1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_to_second).setOnClickListener(this);
        findViewById(R.id.bt_to_third).setOnClickListener(this);


       /* ((MApplication)getApplication()).getAppComponent()
                .plus(new MainModule())
                .inject(this);*/

       // 获取新实例

        DaggerAPPComponent.builder().aPPModule(new APPModule(getApplication())).build().plus(new MainModule()).inject(this);

         //查看 是否和 main的一致，是否是全局范围内单例
        if (dbManager==dbManager1) {
            Log.e(MainActivity.class.getSimpleName(), "onCreate: dbmanager-sintleton->"+dbManager.hashCode());
        }else{
            Log.e(MainActivity.class.getSimpleName(), "onCreate: dbmanager:"+dbManager.hashCode());
            Log.e(MainActivity.class.getSimpleName(), "onCreate: dbmanager1:"+dbManager1.hashCode());
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
