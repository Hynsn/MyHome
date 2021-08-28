package com.hynson.myhome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hynson.navi.NavGraphBuilder
import com.hynson.navi.FragmentDestination

@FragmentDestination(pageUrl = "ABCDEa123abc", asStarter = true)
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
            /*val bundle = Bundle()
            bundle.putString("test","测试使用")
            Navigation.findNavController(it)
                .navigate(R.id.settingActivity,bundle)*/
            com.hynson.navi.NavGraphBuilder.get().build("/setting/frag2")
                .navigate()
        }
        return view
    }
}