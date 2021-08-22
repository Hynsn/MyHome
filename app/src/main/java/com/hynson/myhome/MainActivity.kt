package com.hynson.myhome

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.hynson.navi.ActivityDestination
import com.hynson.navi.FragmentDestination

@ActivityDestination(pageUrl = "ABCDEa123abc", asStarter = true)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 相当于初始化路由
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,navController)

    }
    companion object {
        const val TAG = "MainActivity"
    }
}
