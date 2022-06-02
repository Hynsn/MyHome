package com.hynson.setting

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SettingAdapter(val list:MutableList<Item>,val context: Context,val click: View.OnClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, layout: Int): RecyclerView.ViewHolder {
        //val view = LayoutInflater.from(context).inflate(layout,parent,false)
        val itemView = ItemView1(context)
        val viewHolder = itemView.getViewHolder()
        itemView.bindView()
        return viewHolder
//        return MyViewHolder.createViewHolder(context, parent, layout)//需要通过建造者模式，或单例管理起来
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //holder.bindView()
        holder.itemView.rootView.setOnClickListener(click)
//        holder.rlItem?.apply {
//            setOnClickListener(click)
//        }
        // 在bindView之前将数组内的不同类型的数据拷贝出来，然后绑定试图
        // 需要判断属于哪种数据结构

//        list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return ItemManager.list.get(list[position].eventId)
    }

    private lateinit var event : onEvent

    interface onEvent {
        fun onItemListener()
    }
}