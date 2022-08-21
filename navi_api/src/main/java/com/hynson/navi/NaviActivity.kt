package com.hynson.navi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController

class NaviActivity : FragmentActivity() {

    private var selfGroup: String? = null
    var handler: Handler = Handler(Looper.getMainLooper()) {
        with(it) {
            findNavController(R.id.navi_main_fragment).navigate(arg1)
        }
        Log.i(TAG, "$it")
        return@Handler true
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        ++createCounter
        Log.i(TAG, "onCreate1")
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate2")
        setContentView(R.layout.navi_main)

        if (intent.extras != null) {
            intent.extras?.run {
                selfGroup = getString(NAVI_GROUP)
                val target = getString(NAVI_TARGET)
                navigate(target, selfGroup)
                Log.i(TAG, "target$target")
            }
        } else {
            Log.i(TAG, "target null")
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        selfGroup?.let {
//            handler = null
            NavManager.instance.popGroup(it)
        }
    }

    fun navigate(id: String?, group: String?) {
        if (id.isNullOrEmpty()) return
        if (group.isNullOrEmpty()) return

        val navController = findNavController(R.id.navi_main_fragment)
        Log.i(
            TAG,
            "navigate2: ${System.identityHashCode(this)},${System.identityHashCode(navController)}"
        )
        NavManager.instance.registHandler(group,this, handler)
            .handleGraph(id, this, navController)
    }

    companion object {

        val TAG = NaviActivity::class.java.simpleName

        const val NAVI_TARGET = "NAVI_TARGET"
        const val NAVI_BUNDLE = "NAVI_BUNDLE"
        const val NAVI_GROUP = "NAVI_GROUP"

        var launchCounter = 0

        var createCounter = 0

        fun launch(context: Context, group: String, target: String, bundle: Bundle? = null) {
            launchCounter++
            val intent: Intent = Intent(context, NaviActivity::class.java)
                .apply {
                    putExtra(NAVI_GROUP, group)
                    putExtra(NAVI_TARGET, target)
                    putExtra(NAVI_BUNDLE, bundle)
                }
            context.startActivity(intent)
            Log.i(TAG, "launchCounter: $launchCounter")
        }
    }
}