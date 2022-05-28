package com.hynson.navi

import android.content.ComponentName
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator
import java.io.Serializable

class NavGraphBuilder {
    companion object{

        val TAG = NavGraphBuilder::class.simpleName

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

}