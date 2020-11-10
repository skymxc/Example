package com.skymxc.example.datastorage

import android.app.Application

/**
 * <p>
 *
 * </p>
 *
 * @author skymxc
 * <p>
 * date: 2020/11/10
 */
class APP() : Application() {
    companion object{
        lateinit var application: Application
    }
    override fun onCreate() {
        super.onCreate()
        application = this
    }
}