### Jetpack使用Navigation组件化
组件化的步骤
1. 工程配置：
gradle.properties增加isRunModule = true。
   配置各组件依赖方式apply plugin: rootProject.ext.module_type
   配置各组件不通类型的清单文件。
   配置主工程依赖各组件lib
2. 主工程配置路由图
写跳转到组件Activity业务代码
   