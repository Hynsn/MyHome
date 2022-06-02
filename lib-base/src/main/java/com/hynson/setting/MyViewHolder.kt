package com.hynson.setting

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mViews: SparseArray<View> = SparseArray()
    private val convertView: View = itemView

    private fun <T : View> getView(viewId: Int): T {
        var view = mViews[viewId]
        if (view == null) {
            view = convertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    fun setText(viewId: Int, text: String): MyViewHolder {
        val tv: TextView = getView(viewId);
        tv.text = text;
        return this;
    }

    companion object{
        fun createViewHolder(context: Context, itemView: View): MyViewHolder {
            return MyViewHolder(context, itemView)
        }

        fun createViewHolder(context: Context, parent: ViewGroup, layoutId: Int): MyViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return MyViewHolder(context, itemView)
        }
    }
}