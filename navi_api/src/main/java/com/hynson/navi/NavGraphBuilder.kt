package com.hynson.navi

import android.content.ComponentName
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.NavGraphNavigator
import androidx.navigation.NavGraph

import androidx.navigation.NavController
import java.io.Serializable

class NavGraphBuilder {
    companion object{
        var sNavController: NavController? = null
        private val sBuilder = NavGraphBuilder()
        fun get(controller: NavController) : NavGraphBuilder {
            init(controller)
            return sBuilder
        }
        fun get(): NavGraphBuilder {
            return sBuilder
        }

        private fun init(controller: NavController){
            sNavController = controller
            val provider = controller.navigatorProvider
            val navGraph = NavGraph(NavGraphNavigator(provider))
            val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
            val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)
            val destConfig = NavConfig.getDestinationMap()
            val fragStart:FragmentNavigator.Destination = getStartDest(fragmentNavigator)
            navGraph.addDestination(fragStart)
            for (value in destConfig.values) {
                if (value.isFragment) {
                    val destination = fragmentNavigator.createDestination()
                    destination.id = value.id
                    destination.className = value.className
                    destination.addDeepLink(value.pageUrl)
                    navGraph.addDestination(destination)
                } else {
                    val destination = activityNavigator.createDestination()
                    destination.id = value.id
                    destination.setComponentName(ComponentName(AppUtil.getApp().packageName, value.className))
                    destination.addDeepLink(value.pageUrl)
                    navGraph.addDestination(destination)
                }
            }
            navGraph.startDestination = fragStart.id
            controller.graph = navGraph
        }
        private fun getStartDest(navigator:FragmentNavigator): FragmentNavigator.Destination {
            val fragDest = navigator.createDestination()
            fragDest.id = R.id.empaty_activity
            fragDest.className = EmptyFrag::class.qualifiedName.toString()
            return fragDest
        }
    }

    fun build(toWhere: String) : Builder {
        val bundle = Bundle()
        return Builder(toWhere, bundle)
    }

    class Builder(private val toWhere: String,private val bundle: Bundle){
        fun withString(key:String,value:String): Builder {
            bundle.putString(key, value)
            return this
        }

        fun withInt(key:String,value:Int): Builder {
            bundle.putInt(key, value)
            return this
        }

        fun withLong(key:String,value:Long): Builder {
            bundle.putLong(key, value)
            return this
        }

        fun withDouble(key:String,value:Double): Builder {
            bundle.putDouble(key, value)
            return this
        }

        fun withBoolean(key:String,value:Boolean): Builder {
            bundle.putBoolean(key, value)
            return this
        }

        fun withByte(key:String,value:Byte): Builder {
            bundle.putByte(key, value)
            return this
        }

        fun withSerializable(key:String,value: Serializable): Builder {
            bundle.putSerializable(key, value)
            return this
        }

        fun withParcelable(key:String,value: Parcelable): Builder {
            bundle.putParcelable(key, value)
            return this
        }

        private fun getItemId(pageUrl: String): Int {
            val destination = NavConfig.getDestinationMap()[pageUrl]
            return destination?.id ?: R.id.empaty_activity
        }

        fun navigate(){
            sNavController?.navigate(getItemId(toWhere),bundle)
        }
    }
}