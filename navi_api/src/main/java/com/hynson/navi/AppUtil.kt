package com.hynson.navi

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log

/**
 * author：chs
 * date：2020/7/3
 * des：
 */
class AppUtil {

    companion object {
        private var sApplication: Application? = null
        private val UTIL_HANDLER = Handler(Looper.getMainLooper())

        fun init(app: Application) {
            sApplication = app
            if (sApplication == null) {
                sApplication = getApplicationByReflect()
            }
        }

        @JvmStatic
        fun getApp(): Application {
            if (sApplication != null) {
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
            } catch (e: Exception) {
                e.printStackTrace()
                throw NullPointerException("you should init first")
            }
        }

        fun reflex(obj: Any) {
            val aClass = obj.javaClass
//    aClass.fields 无法获取 kotlin 创建的成员
            val declaredFields = aClass.declaredFields
            val methods = aClass.methods
            for (mt in methods) {
                val mn = mt.name
                val dv = mt.defaultValue
                val returnType = mt.returnType.name
                System.err.println("方法名称：$mn    默认值：$dv    返回类型：$returnType")
            }
            for (field in declaredFields) {
                try {
                    field.isAccessible = true // 如果不设置不能获对应的值
                    val fn = field.name
                    Log.e(javaClass.simpleName, "属性名称：$fn  field.get(obj)= ${field.get(obj)}")
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        }
    }
}