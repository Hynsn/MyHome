package com.hynson.base

import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * author：chs
 * date：2020/3/29
 * des：解析assets目录下的json文件，将解析结果保存到map中
 */
class NavConfig {

    companion object {
        private var sDestinationMap: HashMap<String, Destination> = HashMap()

        fun getDestinationMap(): HashMap<String, Destination> {
            if (sDestinationMap.size == 0) {
                val jsons = parseNavFile()
                for (json in jsons){
                    val destination: HashMap<String, Destination> = GsonUtil.fromJson(json,
                        object : TypeToken<HashMap<String, Destination>>(){}.type)
                    sDestinationMap.putAll(destination)
                }
            }
            return sDestinationMap
        }

        /**
         * 解析assets中特定文件
         */
        private fun parseFile(s: String): String {
            val assets = AppUtil.getApp().resources.assets
            val open = assets.open(s)
            val stringBuilder = StringBuilder()
            val bufferedReader = BufferedReader(InputStreamReader(open))
            bufferedReader.use {
                var line: String?
                while (true) {
                    line = it.readLine() ?: break
                    stringBuilder.append(line)
                }
            }
            return stringBuilder.toString()
        }

        /**
         * 解析assets下的所有的导航相关的文件
         */
        private fun parseNavFile():List<String>{
            val jsons = mutableListOf<String>()
            val assets = AppUtil.getApp().resources.assets
            val list = assets.list("");
            if (list != null) {
                for (item in list){
                    if(item.contains("_nav")){
                        jsons.add(parseFile(item))
                    }
                }
            }
            return jsons
        }
    }

}