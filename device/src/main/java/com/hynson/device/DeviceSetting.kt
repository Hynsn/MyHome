package com.hynson.device

import com.hynson.setting.BaseActivity
import com.hynson.setting.Item
import com.hynson.setting.ViewHolder

class DeviceSetting : BaseActivity() {

    // 两种维护需求
    // 1.设置页配置化，如下
    // 2.方便新增不同样式的Item(ViewHolder)

    override fun notifySettingView() {
        list.add(Item("测试1",R.layout.setting_item_type1,1))
        list.add(Item("测试2",R.layout.setting_item_type2,2))
        list.add(Item("测试2",R.layout.setting_item_type2,3))
        list.add(Item("测试2",R.layout.setting_item_type2,2))
        list.add(Item("测试2",R.layout.setting_item_type2,2))

//        val holder = ViewHolder(R.layout.setting_item_type1,Item("测试2",R.layout.setting_item_type2,2))
//            .bindView()
    }
}