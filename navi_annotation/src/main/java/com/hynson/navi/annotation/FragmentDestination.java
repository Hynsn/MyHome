package com.hynson.navi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface FragmentDestination {
    /**
     * @return 页面路径
     */
    String pageUrl();

    /**
     *
     * @return 是否需要登录
     */
    boolean needLogin() default false;

    /**
     * @return 是否是启动页
     */
    boolean asStarter() default false;
}

