package com.hynson.navi

import android.app.Activity
import android.os.Handler

data class GroupHandler(
    val name:String,
    val activity:Activity,
    var handler: Handler?
)
