package com.hynson.navi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.blankj.utilcode.util.ActivityUtils

class NaviActivity :FragmentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {

        ++createCounter
        Log.i(TAG,"onCreate1")
        super.onCreate(savedInstanceState)
        Log.i(TAG,"onCreate2")
        setContentView(R.layout.navi_main)

        var target:String? = null
        if(intent.extras != null){
            intent.extras?.run{
                target = getString(NAVI_TARGET)
                Log.i(TAG,"target$target")
            }
        }
        else {
            Log.i(TAG,"target null")
        }
        lifecycle.addObserver(NaviLifecycle(lifecycle,this,findNavController(R.id.navi_main_fragment)))

    }

    fun navigate(id: String){
        val navController = findNavController(R.id.navi_main_fragment)
        Log.i(TAG,"navigate2: ${System.identityHashCode(this)},${System.identityHashCode(navController)}")
        NavManager.instance.handleGraph(id,this,navController)
    }

    companion object{

        val TAG = NaviActivity::class.java.simpleName

        const val NAVI_TARGET = "NAVI_TARGET"
        const val NAVI_BUNDLE = "NAVI_BUNDLE"

        var launchCounter = 0

        var createCounter = 0

        fun launch(target: String,bundle: Bundle? = null){
            launchCounter++
            val context = ActivityUtils.getTopActivity();
            val intent: Intent = Intent(context,NaviActivity::class.java)
            intent.putExtra(NAVI_TARGET,target)
            intent.putExtra(NAVI_BUNDLE,bundle)
            context.startActivity(intent)
            Log.i(TAG,"launchCounter: $launchCounter")
//            ActivityUtils.startActivity(context,intent)
        }
    }
}