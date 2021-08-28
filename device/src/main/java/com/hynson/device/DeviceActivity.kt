package com.hynson.device

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.hynson.navi.ActivityDestination
import com.hynson.navi.NavGraphBuilder

@ActivityDestination(pageUrl = "/device", asStarter = true)
class DeviceActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        val navController = Navigation.findNavController(this, R.id.dev_fragment)
        NavGraphBuilder.get(navController).build("/device/frag1")
            .navigate()
    }
}