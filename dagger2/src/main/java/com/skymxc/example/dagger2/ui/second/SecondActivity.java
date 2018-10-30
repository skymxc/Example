package com.skymxc.example.dagger2.ui.second;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.skymxc.example.dagger2.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class SecondActivity extends AppCompatActivity implements SecondView{

     public static void start(Context context){
             Intent intent = new Intent(context,SecondActivity.class);
             context.startActivity(intent);
     }

    @Inject SecondPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        
        presenter.loadData();
    }

    @Override
    public void loaded() {
        Log.e(SecondActivity.class.getSimpleName()+"","loaded->");
    }
}
