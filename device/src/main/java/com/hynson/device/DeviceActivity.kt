package com.hynson.device

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.hynson.navi.annotation.NavDestination
import com.hynson.navi.NavGraphBuilder
import com.hynson.navi.NavManager
import com.hynson.navi.NaviPath

@NavDestination(pageUrl = NaviPath.DEVICE_HOME)
class DeviceActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        val navController = Navigation.findNavController(this, R.id.dev_fragment)
        /*NavGraphBuilder.get(navController).build(NaviPath.DEVICE_FRAG1)
            .navigate()*/
        NavManager.instance.navigate(NaviPath.DEVICE_FRAG1)

        Log.i(TAG,"onCreate")
    }

    override fun onResume() {
        super.onResume()

        Log.i(TAG,"onResume")

    }

    companion object {
        val TAG = DeviceActivity::class.java.simpleName
    }
}