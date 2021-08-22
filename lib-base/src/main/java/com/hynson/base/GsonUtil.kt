package com.hynson.base

import com.google.gson.GsonBuilder
import java.lang.reflect.Type

class GsonUtil {
    companion object{
        private val GSON = GsonBuilder().create()
        private val GSON_NO_NULLS = GsonBuilder().serializeNulls().create()

        fun <T>fromJson(json:String, classOfT:Class<T> ):T{
            return GSON.fromJson<T>(json,classOfT)
        }
        fun <T> fromJson(json: String?, type: Type?): T {
            return GSON.fromJson(json, type)
        }
        fun toJson(`object`: Any?, includeNulls: Boolean): String? {
            return if (includeNulls)GSON.toJson(`object`) else GSON_NO_NULLS.toJson(
                `object`
            )
        }
    }
}