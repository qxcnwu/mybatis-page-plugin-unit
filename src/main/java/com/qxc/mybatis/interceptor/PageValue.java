package com.qxc.mybatis.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author qxc
 * @Date 2023 2023/8/5 14:33
 * @Version 1.0
 * @PACKAGE com.qxc.mybatis.interceptor
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PageValue {
    int value() default 1;
    PageType type() default PageType.PAGE;
}
