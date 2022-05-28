package com.hynson.navi

import android.content.ComponentName
import android.content.Context
import android.util.Log
import android.util.LruCache
import android.view.LayoutInflater
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import com.hynson.navi.bean.Destination1

class NavManager(
    val lruCache: LruCache<String, ADestination?>,
    val groupCache: LruCache<String, Group<ADestination>?>
) {

    private var lastController: NavController? = null

    private var navigateCounter = 0

    companion object {
        val TAG = NavManager::class.java.simpleName
        const val GROUP_FILE_NAME = "Navi$\$Group$$"

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            NavManager(
                LruCache<String, ADestination?>(100),
                LruCache<String, Group<ADestination>?>(100)
            )
        }
    }

    private fun getStartDest(navigator: FragmentNavigator): FragmentNavigator.Destination {
        val fragDest = navigator.createDestination()
        fragDest.id = R.id.empaty_activity
        fragDest.className = EmptyFrag::class.qualifiedName.toString()
        return fragDest
    }

    fun navigate(
        target: String,
        context: Context,
        navController: NavController? = null
    ): Object? {
        navigateCounter++

        var path = target
        if (target.isEmpty() || !target.startsWith("/")) {
            throw IllegalArgumentException("不按常理出牌 path乱搞的啊，正确写法：如 /order/Order_MainActivity")
        } else if (target.lastIndexOf("/") == 0) {
            throw IllegalArgumentException("不按常理出牌 path乱搞的啊，正确写法：如 /order/Order_MainActivity")
        }

        val finalGroup = target.substring(1, target.indexOf("/", 1)) // finalGroup = order

        if (finalGroup.isEmpty()) {
            throw IllegalArgumentException("不按常理出牌 path乱搞的啊，正确写法：如 /order/Order_MainActivity")
        }

        var group = finalGroup // 例如：order，personal

        var controller = navController
        try {
            // TODO 第一步 读取路由组Group类文件
            val packageName = javaClass.`package`.name
            val groupClassName = "${packageName}.$GROUP_FILE_NAME$group"
//                "com.hynson.navi" + "." + GROUP_FILE_NAME + group
            // TODO 第一步 读取路由组Group类文件
            var loadGroup: Group<ADestination>? = groupCache.get(group)
            if (null == loadGroup) { // 缓存里面没有东东
                // 加载APT路由组Group类文件 例如：ARouter$$Group$$order
                val aClass = Class.forName(groupClassName)
                // 初始化类文件
                loadGroup = aClass.newInstance() as Group<ADestination>

                // 保存到缓存
                groupCache.put(group, loadGroup)
            }

            if (loadGroup.getGroupMap().isEmpty()) {
                throw java.lang.RuntimeException("路由表Group报废了...") // Group这个类 加载失败
            }
            if (controller == null) {
                val rootView = LayoutInflater.from(context).inflate(R.layout.navi_main, null, true)
                controller = Navigation.findNavController(rootView)
            }
            // TODO 第二步 读取路由Path类文件
            var loadPath: ADestination? = lruCache.get(path)
            if (null == loadPath) { // 缓存里面没有东东 Path
                // 1.invoke loadGroup
                // 2.Map<String, Class<? extends ARouterLoadPath>>

                val clazz: Class<ADestination>? = loadGroup.getGroupMap()[group]
//                    Class.forName(clazzName) as Class<out ADestination>

                // 3.从map里面获取 ARouter$$Path$$personal.class
                loadPath = clazz?.newInstance()

                // 保存到缓存
                lruCache.put(path, loadPath)
            }

            // 跳转
            if (loadPath != null) { // 健壮
                val destMap = loadPath.getDestinationMap()
                if (destMap.isEmpty()) { // pathMap.get("key") == null
                    throw RuntimeException("路由表Path报废了...")
                } else {
                    Log.i(TAG, "group: ${groupCache.size()},path: ${lruCache.size()}")
                    if (lastController == null || lastController != controller) {
                        lastController = controller
                        val provider = controller.navigatorProvider
                        val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
                        val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)
                        val navGraph = NavGraph(NavGraphNavigator(provider))
                        destMap.forEach {
//                        Log.i(TAG,"${it.key} -> ${it.value}")
                            /*val fragStart: FragmentNavigator.Destination = getStartDest(fragmentNavigator)
                            navGraph.addDestination(fragStart)*/
                            val routerBean = it.value
                            if (routerBean.type == Destination1.Type.FRAGMENT) {
                                val destination = fragmentNavigator.createDestination()
                                destination.id = routerBean.id
                                destination.className = routerBean.className
                                destination.addDeepLink(routerBean.pageUrl)
                                navGraph.addDestination(destination)

                            } else {
                                val destination = activityNavigator.createDestination()
                                destination.id = routerBean.id
                                destination.setComponentName(ComponentName(context, routerBean.mClass))
                                destination.addDeepLink(routerBean.pageUrl)
                                navGraph.addDestination(destination)
                            }
                            Log.i(TAG,"navigateCounter:${navigateCounter} 无路由时存放Destination1:graph:${routerBean.pageUrl}.id:${routerBean.id}")
                        }
                        val targetBean: Destination1? = loadPath.getDestinationMap()[path]
                        targetBean?.run {
                            if (navGraph.startDestination == 0) {
                                navGraph.startDestination = id
                            } else {

                            }

                        }
                        if (navGraph.startDestination != 0) {
                            controller.graph = navGraph
                        }
                    }
                    else {
                        val targetBean: Destination1? = loadPath.getDestinationMap()[path]
                        targetBean?.run {
                            if(controller.graph.startDestination != 0){
                                val provider = controller.navigatorProvider
                                val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
                                val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)
                                var seamKey:Destination1? = null
                                destMap.forEach {
                                    val routerBean = it.value
                                    if(routerBean.id != id) {
                                        if (routerBean.type == Destination1.Type.FRAGMENT) {
                                            val destination = fragmentNavigator.createDestination()
                                            destination.id = routerBean.id
                                            destination.className = routerBean.className
                                            destination.addDeepLink(routerBean.pageUrl)
                                            controller.graph.addDestination(destination)
                                        } else {
                                            val destination = activityNavigator.createDestination()
                                            destination.id = routerBean.id
                                            destination.setComponentName(
                                                ComponentName(
                                                    context,
                                                    routerBean.mClass
                                                )
                                            )
                                            destination.addDeepLink(routerBean.pageUrl)
                                            controller.graph.addDestination(destination)
                                        }
                                        Log.i(TAG,"navigateCounter:${navigateCounter} 放前面的的Destination1:graph:${routerBean.pageUrl}.id:${routerBean.id}")
                                    }
                                    else {
                                        seamKey = it.value
                                    }
                                }
                                seamKey?.run {
                                    if (type == Destination1.Type.FRAGMENT) {
                                        val destination = fragmentNavigator.createDestination()
                                        destination.id = id
                                        destination.className = className
                                        destination.addDeepLink(pageUrl)
                                        controller.graph.addDestination(destination)
                                    } else {
                                        val destination = activityNavigator.createDestination()
                                        destination.id = id
                                        destination.setComponentName(
                                            ComponentName(
                                                context,
                                                mClass
                                            )
                                        )
                                        destination.addDeepLink(pageUrl)
                                        controller.graph.addDestination(destination)
                                    }
                                    Log.i(TAG,"navigateCounter:${navigateCounter} 放在最后的Destination1:graph:${pageUrl}.id:${id}")
                                }
                                controller.graph.forEach {
                                    Log.i(TAG,"navigateCounter:${navigateCounter} graph:${it.id}.id:${id}")
                                }
                                controller.navigate(id)
                            }
                        }
                    }

/*                    try {
                        if(lastController?.graph!=null){
                            controller.navigate(id)
                        }
                    }catch (e:IllegalStateException){
                        //e.printStackTrace()
                    }*/

                    Log.i(
                        TAG,
                        "navGraph:${controller.graph.startDestination},controller.graph:${
                            System.identityHashCode(controller.graph)
                        },controller:${System.identityHashCode(controller)},lastController:${System.identityHashCode(lastController)}"
                    )

                }
                // 最后才执行操作
                val routerBean: Destination1? = loadPath.getDestinationMap()[path]
                if (routerBean != null) {
                    Log.i(
                        TAG,
                        "${System.identityHashCode(controller)} path: ${routerBean.pageUrl},id:${routerBean.id}"
                    )
                    /*val provider = controller.navigatorProvider
                    val fragmentNavigator = provider.getNavigator(FragmentNavigator::class.java)
                    val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)
                    val navGraph = NavGraph(NavGraphNavigator(provider))
                    try {
                        if (routerBean.type == Destination1.Type.FRAGMENT) {
                            val destination = fragmentNavigator.createDestination()
                            destination.id = routerBean.id
                            destination.className = routerBean.className
                            destination.addDeepLink(routerBean.pageUrl)

                            updateController(navGraph,controller,destination)
                        } else {
                            val destination = activityNavigator.createDestination()
                            destination.id = routerBean.id
                            destination.setComponentName(ComponentName(context, routerBean.mClass))
                            destination.addDeepLink(routerBean.pageUrl)

                            updateController(navGraph,controller,destination)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
*/
                } else {
                    throw RuntimeException("找不到路由...")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace();
        }
        return null
    }

//    fun with(String) : Builder {
//        val bundle = Bundle()
//        return Builder(bundle)
//    }
}