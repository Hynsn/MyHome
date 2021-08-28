package com.hynson.navi

import java.lang.StringBuilder

class Destination {
    var id = 0
    lateinit var className: String
    lateinit var pageUrl: String
    var asStarter = false
    var isFragment = false

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(javaClass.simpleName)
        sb.append("(")
        sb.append("$id,")
        sb.append("$className,")
        sb.append("$pageUrl,")
        sb.append("$isFragment")
        sb.append(")")
        return sb.toString()
    }
}