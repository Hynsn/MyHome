package com.hynson.device

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.hynson.navi.NavGraphBuilder
import com.hynson.navi.FragmentDestination

@FragmentDestination(pageUrl = "/device/frag1", asStarter = false)
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
            NavGraphBuilder.get().build("/setting/frag1")
                .navigate()
        }
        return view
    }
}