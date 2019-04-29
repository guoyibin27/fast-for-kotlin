/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.aspect

import io.renren.common.exception.RRException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * Redis切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Configuration
 class RedisAspect {
    private val logger by lazy { LoggerFactory.getLogger(this::class.java) }
    //是否开启redis缓存  true开启   false关闭
    @Value("\${spring.redis.open: false}")
    private val open: Boolean = false

    @Around("execution(* io.renren.common.utils.RedisUtils.*(..))")
    @Throws(Throwable::class)
    fun around(point: ProceedingJoinPoint): Any? {
        var result: Any? = null
        if (open) {
            try {
                result = point.proceed()
            } catch (e: Exception) {
                logger.error("redis error", e)
                throw RRException("Redis服务异常")
            }

        }
        return result
    }
}
