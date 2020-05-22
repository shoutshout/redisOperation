package com.example.redis.cache.redisson;

import lombok.Data;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 *<P>
 *  RedisLock
 *<P>
 *
 *@author hucj
 *@date 2020-05-20
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {


    String description()  default "";
}
