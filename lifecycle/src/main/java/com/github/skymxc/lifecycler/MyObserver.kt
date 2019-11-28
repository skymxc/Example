package com.github.skymxc.lifecycler

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * <p>
 *
 * </p>
 *
 * @author 孟祥超
 *
 * date: 2019/11/28  13:42
 */
class MyObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {

        Log.e(this.javaClass.simpleName, "--> onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.e(this.javaClass.simpleName, "--> onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.e(this.javaClass.simpleName, "---> onResume()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.e(this.javaClass.simpleName, "---> onPause()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.e(this.javaClass.simpleName, "---> onStop()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.e(this.javaClass.simpleName, "---> onDestroy()")

    }

    /*@OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny() {
//        Log.e(TAG, "---> onAny()")
        //任何事情都会接收到
    }*/



}