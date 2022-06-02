package com.hynson.myhome

import android.app.Application
import android.util.Log
import com.hynson.base.NaviManager
import com.hynson.navi.NavManager

//import com.hynson.base.NaviManager

class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()

        Log.i("TAG", "onCreate: 我是java目录下的app")
        println("com.hynson.myhome.TheClass" + 2)
        NavManager.instance.register(this)
    }
}