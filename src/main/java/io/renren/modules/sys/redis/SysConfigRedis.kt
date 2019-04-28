/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.redis


import io.renren.common.utils.RedisKeys
import io.renren.common.utils.RedisUtils
import io.renren.modules.sys.entity.SysConfigEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * 系统配置Redis
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
class SysConfigRedis {
    @Autowired
    lateinit var redisUtils: RedisUtils

    fun saveOrUpdate(config: SysConfigEntity?) {
        if (config == null) {
            return
        }
        val key = RedisKeys.getSysConfigKey(config.paramKey!!)
        redisUtils.set(key, config)
    }

    fun delete(configKey: String) {
        val key = RedisKeys.getSysConfigKey(configKey)
        redisUtils.delete(key)
    }

    fun get(configKey: String): SysConfigEntity? {
        val key = RedisKeys.getSysConfigKey(configKey)
        return redisUtils.get(key, SysConfigEntity::class.java)
    }
}
