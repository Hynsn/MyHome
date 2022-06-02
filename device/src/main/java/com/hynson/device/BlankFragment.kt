package com.hynson.device

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.hynson.navi.annotation.NavDestination
import com.hynson.navi.NavManager
import com.hynson.navi.NaviPath

@NavDestination(pageUrl = NaviPath.DEVICE_FRAG1)
class BlankFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blank2, container, false)
        view.findViewById<Button>(R.id.btn_jump).setOnClickListener {
            /*NavGraphBuilder.get().build("/setting/frag1")
                .navigate()*/
            NavManager.instance.navigate(NaviPath.SETTING_FRAG1)

        }
        return view
    }
}