package com.hynson.myhome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.hynson.navi.NavGraphBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 相当于初始化路由
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavGraphBuilder.get(navController).build("/device")
            .navigate()
    }
    companion object {
        const val TAG = "MainActivity"
    }
}
