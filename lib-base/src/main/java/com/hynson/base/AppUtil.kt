package com.hynson.base

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper

/**
 * author：chs
 * date：2020/7/3
 * des：
 */
class AppUtil {

    companion object{
        private var sApplication : Application? = null
        private val UTIL_HANDLER = Handler(Looper.getMainLooper())

        fun init(app:Application){
            sApplication = app
            if(sApplication == null){
                sApplication = getApplicationByReflect()
            }
        }
        @JvmStatic
        fun getApp() : Application{
            if(sApplication != null){
                return sApplication!!
            }
            val app = getApplicationByReflect()
            sApplication = app
            return sApplication!!
        }

        @SuppressLint("PrivateApi")
        private fun getApplicationByReflect(): Application {
            return try {
                val app =
                    Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null) as Application
                app
            }catch (e:Exception){
                e.printStackTrace()
                throw NullPointerException("you should init first")
            }
        }
    }
}