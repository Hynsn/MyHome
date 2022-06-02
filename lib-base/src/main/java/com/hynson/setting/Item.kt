package com.hynson.setting

open class Item(open val name:String, open val layout:Int, open val eventId:Int){
    init {
        ItemManager.list.put(eventId,layout)
        ItemManager.listViewHolder.put(layout,ViewHolder::class.simpleName)
    }
}

class Item1(override val name: String, override val layout: Int, override val eventId: Int) : Item(name, layout, eventId){

}
