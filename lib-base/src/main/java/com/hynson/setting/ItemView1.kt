package com.hynson.setting

import android.content.Context
import android.widget.TextView
import com.hynson.base.R

class ItemView1(context: Context) : BaseItemView<Item>(context){
    override val bean: Item
        get() = Item("测试1", 12, 1)
    override val layout: Int
        get() = R.layout.setting_item_type2

    override fun bindView() {
        convertView?.apply {
            findViewById<TextView>(R.id.tv_name).text = bean.name
        }
    }
}