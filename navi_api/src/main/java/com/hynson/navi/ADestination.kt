package com.hynson.navi

import com.hynson.navi.bean.Destination

interface ADestination {
    fun getDestinationMap(): Map<String, Destination>
}