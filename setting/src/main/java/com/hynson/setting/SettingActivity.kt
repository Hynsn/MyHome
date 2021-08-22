package com.hynson.setting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.hynson.base.BaseActivity

class SettingActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_setting
    override val graph: Int
        get() = R.navigation.setting_navi_graph
    override val hostId: Int
        get() = R.id.nav_set_fragment
    override var oldId: Int = R.id.blankFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "SettingActivity:${savedInstanceState?.get("key")} ")
//        setContentView(R.layout.activity_setting)

        findViewById<FragmentContainerView>(R.id.nav_set_fragment)
        /*val navController = Navigation.findNavController(this, R.id.nav_set_fragment)
        navController.setGraph(R.navigation.setting_navi_graph)
        navController.navigate(R.id.blankFragment)*/
        //NavigationUI.setupActionBarWithNavController(this,navController)
        val str = intent.getStringExtra("test")
        Log.i(TAG, "SettingActivity: $str")
    }

    companion object{
        const val TAG = "SettingActivity"
    }
}