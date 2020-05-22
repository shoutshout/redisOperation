package com.example.redis.cache.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * StopFormRepeatSubmitAnnotation
 * <p>
 *
 * @author hucj
 * @date 2020-05-21 15:49:27
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StopFormRepeatSubmitAnnotation {
    // 描述
    String value();
}
