package com.skymxc.example.dagger2.di.module;

import com.skymxc.example.dagger2.di.annotation.Age;
import com.skymxc.example.dagger2.di.annotation.Height;

import javax.inject.Named;
import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class ThirdModule {

    @Named("name")
    @Provides
    public String provideName(){
        return "skymxc";
    }

    @Age
    @Provides
    public int provideAge(){
        return 24;
    }

    @Provides
    @Height
    public int provideHeight(){
        return 175;
    }

    @Provides
    @Named("address")
    public String provideAddress(){
        return "北京";
    }

    @Provides
    public OkHttpClient provideOkHttpClient(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        return client;
    }
}
