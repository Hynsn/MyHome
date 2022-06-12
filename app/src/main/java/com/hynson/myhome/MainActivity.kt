package com.hynson.myhome

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.hynson.navi.NavManager
import com.hynson.navi.NaviActivity
import com.hynson.navi.NaviPath


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG,"onCreate1")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG,"onCreate2")

        // 相当于初始化路由
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment1)
        /*NavGraphBuilder.get(navController).build("/device")
            .navigate()*/
        // 跳转fragment必须要指定controller，activity不必须。
//        NavManager.instance.navigate(NaviPath.APP_LAUNCH,this)
        NavManager.instance.navigate(NaviPath.APP_LAUNCH)
//        NavManager.instance.navigate(NaviPath.DEVICE_HOME)

//        NavManager.instance.navigation("/setting/frag1",this)


    }
    companion object {
        const val TAG = "MainActivity"
    }
}
