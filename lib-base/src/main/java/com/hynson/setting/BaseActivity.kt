package com.hynson.setting

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hynson.base.R

abstract class BaseActivity : FragmentActivity() {

    var rvSettings:RecyclerView? = null

    var list:MutableList<Item> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_setting)

        // view可能公用，事件类型可能不一样
        // 需要绑定view和事件类型，多对多的关系

        rvSettings = findViewById(R.id.rv_settings)

        rvSettings?.apply {
            adapter = SettingAdapter(list,context
            ) { Log.i("++++", "-------") }
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        }
    }

    fun initViewHolder(){
        
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        list.clear()
        notifySettingView()
        rvSettings?.adapter?.notifyDataSetChanged()
    }

    abstract fun notifySettingView()
}