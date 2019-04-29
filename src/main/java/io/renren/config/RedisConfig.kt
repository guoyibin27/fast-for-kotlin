/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.*
import org.springframework.data.redis.serializer.StringRedisSerializer

/**
 * Redis配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
 class RedisConfig {
    @Autowired
    lateinit var factory: RedisConnectionFactory

    @Bean
     fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.connectionFactory = factory
        return redisTemplate
    }

    @Bean
     fun hashOperations(redisTemplate: RedisTemplate<String, Any>): HashOperations<String, String, Any> {
        return redisTemplate.opsForHash<String, Any>()
    }

    @Bean
     fun valueOperations(redisTemplate: RedisTemplate<String, String>): ValueOperations<String, String> {
        return redisTemplate.opsForValue()
    }

    @Bean
     fun listOperations(redisTemplate: RedisTemplate<String, Any>): ListOperations<String, Any> {
        return redisTemplate.opsForList()
    }

    @Bean
     fun setOperations(redisTemplate: RedisTemplate<String, Any>): SetOperations<String, Any> {
        return redisTemplate.opsForSet()
    }

    @Bean
     fun zSetOperations(redisTemplate: RedisTemplate<String, Any>): ZSetOperations<String, Any> {
        return redisTemplate.opsForZSet()
    }
}
