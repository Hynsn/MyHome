package com.hynson.myhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.hynson.navi.annotation.NavDestination
import com.hynson.navi.NavManager
import com.hynson.navi.NaviPath

@NavDestination(pageUrl = NaviPath.APP_LAUNCH)
class BlankFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        view.findViewById<Button>(R.id.btn_jump).setOnClickListener {
//            NavManager.instance.navigate(requireContext(),NaviPath.DEVICE_FRAG1)

            NavManager.instance.navigate(requireContext(),NaviPath.DEVICE_HOME)

        }
        return view
    }
}