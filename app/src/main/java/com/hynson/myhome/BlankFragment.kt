package com.hynson.myhome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.hynson.base.NavManager

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
            NavManager.get().build("/device")
                .navigate()
        }
        return view
    }
}