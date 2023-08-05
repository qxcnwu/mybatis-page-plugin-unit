package com.qxc.mybatis.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author qxc
 * @Date 2023 2023/8/5 14:28
 * @Version 1.0
 * @PACKAGE com.qxc.mybatis.interceptor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageSplit {
    // 第几页
    int page() default 1;

    // 每一页默认几条数据
    int count() default 10;
}
