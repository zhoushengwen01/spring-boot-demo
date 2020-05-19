package com.example.utils.AOP;

import java.lang.annotation.*;

/**
 * 自定义 同步缓存注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {
}
