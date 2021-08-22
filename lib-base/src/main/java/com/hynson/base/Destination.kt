package com.hynson.base

import java.lang.StringBuilder

class Destination {
    var id = 0
    var className: String? = null
    var pageUrl: String? = null
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