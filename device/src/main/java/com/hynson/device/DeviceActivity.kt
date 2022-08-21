package com.hynson.device

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.hynson.navi.NavManager
import com.hynson.navi.NaviPath
import com.hynson.navi.annotation.NavDestination

@NavDestination(pageUrl = NaviPath.DEVICE_HOME)
class DeviceActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        NavManager.instance.navigate(this, NaviPath.DEVICE_FRAG1)

        Log.i(TAG, "onCreate")
    }

    override fun onResume() {
        super.onResume()

        Log.i(TAG, "onResume")

    }

    companion object {
        val TAG = DeviceActivity::class.java.simpleName
    }
}