package com.hynson.setting

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.hynson.navi.NaviPath
import com.hynson.navi.annotation.NavDestination

//@NavDestination(pageUrl = NaviPath.SETTING_FRAG2)
class SettingsActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.set_fragment_blank2)

/*        findViewById<Button>(R.id.btn_back).setOnClickListener {

        }
        findViewById<Button>(R.id.btn_next).setOnClickListener {

        }*/
    }
}