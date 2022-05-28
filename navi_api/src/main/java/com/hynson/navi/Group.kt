package com.hynson.navi

interface Group<T : ADestination> {
    fun getGroupMap(): Map<String, Class<T>>
}