package com.hynson.navi

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

class Builder(private val toWhere: String,private val bundle: Bundle){
    fun withString(key:String,value:String): Builder {
        bundle.putString(key, value)
        return this
    }

    fun withInt(key:String,value:Int): Builder {
        bundle.putInt(key, value)
        return this
    }

    fun withLong(key:String,value:Long): Builder {
        bundle.putLong(key, value)
        return this
    }

    fun withDouble(key:String,value:Double): Builder {
        bundle.putDouble(key, value)
        return this
    }

    fun withBoolean(key:String,value:Boolean): Builder {
        bundle.putBoolean(key, value)
        return this
    }

    fun withByte(key:String,value:Byte): Builder {
        bundle.putByte(key, value)
        return this
    }

    fun withSerializable(key:String,value: Serializable): Builder {
        bundle.putSerializable(key, value)
        return this
    }

    fun withParcelable(key:String,value: Parcelable): Builder {
        bundle.putParcelable(key, value)
        return this
    }

    private fun getItemId(pageUrl: String): Int {
        val destination = NavConfig.getDestinationMap()[pageUrl]
        return destination?.id ?: R.id.empaty_activity
    }

    fun navigate(){
        NavGraphBuilder.sNavController?.navigate(getItemId(toWhere),bundle)
    }
}
