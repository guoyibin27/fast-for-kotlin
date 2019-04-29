/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.common.utils

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.*
import org.springframework.stereotype.Component

import java.util.concurrent.TimeUnit

/**
 * Redis工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
 class RedisUtils {
    companion object {
        /**  默认过期时长，单位：秒  */
        val DEFAULT_EXPIRE = (60 * 60 * 24).toLong()
        /**  不设置过期时长  */
        val NOT_EXPIRE: Long = -1
    }

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, Any>
    @Autowired
    lateinit var valueOperations: ValueOperations<String, String>
    @Autowired
    lateinit var hashOperations: HashOperations<String, String, Any>
    @Autowired
    lateinit var listOperations: ListOperations<String, Any>
    @Autowired
    lateinit var setOperations: SetOperations<String, Any>
    @Autowired
    lateinit var zSetOperations: ZSetOperations<String, Any>


    @JvmOverloads
    fun set(key: String, value: Any, expire: Long = DEFAULT_EXPIRE) {
        valueOperations.set(key, value.toJson())
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS)
        }
    }

    fun <T> get(key: String, clazz: Class<T>, expire: Long): T? {
        val value = valueOperations.get(key)
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS)
        }
        return value?.fromJson(clazz)
    }

    fun <T> get(key: String, clazz: Class<T>): T? {
        return get(key, clazz, NOT_EXPIRE)
    }

    fun get(key: String, expire: Long = NOT_EXPIRE): String? {
        val value = valueOperations.get(key)
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS)
        }
        return value
    }

    fun delete(key: String) {
        redisTemplate.delete(key)
    }
}

inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson<T>(this, T::class.java)
}

fun <T> String.fromJson(clazz: Class<T>): T {
    return Gson().fromJson<T>(this, clazz)
}

fun Any.toJson(): String {
    return if (this is Int || this is Long || this is Float || this is Double || this is Boolean || this is String) {
        return this.toString()
    } else Gson().toJson(this)
}