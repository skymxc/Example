package com.skymxc.example.dagger2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.skymxc.example.dagger2.app.MApplication;
import com.skymxc.example.dagger2.data.DBManager;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {


    @Inject
    DBManager dbManager;


    @Inject DBManager dbManager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MApplication)getApplication()).getAppComponent()
                .plus().inject(this);
        Log.e(MainActivity.class.getSimpleName(), "onCreate: "+(dbManager==dbManager1));
        Toast.makeText(this,String.valueOf(dbManager==dbManager1),Toast.LENGTH_SHORT).show();
        // TODO: 2018/9/11 全局单例

    }
}
