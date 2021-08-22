package com.hynson.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.SparseArrayCompat
import androidx.collection.forEach
import androidx.navigation.*

abstract class BaseActivity : AppCompatActivity(){
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutId)

        val control = Navigation.findNavController(this,hostId)
//        var navGraph = NavGraphNavigato
        control.setGraph(graph)
        val mNodes = control.graph.javaClass.getDeclaredField("mNodes")
        mNodes.isAccessible = true
        val nodes:SparseArrayCompat<NavDestination> = mNodes.get(control.graph) as SparseArrayCompat<NavDestination>
        if(nodes.containsKey(oldId)){
            nodes.put(naviId,nodes.get(oldId))
            nodes.remove(oldId)
        }
        nodes.forEach { key, value ->
            Log.i("TAG", " ${oldId} ==?nodes:$key --- $value")
        }
        Log.i("TAG", "onCreate:${naviId} ")
        Log.i("TAG", "find1:${control.graph.findNode(naviId)} find2:${control.graph.findNode(oldId)} ")
        control.navigate(naviId)
    }

//    protected abstract fun navigate(id:Int)
    abstract val hostId: Int
    abstract val layoutId: Int
    abstract val graph: Int
    var naviId:Int = 0
    abstract var oldId: Int
}