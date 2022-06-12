### Jetpack使用Navigation组件化
组件化的步骤
1. 工程配置：
gradle.properties增加isRunModule = true。
   配置各组件依赖方式apply plugin: rootProject.ext.module_type
   配置各组件不通类型的清单文件。
   配置主工程依赖各组件lib
2. 主工程配置路由图
写跳转到组件Activity业务代码
   

基于Navigation开发自己的路由框架技术难点及思路记录
1.如何使用注解处理器自动生成graph路由文件？
路由文件格式，需要参考navigation graph，且要精简不是很重要的参数不生成了。
fragment id使用path="/setting/login"生成对应的hash值是否可以当做resId使用。经过测试验证，StringHashcode不能减少冲突，会采用hashmap存储(path,resId)
    
    <?xml version="1.0" encoding="utf-8"?>
    <navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    app:startDestination="@id/blankFragment">
    <fragment
    android:id="@+id/blankFragment"
    android:name="com.hynson.setting.BlankFragment" />
    </navigation>

2.注解处理器生成的xml存放在那个位置方便打包编译
3.需要实现注解器的业务
activity类配置为graph group -> 对应路由文件，只允许有一个主要的，如果不是主要的需要配置为
activity节点

fragment类配置为destination -> fragment节点
4.注解处理器能在navigation目录下自动生成xml文件？
生成的xml文件id、xml节点内部的resid会不会和主工程资源Id冲突
xml很不灵活。

需求
假定activity必须注册清单文件
- activity跳转activity
- activity跳转fragment：目标fragment存在当前activity中；目标fragment存在另外一个Activity中；目标fragment未放在activity中
- fragment跳转fragment：存在同一个activity中；存在不同activity中
- fragment跳转activity：存在同一个activity中；存在不同activity中

实现效果：
最终调用存在路由配置文件和跳转api调用

2022/06/12
路由框架思路重新整理
- 方案1 所有路由配置合成一个路由表，Application初始化时初始化，然后在NaviActivity中配置。
- 方案2 参照navigation现在使用逻辑，将路由资源文件使用apt替代。不同组件间跳转仅支持activity，由activity分管不同fragment、activity跳转，一个模块注解处理器会生成group类包含该模块不同的group（是指activity），group通过哈希表指定子路由表。需要支持不同组件相同group合并的情况，进而支持fragment跳转。