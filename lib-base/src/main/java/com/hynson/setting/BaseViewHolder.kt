package com.hynson.setting

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView){
    abstract val bean:T
    abstract fun bindView():BaseViewHolder<T>
}