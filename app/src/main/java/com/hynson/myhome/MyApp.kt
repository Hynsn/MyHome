package com.hynson.myhome

import android.app.Application
import android.util.Log

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.i("TAG", "onCreate: 我是java目录下的app")
    }
}