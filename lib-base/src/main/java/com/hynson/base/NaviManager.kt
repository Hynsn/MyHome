package com.hynson.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

//kotlin实现
class NaviManager private constructor() : Application.ActivityLifecycleCallbacks{
    companion object {
        val instance: NaviManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NaviManager() }
        const val TAG = "NaviManager"
    }

    override fun onActivityCreated(activity: Activity, state: Bundle?) {
        if(activity is BaseActivity){

            Log.i(TAG, "onActivityCreated: ${activity.javaClass.simpleName} ${activity.intent.getStringExtra("test")}")
            activity.naviId = -1
        }
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i(TAG, "onActivityStarted: ")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(TAG, "onActivityResumed: ")
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i(TAG, "onActivityPaused: ")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i(TAG, "onActivityStopped: ")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.i(TAG, "onActivitySaveInstanceState: ")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i(TAG, "onActivityDestroyed: ")
    }
}