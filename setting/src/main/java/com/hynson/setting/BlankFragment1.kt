package com.hynson.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.hynson.navi.NavManager
import com.hynson.navi.NaviPath
import com.hynson.navi.annotation.NavDestination

@NavDestination(pageUrl = NaviPath.SETTING_FRAG2)
class BlankFragment1 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.set_fragment_blank2, container, false)
        view.findViewById<Button>(R.id.btn_back).setOnClickListener {
            NavManager.instance.navigate(requireContext(),NaviPath.DEVICE_HOME)
        }
        return view
    }
}