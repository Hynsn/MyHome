package com.hynson.setting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

class SettingActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val str = intent.getStringExtra("test")
        Log.i(TAG, "onCreate: $str")
    }

    companion object{
        val TAG = this.javaClass.simpleName
    }
}