package com.skymxc.example.dagger2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.skymxc.example.dagger2.data.DBManager;
import com.skymxc.example.dagger2.di.annotation.Age;
import com.skymxc.example.dagger2.di.annotation.Height;
import com.skymxc.example.dagger2.di.component.DaggerThirdComponent;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.OkHttpClient;

public class ThirdActivity extends AppCompatActivity {


    @Named("name")
    @Inject String name;

    @Age
    @Inject int age;

    @Height
    @Inject int height;

    @Inject
    OkHttpClient client;

    @Named("address")
    @Inject String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        DaggerThirdComponent.create().inject(this);
        StringBuffer buffer = new StringBuffer();
        buffer.append("name-"+name);
        buffer.append("\n");
        buffer.append("age - "+age);
        buffer.append("\n");
        buffer.append("client - "+client);
        buffer.append("\n");
        buffer.append("address - "+address);
        buffer.append("\n");
        buffer.append("height - "+height);

        Log.e(ThirdActivity.class.getSimpleName(), "onCreate: \n"+buffer.toString());

    }
}
