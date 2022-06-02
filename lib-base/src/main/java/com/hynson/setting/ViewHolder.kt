package com.hynson.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.hynson.base.R

class ViewHolder(val type:Int, override val bean: Item,itemView: View) : BaseViewHolder<Item>(itemView) {
    var nameTV = itemView.findViewById<TextView>(R.id.tv_name)
    override fun bindView() :ViewHolder{
        ItemManager.listViewHolder.get(type)
        nameTV.text = bean.name
//        nameTV.text = item.name

        return this
    }

}