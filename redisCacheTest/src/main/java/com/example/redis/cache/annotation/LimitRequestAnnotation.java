package com.example.redis.cache.annotation;

import com.example.redis.cache.enums.LimitType;

import java.lang.annotation.*;

/**
 * @author ASUS
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitRequestAnnotation {

    /**
     * 描述
     *
     * @return {String}
     */
    String value();

    /**
     * 异常信息
     *
     * @return {String}
     */
    String exceptionMsg();

    /**
     * 资源的名字
     *
     * @return String
     */
    String name() default "";

    /**
     * 资源的key
     *
     * @return String
     */
    String key() default "";

    /**
     * Key的prefix
     *
     * @return String
     */
    String prefix() default "";

    /**
     * 给定的时间段
     * 单位秒
     *
     * @return int
     */
    int period();

    /**
     * 最多的访问限制次数
     *
     * @return int
     */
    int count();

    /**
     * 类型
     *
     * @return LimitType
     */
    LimitType limitType() default LimitType.CUSTOMER;
}
