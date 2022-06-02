package com.hynson.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseItemView<T>(val context: Context) {
    // 实体
    abstract val bean:T
    // 视图
    abstract val layout:Int
    // 供适配器绑定
    abstract fun bindView()

    var convertView:View? = null

    fun getViewHolder() : ViewHolder{
        val view = LayoutInflater.from(context).inflate(layout, null)
        convertView = view
        return MyViewHolder(context,view)
    }
}